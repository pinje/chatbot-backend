package sem3chatbot.backend.business.ServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sem3chatbot.backend.business.InputProcessingService;
import sem3chatbot.backend.business.exception.SolutionNotFoundException;
import sem3chatbot.backend.domain.UserInput;
import sem3chatbot.backend.domain.answers.Answer;
import sem3chatbot.backend.domain.answers.Solution;
import sem3chatbot.backend.domain.keywords.Keyword;
import sem3chatbot.backend.persistence.AnswerRepository;
import sem3chatbot.backend.persistence.KeywordRepository;
import sem3chatbot.backend.persistence.SolutionRepository;
import sem3chatbot.backend.persistence.entity.KeywordEntity;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class InputProcessingServiceImpl implements InputProcessingService {

    private KeywordRepository keywordRepository;
    private AnswerRepository answerRepository;
    private SolutionRepository solutionRepository;

    public HashMap<String, long[]> findMatches(UserInput request){
        //Long -> Long[]
        HashMap<String, long[]> matches = new HashMap<>();
        String question = request.getQuestion().toLowerCase();
        List<KeywordEntity> keywordEntities = keywordRepository.findAll();

        List<Keyword> keywords = keywordEntities.stream().map(KeywordConverter::convert).toList();
        keywordEntities.clear();
        long[] questionIds = groupKeywords(keywords, "question", question);
        long[] topicIds = groupKeywords(keywords, "topic", question);
        long[] auxiliaryIds = groupKeywords(keywords, "auxiliary", question);

        matches.put("question", questionIds);
        matches.put("topic", topicIds);
        matches.put("auxiliary", auxiliaryIds);

       return matches;
    }

    public String findAnswer(HashMap<String, long[]> matchedKeywords) {
        List<Answer> answers = answerRepository.findAll().stream().map(AnswerConverter::convert).toList();

        System.out.println(Arrays.toString(matchedKeywords.get("question")));
        System.out.println(Arrays.toString(matchedKeywords.get("auxiliary")));
        System.out.println(Arrays.toString(matchedKeywords.get("topic")));
//        for(Answer answer: answers){
        // Checking if the combination of the keywords in the question
//            if(
//                    answer.getQuestionsKeyword().getId().equals(matchedKeywords.get("question"))
//                            &&
//                            answer.getSecondaryKeyword().getId().equals(matchedKeywords.get("auxiliary"))
//                            &&
//                            answer.getTertiaryKeyword().getId().equals(matchedKeywords.get("topic"))
//            ){
//                // Retrieving the right solution and returning this.
//                Solution solution = SolutionConverter.convert(solutionRepository.getReferenceById(answer.getSolution().getId()));
//                return "You can find an answer for your question on " + solution.getText();
//            }
   // }
        return null;
    }

    private long[] groupKeywords(List<Keyword> keywords, String type, String question){
        List<Keyword> initial = new ArrayList<>();
        for (int i = 0; i < keywords.size(); i++) {
            if (question.contains(keywords.get(i).getText()) && keywords.get(i).getType().equals(type)) {
                initial.add(keywords.get(i));
            }
        }
        long[] results = new long[initial.size()];
        //im a genius lol
        int j = 0;
        for(Keyword k : initial){
          results[j++] = k.getId();
        }
        return results;
    }

}
