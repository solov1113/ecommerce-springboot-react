package h1r0ku.exceptions;


import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class RestApiException extends RuntimeException {
    private String message;
    private HttpStatus status;

    public RestApiException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }
}
