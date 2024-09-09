package com.arti.inventory.gestion;

import com.arti.inventory.MainAppLayout;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route(value = "/redirect/ca/manager", layout = MainAppLayout.class)
@RolesAllowed({"APP_ABSENCES","ADMIN"})
public class CongesAbsencesRedirect extends VerticalLayout {

    public CongesAbsencesRedirect(){
        // add(new H2("Tableau de bord: Parc Auto"));
        
        IFrame dboard = new IFrame("https://apps.powerapps.com/play/e/default-9dc9ec34-5a5d-48aa-b616-854f17c43596/a/bb945ce3-72ec-4ef6-b064-4b09a0d24aa9?tenantId=9dc9ec34-5a5d-48aa-b616-854f17c43596&hint=c411359f-6bc3-4a6b-81ea-565e68211675&sourcetime=1707137739617&EcranConect=manager");
        dboard.setWidth("100%");
        dboard.setHeight("100%");
        add(dboard);
        setSizeFull();
    }
}
