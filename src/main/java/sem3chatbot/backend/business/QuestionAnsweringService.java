package sem3chatbot.backend.business;

import sem3chatbot.backend.domain.BotResponse;
import sem3chatbot.backend.domain.UserInput;

public interface QuestionAnsweringService {

    BotResponse processUserInput(UserInput request);
}
