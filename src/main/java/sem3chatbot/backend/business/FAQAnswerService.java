package sem3chatbot.backend.business;

import sem3chatbot.backend.domain.requests.CreateAnswerRequest;

public interface FAQAnswerService {
    void createOrUpdate(CreateAnswerRequest request);

    void delete(long answerId);
}
