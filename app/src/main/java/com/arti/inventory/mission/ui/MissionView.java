package com.arti.inventory.mission.ui;

import org.vaadin.crudui.crud.impl.GridCrud;

import com.arti.inventory.device.ui.MainAppLayout;
import com.arti.inventory.mission.backend.model.Mission;
import com.arti.inventory.mission.backend.model.MissionType;
import com.arti.inventory.mission.backend.model.Status;
import com.arti.inventory.mission.backend.service.MissionService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.shared.Tooltip;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

@Route(value = "missions", layout = MainAppLayout.class)
public class MissionView extends VerticalLayout {

    public MissionView(MissionService service) {
        add(new H2("Missions ARTI"),
            new Paragraph("Liste des missions en cours et à venir pour l'année en cours."));

        GridCrud<Mission> crud = new GridCrud<>(Mission.class);
        crud.getGrid().setColumns("type", "subject", "dateOfDeparture", "dateOfReturn","location","members","totalBudget","status");
        crud.getGrid().getColumns().forEach(column -> column.setAutoWidth(true));
        crud.setCrudListener(service);

        crud.getGrid().getColumnByKey("type").setRenderer(new ComponentRenderer<>(mission -> {
            HorizontalLayout layout = new HorizontalLayout();
            Span value = new Span();
            String theme = String.valueOf("");
            if (mission.getType().equals(MissionType.INLAND)) {theme = "badge contrast primary"; value.setText("Côte d'Ivoire");}
            else if (mission.getType().equals(MissionType.ABROAD)) {theme = "badge contrast primary"; value.setText("Hors Afrique");}
            else if (mission.getType().equals(MissionType.AFRICA)) {theme = "badge contrast primary"; value.setText("Afrique");}
            value.getElement().setAttribute("theme", theme);
            value.setWidthFull();
            layout.add(value);
            return layout;
        })).setHeader("Type");

        crud.getGrid().getColumnByKey("subject").setRenderer(new ComponentRenderer<>(mission -> {
            Paragraph p = new Paragraph();
            p.setText(mission.getSubject().substring(0, Math.min(mission.getSubject().length(), 20)) + "...");
            @SuppressWarnings("unused")
            Tooltip tooltip = Tooltip.forComponent(p).withText(mission.getSubject());
            return p;
        })).setHeader("Mission");

        crud.getGrid().getColumnByKey("dateOfDeparture").setRenderer(new ComponentRenderer<>(mission -> {
            Paragraph p = new Paragraph();
            p.setText(mission.getDateOfDeparture().toString().substring(0, 10));
            return p;
        })).setHeader("Départ");

        crud.getGrid().getColumnByKey("dateOfReturn").setRenderer(new ComponentRenderer<>(mission -> {
            Paragraph p = new Paragraph();
            p.setText(mission.getDateOfReturn().toString().substring(0, 10));
            return p;
        })).setHeader("Retour");

        crud.getGrid().getColumnByKey("totalBudget").setRenderer(new ComponentRenderer<>(mission -> {
            HorizontalLayout layout = new HorizontalLayout();
            Span value = new Span();
            value.setText(mission.getTotalBudget().toString());
            layout.add(VaadinIcon.MONEY.create(), value);
            return layout;
        })).setHeader("Budget");

        crud.getGrid().getColumnByKey("members").setRenderer(new ComponentRenderer<>(mission -> {
            HorizontalLayout layout = new HorizontalLayout();
            Span value = new Span();
            value.setText(String.format("%02d", mission.getMembers().size()));
            layout.add(VaadinIcon.USER_CARD.create(), value);
            return layout;
        })).setHeader("Participants");

        crud.getGrid().getColumnByKey("status").setRenderer(new ComponentRenderer<>(mission -> {
            HorizontalLayout layout = new HorizontalLayout();
            Span value = new Span();
            String theme = String.valueOf("");
            if (mission.getStatus().equals(Status.PENDING)) {theme = "badge"; value.setText("En attente");}
            else if (mission.getStatus().equals(Status.APPROVED)) {theme = "badge success"; value.setText("Approuvé");}
            else if (mission.getStatus().equals(Status.REJECTED)) {theme = "badge error"; value.setText("Rejeté");}
            value.getElement().setAttribute("theme", theme);
            value.setWidthFull();
            layout.add(value);
            // layout.setWidthFull();
            return layout;
        })).setHeader("Statut");

        crud.getGrid().addColumn(new ComponentRenderer<>(mission -> {
            HorizontalLayout layout = new HorizontalLayout();
            Button edit = new Button();
            edit.setText("Voir");
            edit.setSuffixComponent(VaadinIcon.EDIT.create());
            edit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            edit.addClickListener(e -> {
                getUI().ifPresent(ui -> ui.navigate(MissionDetailsView.class, mission.getId()));
            });
            layout.add(edit);
            return layout;
        }));//.setHeader("Action");
        
        setSizeFull();
        add(crud);
    }
}
