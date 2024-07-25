package com.arti.inventory.dashboard.views;

import com.arti.inventory.MainAppLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route(value = "/dashboards/finances", layout = MainAppLayout.class)
@RolesAllowed({"APP_DBOARD_COMPTESFINANCES","ADMIN"})
public class CompteFinances extends VerticalLayout {

    public CompteFinances(){
        add(new H2("Tableau de bord: Comptes & Finances"));
        
        IFrame dboard = new IFrame("https://app.powerbi.com/reportEmbed?reportId=376599eb-967b-402b-b0fa-2e0580451357&autoAuth=true&ctid=9dc9ec34-5a5d-48aa-b616-854f17c43596");
        dboard.setWidth("100%");
        dboard.setHeight("100%");
        add(dboard);
        setSizeFull();
    }
}
