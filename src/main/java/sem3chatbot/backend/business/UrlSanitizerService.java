package sem3chatbot.backend.business;



public interface UrlSanitizerService {
    boolean isSanitizable(String trimmedUrl);
}
