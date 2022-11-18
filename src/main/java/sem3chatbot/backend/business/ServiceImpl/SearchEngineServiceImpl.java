package sem3chatbot.backend.business.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36")
                .get();
        System.out.println("Found title: " + rawHtml.title());
        return SearchEngineTopThreeResponse.builder()
                .links(Set.of(rawHtml.title()))
                .build();
    }
}
