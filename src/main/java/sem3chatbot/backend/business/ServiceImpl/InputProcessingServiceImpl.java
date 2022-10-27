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
import sem3chatbot.backend.persistence.entity.SolutionEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class InputProcessingServiceImpl implements InputProcessingService {

    private KeywordRepository keywordRepository;
    private AnswerRepository answerRepository;
    private SolutionRepository solutionRepository;


    //
    // This method returns now a HashMap of the keywords:
    //      - Key: Type of the keyword
    //      - Value: ID of the keyword
    //
    // The intention is to have three unique types in the return, but as of now
    // there is no prevention of this. This would lead into a lot of problems
    // in getting a sufficient answer. As of now, lets assume that every question
    // contains only three types of keywords and not doubles.
    public HashMap<String, Long> findMatches(UserInput request){
        HashMap<String, Long> matches = new HashMap<>();
        String question = request.getQuestion();
        List<KeywordEntity> keywordEntities = keywordRepository.findAll();

        List<Keyword> keywords = keywordEntities.stream().map(KeywordConverter::convert).toList();
        keywordEntities.clear();
        for (Keyword keyword : keywords) {
            if (question.contains(keyword.getText())) {
                matches.put(keyword.getType(), keyword.getId());
            }
        }
       return matches;
    }

    // Currently in the If statement, placeholder Keys are used.
    // This should be corrected using the right types in the DB.
    public String findAnswer(HashMap<String, Long> matchedKeywords){
        List<Answer> answers = answerRepository.findAll().stream().map(AnswerConverter::convert).toList();


        throw new SolutionNotFoundException();
    }

}
