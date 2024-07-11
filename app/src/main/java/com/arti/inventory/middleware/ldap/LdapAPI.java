package com.arti.inventory.middleware.ldap;

import org.springframework.web.bind.annotation.RestController;

import com.arti.inventory.middleware.ldap.model.LdapEmployee;
import com.arti.inventory.middleware.ldap.repository.LdapEmployeeRepository;
import com.arti.inventory.middleware.ldap.service.EmployeeConverter;
import com.arti.inventory.mission.backend.model.Employee;
import com.arti.inventory.mission.backend.repository.EmployeeRepository;
import com.arti.inventory.mission.backend.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class LdapAPI {

    @Autowired
    LdapEmployeeRepository repository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/ldap")
    public String getMethodName(Authentication authentication) {
        LdapEmployee ldapEmployee = repository.getLdapEmployeeByUsername(authentication.getName());
        Employee employee = EmployeeConverter.convert(ldapEmployee);
        //employeeRepository.save(employee);
        System.out.println(ldapEmployee);
        System.out.println(employee);
        return new String("Hello");
    }

    @GetMapping("/ldap/update")
    public String getUpdqte(Authentication authentication) {
        LdapEmployee ldapEmployee = repository.getLdapEmployeeByUsername(authentication.getName());
        ldapEmployee.setGender("Madame");
        Employee employee = employeeService.addOrUpdatEmployee(ldapEmployee);
        System.out.println(ldapEmployee);
        System.out.println(employee);
        return new String("Hello");
    }
    
}
