package sem3chatbot.backend.business;


import sem3chatbot.backend.domain.UserInput;

import java.util.HashMap;

public interface InputProcessingService {
    HashMap<String, long[]> findMatches(UserInput request);

    // matchedKeywords  -> Key: Type of keyword, Value: ID of the keyword
    // The combination of keyword IDs will return the value for a proper response
    //
    String findAnswer(HashMap<String, long[]> matchedKeywords);
}
