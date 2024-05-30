package com.arti.inventory.mission.backend.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arti.inventory.mission.backend.model.Member;
import com.arti.inventory.mission.backend.repository.MemberRepository;

@Service
public class MemberService {

    @Autowired
    private MemberRepository repository;

    public Collection<Member> findMissionMembers(Long missionId) {
        return repository.findAllByMissionId(missionId);
    }

}
