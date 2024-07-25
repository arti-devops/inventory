package com.arti.inventory;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.arti.inventory.device.backend.model.Printer;
import com.arti.inventory.device.backend.model.PrinterDetail;
import com.arti.inventory.device.backend.repository.PrinterRepository;

@Component
@EnableScheduling
public class AppCmdRunnerFetchApiData implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppCmdRunnerFetchApiData.class);

    @Autowired
    private PrinterRepository printerRepository;

    private void fetchPrinterDetailsFromApi(Printer printer) {
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        String url = "http://api:3000/ipp?ip=";
        
        try {
            PrinterDetail details = restTemplate.getForObject(url + printer.getIp(), PrinterDetail.class);
            printer.setPrinterDetails(details);
            printerRepository.save(printer);
            logger.warn("Printer #{} updated", printer.getId());
        } catch (Exception e) {
            logger.error("Error updating printer #{}", printer.getId());
            logger.error(e.getMessage());
        }
    }

    // Method to fetch details for all printers
    private void fetchPrintersDetails() {
        List<Printer> printers = printerRepository.findAll();
        
        logger.warn("Fetching printers API data");
        
        printers.forEach(this::fetchPrinterDetailsFromApi);
        
        logger.warn("DONE: Fetching printers API data");
    }

    // Scheduled method to run every 5 minutes
    @Scheduled(fixedRate = 900000) // 300000 milliseconds = 15 minutes
    public void fetchAndStorePrinterDetailsScheduled() {
        fetchPrintersDetails();
    }

    @Override
    public void run(String... args) throws Exception {
        // This method is executed once at application startup
        //fetchPrintersDetails(); // Initially fetch printer details
    }
}