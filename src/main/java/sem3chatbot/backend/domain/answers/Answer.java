package sem3chatbot.backend.domain.answers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sem3chatbot.backend.domain.keywords.Keyword;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    private Long id;
    private String questionsKeywords;
    private String secondaryKeywords;
    private String tertiaryKeywords;
    private Solution solution;
}
