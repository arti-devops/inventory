package com.arti.inventory.backend.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import com.arti.inventory.backend.model.Phone;
import com.arti.inventory.backend.repository.PhoneRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PhoneService implements CrudListener<Phone> {

    @Autowired
    private PhoneRepository repository;

    @Override
    public Collection<Phone> findAll() {
        return repository.findAll();
    }

    @Override
    public Phone add(Phone phone) {
        return repository.save(phone);
    }

    @Override
    public Phone update(Phone phone) {
        return repository.save(phone);
    }

    @Override
    public void delete(Phone phone) {
        repository.delete(phone);
    }

}
