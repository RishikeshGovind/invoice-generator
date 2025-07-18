package com.rishi.invoicegeneratorapi.security;

import java.io.IOException;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;


@Component
@RequiredArgsConstructor
public class ClerkJwtAuthenticationFilter extends OncePerRequestFilter{
    
    @Value("${clerk.issuer}")
    private String clerkIssuer;

    private final ClerkJwksProvider jwksProvider;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, 
                                    @NonNull HttpServletResponse response, 
                                    @NonNull FilterChain filterChain)
                                throws ServletException, IOException {

        
        // TODO implement the auth filter
        if(request.getRequestURI().contains("/api/webhooks")){
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Authorization header is missing or invalid");
            return;
        }

        try {
            String token = authHeader.substring(7);
            
            String[] chunks = token.split("\\.");
            String headerJson = new String(Base64.getUrlDecoder().decode(chunks[0]));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode headerNode = mapper.readTree(headerJson);
            String kid = headerNode.get("kid").asText();

            PublicKey publicKey = jwksProvider.getPublicKey(kid);
            
            Claims claims = Jwts.parserBuilder()
                                .setSigningKey(publicKey)
                                .setAllowedClockSkewSeconds(60)
                                .requireIssuer(clerkIssuer)
                                .build()
                                .parseClaimsJws(token)
                                .getBody();
            
            String clerkUserId = claims.getSubject();
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                clerkUserId, 
                null, 
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );

            // ✅ Set the authentication into the SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            System.out.println("Auth header: " + authHeader);
            System.out.println("Token: " + token);
            System.out.println("Claims: " + claims);

            //continue processing
            filterChain.doFilter(request, response);
        } catch(Exception e){
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid JWT token");
        }

    }  


}
