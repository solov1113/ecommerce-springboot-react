package h1r0ku.feign;

import h1r0ku.configuration.FeignClientConfiguration;
import h1r0ku.dto.request.AuthenticationRequest;
import h1r0ku.dto.response.AuthenticationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static h1r0ku.constants.FeignConstants.AUTH_SERVICE;
import static h1r0ku.constants.PathConstants.API_V1_AUTH;

@FeignClient(value = AUTH_SERVICE, configuration = FeignClientConfiguration.class)
public interface AuthClient {
    @PostMapping(API_V1_AUTH)
    ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest);
}
