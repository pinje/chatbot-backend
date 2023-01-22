package sem3chatbot.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FAQQuestion{
    private Long id;
    private String questionText;
    private String questionTextDutch;
    private FAQTopic topicId;
    private List<FAQQuestion> children;
    private FAQAnswer answer;
}
