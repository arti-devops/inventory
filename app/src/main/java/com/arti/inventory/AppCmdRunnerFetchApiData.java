package com.arti.inventory;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.arti.inventory.device.backend.model.Printer;
import com.arti.inventory.device.backend.model.PrinterDetail;
import com.arti.inventory.device.backend.repository.PrinterRepository;

@Component
public class AppCmdRunnerFetchApiData implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PrinterRepository printerRepository;

    @Override
    public void run(String... args) throws Exception {
        fetchPrintersDetails();
        // logger.info(printerRepository.findById(1L).toString());
    }

    private void fetchPrintersDetails() {
        List<Printer> printers = printerRepository.findAll();

        RestTemplate restTemplate = new RestTemplateBuilder().build();
        String url = "http://api:3000/ipp?ip=";

        logger.warn("Feching printers API data");

        try {
            printers.forEach(printer -> {
                PrinterDetail details = restTemplate.getForObject(url + printer.getIp(), PrinterDetail.class);
                printer.setPrinterDetails(details);
                printerRepository.save(printer);
                // logger.warn("Printer #{} updated", printer.getId());
            });
            logger.warn("DONE: Feching printers API data");
        } catch (Exception e) {
            logger.error("ERROR: Feching printers API data FAILLED");
            logger.error(e.getMessage());
        }
    }

}
