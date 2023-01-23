package sem3chatbot.backend.business;


import java.util.Set;

public interface UrlSanitizerService {
   void sanitizeUrls(Set<String> urls);
}
