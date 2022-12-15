package sem3chatbot.backend.business;

import sem3chatbot.backend.domain.answers.SearchEngineTopThreeResponse;

import java.io.IOException;
import java.util.Set;

public interface SearchEngineService {
    /**
     Lists the top 10 links found in a search engine result, ordered by occurrence on the web page
     @param queryString The initial search term that would be appended to the url, e.g. "fontys ict"
     @param limit The number of results to be returned on the page. If the search term is more than 1 word long, limit is automatically set to 10
     @return A response containing all found links in an appropriate format.
     @throws IOException If the queryString contains illegal characters, such as " '', / " or "\\".
     */
    SearchEngineTopThreeResponse getTopLinksFromSearchQuery(String queryString, int limit) throws IOException;
    /**
     Formats the search term as a search string, by injecting the separator used by search engines - '+'.
     @param queryString The url that's being searched
     @return A separator-injected URL that can be passed to the search engine
     */
    String injectSeparator(String queryString);
}
