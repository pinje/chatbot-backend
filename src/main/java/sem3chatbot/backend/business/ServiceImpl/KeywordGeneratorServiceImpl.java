package sem3chatbot.backend.business.ServiceImpl;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import sem3chatbot.backend.business.KeywordGeneratorService;
import sem3chatbot.backend.persistence.KeywordRepository;
import sem3chatbot.backend.persistence.entity.KeywordEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class KeywordGeneratorServiceImpl implements KeywordGeneratorService{
    private final KeywordRepository keywordRepository;

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

       Set<String> keywordsText = findKeywords(rawHtml);

       if(keywordsText.size() != 0){
           List<KeywordEntity> keywordEntities = new ArrayList<>();
           for(String k : keywordsText){
               if(!keywordRepository.existsByText(k)){
                   keywordEntities.add(KeywordEntity.builder()
                           .text(k)
                           .type("topic")
                           .build());
               }
           }
           keywordRepository.saveAll(keywordEntities);
       }
       //returning generated keywords here so that they can be instantly used, instead of calling db again.
        return keywordsText;
    }

    private Set<String> findKeywords(Document html){
        Elements metaTag = html.getElementsByAttributeValue("name", "keywords");

       String keywords = metaTag.attr("content");
       Set<String> keywordsSet = Stream.of(keywords.split(","))
                .map(String::trim)
                .collect(Collectors.toSet());
       watch.stop();
       Logger.print(String.format("Extracted %1$s keywords in %2$s ms"
               , keywordsSet.size()
               , watch.getTotalTimeMillis()));
       return keywordsSet;
    }
}
