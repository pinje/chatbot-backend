package sem3chatbot.backend.business;


import java.util.Set;

public interface UrlSanitizerService {
   /**
    Finds all links located in the search results of the page.
    @param html The raw html of the document being searched
    @return A set containing all found links, an empty set if none are found
    */
   void sanitizeUrls(Set<String> urls);
}
