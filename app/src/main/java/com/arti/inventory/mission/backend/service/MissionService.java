package com.arti.inventory.mission.backend.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import com.arti.inventory.mission.backend.model.Member;
import com.arti.inventory.mission.backend.model.Mission;
import com.arti.inventory.mission.backend.repository.MissionRepository;

@Service
public class MissionService implements CrudListener<Mission> {

    @Autowired
    private MissionRepository service;

    @Autowired
    MemberService memberService;

    @Override
    public Collection<Mission> findAll() {
        Collection<Mission> missions = service.findAll();
        missions.forEach(mission -> {
            Collection<Member> members = memberService.findMissionMembers(mission.getId());
            mission.setTotalBudget(members.stream().mapToLong(Member::getTotalBudget).sum());
            mission.setMembers(members);
        });
        return missions;
    }

    @Override
    public Mission add(Mission domainObjectToAdd) {
        return service.save(domainObjectToAdd);
    }

    @Override
    public Mission update(Mission domainObjectToUpdate) {
        return service.save(domainObjectToUpdate);
    }

    @Override
    public void delete(Mission domainObjectToDelete) {
        service.delete(domainObjectToDelete);
    }
}
