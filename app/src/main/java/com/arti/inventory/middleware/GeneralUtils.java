package com.arti.inventory.middleware;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GeneralUtils {
    public static final String formatDate(String departure) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(departure, formatter);
        return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
    
    }
    public static final String formatDateFull(String departure) {
        departure = departure.split(" ")[0];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(departure, formatter);
        return date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
    }

    public static String formatMoneyNumber(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        String formattedNumber = decimalFormat.format(number);
        
        return formattedNumber;
    }
}

