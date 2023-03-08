package h1r0ku.mapper;

import h1r0ku.dto.request.ShippingAddressRequest;
import h1r0ku.dto.response.ShippingAddressResponse;
import h1r0ku.entity.ShippingAddress;
import h1r0ku.service.ShippingAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ShippingAddressMapper {

    private final BasicMapper basicMapper;
    private final ShippingAddressService shippingAddressService;

    public ShippingAddressResponse createShippingAddress(ShippingAddressRequest address) {
        ShippingAddress shippingAddress = basicMapper.convertTo(address, ShippingAddress.class);
        return basicMapper.convertTo(
                shippingAddressService.createShippingAddress(address.getCustomerId(), shippingAddress),
                ShippingAddressResponse.class
        );
    }

    public ShippingAddressResponse getShippingAddressById(Long id) {
        return basicMapper.convertTo(shippingAddressService.getShippingAddressById(id), ShippingAddressResponse.class);
    }

    public List<ShippingAddressResponse> getAllShippingAddressesByCustomerId(Long customerId) {
        return basicMapper.convertListTo(shippingAddressService.getAllShippingAddressesByCustomerId(customerId), ShippingAddressResponse.class);
    }

    public ShippingAddressResponse updateShippingAddress(Long id, ShippingAddressRequest addressDetails) {
        ShippingAddress shippingAddress = basicMapper.convertTo(addressDetails, ShippingAddress.class);
        return basicMapper.convertTo(shippingAddressService.updateShippingAddress(id, shippingAddress), ShippingAddressResponse.class);
    }

    public void deleteShippingAddress(Long id) {
        shippingAddressService.deleteShippingAddress(id);
    }
}
