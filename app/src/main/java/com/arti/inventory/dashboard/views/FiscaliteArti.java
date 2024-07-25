package com.arti.inventory.dashboard.views;

import com.arti.inventory.MainAppLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route(value = "/dashboards/fiscalite-arti", layout = MainAppLayout.class)
@RolesAllowed({"DRH","DG","ADMIN"})
public class FiscaliteArti extends VerticalLayout {

    public FiscaliteArti(){
        add(new H2("Tableau de bord: Fiscalité ARTI"));
        
        IFrame dboard = new IFrame("https://app.powerbi.com/reportEmbed?reportId=5e69ea3e-cb41-4819-9888-ef2c99a96598&autoAuth=true&ctid=9dc9ec34-5a5d-48aa-b616-854f17c43596");
        dboard.setWidth("100%");
        dboard.setHeight("100%");
        add(dboard);
        setSizeFull();
    }
}
