package h1r0ku.service;

import h1r0ku.dto.request.AuthenticationRequest;
import h1r0ku.dto.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
