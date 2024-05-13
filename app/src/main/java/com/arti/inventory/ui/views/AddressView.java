package com.arti.inventory.ui.views;

import org.vaadin.crudui.crud.impl.GridCrud;

import com.arti.inventory.backend.model.Address;
import com.arti.inventory.backend.service.AddressService;
import com.arti.inventory.ui.MainAppLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "/ip", layout = MainAppLayout.class)
public class AddressView extends VerticalLayout {

    H2 title = new H2("Adresses IP");
    Paragraph paragraph = new Paragraph("Repertoire des Adresse IP du réseau informatique de l'ARTI");

    public AddressView(AddressService addressService) {
        add(title, paragraph);

        GridCrud<Address> crud = new GridCrud<>(Address.class);
        crud.getGrid().setColumns("ipv4", "hostname", "deviceType", "connexionMode", "assignedTo", "direction", "online");
        crud.setCrudListener(addressService);

        setSizeFull();
        add(crud);
    }

}
