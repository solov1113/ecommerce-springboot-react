package h1r0ku.repository;

import h1r0ku.entity.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShippingAddressServiceRepository extends JpaRepository<ShippingAddress, Long> {
    List<ShippingAddress> findAllByCustomer_Id(Long customerId);
}
