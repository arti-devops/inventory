package com.arti.inventory.backend.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vaadin.crudui.crud.CrudListener;

import com.arti.inventory.backend.model.Printer;
import com.arti.inventory.backend.model.PrinterDetail;
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
        List<Printer> printers = repository.findAll().subList(1, 12);
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        printers.forEach(printer -> {
            PrinterDetail details = restTemplate.getForObject("http://api:3000/printers/"+printer.getId(), PrinterDetail.class);
            printer.setOnline(details.status());
            printer.setDetails(details);
        });
        return printers;
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

    public Printer findOne(Long id) {
        Printer printer = repository.findById(id).orElse(null);
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        PrinterDetail details = restTemplate.getForObject("http://api:3000/printers/"+printer.getId(), PrinterDetail.class);
        printer.setDetails(details);
        return printer;
    }
}
