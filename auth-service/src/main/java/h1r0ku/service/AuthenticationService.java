package h1r0ku.service;

import h1r0ku.dto.authentication.AuthenticationRequest;
import h1r0ku.dto.authentication.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
