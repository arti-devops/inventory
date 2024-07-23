package com.arti.inventory.middleware.ldap.service;

import com.arti.inventory.middleware.ldap.model.LdapEmployee;
import com.arti.inventory.mission.backend.model.Employee;

public class EmployeeConverter {
    public static Employee convert(LdapEmployee ldapEmployee) {
        Employee employee = new Employee();
        employee.setMatricule(ldapEmployee.getMatricule());
        employee.setUsername(ldapEmployee.getUsername());
        employee.setFirstName(extractFirstName(ldapEmployee.getFullname()));
        employee.setLastName(extractLastName(ldapEmployee.getFullname()));
        employee.setFullname(ldapEmployee.getFullname());
        employee.setPosition(ldapEmployee.getPosition());
        employee.setEmail(ldapEmployee.getEmail());
        employee.setCategory(EmployeeCategoryMapper.mapCategory(ldapEmployee.getCategory()));
        employee.setGender(EmployeeGenderMapper.mapGender(ldapEmployee.getGender()));
        employee.setInitials(ldapEmployee.getInitials());
        // Set other fields as necessary
        return employee;
    }

    private static String extractFirstName(String fullname) {
        // Implement logic to extract first name from fullname
        return fullname.split(" ")[0];
    }

    private static String extractLastName(String fullname) {
        // Implement logic to extract last name from fullname
        try {
            return fullname.split(" ")[1];
        } catch (Exception e) {
            return fullname;
        }
    }
}
