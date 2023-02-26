package h1r0ku.exceptions;

import lombok.Data;

@Data
public class NotFoundException extends RuntimeException {
    private String message;


    public NotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
