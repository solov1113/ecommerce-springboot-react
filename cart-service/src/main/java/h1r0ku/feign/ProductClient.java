package h1r0ku.feign;

import h1r0ku.configuration.FeignClientConfiguration;
import h1r0ku.dto.catalog.product.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static h1r0ku.constants.FeignConstants.CATALOG_SERVICE;
import static h1r0ku.constants.PathConstants.API_V1_PRODUCT;

@FeignClient(name = CATALOG_SERVICE, configuration = FeignClientConfiguration.class)
public interface ProductClient {
    @GetMapping(value=API_V1_PRODUCT + "/{productId}")
    ResponseEntity<ProductResponse> getProductById(@PathVariable("productId") Long productId);

}
