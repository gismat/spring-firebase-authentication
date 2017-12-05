package com.gismat.test.config.firebase;

import com.gismat.test.domain.Authority;
import com.gismat.test.domain.User;
import com.gismat.test.security.DomainUserDetailsService;
import com.gismat.test.security.jwt.JWTConfigurer;
import com.gismat.test.security.jwt.TokenProvider;
import com.gismat.test.service.UserService;
import com.gismat.test.service.dto.UserDTO;
import com.gismat.test.service.exception.FirebaseUserNotExistsException;
import com.gismat.test.service.shared.RegisterUserInit;
import com.gismat.test.service.util.RandomUserNameGenerator;
import com.gismat.test.web.rest.UserJWTController;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


@Component
public class FirebaseAuthenticationProvider implements AuthenticationProvider {

    public static final String NAME="FirebaseAuthenticationProvider";

    @Autowired
	private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private  AuthenticationManager authenticationManager;

	public boolean supports(Class<?> authentication) {
		return (FirebaseAuthenticationToken.class.isAssignableFrom(authentication));
	}

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (!supports(authentication.getClass())) {
			return null;
		}
		FirebaseAuthenticationToken authenticationToken = (FirebaseAuthenticationToken) authentication;
        User details= userService.loadUserByUID(((FirebaseTokenHolder)authenticationToken.getCredentials()).getUid()).orElse(null);
		if (details == null) {
                details=userService.registerFirebaseUser(
                    ((FirebaseTokenHolder)authenticationToken.getCredentials()).getName(),
                    ((FirebaseTokenHolder)authenticationToken.getCredentials()).getUid(),
                    ((FirebaseTokenHolder)authenticationToken.getCredentials()).getEmail());
        }
        authenticationToken = new FirebaseAuthenticationToken( details , authentication.getCredentials(),
           details.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList()));

        return authenticationToken;
	}

}
