package com.arti.inventory.backend.service;

import java.util.List;
import java.util.Optional;

import com.arti.inventory.backend.model.Device;

public interface DeviceService<T extends Device> {

    public List<T> findAll();
    public Optional<T> findOne(Long id);
    public T save(T device);
    public void delete(T device);

}
