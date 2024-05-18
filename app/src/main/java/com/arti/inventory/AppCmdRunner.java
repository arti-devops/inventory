package com.arti.inventory;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.arti.inventory.backend.model.Computer;
import com.arti.inventory.backend.model.Phone;
import com.arti.inventory.backend.model.Printer;
import com.arti.inventory.backend.model.PrinterDetail;
import com.arti.inventory.backend.repository.ComputerRepository;
import com.arti.inventory.backend.repository.PhoneRepository;
import com.arti.inventory.backend.repository.PrinterRepository;

@Component
@Profile("dev")
public class AppCmdRunner implements CommandLineRunner{

    private Logger logger = org.slf4j.LoggerFactory.getLogger(AppCmdRunner.class);

    @Autowired
    ComputerRepository repository;

    @Autowired
    PhoneRepository phoneRepository;

    @Autowired
    PrinterRepository printerRepository;

    @Override
    public void run(String... args) throws Exception {
        Computer computer = repository.findAll().get(1);
        System.out.println(computer);

        Phone phone = phoneRepository.findAll().get(1);
        System.out.println(phone);

        testApiCall();
    }

    private void testApiCall(){
        // Call the API here
        List<Printer> printers = printerRepository.findAll().subList(10, 11);
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        String url = "http://api:3000/printers/";

        printers.forEach(printer -> {
            PrinterDetail detail = restTemplate.getForObject(url+printer.getId(), PrinterDetail.class);
            printer.setDetails(detail);
            logger.warn("Printer: {} details {}", printer, printer.getDetails());
        });
    }

}
