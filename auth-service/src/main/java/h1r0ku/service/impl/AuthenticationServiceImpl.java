package h1r0ku.service.impl;

import h1r0ku.dto.authentication.AuthenticationRequest;
import h1r0ku.dto.authentication.AuthenticationResponse;
import h1r0ku.service.AuthenticationService;
import h1r0ku.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                    )
            );

        }
        catch (BadCredentialsException e) {
            throw new RuntimeException("#### Bad credentials! ####");
        }

        return new AuthenticationResponse(jwtService.generateToken(userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername())));
    }
}
