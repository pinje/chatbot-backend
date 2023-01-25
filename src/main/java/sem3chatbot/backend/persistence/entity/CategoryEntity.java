package sem3chatbot.backend.persistence.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "category")
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(unique = true, name = "name")
    @NotNull
    private String name;

    // Maybe a join here, instead of a string check. Will contain only the id of the keyword
    @Column(unique = true, name = "topic")
    private Long topic;

    @Column(unique = true, name = "source")
    private String source;


    // Can be added later, to filter by this.
    @Column(name = "metadata")
    private String metadata;

}
