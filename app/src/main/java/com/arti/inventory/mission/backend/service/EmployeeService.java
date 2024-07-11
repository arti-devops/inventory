package com.arti.inventory.mission.backend.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import com.arti.inventory.middleware.ldap.model.LdapEmployee;
import com.arti.inventory.middleware.ldap.service.EmployeeConverter;
import com.arti.inventory.mission.backend.model.Employee;
import com.arti.inventory.mission.backend.repository.EmployeeRepository;

@Service
public class EmployeeService implements CrudListener<Employee> {

    @Autowired
    private EmployeeRepository repository;

    @Override
    public Collection<Employee> findAll() {
        return repository.findAll();   
    }

    @Override
    public Employee add(Employee domainObjectToAdd) {
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    public Employee addOrUpdatEmployee(LdapEmployee ldapEmployee){
        Employee employeeToSave = EmployeeConverter.convert(ldapEmployee);
        Optional<Employee> employeeInDb = repository.findByUsername(employeeToSave.getUsername());
        if (employeeInDb.isPresent()) {
            System.out.println("Updating employee {}");
            System.out.println(employeeInDb.get());
            employeeToSave.setId(employeeInDb.get().getId());
            return repository.save(employeeToSave);
        }
        System.out.println("Saving employee");
        System.out.println(employeeToSave);
        return repository.save(employeeToSave);
    }

    @Override
    public Employee update(Employee domainObjectToUpdate) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Employee domainObjectToDelete) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

}
