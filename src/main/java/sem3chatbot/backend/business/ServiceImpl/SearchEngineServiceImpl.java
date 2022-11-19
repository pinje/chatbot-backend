package sem3chatbot.backend.business.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import sem3chatbot.backend.business.SearchEngineService;
import sem3chatbot.backend.domain.answers.SearchEngineTopThreeResponse;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SearchEngineServiceImpl implements SearchEngineService {

    @Override
    public SearchEngineTopThreeResponse getTopLinksFromSearchQuery(String queryString, int limit) throws IOException {
        String searchUrl = "https://google.com/search?q=";
        print("Searching..." + searchUrl);
        print(searchUrl + queryString);
        Document rawHtml = Jsoup.connect(searchUrl + queryString + "&num=" + limit )
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36")
                .get();
        Set<String> links = findLinks(rawHtml);
        print("Returning " + links.size() + " trimmed links");
        return SearchEngineTopThreeResponse.builder()
                .links(links)
                .build();
    }

    private Set<String> findLinks(Document html){
        Set<String> results = new HashSet<>();
        //the selector here for the query needs to be correct to actually get search result links
        Elements links = html.getElementsByTag("a");
        print("Total links found: " + links.size());

        if(links.size() == 0){
            return new HashSet<>();
        }
        for(int i = 0; i < links.size(); i++){
            String nodeUrl = links.get(i).attr("ping");
            if(!nodeUrl.startsWith("https://policies") && !nodeUrl.startsWith("https://support")){
                if(nodeUrl.length() > 1){
                    String redirectUrl = nodeUrl.substring(31);
                    int cutIndex = getDomainUrl(redirectUrl);
                    String actualUrl = redirectUrl.substring(0, cutIndex);
                    results.add(actualUrl);
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
        char slash = '/';
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
