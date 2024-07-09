package com.arti.inventory.mission.ui;

import org.vaadin.crudui.crud.impl.GridCrud;
import com.arti.inventory.MainAppLayout;
import com.arti.inventory.mission.backend.model.Mission;
import com.arti.inventory.mission.backend.model.MissionType;
import com.arti.inventory.mission.backend.service.MissionService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.shared.Tooltip;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.PermitAll;

@Route(value = "missions", layout = MainAppLayout.class)
@PermitAll
public class MissionView extends VerticalLayout {

    public MissionView(AuthenticationContext auth, MissionService service) {
        // Add header and description
        add(new H2("Missions ARTI"),
            new Paragraph("Liste des missions en cours et à venir pour l'année en cours."));

        // Create and configure the CRUD grid
        GridCrud<Mission> crud = new GridCrud<>(Mission.class);
        crud.getGrid().setColumns("type", "subject", "dateOfDeparture", "dateOfReturn", "location", "members", "totalBudget", "status");
        crud.getGrid().getColumns().forEach(column -> column.setAutoWidth(true));
        crud.setCrudListener(service);
        crud.setShowNotifications(false);

        // Configure columns with custom renderers
        configureTypeColumn(crud);
        configureSubjectColumn(crud);
        configureDateColumn(crud, "dateOfDeparture", "Départ");
        configureDateColumn(crud, "dateOfReturn", "Retour");
        configureBudgetColumn(crud);
        configureMembersColumn(crud);
        configureStatusColumn(crud);
        configureActionsColumn(crud);

        // Configure form
        configureForm(crud);

        setSizeFull();
        add(crud);
    }

    private void configureTypeColumn(GridCrud<Mission> crud) {
        crud.getGrid().getColumnByKey("type").setRenderer(new ComponentRenderer<>(mission -> {
            HorizontalLayout layout = new HorizontalLayout();
            Span value = new Span();
            String theme = "badge contrast primary";
            
            switch (mission.getType()) {
                case INLAND: value.setText("Côte d'Ivoire"); break;
                case ABOARD: value.setText("Hors Afrique"); break;
                case AFRICA: value.setText("Afrique"); break;
            }
            
            value.getElement().setAttribute("theme", theme);
            value.setWidthFull();
            layout.add(value);
            return layout;
        })).setHeader("Type");
    }

    private void configureSubjectColumn(GridCrud<Mission> crud) {
        crud.getGrid().getColumnByKey("subject").setRenderer(new ComponentRenderer<>(mission -> {
            Paragraph p = new Paragraph();
            p.setText(mission.getSubject().substring(0, Math.min(mission.getSubject().length(), 20)) + "...");
            Tooltip.forComponent(p).withText(mission.getSubject());
            return p;
        })).setHeader("Mission");
    }

    private void configureDateColumn(GridCrud<Mission> crud, String field, String header) {
        crud.getGrid().getColumnByKey(field).setRenderer(new ComponentRenderer<>(mission -> {
            Paragraph p = new Paragraph();
            p.setText(mission.getDateOfDeparture().toString().substring(0, 10));
            return p;
        })).setHeader(header);
    }

    private void configureBudgetColumn(GridCrud<Mission> crud) {
        crud.getGrid().getColumnByKey("totalBudget").setRenderer(new ComponentRenderer<>(mission -> {
            HorizontalLayout layout = new HorizontalLayout();
            Span value = new Span(mission.getTotalBudget().toString());
            layout.add(VaadinIcon.MONEY.create(), value);
            return layout;
        })).setHeader("Budget");
    }

    private void configureMembersColumn(GridCrud<Mission> crud) {
        crud.getGrid().getColumnByKey("members").setRenderer(new ComponentRenderer<>(mission -> {
            HorizontalLayout layout = new HorizontalLayout();
            Span value = new Span(String.format("%02d", mission.getMembers().size()));
            layout.add(VaadinIcon.USER_CARD.create(), value);
            return layout;
        })).setHeader("Participants");
    }

    private void configureStatusColumn(GridCrud<Mission> crud) {
        crud.getGrid().getColumnByKey("status").setRenderer(new ComponentRenderer<>(mission -> {
            HorizontalLayout layout = new HorizontalLayout();
            Span value = new Span();
            String theme = "";
            
            switch (mission.getStatus()) {
                case PENDING: theme = "badge"; value.setText("En attente"); break;
                case APPROVED: theme = "badge success"; value.setText("Approuvé"); break;
                case REJECTED: theme = "badge error"; value.setText("Rejeté"); break;
            }
            
            value.getElement().setAttribute("theme", theme);
            value.setWidthFull();
            layout.add(value);
            return layout;
        })).setHeader("Statut");
    }

    private void configureActionsColumn(GridCrud<Mission> crud) {
        crud.getGrid().addColumn(new ComponentRenderer<>(mission -> {
            HorizontalLayout layout = new HorizontalLayout();
            Button edit = new Button("Voir", VaadinIcon.EDIT.create());
            edit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            edit.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(MissionDetailsView.class, mission.getId())));
            layout.add(edit);
            return layout;
        }));
    }

    private void configureForm(GridCrud<Mission> crud) {
        crud.getCrudFormFactory().setUseBeanValidation(true);
        crud.getCrudFormFactory().setVisibleProperties("type", "subject", "dateOfDeparture", "dateOfReturn", "location");
        crud.getCrudFormFactory().setFieldCaptions("Type de mission", "Intitulé de la mission", "Départ", "Retour", "Lieu");
        
        crud.getCrudFormFactory().setFieldProvider("type", mission -> {
            ComboBox<MissionType> typeComboBox = new ComboBox<>("Type");
            typeComboBox.setItems(MissionType.values());
            typeComboBox.setItemLabelGenerator(Enum::toString);
            return typeComboBox;
        });
    }
}
