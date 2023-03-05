package h1r0ku.dto.request;

import h1r0ku.enums.CustomerRole;
import h1r0ku.validator.constraint.PasswordConstraint;
import h1r0ku.validator.constraint.UsernameConstraint;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CustomerRequest {
    @UsernameConstraint(message = "Username should be unique and be between 8 and 30 characters")
    private String username;

    @PasswordConstraint(message = "Your password must contain at least 8 characters, and include uppercase letters, lowercase letters and numbers.")
    private String password;

    private CustomerRole role;

    @Size(min = 2, max = 20, message = "First name must be between 2 and 20 characters")
    private String firstName;

    @Size(min = 2, max = 20, message = "Last name must be between 2 and 20 characters")
    private String lastName;
}