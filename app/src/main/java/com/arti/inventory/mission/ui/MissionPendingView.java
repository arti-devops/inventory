package com.arti.inventory.mission.ui;

import org.vaadin.crudui.crud.impl.GridCrud;
import com.arti.inventory.MainAppLayout;
import com.arti.inventory.mission.backend.model.Mission;
import com.arti.inventory.mission.backend.model.MissionType;
import com.arti.inventory.mission.backend.model.Status;
import com.arti.inventory.mission.backend.service.MissionService;
import com.arti.inventory.mission.ui.component.RenderMoney;
import com.arti.inventory.security.AuthService;
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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "missions/pending", layout = MainAppLayout.class)
@RolesAllowed({"ROLE_DG","ROLE_DRH"})
@PageTitle(value = "SGI ARTI | Missions à valider")
public class MissionPendingView extends VerticalLayout {

    private AuthService auth;

    public MissionPendingView(MissionService service, AuthenticationContext ac) {

        auth = new AuthService(ac);

        // Add header and description
        Paragraph p = new Paragraph("Missions en attente de ");
        Span votre = new Span("VOTRE");
        votre.getElement().getThemeList().add("badge");
        p.add(votre, new Span(" validaion"));
        add(new H2("Missions ARTI"), p);

        // Create and configure the CRUD grid
        GridCrud<Mission> crud = new GridCrud<>(Mission.class);
        crud.setShowNotifications(false);

        crud.getGrid().setColumns("type", "subject", "dateOfDeparture", "dateOfReturn", "location", "members", "totalBudget", "status");
        crud.getGrid().getColumns().forEach(column -> column.setAutoWidth(true));
        crud.setFindAllOperation(() -> {
            var pending = service.getAllMissionByStatus(Status.PENDING);
            if (auth.isDG()) {
                pending.removeIf(m -> m.getValidationRH()==Status.PENDING);
            }else if (auth.isDRH()) {
                pending.removeIf(m -> m.getValidationRH()==Status.APPROVED);
            }
            return pending;
        });

        configureCrudOpVisibility(crud);

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
                case INLAND:
                    value.setText("Côte d'Ivoire");
                    break;
                case ABOARD:
                    value.setText("Hors Afrique");
                    break;
                case AFRICA:
                    value.setText("Afrique");
                    break;
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
            if (field == "dateOfDeparture") {
                p.setText(mission.getDateOfDeparture().toString().substring(0, 10));
            } else {
                p.setText(
                        mission.getDateOfReturn().toString().substring(0, 10) + " (" + mission.getNumberOfDays() + ")");
            }
            return p;
        })).setHeader(header);
    }

    private void configureBudgetColumn(GridCrud<Mission> crud) {
        crud.getGrid().getColumnByKey("totalBudget")
                .setRenderer(new ComponentRenderer<RenderMoney, Mission>(mission -> {
                    return new RenderMoney(mission.getTotalBudget());
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
                case PENDING:
                    theme = "badge";
                    value.setText("En attente");
                    break;
                case APPROVED:
                    theme = "badge success";
                    value.setText("Approuvé");
                    break;
                case REJECTED:
                    theme = "badge error";
                    value.setText("Rejeté");
                    break;
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
        crud.getCrudFormFactory().setVisibleProperties("type", "subject", "dateOfDeparture", "dateOfReturn",
                "location");
        crud.getCrudFormFactory().setFieldCaptions("Type de mission", "Intitulé de la mission", "Départ", "Retour",
                "Lieu");

        crud.getCrudFormFactory().setFieldProvider("type", mission -> {
            ComboBox<MissionType> typeComboBox = new ComboBox<>("Type");
            typeComboBox.setItems(MissionType.values());
            typeComboBox.setItemLabelGenerator(Enum::toString);
            return typeComboBox;
        });
    }

    private void configureCrudOpVisibility(GridCrud<Mission> crud) {
        if (!auth.isAdmin()) {
            crud.setAddOperationVisible(false);
            crud.setDeleteOperationVisible(false);
            crud.setUpdateOperationVisible(false);
            crud.setFindAllOperationVisible(false);
        }
    }
}
