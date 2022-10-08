package sem3chatbot.backend.business.ServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sem3chatbot.backend.business.ResponseService;
import sem3chatbot.backend.business.exception.NoBlankQuestionsException;
import sem3chatbot.backend.domain.BotResponse;
import sem3chatbot.backend.domain.UserInput;


@Service
@AllArgsConstructor
public class ResponseServiceImpl implements ResponseService {

    public BotResponse processQuestion(UserInput request){
        if(request.getQuestion() == null || request.getQuestion().equals("")){
            throw new NoBlankQuestionsException();
        }
        return BotResponse.builder()
                .response("we chillin")
                .build();
    }
}
