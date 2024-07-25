package com.arti.inventory.dashboard.views;

import com.arti.inventory.MainAppLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route(value = "dashboards/chiffres-transport", layout = MainAppLayout.class)
@RolesAllowed({"DRH","DG","ADMIN"})
public class ChiffresTransport extends VerticalLayout {

    public ChiffresTransport(){
        add(new H2("Tableau de bord: Le Transport en Chiffres"));
        
        IFrame dboard = new IFrame("https://app.powerbi.com/reportEmbed?reportId=9d90a676-cbbb-4729-b0d0-9ae75fd2364e&autoAuth=true&ctid=9dc9ec34-5a5d-48aa-b616-854f17c43596");
        dboard.setWidth("100%");
        dboard.setHeight("100%");
        add(dboard);
        setSizeFull();
    }
}
