package sem3chatbot.backend.business.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sem3chatbot.backend.business.UrlSanitizerService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UrlSanitizerServiceImpl implements UrlSanitizerService {

    @Override
    public void sanitize(Set<String> trimmedUrls){
        //for each trimmedUrl we get from the results, we check against the sanitizable urls
        //and delete them if any are found
    }

    @Override
    public Map<Integer, String> generateSanitizableUrls(){
        //integer value is the index of the sanitizable url, string is the actual url we check against
        //performance should be decent with the constant lookup time
        return new HashMap<>();
    }

}
