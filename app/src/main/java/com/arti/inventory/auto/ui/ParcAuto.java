package com.arti.inventory.auto.ui;

import com.arti.inventory.MainAppLayout;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route(value = "/auto/parc-auto", layout = MainAppLayout.class)
@RolesAllowed({"APP_PARCAUTO","ADMIN"})
public class ParcAuto extends VerticalLayout {

    public ParcAuto(){
        // add(new H2("Tableau de bord: Parc Auto"));
        
        IFrame dboard = new IFrame("https://apps.powerapps.com/play/e/default-9dc9ec34-5a5d-48aa-b616-854f17c43596/a/3f71619a-b191-487a-8c11-6f30177d8dda?tenantId=9dc9ec34-5a5d-48aa-b616-854f17c43596&sourcetime=1721826915590");
        dboard.setWidth("100%");
        dboard.setHeight("100%");
        add(dboard);
        setSizeFull();
    }
}
