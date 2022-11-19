package sem3chatbot.backend.business;

import sem3chatbot.backend.domain.answers.SearchEngineTopThreeResponse;

import java.io.IOException;
import java.util.Set;

public interface SearchEngineService {
    SearchEngineTopThreeResponse getTopLinksFromSearchQuery(String queryString, int limit) throws IOException;
}
