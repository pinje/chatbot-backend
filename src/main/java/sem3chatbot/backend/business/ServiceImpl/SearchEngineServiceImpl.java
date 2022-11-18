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
    public SearchEngineTopThreeResponse getTopLinksFromSearchQuery(String queryString) throws IOException {
        print("Searching...");
        String searchUrl = "https://google.com/search?q";
        Document rawHtml = Jsoup.connect(searchUrl + queryString)
                .get();
        Set<String> links = findLinks(rawHtml);
        print("Links are :" + links);
        return SearchEngineTopThreeResponse.builder()
                .links(links)
                .build();
    }

    private Set<String> findLinks(Document html){
        Set<String> results = new HashSet<>();
        //the selector here for the query needs to be correct to actually get search result links
        Elements links = html.getElementsByAttribute("href");
        if(links.size() == 0){
            return new HashSet<String>();
        }
        print(links.text());

        for(int i = 0; i < 3; i++){
            results.add(links.get(i).attr("href"));
        }
        return results;
    }

    //this is just for convenience in order to not call the sysout function over and over
    private static void print(String text, Object... args){
        System.out.printf((text) + "%n", args);
    }
}
