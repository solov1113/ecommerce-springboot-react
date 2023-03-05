package h1r0ku.exceptions;

import lombok.Data;

@Data
public class AlreadyExistException extends RuntimeException {
    private String message;

    public AlreadyExistException(String message) {
        super(message);
        this.message = message;
    }
}
