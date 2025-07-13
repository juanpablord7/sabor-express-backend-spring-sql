package com.miempresa.serviceuser.jwt;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miempresa.serviceuser.dto.client.RoleResponse;
import com.miempresa.serviceuser.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private Long jwtExpiration;

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public long getExpirationTime() {
        return jwtExpiration;
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public List<GrantedAuthority> extractAuthorities(String token){
        List<String> permissions = extractClaim(token, claims -> {
            Object permissionsObject = claims.get("permissions");
            return new ObjectMapper().convertValue(permissionsObject, new TypeReference<List<String>>() {});
        });

        return permissions != null  //Check if the jwt have permissions
                ? permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList())
                : List.of();    //Empty list when don't have permissions
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token){
        return !isTokenExpired(token);
    }

    public String generateToken(UserDetails userDetails, RoleResponse role) {
        Map<String, Object> extraClaims = new HashMap<>();

        extraClaims.put("roleId", role.getId());
        extraClaims.put("roleName", role.getName());
        extraClaims.put("isDefault", role.isDefault());

        List<String> permissions = new ArrayList<>();
        if (role.isManageUser()) permissions.add("manageUser");
        if (role.isManageOrder()) permissions.add("manageOrder");
        if (role.isManageProduct()) permissions.add("manageProduct");
        if (role.isManageRole()) permissions.add("manageRole");
        if (role.isPromoteAll()) permissions.add("promoteAll");
        if (role.isEditPassword()) permissions.add("editPassword");

        extraClaims.put("permissions", permissions);

        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (jwtExpiration * 1000)))
                .signWith(getSignInKey())
                .compact();
    }

}
