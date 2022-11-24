package sem3chatbot.backend.business.ServiceImpl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
        print("Query string format: " + queryStringInjected);
        String searchUrl = "https://google.com/search?q=" + queryStringInjected + "&num=" + limit;
        print("Searching..." + searchUrl);
            Document rawHtml = Jsoup.connect(searchUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36")
                    .referrer("http://www.google.com")
                    .get();

        Set<String> links = findLinks(rawHtml);
        links.remove("");
        print("Returning " + links.size() + " trimmed links");
        return SearchEngineTopThreeResponse.builder()
                .links(links)
                .build();
    }

    private Set<String> findLinks(Document html){
        Elements links = html.getElementsByTag("a");
        print("Total links found: " + links.size());
        if(links.size() == 0){
            return new HashSet<>();
        }
        Set<String> results = new HashSet<>();
        for (Element link : links) {
            String nodeUrl = link.attr("ping");
                if (nodeUrl.length() > 1) {
                    try {
                        String redirectUrl = nodeUrl.substring(31);
                        int cutIndex = getDomainUrl(redirectUrl);
                        String actualUrl = redirectUrl.substring(0, cutIndex);
                        if(!actualUrl.startsWith("https://policies") &&
                                !actualUrl.startsWith("https://support") &&
                                !actualUrl.startsWith("https://translate") &&
                                !urlSanitizerService.isSanitizable(actualUrl)){
                            results.add(actualUrl);
                        }
                    }
                    catch(StringIndexOutOfBoundsException ex){
                        print("Query string threw an exception: " + ex.getMessage());
                    }
                }
        }
        return results;
    }

    //this is just for convenience in order to not call the sysout function over and over
    private static void print(String text, Object... args){
        System.out.printf((text) + "%n", args);
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

    private String injectSeparator(String queryString){
       return queryString.replace(' ', '+');
    }
}
