package com.arti.inventory.dashboard.views;

import org.springframework.beans.factory.annotation.Value;

import com.arti.inventory.MainAppLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route(value = "/dashboards/parc-auto", layout = MainAppLayout.class)
@RolesAllowed({"APP_DBOARD_PARCAUTO","ADMIN"})
public class ParcAuto extends VerticalLayout {

    public ParcAuto(@Value("${dboard.url.parc_auto}") String parcAutoUrl){
        add(new H2("Tableau de bord: Parc Auto"));
        
        IFrame dboard = new IFrame(parcAutoUrl);
        dboard.setWidth("100%");
        dboard.setHeight("100%");
        add(dboard);
        setSizeFull();
    }
}
