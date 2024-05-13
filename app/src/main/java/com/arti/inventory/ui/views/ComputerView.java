package com.arti.inventory.ui.views;

import org.vaadin.crudui.crud.impl.GridCrud;

import com.arti.inventory.backend.model.Computer;
import com.arti.inventory.backend.service.ComputerService;
import com.arti.inventory.ui.MainAppLayout;
import com.arti.inventory.ui.DeviceStatusRenderer;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "computers", layout = MainAppLayout.class)
public class ComputerView extends VerticalLayout{

    H2 title = new H2("Ordinateurs");
    Paragraph paragraph = new Paragraph("Inventaire des ordinateurs de l'ARTI");

    public ComputerView(ComputerService computerService, 
                        DeviceStatusRenderer<Computer> deviceStatusRenderer){
        
        add(title, paragraph);

        // Crud
        GridCrud<Computer> crud = new GridCrud<>(Computer.class);
        crud.getGrid().setColumns("name","ip","brand","connexionMode","serie",
                                "direction","assignedTo","purchaseDate");
        crud.getGrid().addColumn(deviceStatusRenderer.createDeviceStatusComponentRenderer()).setHeader("Statut");
        
        crud.setCrudListener(computerService);

        setSizeFull();
        add(crud);
    }
}
