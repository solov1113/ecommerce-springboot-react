package h1r0ku.exceptions;

import lombok.Data;

@Data
public class ProductQuantityUnavailableException extends RuntimeException {
    private String message;

    public ProductQuantityUnavailableException(String message) {
        super(message);
        this.message = message;
    }
}
