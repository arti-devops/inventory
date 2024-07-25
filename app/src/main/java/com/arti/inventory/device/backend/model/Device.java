package com.arti.inventory.device.backend.model;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String ip;

    private String brand;

    @NotNull
    @Column(name = "connexion_mode")
    @Enumerated(EnumType.STRING)
    private ConnexionMode connexionMode;

    private String serie;

    private String direction;

    @NotNull
    @NotEmpty
    @Column(name = "assigned_to")
    private String assignedTo;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate = LocalDate.now();

    private Boolean online = false;
}
