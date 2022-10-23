package sem3chatbot.backend.business.ServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sem3chatbot.backend.business.ResponseService;
import sem3chatbot.backend.business.exception.NoBlankQuestionsException;
import sem3chatbot.backend.domain.BotResponse;
import sem3chatbot.backend.domain.UserInput;
import sem3chatbot.backend.persistence.KeywordRepository;
import sem3chatbot.backend.persistence.entity.KeywordEntity;

import java.util.List;


@Service
@AllArgsConstructor
public class ResponseServiceImpl implements ResponseService {
KeywordRepository keywordRepository;
    public BotResponse processQuestion(UserInput request){
        if(request.getQuestion() == null || request.getQuestion().equals("")){
            throw new NoBlankQuestionsException();
        }
        //check if db con works
        List<KeywordEntity> keywordEntities = keywordRepository.findAll();
        return BotResponse.builder()
                .response(keywordEntities.get(0).getId().toString() + keywordEntities.get(0).getText())
                .build();
    }
}
