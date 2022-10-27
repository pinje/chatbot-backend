package sem3chatbot.backend.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sem3chatbot.backend.domain.answers.Solution;
import sem3chatbot.backend.domain.keywords.Keyword;

import javax.persistence.*;

@Table(name = "answers")
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @JoinColumn(name = "questionKeywordId")
    @OneToOne
    private KeywordEntity questionsKeyword;
    @JoinColumn(name = "auxiliaryKeywordId")
    @OneToOne
    private KeywordEntity secondaryKeyword;
    @OneToOne
    @JoinColumn(name = "topicKeywordId")
    private KeywordEntity tertiaryKeyword;
    @OneToOne
    @JoinColumn(name = "solutionId")
    private SolutionEntity solution;
}
