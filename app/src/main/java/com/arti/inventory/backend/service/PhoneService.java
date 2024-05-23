package com.arti.inventory.backend.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vaadin.crudui.crud.CrudListener;

import com.arti.inventory.backend.model.DeviceOnlineStatus;
import com.arti.inventory.backend.model.Phone;
import com.arti.inventory.backend.repository.PhoneRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PhoneService implements CrudListener<Phone>, DeviceService {

    @Autowired
    private PhoneRepository repository;

    @Override
    public Collection<Phone> findAll() {
        Collection<Phone> phones = repository.findAll();
        phones.forEach(phone -> setDetails(phone));
        return phones;
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

    @Override
    public Integer getDeviceCount() {
        return repository.findAll().size();
    }

    public Phone setDetails(Phone phone) {
        if (phone == null) {
            System.err.println("Phone is null");
            return null;
        }
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        DeviceOnlineStatus status = restTemplate.getForObject("http://api:3000/devices/status?ip=" + phone.getIp(), DeviceOnlineStatus.class);
        phone.setOnline(status.status());
        return phone;
    }

}
