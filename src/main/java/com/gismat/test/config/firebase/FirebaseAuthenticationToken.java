package com.gismat.test.config.firebase;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * UsernamePasswordAuthenticationToken
 *
 * @author prvoslav
 *
 */
public class FirebaseAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = -1869548136546750302L;
	private final Object token;
	private Object credentials;

	/**
	 * This constructor can be safely used by any code that wishes to create a
	 * <code>UsernamePasswordAuthenticationToken</code>, as the
	 * {@link #isAuthenticated()} will return <code>false</code>.
	 *
	 */
	public FirebaseAuthenticationToken(Object principal, Object credentials) {
		super(null);
		this.token = principal;
		this.credentials = credentials;
		setAuthenticated(false);
	}

	/**
	 * This constructor should only be used by
	 * <code>AuthenticationManager</code> or <code>AuthenticationProvider</code>
	 * implementations that are satisfied with producing a trusted (i.e.
	 * {@link #isAuthenticated()} = <code>true</code>) authentication token.
	 *
	 * @param principal
	 * @param credentials
	 * @param authorities
	 */
	public FirebaseAuthenticationToken(Object principal, Object credentials,
                                       Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.token = principal;
		this.credentials = credentials;
		super.setAuthenticated(true); // must use super, as we override
	}
	// ~ Methods
	// ========================================================================================================

	public Object getCredentials() {
		return this.credentials;
	}

	public Object getPrincipal() {
		return token;
	}

	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		if (isAuthenticated) {
			throw new IllegalArgumentException(
					"Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
		}

		super.setAuthenticated(false);
	}



    @Override
    public String toString() {
        return "FirebaseAuthenticationToken{" +
            "principal=" + token +
            ", credentials=" + credentials +
            '}';
    }

    @Override
	public void eraseCredentials() {
		super.eraseCredentials();
		credentials = null;
	}
}
