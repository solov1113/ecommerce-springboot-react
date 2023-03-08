package h1r0ku.service.impl;

import h1r0ku.entity.Customer;
import h1r0ku.entity.ShippingAddress;
import h1r0ku.exceptions.NotFoundException;
import h1r0ku.repository.ShippingAddressServiceRepository;
import h1r0ku.service.ShippingAddressService;
import h1r0ku.utils.UpdatingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShippingAddressServiceImpl implements ShippingAddressService {

    private final ShippingAddressServiceRepository shippingAddressServiceRepository;
    private final CustomerServiceImpl customerService;

    @Override
    public ShippingAddress createShippingAddress(Long customerId, ShippingAddress address) {
        Customer customer = customerService.getById(customerId);
        address.setCustomer(customer);
        return shippingAddressServiceRepository.save(address);
    }

    @Override
    public ShippingAddress getShippingAddressById(Long id) {
        return shippingAddressServiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Shipping address not found"));
    }

    @Override
    public List<ShippingAddress> getAllShippingAddressesByCustomerId(Long customerId) {
        return shippingAddressServiceRepository.findAllByCustomer_Id(customerId);
    }

    @Override
    public ShippingAddress updateShippingAddress(Long id, ShippingAddress addressDetails) {
        ShippingAddress address = getShippingAddressById(id);
        BeanUtils.copyProperties(addressDetails, address, UpdatingUtil.getNullPropertyNames(addressDetails));
        return shippingAddressServiceRepository.save(address);
    }

    @Override
    public void deleteShippingAddress(Long id) {
        shippingAddressServiceRepository.deleteById(id);
    }
}
