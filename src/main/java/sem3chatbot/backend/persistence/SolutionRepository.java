package sem3chatbot.backend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sem3chatbot.backend.persistence.entity.SolutionEntity;

import java.util.List;

@Repository
public interface SolutionRepository extends JpaRepository<SolutionEntity, Long>{


//    List<SolutionEntity> findByCategory(Long category);
//
//    // The string question would contain the question id
//    // See keyword repo for why this might not work
//    SolutionEntity findByQuestionsContaining(String question);

}
