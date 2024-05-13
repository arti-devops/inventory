package com.arti.inventory.backend.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import com.arti.inventory.backend.model.Printer;
import com.arti.inventory.backend.repository.PrinterRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrinterService implements CrudListener<Printer> {

    @Autowired
    private PrinterRepository repository;

    public PrinterService(PrinterRepository printerRepository) {
        this.repository = printerRepository;
    }

    @Override
    public Collection<Printer> findAll() {
        return repository.findAll();
    }

    @Override
    public Printer add(Printer printer) {
        return repository.save(printer);
    }

    @Override
    public Printer update(Printer printer) {
        return repository.save(printer);
    }

    @Override
    public void delete(Printer printer) {
        repository.delete(printer);
    }

}
