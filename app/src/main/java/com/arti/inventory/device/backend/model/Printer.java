package com.arti.inventory.device.backend.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Printer extends Device {

    private String label;
    
    private String ink1;
    private String ink2;
    private String ink3;
    private String ink4;

    @OneToOne(mappedBy = "printer", cascade = CascadeType.ALL)
    private PrinterDetails printerDetails;

    private transient PrinterDetail details;

    public void setPrinterDetails(PrinterDetail details) {
        this.setOnline(details.status());
        PrinterDetails pDetails = new PrinterDetails();
        pDetails.setOnline(details.status());
        pDetails.setBlack(details.color().black());
        pDetails.setCyan(details.color().cyan());
        pDetails.setMagenta(details.color().magenta());
        pDetails.setYellow(details.color().yellow());
        if (this.printerDetails!=null) {
            pDetails.setId(this.printerDetails.getId());
        }
        this.printerDetails = pDetails;
        printerDetails.setPrinter(this);
    }

}
