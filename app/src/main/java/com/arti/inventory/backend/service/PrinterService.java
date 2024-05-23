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
public class PrinterService implements CrudListener<Printer>, DeviceService {

    @Autowired
    private PrinterRepository repository;

    public PrinterService(PrinterRepository printerRepository) {
        this.repository = printerRepository;
    }

    @Override
    public Collection<Printer> findAll() {
        List<Printer> printers = repository.findAll();
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
        return getDetails(printer);
    }

    private Printer getDetails(Printer printer) {
        if (printer == null) {
            System.err.println("Printer is null");
            return null;
        }
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        //TODO Fix the URL
        PrinterDetail details = restTemplate.getForObject("http://aapi:3005/printers/"+printer.getId(), PrinterDetail.class);
        printer.setOnline(details.status());
        printer.setDetails(details);
        return printer;
    }

    @Override
    public Integer getDeviceCount() {
        return repository.findAll().size();
    }
}
