package com.arti.inventory.ui.views;

import org.vaadin.crudui.crud.impl.GridCrud;

import com.arti.inventory.backend.model.Printer;
import com.arti.inventory.backend.service.PrinterService;
import com.arti.inventory.ui.MainAppLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializableBiConsumer;
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

        crud.getGrid().addColumn(createDeviceStatusComponentRenderer()).setHeader("Statut");

        crud.setCrudListener(printerService);

        setSizeFull();
        add(crud);
    }

    private static final SerializableBiConsumer<Span, Printer> deviceStatusComponentUpdater = (span, device) -> {
        Boolean isOnline = device.getOnline();
        String theme = String.format("badge %s", isOnline ? "success" : "error");
        span.getElement().setAttribute("theme", theme);
        span.setText(isOnline ? "En ligne" : "Hors ligne");
    };

    private static ComponentRenderer<Span, Printer> createDeviceStatusComponentRenderer() {
        return new ComponentRenderer<>(Span::new, deviceStatusComponentUpdater);
    }

}
