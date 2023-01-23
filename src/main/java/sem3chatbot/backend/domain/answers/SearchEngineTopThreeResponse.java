package sem3chatbot.backend.domain.answers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchEngineTopThreeResponse {
    Set<String> links;
    Set<String> titles;
}
