package com.arti.inventory.mission.ui;

import org.vaadin.crudui.crud.impl.GridCrud;

import com.arti.inventory.device.ui.MainAppLayout;
import com.arti.inventory.mission.backend.model.Mission;
import com.arti.inventory.mission.backend.service.MissionService;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.shared.Tooltip;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

@Route(value = "missions", layout = MainAppLayout.class)
public class MissionView extends VerticalLayout {

    public MissionView(MissionService service) {
        add(new H2("Missions ARTI"),
            new Paragraph("Listing des missions de l'ARTI"));

        GridCrud<Mission> crud = new GridCrud<>(Mission.class);
        crud.getGrid().setColumns("type", "subject", "dateOfDeparture", "dateOfReturn","location","totalBudget","status");
        crud.getGrid().getColumns().forEach(column -> column.setAutoWidth(true));
        crud.setCrudListener(service);

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
        
        setSizeFull();
        add(crud);
    }
}
