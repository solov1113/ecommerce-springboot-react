package h1r0ku.repository;

import h1r0ku.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    void deleteByCustomerId(Long customerId);
    Cart findByCustomerId(Long customerId);
}
