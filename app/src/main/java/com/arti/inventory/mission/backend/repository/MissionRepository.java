package com.arti.inventory.mission.backend.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arti.inventory.mission.backend.model.Mission;

import java.util.Collection;
import com.arti.inventory.mission.backend.model.Status;


@Repository
public interface MissionRepository extends JpaRepository<Mission, Long>{
    Collection<Mission> findAllByStatus(Status status, Sort sort);
}
