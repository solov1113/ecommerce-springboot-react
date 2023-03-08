package h1r0ku.mapper;

import h1r0ku.dto.request.WishlistRequest;
import h1r0ku.dto.response.WishlistResponse;
import h1r0ku.entity.Customer;
import h1r0ku.entity.Wishlist;
import h1r0ku.feign.ProductClient;
import h1r0ku.repository.WishlistRepository;
import h1r0ku.service.CustomerService;
import h1r0ku.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WishlistMapper {

    private final BasicMapper basicMapper;
    private final WishlistService wishlistService;

    public WishlistResponse create(WishlistRequest wishlistRequest) {
        return basicMapper.convertTo(wishlistService.create(wishlistRequest), WishlistResponse.class);
    }

    public Page<WishlistResponse> getAllByCustomerId(Long customerId, Pageable pageable) {
        return wishlistService.getAllByCustomerId(customerId, pageable).map(w -> basicMapper.convertTo(w, WishlistResponse.class));
    }

    public void deleteById(Long id) {
        wishlistService.delete(id);
    }
}
