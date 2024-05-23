package com.arti.inventory.backend.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import com.arti.inventory.backend.model.Computer;
import com.arti.inventory.backend.repository.ComputerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComputerService implements CrudListener<Computer>, DeviceService {

    @Autowired
    private ComputerRepository repository;

    @Override
    public Computer add(Computer computer) {
        return repository.save(computer);
    }

    @Override
    public void delete(Computer computer) {
        repository.delete(computer);
    }

    @Override
    public Collection<Computer> findAll() {
        return repository.findAll();
    }

    @Override
    public Computer update(Computer computer) {
        return repository.save(computer);
    }

    @Override
    public Integer getDeviceCount() {
        return repository.findAll().size();
    }
}
