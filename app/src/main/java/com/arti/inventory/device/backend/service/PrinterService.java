package com.arti.inventory.device.backend.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import com.arti.inventory.device.backend.model.DeviceStats;
import com.arti.inventory.device.backend.model.Printer;
import com.arti.inventory.device.backend.repository.PrinterRepository;

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
        //printers.forEach(printer -> setDetails(printer));
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
        return repository.findById(id).orElse(null);
        //return setDetails(printer);
    }

    @Override
    public Integer getDeviceCount() {
        return repository.findAll().size();
    }

    public DeviceStats countOnlinePrinters() {
        List<Printer> printers = repository.findAll();
        //printers.forEach(this::setDetails);
        List<Printer> onlinePrinters = printers.stream()
                        //.map(this::setDetails)
                        .filter(printer -> printer.getPrinterDetails().isOnline())
                        .collect(Collectors.toList());
        DeviceStats stats = new DeviceStats(printers.size(), onlinePrinters.size(), printers.size() - onlinePrinters.size());
        return stats;
    }
}
