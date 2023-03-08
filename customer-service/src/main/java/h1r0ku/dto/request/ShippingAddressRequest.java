package h1r0ku.dto.request;

import lombok.Data;

@Data
public class ShippingAddressRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String streetAddress;
    private String country;
    private String city;
    private String zipCode;
    private Long customerId;
}
