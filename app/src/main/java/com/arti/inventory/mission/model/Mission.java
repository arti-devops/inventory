package com.arti.inventory.mission.model;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subject;
    @Column(name = "mission_type")
    @Enumerated(EnumType.STRING)
    private MissionType type;
    private String location;
    @Column(name = "departure_date")
    private String dateOfdeparture;
    @Column(name = "return_date")
    private String dateOfReturn;
    private String numberOfDays;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Long totalBudget;
}
