package sem3chatbot.backend.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//@Table(name = "solutions")
@Table(name = "solutions_refactored")
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolutionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // This would represent the URL of a fontys page for example for the current version of the chatbot.
    //
//    @Column(unique = true, name = "answerText")
//    private String text;

    @Column(name = "category")
    private Long category;

    @Column(name = "questions")
    private String questions;

    @Column(name = "answer")
    private String answer;
    @Column(name = "dutch_answer")
    private String dutchAnswer;

//    @Column(name = "category_solution")
//    private Boolean categoryEntity;
//
//    @Column(name = "children_solutions")
//    private String childrenSolutions;

//    @Column(name = "metadata")
//    private String metadata;
//
//    @Column(name = "role_requirement")
//    private String roleRequirement;
}
