package com.benope.apple.config.auth;

import com.benope.apple.utils.DomainObjectUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TokenProvider implements InitializingBean {

    private final JwtProperties jwtProperties;

    private Key key;

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    private static final String AUTHORITY_KEY = "authorities";

    public String createAccessToken(Authentication authentication) {
        return createToken(authentication, TokenType.ACCESS_TOKEN);
    }

    public String createRefreshToken(Authentication authentication) {
        return createToken(authentication, TokenType.REFRESH_TOKEN);
    }

    private String createToken(Authentication authentication, TokenType tokenType) {
        return Jwts.builder()
                .setClaims(retrieveClaims(authentication, tokenType))
                .setSubject(authentication.getName())
                .setIssuedAt(new Date())
                .setExpiration(toExpiration(tokenType))
                .signWith(key)
                .compact();
    }

    private Map<String, Object> retrieveClaims(Authentication authentication, TokenType tokenType) {
        List<String> authorities = tokenType.toAuthorities();

        if (TokenType.ACCESS_TOKEN.equals(tokenType)) {
            authorities.addAll(
                    authentication.getAuthorities()
                            .stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toUnmodifiableList())
            );
        }

        return Map.of(AUTHORITY_KEY, authorities);
    }

    private Date toExpiration(TokenType tokenType) {
        if (TokenType.ACCESS_TOKEN.equals(tokenType)) {
            return parseSeconds(jwtProperties.getValidityInSeconds().getAccessToken());
        } else if (TokenType.REFRESH_TOKEN.equals(tokenType)) {
            return parseSeconds(jwtProperties.getValidityInSeconds().getRefreshToken());
        } else {
            throw new IllegalArgumentException();
        }
    }

    private Date parseSeconds(Long validityInSeconds) {
        if (Objects.isNull(validityInSeconds)) {
            return null;
        }

        long currentMillis = new Date().getTime();
        long deltaMillis = Duration.ofSeconds(validityInSeconds).toMillis();
        return new Date(currentMillis + deltaMillis);
    }

    public Authentication getAuthenticationFromToken(String token) {
        try {
            return doGetAuthenticationFromToken(token);
        } catch (Exception e) {
            throw new InsufficientAuthenticationException(e.getMessage(), e);
        }
    }

    private Authentication doGetAuthenticationFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return retrieveAuthentication(claims);
    }

    private UsernamePasswordAuthenticationToken retrieveAuthentication(Claims claims) {
        return new UsernamePasswordAuthenticationToken(
                claims.getSubject(), null, retrieveAuthorities(claims)
        );
    }

    private List<GrantedAuthority> retrieveAuthorities(Claims claims) {
        List<String> authorities = (List<String>) claims.get(AUTHORITY_KEY);

        return DomainObjectUtil.nullSafeStream(authorities)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toUnmodifiableList());
    }

    public enum TokenType {
        ACCESS_TOKEN {
            @Override
            public List<String> toAuthorities() {
                return new ArrayList<>(Collections.singletonList(AuthorityConstants.Token.ACCESS));
            }
        },
        REFRESH_TOKEN {
            @Override
            public List<String> toAuthorities() {
                return new ArrayList<>(Collections.singletonList(AuthorityConstants.Token.REFRESH));
            }
        };

        public abstract List<String> toAuthorities();
    }
}
