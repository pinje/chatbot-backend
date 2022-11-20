package sem3chatbot.backend.business;

import java.util.Map;
import java.util.Set;

public interface UrlSanitizerService {
    void sanitize(Set<String> trimmedUrls);
    Map<Integer, String> generateSanitizableUrls();
}
