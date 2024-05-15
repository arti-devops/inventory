package com.arti.inventory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.arti.inventory.backend.model.Computer;
import com.arti.inventory.backend.model.Phone;
import com.arti.inventory.backend.model.PrinterDetail;
import com.arti.inventory.backend.repository.ComputerRepository;
import com.arti.inventory.backend.repository.PhoneRepository;


@Component
public class AppCmdRunner implements CommandLineRunner{

    private static final Logger log = LoggerFactory.getLogger(AppCmdRunner.class);

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

        RestTemplate restTemplate = new RestTemplateBuilder().build();
        PrinterDetail printerDetail = restTemplate.getForObject("https://my.api.mockaroo.com/printers/95.json?key=c64e6880", PrinterDetail.class);
        log.warn(printerDetail.toString());
    }

}
