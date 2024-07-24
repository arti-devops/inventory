package com.arti.inventory.security;

import org.springframework.security.core.userdetails.UserDetails;
import com.vaadin.flow.spring.security.AuthenticationContext;

public class AuthService {

    private UserDetails user;

    public AuthService(AuthenticationContext auth) {
        this.user = auth.getAuthenticatedUser(UserDetails.class).get();
    }

    public boolean isAdmin() {
        return this.is("ADMIN");
    }

    public boolean isDG(){
        return this.is("DG");
    }

    public boolean isDRH(){
        return this.is("DRH");
    }

    public boolean isAppManager(){
        return this.is("APP_MANAGER");
    }

    public boolean isMissionAppStaff(){
        return this.is("APP_MISSION_STAFF");
    }

    public String getUsername(){
        System.out.println(user.getAuthorities());
        return user.getUsername();
    }

    public boolean is(String role) {
        return user.getAuthorities().stream()
        .anyMatch(grantedAuthority -> String.valueOf("ROLE_" + role).equals(grantedAuthority.getAuthority()));
    }
}
