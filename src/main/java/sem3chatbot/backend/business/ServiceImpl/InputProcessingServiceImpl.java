package sem3chatbot.backend.business.ServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sem3chatbot.backend.business.InputProcessingService;
import sem3chatbot.backend.domain.UserInput;
import sem3chatbot.backend.domain.keywords.Keyword;
import sem3chatbot.backend.persistence.KeywordRepository;
import sem3chatbot.backend.persistence.entity.KeywordEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class InputProcessingServiceImpl implements InputProcessingService {

    private KeywordRepository keywordRepository;

    public HashMap<Integer, String> findMatches(UserInput request){
        HashMap<Integer, String> matches = new HashMap<>();
        //getting the words from the sentence, regex takes care of spaces, commas and semicolons
        String[] requestWords = request.getQuestion().split("\\W+");
        List<KeywordEntity> keywordEntities = keywordRepository.findAll();

        List<Keyword> keywords = keywordEntities.stream().map(KeywordConverter::convert).toList();
        //clearing all objects from the list and making them null, so that the garbage collector can clean them up
        //and free up memory
        keywordEntities.clear();

      List<String> results = keywords.stream().map(Keyword::getText).toList();
        for(int i = 0; i < requestWords.length; i++){
            if(results.contains(requestWords[i])){
                matches.put(i, requestWords[i]);
            }
        }

//       for(int i = 0; i < requestWords.length; i++){
//           for(int j = 0; j < keywords.size(); j++){
//               if(keywords.get(j).getText().equals(requestWords[i])){
//                   matches.put(j, requestWords[i]);
//               }
//           }
//       }
       return matches;





    }
}
