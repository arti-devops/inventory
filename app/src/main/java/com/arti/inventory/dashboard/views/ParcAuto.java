package com.arti.inventory.dashboard.views;

import com.arti.inventory.MainAppLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route(value = "/dashboards/parc-auto", layout = MainAppLayout.class)
@RolesAllowed({"DRH","DG","ADMIN"})
public class ParcAuto extends VerticalLayout {

    public ParcAuto(){
        add(new H2("Tableau de bord: Parc Auto"));
        
        IFrame dboard = new IFrame("https://app.powerbi.com/reportEmbed?reportId=58a0df74-68f2-4778-aabe-81dee40d79de&autoAuth=true&ctid=9dc9ec34-5a5d-48aa-b616-854f17c43596");
        dboard.setWidth("100%");
        dboard.setHeight("100%");
        add(dboard);
        setSizeFull();
    }
}
