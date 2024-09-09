package com.arti.inventory.dashboard.views;

import org.springframework.beans.factory.annotation.Value;

import com.arti.inventory.MainAppLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route(value = "/dashboards/mp", layout = MainAppLayout.class)
@RolesAllowed({"APP_DBOARD_MARCHESPUBLICS","ADMIN"})
public class MarchePublic extends VerticalLayout {

    public MarchePublic(@Value("${dboard.url.mp}") String mpUrl){
        add(new H2("Tableau de bord: Marché Public"));
        
        IFrame dboard = new IFrame(mpUrl);
        dboard.setWidth("100%");
        dboard.setHeight("100%");
        add(dboard);
        setSizeFull();
    }
}
