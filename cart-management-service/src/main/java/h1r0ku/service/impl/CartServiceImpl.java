package h1r0ku.service.impl;

import h1r0ku.dto.cart.CartResponse;
import h1r0ku.dto.catalog.product.ProductResponse;
import h1r0ku.entity.Cart;
import h1r0ku.entity.CartItem;
import h1r0ku.exceptions.NotFoundException;
import h1r0ku.feign.OrderClient;
import h1r0ku.feign.ProductClient;
import h1r0ku.mapper.BasicMapper;
import h1r0ku.repository.CartItemRepository;
import h1r0ku.repository.CartRepository;
import h1r0ku.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final BasicMapper basicMapper;
    private final OrderClient orderClient;
    private final ProductClient productClient;

    @Override
    public Cart create(Cart cart) {
        Cart existingCart = getByCustomerId(cart.getCustomerId());
        if(existingCart != null) {
//            throw new AlreadyExist
        }
        return cartRepository.save(cart);
    }

    @Override
    public Cart addItem(Long cartId, CartItem cartItem) {
        Cart cart = getById(cartId);
        ProductResponse productResponse = productClient.getProductById(cartItem.getProductId()).getBody();
        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(i -> i.getProductId().equals(cartItem.getProductId()))
                .findFirst();
        if(existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + cartItem.getQuantity());
            item.addPrice(productResponse.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            cartItemRepository.save(item);
        } else {
            cart.addItem(cartItem);
            cartItem.setCart(cart);
            cartItem.addPrice(productResponse.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            cartItemRepository.save(cartItem);
        }

        return cartRepository.save(cart);
    }

    @Override
    public Cart removeItem(Long cartId, Long itemId) {
        Cart cart = getById(cartId);
        cart.removeItem(itemId);
        return cartRepository.save(cart);
    }

    @Override
    public Cart getById(Long id) {
        return cartRepository.findById(id).orElseThrow(() -> new NotFoundException("Cart not found"));
    }

    @Override
    @Transactional
    public Cart completeCart(Long id) {
        Cart cart = getById(id);
        orderClient.create(basicMapper.convertTo(cart, CartResponse.class));
        //        Clear cart items
        cartItemRepository.deleteCartItemsByCart_Id(id);
        return cartRepository.save(cart);
    }

    @Override
    public Cart getByCustomerId(Long id) {
        return cartRepository.findByCustomerId(id);
    }

    @Override
    public void deleteByCustomerId(Long id) {
        cartRepository.deleteByCustomerId(id);
    }
}
