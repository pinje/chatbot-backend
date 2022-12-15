package sem3chatbot.backend.business;

import java.io.IOException;
import java.util.Set;

public interface KeywordGeneratorService {
    /**
     Finds all non-duplicate keywords found in the meta tag of the given URL.
     @param fontysUrl the url to search
     @return a Set containing all found keywords, an empty set if none are found
     @throws IOException if the url is malformed or is not a valid url
     */
    Set<String> scrapeKeywords(final String fontysUrl) throws IOException;
}
