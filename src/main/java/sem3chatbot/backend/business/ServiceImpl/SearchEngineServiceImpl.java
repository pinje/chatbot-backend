package sem3chatbot.backend.business.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
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
        System.out.println("Searching...");
        String searchUrl = "https://google.com/search?q";
        Document rawHtml = Jsoup.connect(searchUrl + queryString)
                .get();
        Set<String> links = findLinks(rawHtml);
        System.out.println(links);
        return SearchEngineTopThreeResponse.builder()
                .links(links)
                .build();
    }

    private Set<String> findLinks(Document html){
        Set<String> results = new HashSet<>();;
        Elements links = html.getElementsByTag("a");

        for(int i = 0; i < 3; i++){
            results.add(links.get(i).attr("abs:href"));
        }
        return results;
    }
}
