package com.abhishek.demo.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService
{

    private String key;

    public JwtService()
    {
        try
        {
            KeyGenerator generator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = generator.generateKey();
            this. key = Base64.getEncoder().encodeToString(sk.getEncoded());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public String generateToken(String userName)
    {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims(claims)
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (60 * 60 * 10)))
                .signWith(getKey())
                .compact();
    }

    private Key getKey()
    {
        byte[] keyByte = Decoders.BASE64.decode(this.key);
        return Keys.hmacShaKeyFor(keyByte);
    }
}
