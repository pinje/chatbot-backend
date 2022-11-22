package sem3chatbot.backend.business.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sem3chatbot.backend.business.UrlSanitizerService;


@Service
@RequiredArgsConstructor
public class UrlSanitizerServiceImpl implements UrlSanitizerService {

    @Override
    public boolean isSanitizable(String trimmedUrl){
        String[] badUrls = generateBadUrls();
        for (String badUrl : badUrls) {
            if (badUrl.equals(trimmedUrl)) {
                return true;
            }
        }
        return false;
    }

    private String[] generateBadUrls(){
        //pretty sure this can be done with a regex but i couldnt find one that works
        return new String[]{
                "https://www.instagram.com",
                "https://nl-nl.facebook.com",
                "https://www.youtube.com",
                "https://twitter.com",
                "https://nl.wikipedia.org",
                "https://en.wikipedia.org",
                "https://www.forbes.com",
                "http://en.wikipedia.org",
                "http://nl.wikiepdia.org",
                "https://www.facebook.com"
        };
    }

}
