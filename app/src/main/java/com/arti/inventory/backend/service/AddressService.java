package com.arti.inventory.backend.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import com.arti.inventory.backend.model.Address;
import com.arti.inventory.backend.repository.AddressRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService implements CrudListener<Address> {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Collection<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public Address add(Address domainObjectToAdd) {
        return addressRepository.save(domainObjectToAdd);
    }

    @Override
    public Address update(Address domainObjectToUpdate) {
        return addressRepository.save(domainObjectToUpdate);
    }

    @Override
    public void delete(Address domainObjectToDelete) {
        addressRepository.delete(domainObjectToDelete);
    }

}
