package h1r0ku.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShippingAddressResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String streetAddress;
    private String country;
    private String city;
    private String zipCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
