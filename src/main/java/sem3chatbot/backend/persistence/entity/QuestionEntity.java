package sem3chatbot.backend.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "questions")
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "topic_kw")
    private String topicKw;

//    @Column(name = "question_structure")
//    private String questionStructure;

    @Column(name = "category")
    private Long category;

    @Column(name = "solution")
    private Long solution;

//    @Column(name = "metadata")
//    private String metadata;
}
