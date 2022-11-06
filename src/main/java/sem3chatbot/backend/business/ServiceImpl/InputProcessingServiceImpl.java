package sem3chatbot.backend.business.ServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sem3chatbot.backend.business.InputProcessingService;
import sem3chatbot.backend.domain.UserInput;
import sem3chatbot.backend.domain.answers.Answer;
import sem3chatbot.backend.domain.keywords.Keyword;
import sem3chatbot.backend.persistence.AnswerRepository;
import sem3chatbot.backend.persistence.KeywordRepository;
import sem3chatbot.backend.persistence.SolutionRepository;
import sem3chatbot.backend.persistence.entity.KeywordEntity;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Service
public class InputProcessingServiceImpl implements InputProcessingService {

    private KeywordRepository keywordRepository;
    private AnswerRepository answerRepository;
    private SolutionRepository solutionRepository;

    public HashMap<String, List<Long>> findMatches(UserInput request){
        //Long -> Long[]
        HashMap<String, List<Long>> matches = new HashMap<>();
        String question = request.getQuestion().toLowerCase();
        List<KeywordEntity> keywordEntities = keywordRepository.findAll();
        //HashMap<String, long[]> keywords = keywordRepository.findByType("type");
        //hashmap pulls the arrays from the keywords table, keys = types of keywords
        //add new words to db when a user types a question
        List<Keyword> keywords = keywordEntities.stream().map(KeywordConverter::convert).toList();
        keywordEntities.clear();
        List<Long> questionIds = groupKeywords(keywords, "question", question);
        List<Long> topicIds = groupKeywords(keywords, "topic", question);
        List<Long> auxiliaryIds = groupKeywords(keywords, "auxiliary", question);

        matches.put("question", questionIds);
        matches.put("topic", topicIds);
        matches.put("auxiliary", auxiliaryIds);

       return matches;
    }


    // NOTE: if the topic only has 1 keyword, then the response should also be handled differently.
    // either separate behaviour or the database should be more taught about
    public String findAnswer(HashMap<String, List<Long>> matchedKeywords) {
        // The response of the chatbot
        String resultAnswer = "No answer found. Try to rewrite your question.";

        if(matchedKeywords.get("question").size() == 0 || matchedKeywords.get("topic").size() == 0)
            return resultAnswer;

        // The higher the weight of a potential answer, the better it should be as a response for the given question.
        double currentWeight = 0;

        List<Answer> answers = answerRepository.findAll().stream().map(AnswerConverter::convert).toList();

        for(Answer answer: answers){
            double newWeight = CalculateTotalWeight(matchedKeywords, answer);

            // Right now this doesn't take into account identical weights, should be addressed later
            if(currentWeight < newWeight){
                resultAnswer = "You can find a solution to your question here:" + answer.getSolution().getText();
                currentWeight = newWeight;

                if(currentWeight == 10){
                    return resultAnswer;
                }
            }
        }

        return resultAnswer;
    }

    private List<Long> ConvertFromStringToList(String keywordsById){
        return Stream.of(keywordsById.split(","))
                .map(String::trim)
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

    // Retrieve the weight(= importance) of an answer by type.
    // The retrieved ids will be compared to the keywords present in the inputQuestion
    //
    // The return will be used in a calculation to determine the best possible answer that is present
    private double findWeightByType(List<Long> matchedKeywordsIdByType, String keywordsById){
        //
        List<Long> convertedIdList = ConvertFromStringToList(keywordsById);

        // The amount of keywords that matched by the given type
        double amountMatchedByType = convertedIdList.stream().filter(id ->matchedKeywordsIdByType.contains(id)).collect(Collectors.toList()).size();

        if(amountMatchedByType == 0)
            return 0;

        // The percentage of: amount of matched keywords/total keywords in answer of this type
        double pctMatchedInDB = amountMatchedByType/convertedIdList.size();

        // The percentage of: amount matched keywords/ total keywords from the initial question of this type
        double pctMatchedInQuestion = amountMatchedByType/matchedKeywordsIdByType.size();

        return pctMatchedInDB + pctMatchedInQuestion;
    }

    private double CalculateTotalWeight(HashMap<String, List<Long>> matchedKeywords, Answer answer){
        double resultedWeight = 0;

        // Question keyword has to contain at least one match, else the answer is not suitable.
        // This variable will be multiplied by the calculated weight.
        double questionWeight = findWeightByType(matchedKeywords.get("question"), answer.getQuestionsKeywords());

        if(questionWeight != 0){
            double auxiliaryWeight = findWeightByType(matchedKeywords.get("auxiliary"), answer.getSecondaryKeywords());
            double topicWeight = findWeightByType(matchedKeywords.get("topic"), answer.getTertiaryKeywords());

            // The topic should weigh more to find the best answer for a question. The multiplication makes sure it has more impact than the auxiliary weight.
            resultedWeight = auxiliaryWeight + (topicWeight * 4);
        }

        // The max weight is a 10:  1 * (2 + (4 * 2))
        // The lowest weight would be a 0.
        return resultedWeight;
    }

    private List<Long> groupKeywords(List<Keyword> keywords, String type, String question){
        List<Keyword> initial = new ArrayList<>();
        for (int i = 0; i < keywords.size(); i++) {
            if (question.contains(keywords.get(i).getText()) && keywords.get(i).getType().equals(type)) {
                initial.add(keywords.get(i));
            }
        }

        // Maybe adding it in the for loop might be better?
        List<Long> results = initial.stream()
                .map(Keyword::getId)
                .collect(Collectors.toList());

        return results;

//        long[] results = new long[initial.size()];
//        //im a genius lol
//        int j = 0;
//        for(Keyword k : initial){
//          results[j++] = k.getId();
//        }
//
//        return results;
    }



}
