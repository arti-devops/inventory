package com.arti.inventory.mission.backend.model;

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
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String username;
    private String lastName;
    private String fullname;
    private String department;
    private String position;
    private String email;
    private String photoUrl;
    private String matricule;
    @Enumerated(EnumType.STRING)
    private EmployeeCategory category = EmployeeCategory.H;
    @Enumerated(EnumType.STRING)
    private EmployeeGender gender = EmployeeGender.M;
}
