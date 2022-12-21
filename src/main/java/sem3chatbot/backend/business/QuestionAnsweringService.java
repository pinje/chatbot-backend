package sem3chatbot.backend.business;

import sem3chatbot.backend.domain.UserInput;

public interface QuestionAnsweringService {

    String processUserInput(UserInput request);

}
