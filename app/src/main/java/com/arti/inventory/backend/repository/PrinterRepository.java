package com.arti.inventory.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arti.inventory.backend.model.Printer;

public interface PrinterRepository extends JpaRepository<Printer, Long>{

}
