package com.arti.inventory.auto.backend.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import com.arti.inventory.auto.backend.model.Vehicule;
import com.arti.inventory.auto.backend.repository.VehiculeRepository;

@Service
public class VehiculeService implements CrudListener<Vehicule>{

    @Autowired
    private VehiculeRepository repository;

    @Override
    public Vehicule add(Vehicule arg0) {
        return repository.save(arg0);
    }

    @Override
    public void delete(Vehicule arg0) {
        repository.delete(arg0);
    }

    @Override
    public Collection<Vehicule> findAll() {
        return repository.findAll();
    }

    @Override
    public Vehicule update(Vehicule arg0) {
        return repository.save(arg0);
    }

}
