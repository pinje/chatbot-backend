package sem3chatbot.backend.business.ServiceImpl;

import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import sem3chatbot.backend.business.SearchEngineService;
import sem3chatbot.backend.business.UrlSanitizerService;
import sem3chatbot.backend.domain.answers.SearchEngineTopThreeResponse;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class SearchEngineServiceImpl implements SearchEngineService {
    private final UrlSanitizerService urlSanitizerService;
    @Override
    public SearchEngineTopThreeResponse getTopLinksFromSearchQuery(final String queryString, int limit) throws IOException {
        String queryStringInjected = injectSeparator(queryString);
        //dynamically change the result limit to accommodate longer search queries
        if(queryStringInjected.contains("+")){
            limit = 10;
        }
        Logger.print("Query string format: " + queryStringInjected);
        String searchUrl = "https://bing.com/search?q=" + queryStringInjected + "&num=" + limit;
        Logger.print("Searching..." + searchUrl);
            Document rawHtml = Jsoup.connect(searchUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36")
                    .referrer("http://www.bing.com")
                    .get();

        Set<String> links = findLinks(rawHtml);
        urlSanitizerService.sanitizeUrls(links);
        Logger.print("Returning " + links.size() + " trimmed links");
        return SearchEngineTopThreeResponse.builder()
                .links(links)
                .build();
    }
    @Override
    public String injectSeparator(String queryString){
        return queryString.replace(' ', '+');
    }

    /**
     Finds all links located in the search results of the page.
     @param html The raw html of the document being searched
     @return A set containing all found links, an empty set if none are found
     */
    private Set<String> findLinks(Document html) {
        //probably better if we do .select(li[class^=b_algo]), but this works for now
        Elements links = html.select("h2 a");
        Set<String> results = new HashSet<>();
        for (var link : links) {
            results.add(link.attr("href"));
        }
        return results;
    }
    private int getDomainUrl(String absUrl){
        int occurences = 0;
        for(int i = 0; i < absUrl.length(); i++){
            if(absUrl.charAt(i) == '/'){
                occurences++;
            }
            if(occurences == 3){
                return i;
            }
        }
      return -1;
    }
}
