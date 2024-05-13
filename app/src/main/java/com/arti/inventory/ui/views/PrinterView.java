package com.arti.inventory.ui.views;

import org.vaadin.crudui.crud.impl.GridCrud;

import com.arti.inventory.backend.model.Printer;
import com.arti.inventory.backend.service.PrinterService;
import com.arti.inventory.ui.MainAppLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "printers", layout = MainAppLayout.class)
public class PrinterView extends VerticalLayout {

    H2 title = new H2("Imprimantes");
    Paragraph subtitle = new Paragraph("Liste des imprimantes de l'ARTI");

    public PrinterView(PrinterService printerService) {
        add(title, subtitle);

        GridCrud<Printer> crud = new GridCrud<>(Printer.class);
        crud.getGrid().setColumns("name","ip","brand","connexionMode","serie",
                                "direction","assignedTo","purchaseDate","online");
        crud.setCrudListener(printerService);

        setSizeFull();
        add(crud);
    }

}
