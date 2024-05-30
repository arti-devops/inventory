package com.arti.inventory.mission.ui;

import org.vaadin.crudui.crud.impl.GridCrud;

import com.arti.inventory.device.ui.MainAppLayout;
import com.arti.inventory.mission.backend.model.Mission;
import com.arti.inventory.mission.backend.service.MissionService;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
        
        setSizeFull();
        add(crud);
    }
}
