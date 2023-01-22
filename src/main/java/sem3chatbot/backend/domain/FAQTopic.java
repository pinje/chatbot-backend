package sem3chatbot.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FAQTopic{
    private Long id;
    private String description;
    private String descriptionDutch;
    private byte[] photo;
}
