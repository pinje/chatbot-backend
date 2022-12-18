package sem3chatbot.backend.domain.requests;

import lombok.Builder;
import lombok.Data;
import sem3chatbot.backend.persistence.entity.MessageEntity;

@Data
@Builder
public class CreateLogRequest{
        private MessageEntity[] messages;

}
