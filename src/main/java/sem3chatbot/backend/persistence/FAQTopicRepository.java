package sem3chatbot.backend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import sem3chatbot.backend.persistence.entity.FAQTopicEntity;

public interface FAQTopicRepository extends JpaRepository<FAQTopicEntity, Long>{
}
