package com.arti.inventory.dashboard.views;

import com.arti.inventory.MainAppLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route(value = "/dashboards/mp", layout = MainAppLayout.class)
@RolesAllowed({"DRH","DG","ADMIN"})
public class MarchePublic extends VerticalLayout {

    public MarchePublic(){
        add(new H2("Tableau de bord: Marché Public"));
        
        IFrame dboard = new IFrame("https://app.powerbi.com/reportEmbed?reportId=d9478503-f4b5-4870-afba-cbeb6d118b76&autoAuth=true&ctid=9dc9ec34-5a5d-48aa-b616-854f17c43596");
        dboard.setWidth("100%");
        dboard.setHeight("100%");
        add(dboard);
        setSizeFull();
    }
}
