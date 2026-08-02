package com.portfolio.service;

import com.portfolio.dto.LoginRequest;
import com.portfolio.dto.JwtResponse;
import com.portfolio.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

//    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        // Authenticate using the manager (which delegates to CustomUserDetailsService)
  //      Authentication authentication = authenticationManager.authenticate(
    //            new UsernamePasswordAuthenticationToken(
      //                  loginRequest.getEmail(),
        //                loginRequest.getPassword()
          //      )
        //);
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
    log.info("Login attempt: {}", loginRequest.getEmail());
    try {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        log.info("Authentication SUCCESS");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        User userPrincipal = (User) authentication.getPrincipal();
        return new JwtResponse(
                jwt,
                userPrincipal.getUsername(),
                userPrincipal.getAuthorities().iterator().next().getAuthority()
        );
    } catch (Exception e) {
        log.error("Authentication FAILED", e);
        throw e;
    }
}
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT token
        String jwt = tokenProvider.generateToken(authentication);

        // Extract user metadata from context principal
        User userPrincipal = (User) authentication.getPrincipal();
        String email = userPrincipal.getUsername();
        String role = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_ADMIN");

        return new JwtResponse(jwt, email, role);
    }
}
