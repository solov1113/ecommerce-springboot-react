package h1r0ku.feign;

import h1r0ku.configuration.FeignClientConfiguration;
import h1r0ku.dto.cart.CartResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static h1r0ku.constants.FeignConstants.ORDER_SERVICE;
import static h1r0ku.constants.PathConstants.API_V1_ORDER;

@FeignClient(name = ORDER_SERVICE, configuration = FeignClientConfiguration.class)
public interface OrderClient {
    @PostMapping(value=API_V1_ORDER)
    ResponseEntity<Object> create(@Valid @RequestBody CartResponse cart);
}
