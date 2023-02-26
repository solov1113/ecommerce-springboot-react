package h1r0ku.dto.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@Data
public class ErrorDetails {
    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ErrorDetails(HttpStatus status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ErrorDetails(HttpStatus status, String message, String error) {
        this.status = status;
        this.message = message;
        this.errors = Arrays.asList(error);
    }
}
