package sem3chatbot.backend.domain.answers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    private Long id;
    private Long questionsKeyword;
    private Long secondaryKeyword;
    private Long tertiaryKeyword;
    private Long solution;
}
