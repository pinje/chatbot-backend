package sem3chatbot.backend.business;

import sem3chatbot.backend.domain.requests.CreateLogRequest;

public interface CreateLogService{
    void createLog (CreateLogRequest request);
}
