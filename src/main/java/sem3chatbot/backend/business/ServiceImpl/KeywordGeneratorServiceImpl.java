package sem3chatbot.backend.business.ServiceImpl;

import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import sem3chatbot.backend.business.KeywordGeneratorService;

import java.io.IOException;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class KeywordGeneratorServiceImpl implements KeywordGeneratorService{
    StopWatch watch;
    @Override
    public Set<String> scrapeKeywords(final String fontysUrl) throws IOException {
       this.watch = new StopWatch();
       watch.start();
        Logger.print("Extracting keywords for: " + fontysUrl);

        Document rawHtml = Jsoup.connect(fontysUrl)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36")
                .referrer("http://www.bing.com")
                .get();
        return findKeywords(rawHtml);
    }

    private Set<String> findKeywords(Document html){
        Instant start = Instant.now();
        Elements metaTag = html.getElementsByAttributeValue("name", "keywords");

       String keywords = metaTag.attr("content");
       Set<String> keywordsSet = Stream.of(keywords.split(","))
                .map(String::trim)
                .collect(Collectors.toSet());
       watch.stop();
       Logger.print("Extracted " + keywordsSet.size() + " keywords in " + watch.getTotalTimeMillis() + "ms");
       return keywordsSet;
    }
}
