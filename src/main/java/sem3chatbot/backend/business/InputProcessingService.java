package sem3chatbot.backend.business;


import sem3chatbot.backend.domain.UserInput;

import java.util.HashMap;

public interface InputProcessingService {
    HashMap<Long, String> findMatches(UserInput request);
}
