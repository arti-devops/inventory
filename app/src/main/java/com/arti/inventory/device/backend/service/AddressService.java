package com.arti.inventory.device.backend.service;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import com.arti.inventory.device.backend.model.Address;
import com.arti.inventory.device.backend.model.Computer;
import com.arti.inventory.device.backend.model.Device;
import com.arti.inventory.device.backend.model.Phone;
import com.arti.inventory.device.backend.model.Printer;
import com.arti.inventory.device.backend.repository.AddressRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService implements CrudListener<Address>, DeviceService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PrinterService printerService;

    @Autowired
    private ComputerService computerService;

    @Autowired
    private PhoneService phoneService;

    @Override
    public Collection<Address> findAll() {
        
        Collection<Address> ipAddresses = new ArrayList<>();
        
        Collection<Printer> printers = printerService.findAll();
        Collection<Computer> computers = computerService.findAll();
        Collection<Phone> phones = phoneService.findAll();

        createAndFillDevice(ipAddresses, printers, "IMPRIMANTE");
        createAndFillDevice(ipAddresses, computers, "ORDINATEUR");
        createAndFillDevice(ipAddresses, phones, "TELEPHONE IP");
        
        return ipAddresses;
    }

    @Override
    public Address add(Address domainObjectToAdd) {
        return addressRepository.save(domainObjectToAdd);
    }

    @Override
    public Address update(Address domainObjectToUpdate) {
        return addressRepository.save(domainObjectToUpdate);
    }

    @Override
    public void delete(Address domainObjectToDelete) {
        addressRepository.delete(domainObjectToDelete);
    }

    @Override
    public Integer getDeviceCount() {
        return addressRepository.findAll().size();
    }

    public static Integer IpToInteger(String ip) {
        String[] ipArray = ip.split("\\.");
        return Integer.parseInt(ipArray[0]) * 256 * 256 * 256 + Integer.parseInt(ipArray[1]) * 256 * 256
                + Integer.parseInt(ipArray[2]) * 256 + Integer.parseInt(ipArray[3]);
    }

    private void createAndFillDevice(Collection<Address> ipAddresses, Collection<? extends Device> printers, String deviceType) {
        printers.forEach(printer -> {
            Address address = new Address();
            address.setHostname(printer.getName());
            address.setIpv4(printer.getIp());
            address.setIpv4ToIngeter(AddressService.IpToInteger(printer.getIp()));
            address.setDeviceType(deviceType);
            address.setConnexionMode(printer.getConnexionMode());
            address.setAssignedTo(printer.getAssignedTo());
            address.setDirection(printer.getDirection());
            address.setOnline(printer.getOnline());
            ipAddresses.add(address);
        });
    }

}
