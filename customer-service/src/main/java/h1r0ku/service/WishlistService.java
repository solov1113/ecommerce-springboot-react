package h1r0ku.service;

import h1r0ku.dto.request.WishlistRequest;
import h1r0ku.entity.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WishlistService {
    Wishlist create(WishlistRequest wishlist);
    Page<Wishlist> getAllByCustomerId(Long id, Pageable pageable);
    void delete(Long id);
}
