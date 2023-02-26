package h1r0ku.dto.request;

import h1r0ku.enums.CustomerRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerRequest {
    @Size(min = 3, max = 25, message = "Username must be between 3 and 20 characters")
    @NotBlank(message = "Username value mustn't be null or whitespace")
    private String username;
    @Size(min = 3, max = 25, message = "Password must be between 3 and 20 characters")
    @NotBlank(message = "Password value mustn't be null or whitespace")
    private String password;
    private CustomerRole role;
    private String firstName;
    private String lastName;
    private String imageUrl;
}
