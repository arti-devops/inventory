package com.arti.inventory.ui.views;

import org.vaadin.crudui.crud.impl.GridCrud;

import com.arti.inventory.backend.model.Phone;
import com.arti.inventory.backend.service.PhoneService;
import com.arti.inventory.ui.DeviceStatusRenderer;
import com.arti.inventory.ui.MainAppLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "phones", layout = MainAppLayout.class)
public class PhoneView extends VerticalLayout {

    H2 title = new H2("Téléphones");
    Paragraph paragraph = new Paragraph("Inventaire des téléphones de l'ARTI");

    public PhoneView(PhoneService phoneService,
                     DeviceStatusRenderer<Phone> deviceStatusRenderer){  
        
        add(title, paragraph);

        // CRUD
        GridCrud<Phone> crud = new GridCrud<>(Phone.class);
        crud.getGrid().setColumns("name","ip","brand","connexionMode","serie",
                                "direction","assignedTo","purchaseDate");
        crud.getGrid().addColumn(deviceStatusRenderer.createDeviceStatusComponentRenderer()).setHeader("Statut");
        
        crud.setCrudListener(phoneService);
        
        setSizeFull();
        add(crud);
    }

}
