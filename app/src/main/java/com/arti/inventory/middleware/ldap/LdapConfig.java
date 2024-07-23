package com.arti.inventory.middleware.ldap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
public class LdapConfig {

    @Bean
    LdapContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        // TODO load these creads form properties file
        contextSource.setUrl("ldap://192.168.0.53:389/");
        contextSource.setBase("dc=arti,dc=local");
        contextSource.setUserDn("CN=Administrateur,OU=DSI,OU=ARTI,DC=arti,DC=local");
        contextSource.setPassword("tranSPort2021!");
        return contextSource;
    }

    @Bean
    LdapTemplate ldapTemplate() {
        return new LdapTemplate(contextSource());
    }
}
