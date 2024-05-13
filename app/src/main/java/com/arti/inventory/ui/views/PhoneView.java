package com.arti.inventory.ui.views;

import org.vaadin.crudui.crud.impl.GridCrud;

import com.arti.inventory.backend.model.Phone;
import com.arti.inventory.backend.service.PhoneService;
import com.arti.inventory.ui.MainAppLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "phones", layout = MainAppLayout.class)
public class PhoneView extends VerticalLayout {

    H2 title;

    public PhoneView(PhoneService phoneService){
        title = new H2("Téléphone");
        add(title);
        add(new Paragraph("Inventaire des téléphone IP de l'ARTI"));

        // CRUD
        GridCrud<Phone> crud = new GridCrud<>(Phone.class);
        crud.getGrid().setColumns("name","ip","brand","connexionMode","serie",
                                "direction","assignedTo","purchaseDate","online");
        crud.setCrudListener(phoneService);
        
        setSizeFull();
        add(crud);
    }

}
