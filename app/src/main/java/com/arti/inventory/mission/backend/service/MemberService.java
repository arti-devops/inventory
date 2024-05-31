package com.arti.inventory.mission.backend.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import com.arti.inventory.mission.backend.model.Member;
import com.arti.inventory.mission.backend.model.Mission;
import com.arti.inventory.mission.backend.repository.MemberRepository;

@Service
public class MemberService implements CrudListener<Member> {

    @Autowired
    private MemberRepository repository;

    public Collection<Member> findMissionMembers(Long missionId) {
        return repository.findAllByMissionId(missionId);
    }

    @Override
    public Collection<Member> findAll() {
        return repository.findAll();
    }

    @Override
    public Member add(Member domainObjectToAdd) {
        return repository.save(domainObjectToAdd);
    }

    public Member add(Member domainObjectToAdd, Mission mission) {
        domainObjectToAdd.setMission(mission);
        domainObjectToAdd.setNumberOfDays(MissionService.computeNumberOfDays(domainObjectToAdd.getDateOfDeparture(), domainObjectToAdd.getDateOfReturn()));
        //TODO compute all dynamic fields
        return repository.save(domainObjectToAdd);
    }

    @Override
    public Member update(Member domainObjectToUpdate) {
        domainObjectToUpdate.setNumberOfDays(MissionService.computeNumberOfDays(domainObjectToUpdate.getDateOfDeparture(), domainObjectToUpdate.getDateOfReturn()));
        return repository.save(domainObjectToUpdate);
    }

    @Override
    public void delete(Member domainObjectToDelete) {
        repository.delete(domainObjectToDelete);
    }

}
