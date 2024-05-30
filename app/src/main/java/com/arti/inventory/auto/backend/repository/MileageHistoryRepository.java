package com.arti.inventory.auto.backend.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arti.inventory.auto.backend.model.MileageHistory;

@Repository
public interface MileageHistoryRepository extends JpaRepository<MileageHistory, Long>{
    
    public Collection<MileageHistory> findAllByVehiculeId(long vehiculeId);
}
