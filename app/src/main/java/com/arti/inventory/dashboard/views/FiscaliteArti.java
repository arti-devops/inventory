package com.arti.inventory.dashboard.views;

import org.springframework.beans.factory.annotation.Value;

import com.arti.inventory.MainAppLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route(value = "/dashboards/fiscalite-arti", layout = MainAppLayout.class)
@RolesAllowed({"APP_DBOARD_FISCALITE","ADMIN"})
public class FiscaliteArti extends VerticalLayout {

    public FiscaliteArti(@Value("${dboard.url.fiscalite_arti}") String fiscaliteArtiUrl){
        add(new H2("Tableau de bord: Fiscalité ARTI"));
        
        IFrame dboard = new IFrame(fiscaliteArtiUrl);
        dboard.setWidth("100%");
        dboard.setHeight("100%");
        add(dboard);
        setSizeFull();
    }
}
