package com.arti.inventory.dashboard.views;

import com.arti.inventory.MainAppLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route(value = "dashboards/suivi-personnel", layout = MainAppLayout.class)
@RolesAllowed({"DRH","DG","ADMIN"})
public class SuiviPersonnel extends VerticalLayout {

    public SuiviPersonnel(){
        add(new H2("Tableau de bord: Suivi du Personnel"));
        
        IFrame dboard = new IFrame("https://app.powerbi.com/reportEmbed?reportId=6a17f108-6f11-4857-884b-669a379fc71a&autoAuth=true&ctid=9dc9ec34-5a5d-48aa-b616-854f17c43596");
        dboard.setWidth("100%");
        dboard.setHeight("100%");
        add(dboard);
        setSizeFull();
    }
}
