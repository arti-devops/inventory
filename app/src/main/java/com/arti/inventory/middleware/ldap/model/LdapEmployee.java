package com.arti.inventory.middleware.ldap.model;

import lombok.Data;

@Data
public class LdapEmployee {
    private String matricule; // AD employeeID
    private String username; // sAMAccountName 
    private String fullname; // AD cn
    private String gender; // AD personalTitle 
    private String email; // AD email
    private String category; // AD employeeType
    private String position; // AD title
    private String initials; // AD initials
}
