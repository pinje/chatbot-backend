package sem3chatbot.backend.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "solutions")
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
    @Column(unique = true, name = "text")
    private String text;
}
