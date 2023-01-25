package sem3chatbot.backend.business.ServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sem3chatbot.backend.business.QuestionAnsweringService;
import sem3chatbot.backend.domain.BotResponse;
import sem3chatbot.backend.domain.UserInput;
import sem3chatbot.backend.domain.answers.Solution;
import sem3chatbot.backend.persistence.*;
import sem3chatbot.backend.persistence.entity.*;

import javax.swing.text.html.parser.Entity;
import java.awt.*;
import java.security.Key;
import java.sql.Array;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class QuestionAnsweringServiceImpl  implements QuestionAnsweringService {

    private KeywordRepository keywordRepository;
    private CategoryRepository categoryRepository;
    private QuestionRepository questionRepository;
    private SolutionRepository solutionRepository;

    // Threshold for confidence scores.
    // A score has to be at least higher than this to be accounted.
    private final double threshold = 0.66;
    private final Integer amountOfTopPossibleAnswers_toSend = 3;

//    public List<SolutionEntity> ProcessUserInput(UserInput request)
    public BotResponse processUserInput(UserInput request){

        // All the keywords in the question
        List<KeywordEntity> kw_UserInput = getKeywords_inQuestion(request.getQuestion());

        List<SolutionEntity> possible_solutions = new ArrayList<>();
        if(request.getCategory() != null){
            possible_solutions = findAnswer_FilterCategory(kw_UserInput, request.getCategory());
        }
        else{
            possible_solutions = findAnswer_NoFilter(kw_UserInput);
        }

        return getAnswer_FromSolution(possible_solutions);
    }

    private BotResponse getAnswer_FromSolution(List<SolutionEntity> solutions){

        // Default answer (hard coded, but should be in DB)
         BotResponse response = BotResponse.builder().answer("Sorry, I couldn't find an answer to your question. Maybe try again asking it differently.")
                .answerDutch("Sorry, ik kon geen antwoord vinden op je vraag. Probeer het nog eens anders te vragen.").build();

        // 0 solutions means default message should be sent.
        if(solutions.size() > 0){
            response.setAnswer(solutions.get(0).getAnswer());
            response.setAnswerDutch(solutions.get(0).getDutchAnswer()!=null? solutions.get(0).getDutchAnswer() :" ");
        }
        return response;
    }

    private List<SolutionEntity> findAnswer_FilterCategory(List<KeywordEntity> kw_UserInput, String categoryInput){

        // Retrieve category based on suggested category by user
        CategoryEntity category = getCategoryByInput(categoryInput);

        // If no category found, process question without filter
        if(category != null){
            //Retrieve the matched questions and their respective solution to the keywords of the input
            List<QuestionEntity> categoryQuestions = getQuestions_ByCategory(category.getId());

            // All solutions related to the category
            List<SolutionEntity> possibleSolutions = findSolution(categoryQuestions, kw_UserInput);

            // Check for a found solution
            if(possibleSolutions.size() > 0){
                // Found solutions
                return possibleSolutions;
            }
        }

        // No category or no solution found for the category --> try to find solution by comparing with every question
        return findAnswer_NoFilter(kw_UserInput);

    }

    // Try to find matching solutions with every question as a comparison.
    // This method is used when there is no category given/found or no solution found within a category
    private List<SolutionEntity> findAnswer_NoFilter(List<KeywordEntity> kw_UserInput){
        List<QuestionEntity> allQuestions = getQuestions_All();

        return findSolution(allQuestions, kw_UserInput);
    }

    private List<SolutionEntity> findSolution(List<QuestionEntity> questions, List<KeywordEntity> kw_userInput){

        // Sentence Topic Keyword: List of the ids of every keyword in the user input
        List<Long> STKw = convertTo_ListOf_keywordIds(kw_userInput);

        // Get all the scores for the given questions
        List<double[]> final_scores = getQuestionScores(questions, STKw);

        // The parameter that will be returned
        List<SolutionEntity> possible_solutions = new ArrayList<>();

        for(double[] matchedQuestion: final_scores){
            // Get the right solution that is linked to the question
            Optional<SolutionEntity> solution = solutionRepository.findById((long) matchedQuestion[2]);

            // Add solution to the list
            solution.ifPresent(possible_solutions::add);
        }

        // All matched solutions
        return possible_solutions;
    }

    private List<double[]> getQuestionScores(List<QuestionEntity> questions, List<Long> STKw){
        // Return parameter: list with all scores
        List<double[]> final_scores = new ArrayList<>();
        List<List<Double>> test = new ArrayList<>();

        // Calculate and add the scores of the questions for the given question
        for(QuestionEntity q: questions){

            double confidenceScore = getConfidenceScore(q, STKw);

            // A certain threshold needs to be met or exceeded to be considered a 'sufficient' answer
            if(confidenceScore >= threshold){
                double[] question_with_score = new double[]{confidenceScore, q.getId(), q.getSolution()};
                final_scores.add(question_with_score);

                // Perfect answer found --> stop loop
                if(confidenceScore == 1){
                    break;
                }
            }
        }

        // sort them in ascending and then revert the order
        final_scores.sort(Comparator.comparing(doubles -> doubles[0]));
        Collections.reverse(final_scores);

//         Return a list with a max of 3 scores sorted by highest to lowest
        return final_scores.stream()
                .limit(amountOfTopPossibleAnswers_toSend)
                .collect(Collectors.toList());
    }

    private double getConfidenceScore(QuestionEntity question, List<Long> STKw){
        // List of ids of the keywords from the question in the DB
        List<Long> QTKw = convertFromStringToList(question.getTopicKw()); // Question Topic Keywords

        // The amount of keywords that match keywords in the question DB
        double amountMatchedKW = QTKw.stream().filter(STKw::contains).toList().size();

        // The percentage of: matched KW / Total KW in DB Question
        double QTKw_score = amountMatchedKW/QTKw.size();

        // The percentage of: matched KW / Total KW in sentence (User input)
        double STKw_score = amountMatchedKW/STKw.size();

        // Keyword score: between 0-1.
        // 1: perfect score
        // 0: No single match
        double kw_score = (QTKw_score + STKw_score)/2;

        // If it's a perfect match, then there is no need to have correction scores
        if(kw_score == 1)
            return 1;

        // No single match
        if(kw_score == 0)
            return 0;

        // Corrections for the scores
        // Total KW in Sentence <= Total KW in DB Question
        double final_score = 0;
        if(STKw.size() > QTKw.size()){
            final_score = kw_score * addCorrectionsToScore(STKw_score, STKw.size());
        }
        else{
            final_score = kw_score * addCorrectionsToScore(QTKw_score, QTKw.size());
        }

        return final_score;
    }

    private double addCorrectionsToScore(double score, double size){
        return score + (1/size);
    }

    private List<Long> convertFromStringToList(String keywordsById){
        return Stream.of(keywordsById.split(","))
                .map(String::trim)
                .map(Long::valueOf)
                .collect(Collectors.toList());

//        List<String> test = Stream.of(keywordsById.split(",")).collect(Collectors.toList());
//
//        return Stream.of(keywordsById.split(",")).map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
    }

    private List<Long> convertTo_ListOf_keywordIds(List<KeywordEntity> keywords){
        return keywords.stream()
                .map(KeywordEntity::getId)
                .collect(Collectors.toList());
    }

    //////////// MAYBE? ////////////////////
    // Maybe when asking the user if the current page is related to the question, you can still ask for a category just in case?

//    public SolutionEntity ProcessQuestion_BySource(UserInput request){
//
//        // Try to find category using the URL
//        CategoryEntity category = GetCategoryBySource(request.getCurrentPage());
//
//        SolutionEntity solution = null;
//
//        if(category == null){
//            // OPTIONS:
//            // 1) Compare the question to every question in the DB
//            // 2) Say that the related page does not have any shizzle and ask for a category
//            solution = ProcessQuestion_ByCategory(request);
//        }
//        else{
//            // Use the category questions to compare
//            solution = findSolution_ByCategory(category, request);
//        }
//
//        return solution;
//    }

//    private CategoryEntity GetCategoryBySource(String source){
//        CategoryEntity category = categoryRepository.findBySource(source);
//        return category;
//    }

    private List<KeywordEntity> getKeywords_inQuestion(String question){

        // Splitting the question
//        List<String> keywords = List.of(question.split("\\s|,|."));
        List<String> keywords = Collections.list(new StringTokenizer(question.toLowerCase(), " |,|.|?")).stream().map(token -> (String) token).toList();


        // Found KW in the question. Will be returned
        List<KeywordEntity> allKW = new ArrayList<>();

        // See if the words match a keyword from the DB
        for(String word: keywords){
            KeywordEntity possibleKeyword = getKw(word);

            // Keyword found true --> add to list
            if(possibleKeyword != null)
                allKW.add(possibleKeyword); // THERE IS NO ACCOUNTING FOR IF YOU GET MULTIPLE MATCHES AND SEE WHICH ONE IS MORE ACCURATE
        }

        return allKW;
    }

    private List<QuestionEntity> getQuestions_ByCategory(Long categoryId){
        return questionRepository.findByCategory(categoryId);
    }

    private List<QuestionEntity> getQuestions_All(){
        return questionRepository.findAll();
    }

    // temp fix
    private CategoryEntity getCategoryByInput(String userInput){

        // Get the matching keyword
        KeywordEntity possibleKeyword = getKw(userInput);

        if(possibleKeyword != null)

            // The found category. Its either filled or empty
            return categoryRepository.findByTopic(possibleKeyword.getId());

        // keyword not found, so no category
        return null;

    }

    // Temp fix for kw retrieval
    private KeywordEntity getKw(String keyword){

        KeywordEntity possibleKeyword = keywordRepository.findByText(keyword);

        if(possibleKeyword != null)
            return possibleKeyword;

        List<KeywordEntity> allKw = keywordRepository.findAll();

        System.out.println(allKw);

        for(KeywordEntity kw: allKw) {
            if (kw.getSynonyms() != null) {
                List<String> synonyms = Collections.list(new StringTokenizer(kw.getSynonyms().toLowerCase(), ",")).stream().map(token -> (String) token).toList();

                if (synonyms.contains(keyword))
                    return kw;
            }
        }

        return null;
    }
}
