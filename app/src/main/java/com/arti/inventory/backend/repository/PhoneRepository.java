package com.arti.inventory.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arti.inventory.backend.model.Phone;

public interface PhoneRepository extends JpaRepository<Phone, Long> {

}
