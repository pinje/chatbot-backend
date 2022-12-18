package sem3chatbot.backend.business.ServiceImpl;

import org.springframework.stereotype.Service;
import sem3chatbot.backend.business.UrlSanitizerService;

import java.util.Set;


@Service
public class UrlSanitizerServiceImpl implements UrlSanitizerService {


    @Override
    public void sanitizeUrls(Set<String> urls){
        urls.removeIf(s -> s.startsWith("https://www.bing.com") ||
                s.startsWith("/images") ||
                s.startsWith("/videos") ||
                s.startsWith("/news"));
    }

}
