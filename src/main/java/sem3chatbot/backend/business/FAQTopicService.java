package sem3chatbot.backend.business;

import sem3chatbot.backend.domain.GetTopicsResponse;
import sem3chatbot.backend.domain.UpdateTopicRequest;

public interface FAQTopicService {
    /**
     Updates the topic by id, where the id corresponds to the slot in the database.
     @param request The request body, containing the topic ID and the new description.
     @throws InvalidTopicIdException if the topic ID is either not present or not a number.
     */
    void updateTopic(UpdateTopicRequest request);

    /**
     * @return A list of the 5 topics currently registered in the FAQ.
     */
    GetTopicsResponse getTopics();
}

