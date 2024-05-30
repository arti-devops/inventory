package com.arti.inventory.mission.backend.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    private Long hotelFees;
    private Long ressortExpenses;
    @Column(name = "departure_date")
    private Date dateOfDeparture;
    @Column(name = "return_date")
    private Date dateOfReturn;
    private Long numberOfDays;
    @Enumerated(EnumType.STRING)
    private Transportation transportation;
    @Enumerated(EnumType.STRING)
    private Mobility mobility;
    private Long mobilityGasFees;
    private Long totalBudget;
}
