package com.vinay.employeeManagementSystem.employeeServices;

import org.springframework.stereotype.Service;

@Service
public class JWTService {

    public String generateToken() {
        return "";
    }

    @Nonnull
    private Key getkey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
