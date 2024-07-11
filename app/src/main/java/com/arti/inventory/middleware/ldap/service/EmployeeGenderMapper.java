package com.arti.inventory.middleware.ldap.service;

import com.arti.inventory.mission.backend.model.EmployeeGender;

public class EmployeeGenderMapper {
    public static EmployeeGender mapGender(String ldapGender) {
        if (ldapGender == null) {
            throw new IllegalArgumentException("LDAP gender cannot be null");
        }

        switch (ldapGender.toUpperCase()) {
            case "M":
            case "Monsieur":
            case "MONSIEUR":
                return EmployeeGender.M;
            case "F":
            case "Madame":
            case "MADAME":
                return EmployeeGender.F;
            default:
                throw new IllegalArgumentException("Unknown gender: " + ldapGender);
        }
    }
}
