package com.arti.inventory.ui.views;

import org.vaadin.crudui.crud.impl.GridCrud;

import com.arti.inventory.backend.model.Computer;
import com.arti.inventory.backend.service.ComputerService;
import com.arti.inventory.ui.MainAppLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "computers", layout = MainAppLayout.class)
public class ComputerView extends VerticalLayout{

    H2 title;

    public ComputerView(ComputerService computerService){
        
        title = new H2("Liste des ordinateurs");
        add(title);
        add(new Paragraph("Inventaire des ordinateurs de l'ARTI"));

        // Crud
        GridCrud<Computer> crud = new GridCrud<>(Computer.class);
        crud.getGrid().setColumns("name","ip","brand","connexionMode","serie",
                                "direction","assignedTo","purchaseDate","online");
        crud.setCrudListener(computerService);

        setSizeFull();
        add(crud);
    }
}
