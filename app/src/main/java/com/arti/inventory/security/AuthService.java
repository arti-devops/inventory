package com.arti.inventory.security;

import org.springframework.security.core.userdetails.UserDetails;
import com.vaadin.flow.spring.security.AuthenticationContext;

public class AuthService {

    private AuthenticationContext auth;

    private UserDetails user;

    public AuthService(AuthenticationContext auth) {
        this.auth = auth;
        initUser();
    }

    private void initUser() {
        this.user = auth.getAuthenticatedUser(UserDetails.class).get();
    }

    public boolean isAdmin() {
        return user.getAuthorities().stream()
                .anyMatch(grantedAuthority -> "ROLE_ADMIN".equals(grantedAuthority.getAuthority()));
    }

    public boolean is(String role) {
        return user.getAuthorities().stream()
                .anyMatch(grantedAuthority -> String.valueOf("ROLE_" + role).equals(grantedAuthority.getAuthority()));
    }

    public String getUsername(){
        return user.getUsername();
    }
}
