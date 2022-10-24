package sem3chatbot.backend.business.ServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sem3chatbot.backend.business.InputProcessingService;
import sem3chatbot.backend.domain.UserInput;
import sem3chatbot.backend.domain.keywords.Keyword;
import sem3chatbot.backend.persistence.KeywordRepository;
import sem3chatbot.backend.persistence.entity.KeywordEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class InputProcessingServiceImpl implements InputProcessingService {

    private KeywordRepository keywordRepository;

    public HashMap<Long, String> findMatches(UserInput request){
        HashMap<Long, String> matches = new HashMap<>();
        String question = request.getQuestion();
        List<KeywordEntity> keywordEntities = keywordRepository.findAll();

        List<Keyword> keywords = keywordEntities.stream().map(KeywordConverter::convert).toList();
        keywordEntities.clear();
        for (Keyword keyword : keywords) {
            if (question.contains(keyword.getText())) {
                matches.put(keyword.getId(), keyword.getText());
            }
        }
       return matches;
    }

}
