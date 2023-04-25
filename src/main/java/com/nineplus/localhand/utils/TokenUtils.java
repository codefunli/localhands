package com.nineplus.localhand.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nineplus.localhand.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TokenUtils {

    @Value("${app.password.secretKey}")
    private String SECRET_KEY;

    @Value("${app.login.jwtPrefix}")
    private String PREFIX_TOKEN;
    @Value("${app.login.jwtSecretKey.accessToken}")
    private String SECRET_KEY_ACCESS_TOKEN;
    @Value("${app.login.jwtSecretKey.refreshToken}")
    private String SECRET_KEY_REFRESH_TOKEN;
    @Value("${app.login.jwtExpiration.accessToken}")
    private String JWT_EXPIRATION_ACCESS_TOKEN;
    @Value("${app.login.jwtExpiration.refreshToken}")
    private String JWT_EXPIRATION_REFRESH_TOKEN;

    public DecodedJWT decodedPasswordToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    public String generateAccessToken(HttpServletRequest request, User user) {
        List<String> roleList = user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY_ACCESS_TOKEN.getBytes());
        return JWT.create().withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + Integer.parseInt(JWT_EXPIRATION_ACCESS_TOKEN) * 1000L))
                .withIssuer(request.getRequestURL().toString())
                .withClaim(CommonConstants.Authentication.ROLES, roleList).sign(algorithm);
    }

    public String generateRefreshToken(HttpServletRequest request, User user) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY_ACCESS_TOKEN.getBytes());
        return JWT.create().withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + Integer.parseInt(JWT_EXPIRATION_ACCESS_TOKEN) * 1000L))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
    }


    public DecodedJWT decodedAccessToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY_ACCESS_TOKEN.getBytes())).build();
        return verifier.verify(token);
    }

    public DecodedJWT decodedRefreshToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY_REFRESH_TOKEN.getBytes())).build();
        return verifier.verify(token);
    }

    private Boolean isJWTExpired(String token, String secretCode) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretCode.getBytes())).build().verify(token);
        Date expiresAt = decodedJWT.getExpiresAt();
        return expiresAt.before(new Date());
    }

    public Boolean isAccessTokenExpired(String token) {
        return this.isJWTExpired(token,SECRET_KEY_ACCESS_TOKEN);
    }

    public Boolean isRefreshTokenExpired(String token) {
        return this.isJWTExpired(token,SECRET_KEY_REFRESH_TOKEN);
    }
}
