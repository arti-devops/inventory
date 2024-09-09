package com.arti.inventory.dashboard.views;

import org.springframework.beans.factory.annotation.Value;
import com.arti.inventory.MainAppLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route(value = "dashboards/suivi-personnel", layout = MainAppLayout.class)
@RolesAllowed({"APP_DBOARD_SUIVIDUPERSONNEL","ADMIN"})
public class SuiviPersonnel extends VerticalLayout {

    public SuiviPersonnel(@Value("${dboard.url.personnel_arti}") String suiviDuPersonnelUrl){
        add(new H2("Tableau de bord: Suivi du Personnel"));
        
        IFrame dboard = new IFrame(suiviDuPersonnelUrl);
        dboard.setWidth("100%");
        dboard.setHeight("100%");
        add(dboard);
        setSizeFull();
    }
}
