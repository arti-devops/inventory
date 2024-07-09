package com.arti.inventory.mission.backend.model;

public enum EmployeeGender {
    
    M("Monsieur"), F("Madame");

    private final String customValue;

    EmployeeGender(String customValue) {
        this.customValue = customValue;
    }

    @Override
    public String toString() {
        return customValue;
    }
}
