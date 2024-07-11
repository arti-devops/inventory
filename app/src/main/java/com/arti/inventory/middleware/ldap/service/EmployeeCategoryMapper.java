package com.arti.inventory.middleware.ldap.service;

import com.arti.inventory.mission.backend.model.EmployeeCategory;

public class EmployeeCategoryMapper {
    public static EmployeeCategory mapCategory(String ldapCategory) {
        if (ldapCategory==null) {
            return EmployeeCategory.H;
        }
        switch (ldapCategory) {
            case "PR-CR":
                return EmployeeCategory.A;
            case "DG":
                return EmployeeCategory.B;
            case "MEMBRE-CR":
                return EmployeeCategory.C;
            case "DIRECTEUR":
                return EmployeeCategory.D;
            case "SOUS-DIRECTEUR":
                return EmployeeCategory.E;
            case "CHEF DE SERVICE":
                return EmployeeCategory.F;
            case "CADRE":
                return EmployeeCategory.G;
            case "EMPLOYEE":
            default:
                return EmployeeCategory.H;
        }
    }
}

