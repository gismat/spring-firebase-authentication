package com.gismat.test.config.firebase;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gismat.test.security.jwt.JWTConfigurer;
import com.gismat.test.security.jwt.TokenProvider;
import com.gismat.test.web.rest.UserJWTController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class FirebaseBasicAuthenticationFilter extends BasicAuthenticationFilter {

    private final Logger log = LoggerFactory.getLogger(UserJWTController.class);
    private final TokenProvider tokenProvider;

    @Autowired
    public FirebaseBasicAuthenticationFilter(AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
        super(authenticationManager);
        this.tokenProvider=tokenProvider;
    }

    public FirebaseBasicAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint, TokenProvider tokenProvider) {
        super(authenticationManager, authenticationEntryPoint);
        this.tokenProvider=tokenProvider;

    }



    @Override
    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException {
       try{
        String jwt = tokenProvider.createToken(SecurityContextHolder.getContext().getAuthentication(), true);
        response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
    } catch (AuthenticationException ae) {
        log.trace("Authentication exception trace: {}", ae);
    }
    }

    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
