package h1r0ku.service.impl;

import h1r0ku.dto.request.WishlistRequest;
import h1r0ku.entity.Customer;
import h1r0ku.entity.Wishlist;
import h1r0ku.feign.ProductClient;
import h1r0ku.repository.WishlistRepository;
import h1r0ku.service.CustomerService;
import h1r0ku.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final CustomerService customerService;
    private final ProductClient productClient;

    @Override
    public Wishlist create(WishlistRequest wishlistRequest) {
        Wishlist wishlist = new Wishlist();
        Customer customer = customerService.getById(wishlistRequest.getCustomerId());
        Long productId = productClient.getProductById(wishlistRequest.getProductId()).getBody().getId();;
        wishlist.setCustomer(customer);
        wishlist.setProductId(productId);
        return wishlistRepository.save(wishlist);
    }

    @Override
    public Page<Wishlist> getAllByCustomerId(Long id, Pageable pageable) {
        return wishlistRepository.findAllByCustomer_Id(id, pageable);
    }

    @Override
    public void delete(Long id) {
        wishlistRepository.deleteById(id);
    }
}