package com.arti.inventory.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.vaadin.flow.spring.security.VaadinWebSecurity;

@EnableWebSecurity
@Configuration
@ConditionalOnProperty(name = "security.type", havingValue = "ldap")
public class SecurityConfigLdap extends VaadinWebSecurity {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
                http.authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/images/**").permitAll()
                                .requestMatchers("/icons/**").permitAll());
                super.configure(http);
                setLoginView(http, LoginView.class);
        }

        @Override
        protected void configure(WebSecurity web) throws Exception {
                web
                                .ignoring()
                                .requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
                super.configure(web);
        }

        @Bean
        ActiveDirectoryLdapAuthenticationProvider authenticationProvider() {
                return new ActiveDirectoryLdapAuthenticationProvider("arti.local", "ldap://192.168.0.53");
        }
}
