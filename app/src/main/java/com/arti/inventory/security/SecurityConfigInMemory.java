package com.arti.inventory.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import com.vaadin.flow.spring.security.VaadinWebSecurity;

@EnableWebSecurity
@Configuration
@ConditionalOnProperty(name = "security.type", havingValue = "inmemory")
public class SecurityConfigInMemory extends VaadinWebSecurity {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
                http.authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/images/**").permitAll()
                                .requestMatchers("/icons/**").permitAll());
                super.configure(http);
                setLoginView(http, LoginView.class);
        }

        @Bean
        UserDetailsManager userDetailsManager() {
                var admin = User.withUsername("admin")
                                .password("{noop}password")
                                .roles("ADMIN")
                                .build();
                var dg = User.withUsername("dg")
                                .password("{noop}password")
                                .roles("DG")
                                .build();
                var rh = User.withUsername("rh")
                                .password("{noop}password")
                                .roles("RH")
                                .build();
                var staff = User.withUsername("agent")
                                .password("{noop}password")
                                .roles("STAFF")
                                .build();
                var user = User.withUsername("user")
                                .password("{noop}password")
                                .roles("USER")
                                .build();
                return new InMemoryUserDetailsManager(admin, dg, rh, staff, user);
        }
}
