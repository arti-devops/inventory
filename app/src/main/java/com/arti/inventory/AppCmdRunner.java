package com.arti.inventory;

import java.util.concurrent.TimeUnit;

//import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import com.arti.inventory.device.backend.repository.ComputerRepository;
import com.arti.inventory.device.backend.repository.PhoneRepository;
import com.arti.inventory.device.backend.repository.PrinterRepository;
import com.arti.inventory.middleware.ldap.repository.LdapEmployeeRepository;
import com.arti.inventory.mission.backend.model.Employee;
import com.arti.inventory.mission.backend.model.EmployeeCategory;
import com.arti.inventory.mission.backend.model.EmployeeGender;
import com.arti.inventory.mission.backend.model.Member;
import com.arti.inventory.mission.backend.model.Mission;
import com.arti.inventory.mission.backend.model.MissionType;
import com.arti.inventory.mission.backend.model.Mobility;
import com.arti.inventory.mission.backend.model.Status;
import com.arti.inventory.mission.backend.model.Transportation;
import com.arti.inventory.mission.backend.repository.EmployeeRepository;
import com.arti.inventory.mission.backend.repository.MemberRepository;
import com.arti.inventory.mission.backend.repository.MissionRepository;
import com.arti.inventory.mission.backend.service.EmployeeService;
import com.arti.inventory.mission.backend.service.MemberService;
import com.arti.inventory.mission.backend.service.MissionService;
import com.github.javafaker.Faker;

@Component
@Profile("dev")
@ConditionalOnProperty(name = "data.env", havingValue = "cmd")
public class AppCmdRunner implements CommandLineRunner{

    //private Logger logger = org.slf4j.LoggerFactory.getLogger(AppCmdRunner.class);

    @Autowired
    ComputerRepository repository;

    @Autowired
    PhoneRepository phoneRepository;

    @Autowired
    PrinterRepository printerRepository;

    @Override
    public void run(String... args) throws Exception {
        addMissionDataToDB();
    }

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    MissionRepository missionRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    LdapEmployeeRepository ldapEmployeeRepository;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    MemberService memberService;
    
    private void addMissionDataToDB(){
        Faker faker = new Faker();
        // Add 10 employees
        for(int i = 0; i < 10; i++){
            Employee employee = new Employee();
            employee.setFirstName(faker.name().firstName());
            employee.setLastName(faker.name().lastName());
            employee.setDepartment(faker.company().profession());
            employee.setPosition(faker.company().profession());
            employee.setEmail(faker.internet().emailAddress());
            employee.setPhotoUrl(faker.internet().avatar());
            employee.setMatricule(faker.code().ean8());
            employee.setCategory(EmployeeCategory.values()[faker.number().numberBetween(0, EmployeeCategory.values().length)]);
            employee.setGender(EmployeeGender.values()[faker.number().numberBetween(0, EmployeeGender.values().length)]);
            employeeRepository.save(employee);
        }

        // Add 25 missions
        for(int i = 0; i < 30; i++){
            Mission mission = new Mission();
            mission.setSubject(faker.lorem().sentence(6));
            mission.setType(MissionType.values()[faker.number().numberBetween(0, MissionType.values().length)]);
            mission.setLocation(faker.address().city());
            mission.setDateOfDeparture(faker.date().future(5, TimeUnit.DAYS));
            mission.setDateOfReturn(faker.date().future(20, 6, TimeUnit.DAYS));
            mission.setNumberOfDays(MissionService.computeNumberOfDays(mission.getDateOfDeparture(), mission.getDateOfReturn()));
            //mission.setStatus(Status.values()[faker.number().numberBetween(0, Status.values().length)]);
            mission.setValidationRH(Status.values()[faker.number().numberBetween(0, Status.values().length)]);
            if (mission.getValidationRH()==Status.APPROVED) {
                mission.setValidationDG(Status.values()[faker.number().numberBetween(0, Status.values().length)]);
            }else{
                mission.setValidationDG(Status.PENDING);
            }
            if (mission.getValidationRH()==Status.APPROVED && mission.getValidationDG()==Status.APPROVED) {
                mission.setStatus(Status.APPROVED);
            }else if (mission.getValidationDG()==Status.REJECTED || mission.getValidationRH()==Status.REJECTED) {
                mission.setStatus(Status.REJECTED);
            }else {
                mission.setStatus(Status.PENDING);
            }
            mission.setTotalBudget(0L);
            missionRepository.save(mission);
        }
        // Add 200 members
        for(int i = 0; i < 200; i++){
            Member member = new Member();
            member.setEmployee(employeeRepository.findAll().get(faker.number().numberBetween(0, employeeRepository.findAll().size())));
            Mission mission = missionRepository.findAll().get(faker.number().numberBetween(0, 25));
            //member.setHotelFees(faker.number().numberBetween(10000, 200000) * 1L);
            //member.setRessortExpenses(faker.number().numberBetween(10000, 200000) * 1L);
            member.setDateOfDeparture(mission.getDateOfDeparture());
            member.setDateOfReturn(mission.getDateOfReturn());
            member.setNumberOfDays(faker.number().numberBetween(1, 30) * 1L);
            member.setTransportation(faker.options().option(Transportation.values()));
            member.setMobility(faker.options().option(Mobility.values()));
            //member.setMobilityGasFees(faker.number().numberBetween(10000, 150000) * 1L);
            //member.setTotalBudget(member.getHotelFees()*member.getNumberOfDays() + member.getRessortExpenses()*member.getNumberOfDays() + member.getMobilityGasFees() + 0L);
            memberService.add(member, mission);
            // memberRepository.save(member);
        }

    }

}
