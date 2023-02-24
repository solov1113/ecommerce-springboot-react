package h1r0ku.feign;

import h1r0ku.configuration.FeignClientConfiguration;
import h1r0ku.dto.response.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static h1r0ku.constants.FeignConstants.CUSTOMER_SERVICE;
import static h1r0ku.constants.PathConstants.API_V1_CUSTOMER;

@FeignClient(name = CUSTOMER_SERVICE, configuration = FeignClientConfiguration.class)
public interface CustomerClient {
    @GetMapping(API_V1_CUSTOMER + "/username/{username}")
    ResponseEntity<CustomerResponse> getByUsername(@PathVariable("username") String username);
}
