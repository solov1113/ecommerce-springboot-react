package h1r0ku.dto.request;

import h1r0ku.enums.CustomerRole;
import lombok.Data;

@Data
public class CustomerRequest {
    private String username;
    private String password;
    private CustomerRole role;
    private String firstName;
    private String lastName;
    private String imageUrl;
}
