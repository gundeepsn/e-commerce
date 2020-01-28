package com.example.demo.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityConstants {
    @Value("${signup.url}")
    public String SIGNUP_URL;

    @Value("${jwt.secret}")
    public String JWT_SECRET;

    @Value("${jwt.token_prefix}")
    public String TOKEN_PREFIX;

    @Value("${jwt.expirationtime}")
    public long EXPIRATION_TIME;

    @Value("${jwt.header}")
    public String HEADER_STR;

    @Value(value = "${jwt.api_audience}")
    public String API_AUDIENCE;

    @Value(value = "${jwt.issuer}")
    public String ISSUER;
}
