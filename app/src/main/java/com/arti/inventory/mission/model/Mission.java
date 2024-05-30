package com.arti.inventory.mission.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
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
    private Date dateOfdeparture;
    @Column(name = "return_date")
    private Date dateOfReturn;
    private Long numberOfDays;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Long totalBudget;
}
