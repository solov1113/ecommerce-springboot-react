package h1r0ku.repository;

import h1r0ku.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteCartItemsByCart_Id(Long cartId);
}
