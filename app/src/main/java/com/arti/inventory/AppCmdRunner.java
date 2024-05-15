package com.arti.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.arti.inventory.backend.model.Computer;
import com.arti.inventory.backend.model.Phone;
import com.arti.inventory.backend.repository.ComputerRepository;
import com.arti.inventory.backend.repository.PhoneRepository;

@Component
public class AppCmdRunner implements CommandLineRunner{

    @Autowired
    ComputerRepository repository;

    @Autowired
    PhoneRepository phoneRepository;

    @Override
    public void run(String... args) throws Exception {
        Computer computer = repository.findAll().get(1);
        System.out.println(computer);

        Phone phone = phoneRepository.findAll().get(1);
        System.out.println(phone);
    }

}
