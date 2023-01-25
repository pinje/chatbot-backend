package sem3chatbot.backend.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidTopicIdException extends ResponseStatusException{
    public InvalidTopicIdException() {super(HttpStatus.NOT_FOUND, "INVALID_TOPIC_ID");}
}
