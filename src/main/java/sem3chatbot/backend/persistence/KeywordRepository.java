package sem3chatbot.backend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sem3chatbot.backend.persistence.entity.KeywordEntity;

@Repository
public interface KeywordRepository extends JpaRepository<KeywordEntity, Long> {

}
