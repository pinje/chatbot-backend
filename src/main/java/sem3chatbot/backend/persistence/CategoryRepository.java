package sem3chatbot.backend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sem3chatbot.backend.persistence.entity.CategoryEntity;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    CategoryEntity findByTopic(Long topic); // Parameter --> id of the topic : String ; Return the matched Category or nothing
//    CategoryEntity findBySource(String source); // Parameter --> url of the related page : String ; Return the matched Category or nothing
}
