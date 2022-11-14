package sem3chatbot.backend.business;


import sem3chatbot.backend.domain.UserInput;

import java.util.HashMap;
import java.util.List;

public interface InputProcessingService {
    HashMap<String, List<Long>> findMatches(UserInput request);

    // matchedKeywords  -> Key: Type of keyword, Value: ID of the keyword
    // The combination of keyword IDs will return the value for a proper response
    //
    String findAnswer(HashMap<String, List<Long>> matchedKeywords);
}
