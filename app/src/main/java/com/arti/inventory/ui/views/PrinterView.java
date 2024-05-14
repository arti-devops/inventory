package com.arti.inventory.ui.views;

import org.vaadin.crudui.crud.impl.GridCrud;

import com.arti.inventory.backend.model.Printer;
import com.arti.inventory.backend.service.PrinterService;
import com.arti.inventory.ui.DeviceStatusRenderer;
import com.arti.inventory.ui.MainAppLayout;
import com.arti.inventory.ui.render.SqlDateTimeRenderer;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "printers", layout = MainAppLayout.class)
@PageTitle("INV-ARTI | Imprimantes")
public class PrinterView extends VerticalLayout {

    H2 title = new H2("Imprimantes");
    Paragraph subtitle = new Paragraph("Liste des imprimantes de l'ARTI");

    public PrinterView(PrinterService printerService,
                       DeviceStatusRenderer<Printer> deviceStatusRenderer,
                       SqlDateTimeRenderer<Printer> sqlDateTimeRenderer) {

        add(title, subtitle);

        GridCrud<Printer> crud = new GridCrud<>(Printer.class);
        crud.getGrid().setColumns("name","brand","serie","ip","connexionMode","direction","assignedTo");
        renameComputerDeviceTableHeader(crud);

        crud.getGrid().addColumn(sqlDateTimeRenderer.createSqlDateTimeComponentRenderer()).setHeader("Date d'achat");
        crud.getGrid().addColumn(deviceStatusRenderer.createDeviceStatusComponentRenderer()).setHeader("Statut");

        crud.getGrid().getColumns().forEach(column -> column.setAutoWidth(true));
        crud.setCrudListener(printerService);

        // Crud form
        crud.getCrudFormFactory().setVisibleProperties("name","brand","serie","ip","connexionMode","direction","assignedTo","ink1","ink2","ink3","ink4","detailsPage");
        crud.getCrudFormFactory().setFieldCaptions("Nom/Hostname","Marque","Numéro de série","Addresse IP","Mode de connexion","Direction","Bénéficiaire","Cartouche 1","Cartouche 2","Cartouche 3","Cartouche 4","Page de détails");

        setSizeFull();
        add(crud);
    }

    private void renameComputerDeviceTableHeader(GridCrud<Printer> crud){
        crud.getGrid().getColumnByKey("name").setHeader("Nom de l'imprimante");
        crud.getGrid().getColumnByKey("ip").setHeader("Adresse IP");
        crud.getGrid().getColumnByKey("brand").setHeader("Marque");
        crud.getGrid().getColumnByKey("connexionMode").setHeader("Connexion");
        crud.getGrid().getColumnByKey("serie").setHeader("Numéro de série");
        crud.getGrid().getColumnByKey("direction").setHeader("Direction");
        crud.getGrid().getColumnByKey("assignedTo").setHeader("Bénéficiaire");
    }  
}
