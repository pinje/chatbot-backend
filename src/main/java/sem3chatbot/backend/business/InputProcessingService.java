package sem3chatbot.backend.business;


import sem3chatbot.backend.domain.UserInput;

import java.util.HashMap;
import java.util.List;

public interface InputProcessingService {
    HashMap<String, List<Long>> findMatches(UserInput request);
    String findAnswer(HashMap<String, List<Long>> matchedKeywords);
}
