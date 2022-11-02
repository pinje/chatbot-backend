package sem3chatbot.backend.business.ServiceImpl;

import sem3chatbot.backend.domain.keywords.Keyword;
import sem3chatbot.backend.persistence.entity.KeywordEntity;

public class KeywordConverter {
    public static Keyword convert(KeywordEntity entity){
        return Keyword.builder()
                .id(entity.getId())
                .text(entity.getText())
                .timestamp(entity.getTimestamp())
                .type(entity.getType())
                .build();
    }
}
