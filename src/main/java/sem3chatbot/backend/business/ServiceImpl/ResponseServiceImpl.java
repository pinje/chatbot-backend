package sem3chatbot.backend.business.ServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sem3chatbot.backend.business.InputProcessingService;
import sem3chatbot.backend.business.ResponseService;
import sem3chatbot.backend.business.exception.NoBlankQuestionsException;
import sem3chatbot.backend.domain.BotResponse;
import sem3chatbot.backend.domain.UserInput;
import sem3chatbot.backend.persistence.KeywordRepository;
import sem3chatbot.backend.persistence.entity.KeywordEntity;

import java.util.HashMap;
import java.util.List;


@Service
@AllArgsConstructor
public class ResponseServiceImpl implements ResponseService {
    InputProcessingService processor;
    public BotResponse processQuestion(UserInput request){
        if(request.getQuestion() == null || request.getQuestion().equals("")){
            throw new NoBlankQuestionsException();
        }
        //currently does not account for phrases(e.g 3rd semester), single words only
        HashMap<Integer, String> matches = processor.findMatches(request);
        return BotResponse.builder()
                .response(matches.values().toString())
                .build();
    }
}
