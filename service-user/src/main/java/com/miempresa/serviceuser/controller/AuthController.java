package com.miempresa.serviceuser.controller;

import com.miempresa.serviceuser.enums.RoleEnum;
import com.miempresa.serviceuser.model.UserModel;
import com.miempresa.serviceuser.security.JwtUtil;
import com.miempresa.serviceuser.service.AuthService;
import com.miempresa.serviceuser.service.UserService;
import feign.Body;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired private UserDetailsServiceImpl userDetailsService; // tu servicio personalizado
    @Autowired private UserRepository userRepository;

    private final UserService userService;
    private final AuthService authService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            UserModel user = userService.findUserByUsername(request.getEmail());
            String token = authService.createToken(user);

            UserModel user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            Instant expiration = Instant.now().plus(1, ChronoUnit.HOURS);

            return ResponseEntity.ok(new LoginResponse(token, user, expiration));

        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }
    }

    //@PostMapping("/validate")

    @PostMapping("/claims")
    public ResponseEntity<Claims> postClaims(String token){
        return ResponseEntity.ok(authService.findClaims(token));
    }

    @PostMapping("/role")
    public ResponseEntity<RoleEnum> postRole(String token){
        return ResponseEntity.ok(authService.findRole(token));
    }

}
