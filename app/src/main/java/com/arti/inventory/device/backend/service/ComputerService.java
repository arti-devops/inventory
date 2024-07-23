package com.arti.inventory.device.backend.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import com.arti.inventory.device.backend.model.Computer;
import com.arti.inventory.device.backend.repository.ComputerRepository;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

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
        Collection<Computer> computers = repository.findAll();
        computers.forEach(computer -> {
            try {
                Integer ipToInt = AddressService.IpToInteger(computer.getIp());
                computer.setIpToInteger(ipToInt);
            } catch (Exception e) {
                Notification error = new Notification("Une ou plusieurs adresses IP sont invalides");
                error.setDuration(5000);
                error.addThemeVariants(NotificationVariant.LUMO_ERROR);
                error.open();
            }
        });
        return computers;
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
