package h1r0ku.feign;

import h1r0ku.configuration.FeignClientConfiguration;
import h1r0ku.dto.cart.CartRequest;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static h1r0ku.constants.FeignConstants.CART_SERVICE;
import static h1r0ku.constants.PathConstants.API_V1_CART;

@FeignClient(name = CART_SERVICE, configuration = FeignClientConfiguration.class)
public interface CartClient {
    @PostMapping(value=API_V1_CART)
    ResponseEntity<Object> createCart(@Valid @RequestBody CartRequest cartRequest);
}