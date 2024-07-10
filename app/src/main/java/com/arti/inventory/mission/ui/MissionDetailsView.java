package com.arti.inventory.mission.ui;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.impl.GridCrud;

import com.arti.inventory.MainAppLayout;
import com.arti.inventory.middleware.GeneralUtils;
import com.arti.inventory.mission.backend.model.Employee;
import com.arti.inventory.mission.backend.model.Member;
import com.arti.inventory.mission.backend.model.Mission;
import com.arti.inventory.mission.backend.model.Status;
import com.arti.inventory.mission.backend.service.EmployeeService;
import com.arti.inventory.mission.backend.service.MemberService;
import com.arti.inventory.mission.backend.service.MissionService;
import com.arti.inventory.mission.backend.service.PrintPdfService;
import com.arti.inventory.mission.ui.component.RenderMoney;
import com.arti.inventory.security.AuthService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.security.AuthenticationContext;
import com.vaadin.flow.theme.lumo.LumoUtility;

import jakarta.annotation.security.RolesAllowed;

@Route(value = "missions", layout = MainAppLayout.class)
@RolesAllowed({"ADMIN","RH","DG"})
public class MissionDetailsView extends VerticalLayout implements HasUrlParameter<Long> {

    private Mission mission;
    
    @Autowired
    private MissionService missionService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AuthenticationContext authenticationContext;
    @Autowired
    private PrintPdfService printPdfService;
    
    AuthService auth;
    Long deleteCount = 0L;

    HorizontalLayout badgeLayout = new HorizontalLayout();
    HorizontalLayout badgeSection = new HorizontalLayout();
    HorizontalLayout buttonSection = new HorizontalLayout();

    Button rejectBtn = new Button("Rejeter");        
    Button validationBtn = new Button("Approuver la mission");

    ArrayList<Anchor> downloadButtons = new ArrayList<Anchor>();
    
    MissionDetailsItem membersItem;
    MissionDetailsItem totalBudgetItem;

    Paragraph missionType;
    Paragraph missionStatus;
    Paragraph missionValidationRH;
    Paragraph missionValidationDG;

    GridCrud<Member> crud;

    @Override
    public void setParameter(BeforeEvent event, Long missionId) {
        try {
            this.mission = missionService.findOne(missionId);
            if (mission == null) {
                event.rerouteTo("missions");
                return;
            }
        } catch (Exception e) {
            event.rerouteTo("missions");
            return;
        }

        auth = new AuthService(authenticationContext);

        Paragraph missionSubject = new Paragraph();
        missionSubject.setText(mission.getSubject());
        missionSubject.getStyle().set("color", "var(--lumo-primary-text-color)");
        add(
            new H2("Page de détails"), 
            new HorizontalLayout(new Paragraph("Page de détails de la mission:"), missionSubject)
        );
        
        // Configure badge Layout
        badgeLayout.setWidthFull();
        badgeLayout.addClassName(LumoUtility.JustifyContent.BETWEEN);
        configureBadgeSection();
        
        if (auth.isAdmin()) {
            configureButtonSectionDG();
            badgeLayout.add(badgeSection, buttonSection);
        }else if(auth.is("RH")){
            configureButtonSectionRH();
            badgeLayout.add(badgeSection, buttonSection);
        }
        else{
            badgeLayout.add(badgeSection);
        }

        HorizontalLayout detailsLayout = new HorizontalLayout();
        detailsLayout.setWidth("100%");
        MissionDetailsItem locationItem = new MissionDetailsItem("Lieu", mission.getLocation(), "", VaadinIcon.LOCATION_ARROW_CIRCLE_O);
        MissionDetailsItem numberOfDaysItem = new MissionDetailsItem("Durée", String.valueOf(mission.getNumberOfDays()), "jours", VaadinIcon.CALENDAR_CLOCK);
        MissionDetailsItem dateOfDepartureItem = new MissionDetailsItem("Départ", GeneralUtils.formatDateFull(mission.getDateOfDeparture().toString()), "", VaadinIcon.CALENDAR_CLOCK);
        MissionDetailsItem dateOfReturnItem = new MissionDetailsItem("Retour", GeneralUtils.formatDateFull(mission.getDateOfReturn().toString()), "", VaadinIcon.CALENDAR_CLOCK);
        membersItem = new MissionDetailsItem("Participants", String.valueOf(mission.getMembers().size()), "personne(s)", VaadinIcon.USERS);
        totalBudgetItem = new MissionDetailsItem("Budget", String.format("%,d", mission.getTotalBudget()), "FCFA", VaadinIcon.MONEY);
        detailsLayout.add(locationItem, numberOfDaysItem, dateOfDepartureItem, dateOfReturnItem, membersItem, totalBudgetItem);
        
        // Mission members
        configureMissionMembersGrid(missionId);
        configureMissionMemberGridColumns();
        configureValidationButtonsAccess();

        // Form configuration
        configureMissionMemberCrudForm();

        // FORM CRUD OPERATIONS
        configureCrudOperations();
        configureCrudOpVisibility();

        setSizeFull();
        add(badgeLayout, detailsLayout, new Span("Liste de participants à la mission"), crud);
    }

    private void configureMissionMemberCrudForm() {
        crud.getCrudFormFactory().setVisibleProperties("employee", "dateOfDeparture", "dateOfReturn", "transportation", "mobility");
        crud.getCrudFormFactory().setFieldProvider("employee", member -> {
            ComboBox<Employee> employeeComboBox = new ComboBox<>("Participant");
            employeeComboBox.setItems(employeeService.findAll());
            employeeComboBox.setItemLabelGenerator(employee -> employee.getFirstName() + " " + employee.getLastName());
            return employeeComboBox;
        });
        crud.getCrudFormFactory().setFieldCaptions("Participant", "Départ", "Retour", "Transport", "Mobilité");
    }

    private void configureMissionMemberGridColumns() {
        crud.getGrid().getColumnByKey("employee").setRenderer(new ComponentRenderer<>(member -> {
            Employee employee = member.getEmployee();
            return new Span(employee.getFirstName() + " " + employee.getLastName());
        
        })).setHeader("Participant");

        crud.getGrid().getColumnByKey("dateOfDeparture").setRenderer(new ComponentRenderer<>(member -> {
            return new Span(member.getDateOfDeparture().toString().split(" ")[0]);
        })).setHeader("Départ");

        crud.getGrid().getColumnByKey("dateOfReturn").setRenderer(new ComponentRenderer<>(member -> {
            return new Span(member.getDateOfReturn().toString().split(" ")[0]);
        })).setHeader("Retour");

        crud.getGrid().getColumnByKey("numberOfDays").setRenderer(new ComponentRenderer<>(member -> {
            return new Span(String.format("%02d",member.getNumberOfDays()));
        })).setHeader("Durée");

        crud.getGrid().getColumnByKey("transportation").setRenderer(new ComponentRenderer<>(member -> {
            return new Span(MissionService.getTransportation(member.getTransportation()));
        })).setHeader("Transport");

        crud.getGrid().getColumnByKey("mobility").setRenderer(new ComponentRenderer<>(member -> {
            return new Span(MissionService.getMobility(member.getMobility()));
        })).setHeader("Mobilité");

        crud.getGrid().getColumnByKey("hotelFees").setRenderer(new ComponentRenderer<RenderMoney, Member>(member -> {
            return new RenderMoney(member.getHotelFees());
        })).setHeader("Frais d'hôtel");

        crud.getGrid().getColumnByKey("ressortExpenses").setRenderer(new ComponentRenderer<RenderMoney, Member>(member -> {
            return new RenderMoney(member.getRessortExpenses());
        })).setHeader("Frais de séjour");

        crud.getGrid().getColumnByKey("mobilityGasFees").setRenderer(new ComponentRenderer<RenderMoney, Member>(member -> {
            return new RenderMoney(member.getMobilityGasFees());
        })).setHeader("Carburant");

        crud.getGrid().getColumnByKey("totalBudget").setRenderer(new ComponentRenderer<RenderMoney, Member>(member -> {
            return new RenderMoney(member.getTotalBudget());
        })).setHeader("Budget");

        crud.getGrid().addColumn(new ComponentRenderer<>(member -> {
            HorizontalLayout layout = new HorizontalLayout();
            Anchor download1a = new Anchor();
            download1a.setHref(printPdfService.printMissionDetailsFile(this.mission, member));
            Button download1 = new Button(VaadinIcon.DOWNLOAD_ALT.create());
            download1.setTooltipText("Fiche de mission");
            download1a.add(download1);
            Anchor download2a = new Anchor();
            download2a.setHref(printPdfService.printMissionOrderFile(this.mission, member));
            Button download2 = new Button(VaadinIcon.DOWNLOAD_ALT.create());
            download2.setTooltipText("Ordre de mission");
            download2a.add(download2);
            downloadButtons.add(download1a);
            downloadButtons.add(download2a);
            layout.add(download1a, download2a);
            if(mission.getStatus()==Status.REJECTED || mission.getStatus()==Status.PENDING){
                download1a.setEnabled(false);
                download2a.setEnabled(false);
            }
            return layout;
        })).setHeader("Télécharger");
    }

    private void configureMissionMembersGrid(Long missionId) {
        crud = new GridCrud<>(Member.class);
        crud.setCrudListener(memberService);
        crud.setRowCountCaption("%d membre(s) trouvé(s)");
        crud.setFindAllOperation(() -> memberService.findMissionMembers(missionId));
        crud.getGrid().setColumns("employee", "dateOfDeparture", "dateOfReturn", "numberOfDays", "transportation", "mobility", "hotelFees", "ressortExpenses", "mobilityGasFees", "totalBudget");
        crud.getGrid().getColumns().forEach(column -> column.setAutoWidth(true));
    }

    private void configureCrudOpVisibility() {
        if (/*mission.getValidationRH()==Status.APPROVED || */mission.getStatus()==Status.APPROVED || mission.getStatus()==Status.REJECTED) {
            crud.setAddOperationVisible(false);
            crud.setDeleteOperationVisible(false);
            crud.setUpdateOperationVisible(false);
            crud.setFindAllOperationVisible(false);
        }
    }

    private void configureCrudOperations() {
        crud.setAddOperation(member -> {
            Member m = memberService.add(member, mission);
            mission.setTotalBudget(mission.getTotalBudget() + m.getTotalBudget());
            mission.getMembers().add(m);
            totalBudgetItem.setValue(String.format("%,d", mission.getTotalBudget()), "FCFA");
            membersItem.setValue(String.valueOf(mission.getMembers().size()), "personne(s)");
            return m;
        });

        crud.setDeleteOperation(member -> {
            deleteCount += 1;
            mission.setTotalBudget(mission.getTotalBudget() - member.getTotalBudget());
            totalBudgetItem.setValue(String.format("%,d", mission.getTotalBudget()), "FCFA");
            membersItem.setValue(String.valueOf(mission.getMembers().size()-deleteCount), "personne(s)");
            memberService.delete(member);
        });

        crud.setUpdateOperation(member -> {
            Member m = memberService.add(member, mission);
            mission.setTotalBudget(mission.getTotalBudget() + m.getTotalBudget());
            mission.getMembers().add(m);
            totalBudgetItem.setValue(String.format("%,d", mission.getTotalBudget()), "FCFA");
            return m;
        });
    }

    private void configureButtonSectionRH() {
        validationBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        validationBtn.addClickListener(validateMission -> {
            // Validate mission
            // Save new mission status
            // Reload badge status
            mission.setValidationRH(Status.APPROVED);
            // mission.setStatus(Status.APPROVED);
            configureBadgeSection();
            configureCrudOpVisibility();
            missionService.update(mission);
            configureValidationButtonsAccess();
        });
        buttonSection.add(validationBtn);
    }

    private void configureButtonSectionDG() {
        validationBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        validationBtn.addClickListener(validateMission -> {
            // Validate mission
            // Save new mission status
            // Reload badge status
            mission.setValidationDG(Status.APPROVED);
            configureBadgeSection();
            configureCrudOpVisibility();
            missionService.update(mission);
            downloadButtons.forEach(btn -> btn.setEnabled(true));
            configureValidationButtonsAccess();
        });
        rejectBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);
        rejectBtn.addClickListener(rejectMission -> {
            // Reject mission
            // Save new mission status
            // Reload badge status
            mission.setValidationDG(Status.REJECTED);
            mission.setStatus(Status.REJECTED);
            configureBadgeSection();
            configureCrudOpVisibility();
            missionService.update(mission);
            configureValidationButtonsAccess();
        });
        buttonSection.add(rejectBtn, validationBtn);
    }

    private void configureValidationButtonsAccess(){
        if (mission.getStatus()==Status.APPROVED || mission.getStatus()==Status.REJECTED) {
            validationBtn.setEnabled(false);
            rejectBtn.setEnabled(false);
        }

        if (auth.isAdmin()) {
            if (mission.getValidationRH()==Status.PENDING || mission.getValidationRH()==Status.REJECTED) {
                validationBtn.setEnabled(false);
                rejectBtn.setEnabled(false);
            }
        }

        if (auth.is("RH")) {
            if (mission.getValidationRH()==Status.APPROVED) {
                validationBtn.setEnabled(false);
            }
        }
    }

    private void configureBadgeSection() {
        badgeSection.removeAll();
        missionType = new Paragraph(MissionService.getMissionType(mission.getType()));
        missionType.getElement().setAttribute("theme", "badge contrast primary");
        missionStatus = new Paragraph(MissionService.getMissionStatus(mission.getStatus()));
        missionStatus.getElement().setAttribute("theme",MissionService.getMissionStatusTheme(mission.getStatus()));
        missionValidationRH = new Paragraph("RH: "+MissionService.getMissionStatus(mission.getValidationRH()));
        missionValidationRH.getElement().setAttribute("theme",MissionService.getMissionStatusTheme(mission.getValidationRH()));
        missionValidationDG = new Paragraph("DG: "+MissionService.getMissionStatus(mission.getValidationDG()));
        missionValidationDG.getElement().setAttribute("theme",MissionService.getMissionStatusTheme(mission.getValidationDG()));
        
        badgeSection.add(missionType);

        if (mission.getStatus()==Status.APPROVED || mission.getStatus()==Status.REJECTED) {
            badgeSection.add(missionStatus);
        }else{
            badgeSection.add(missionValidationRH, missionValidationDG);
        }
    }

    private class MissionDetailsItem extends VerticalLayout{
        public MissionDetailsItem(String label, String value, String suffix, VaadinIcon icon) {
            setSpacing(false);
            HorizontalLayout layout = new HorizontalLayout();
            Span p = new Span(label);
            layout.add(icon.create(), p);
            p.getStyle().set("font-weight", "bold");
            add(layout, new Paragraph(value + " " + suffix));
        }

        public void setValue(String value, String suffix) {
            ((Paragraph)getComponentAt(1)).setText(value + " " + suffix);
        }
    }
}
