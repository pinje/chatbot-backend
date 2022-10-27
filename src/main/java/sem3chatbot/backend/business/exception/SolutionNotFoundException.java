package sem3chatbot.backend.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SolutionNotFoundException extends ResponseStatusException{

    public SolutionNotFoundException(){
        super(HttpStatus.NOT_FOUND, "NO_SOLUTION_FOUND");
    }
}
