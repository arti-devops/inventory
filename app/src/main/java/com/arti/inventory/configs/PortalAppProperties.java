package com.arti.inventory.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "portal.app")
public class PortalAppProperties {
    private String sygfp;
    private String emeraude;
    private String tableaux;
    private String conges;
    private String equipements;
    private String reservation;
    private String missions;
    private String immobilisations;
    private String parc;
    private String fournisseurs;
    private String sante;
}
