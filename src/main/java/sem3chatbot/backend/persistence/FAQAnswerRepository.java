package sem3chatbot.backend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import sem3chatbot.backend.persistence.entity.FAQAnswerEntity;

public interface FAQAnswerRepository extends JpaRepository<FAQAnswerEntity, Long>{
}
