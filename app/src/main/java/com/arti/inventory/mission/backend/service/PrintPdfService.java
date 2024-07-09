package com.arti.inventory.mission.backend.service;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arti.inventory.middleware.GeneralUtils;
import com.arti.inventory.middleware.NumberToFrenchWords;
import com.arti.inventory.middleware.PdfService;
import com.arti.inventory.mission.backend.model.Member;
import com.arti.inventory.mission.backend.model.Mission;
import com.vaadin.flow.server.StreamResource;

@Service
public class PrintPdfService {

    @Autowired
    private PdfService pdfService;

    public StreamResource printMissionDetailsFile(Mission mission, Member member){
        Map<String, String> fieldValues = getMissionDetailsFieldValues(mission, member);

        try {
            byte[] pdfBytes = pdfService.fillPdfForm(fieldValues, "fiche-de-mission");
            String fullname = member.getEmployee().getLastName()+member.getEmployee().getFirstName();
            StreamResource resource = new StreamResource(String.format("FM-%s.pdf", fullname), () -> new ByteArrayInputStream(pdfBytes));
            resource.setContentType("application/pdf");
            return resource;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public StreamResource printMissionOrderFile(Mission mission, Member member){
        Map<String, String> fieldValues = getMissionOrderFieldValues(mission, member);

        try {
            byte[] pdfBytes = pdfService.fillPdfForm(fieldValues, "ordre-de-mission");
            StreamResource resource = new StreamResource(String.valueOf("OD-CISSE.pdf"), () -> new ByteArrayInputStream(pdfBytes));
            resource.setContentType("application/pdf");
            return resource;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> getMissionDetailsFieldValues(Mission mission, Member member) {
        Map<String, String> fieldValues = new HashMap<>();

        fieldValues.put("dateField", GeneralUtils.formatDate(LocalDate.now().toString()).toUpperCase());
        //TODO proper numbering
        fieldValues.put("refField", "366/"+LocalDate.now().getYear());
        //TODO Loggedin user initals
        fieldValues.put("userField", "CAAM");
        fieldValues.put("memberField", member.getEmployee().getGender()+" "+member.getEmployee().getLastName()+" "+member.getEmployee().getFirstName());
        fieldValues.put("matriculeField", member.getEmployee().getMatricule());
        fieldValues.put("positionField", member.getEmployee().getDepartment());
        fieldValues.put("locationField", mission.getType()+" ("+mission.getLocation()+")");
        fieldValues.put("subjectField", mission.getSubject());
        fieldValues.put("transportationField", member.getTransportation().toString());
        fieldValues.put("departureDateField", GeneralUtils.formatDateFull(member.getDateOfDeparture().toString()));
        fieldValues.put("returnDateField", GeneralUtils.formatDateFull(member.getDateOfReturn().toString()));
        fieldValues.put("numberOfDaysField", member.getNumberOfDays().toString());
        String hftotal = GeneralUtils.formatMoneyNumber(member.getHotelFees() * member.getNumberOfDays());
        fieldValues.put("hotelFeesField", GeneralUtils.formatMoneyNumber(member.getHotelFees())+" * "+member.getNumberOfDays().toString()+" = "+hftotal+" Francs CFA");
        String rftotal = GeneralUtils.formatMoneyNumber(member.getRessortExpenses() * member.getNumberOfDays());
        fieldValues.put("ressortFeesField", GeneralUtils.formatMoneyNumber(member.getRessortExpenses())+" * "+member.getNumberOfDays().toString()+" = "+rftotal+" Francs CFA");
        String str = NumberToFrenchWords.convert(member.getTotalBudget());
        fieldValues.put("globalFeesField", str.substring(0, 1).toUpperCase() + str.substring(1) +" ("+GeneralUtils.formatMoneyNumber(member.getTotalBudget())+") Francs CFA");
        fieldValues.put("budgetImputationField", "ARTI");

        return fieldValues;
    }

    public Map<String, String> getMissionOrderFieldValues(Mission mission, Member member) {
        Map<String, String> fieldValues = new HashMap<>();

        fieldValues.put("dateField", GeneralUtils.formatDate(LocalDate.now().toString()).toUpperCase());
        //TODO proper numbering
        fieldValues.put("refField", "366/"+LocalDate.now().getYear());
        //TODO Loggedin user initals
        fieldValues.put("userField", "CAAM");
        fieldValues.put("memberField", member.getEmployee().getGender()+" "+member.getEmployee().getLastName()+" "+member.getEmployee().getFirstName());
        fieldValues.put("matriculeField", member.getEmployee().getMatricule());
        fieldValues.put("positionField", member.getEmployee().getDepartment());
        fieldValues.put("locationField", mission.getType()+" ("+mission.getLocation()+")");
        fieldValues.put("subjectField", mission.getSubject());
        fieldValues.put("transportationField", member.getTransportation().toString());
        fieldValues.put("departureDateField", GeneralUtils.formatDateFull(member.getDateOfDeparture().toString()));
        fieldValues.put("returnDateField", GeneralUtils.formatDateFull(member.getDateOfReturn().toString()));

        return fieldValues;
    }
}
