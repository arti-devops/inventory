package com.arti.inventory.device.ui.views;

import org.vaadin.crudui.crud.impl.GridCrud;

import com.arti.inventory.MainAppLayout;
import com.arti.inventory.device.backend.model.Computer;
import com.arti.inventory.device.backend.service.CommonsService;
import com.arti.inventory.device.backend.service.ComputerService;
import com.arti.inventory.device.ui.component.DeviceStatusRenderer;
import com.arti.inventory.device.ui.render.SqlDateTimeRenderer;
import com.arti.inventory.security.AuthService;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.security.AuthenticationContext;

import jakarta.annotation.security.RolesAllowed;

@Route(value = "/devices/computers", layout = MainAppLayout.class)
@PageTitle("INV-ARTI | Ordinateurs")
@RolesAllowed({ "ROLE_APP_DEVICE_USER", "USER" })
public class ComputerView extends VerticalLayout {

    AuthService auth;
    H2 title = new H2("Ordinateurs");
    Paragraph paragraph = new Paragraph("Inventaire des ordinateurs de l'ARTI");

    public ComputerView(AuthenticationContext context,
            ComputerService computerService,
            DeviceStatusRenderer<Computer> deviceStatusRenderer,
            SqlDateTimeRenderer<Computer> sqlDateTimeRenderer) {

        auth = new AuthService(context);
        add(title, paragraph);

        // Crud
        GridCrud<Computer> crud = new GridCrud<>(Computer.class);
        crud.getCrudFormFactory().setUseBeanValidation(true);
        crud.getGrid().setColumns("name", "brand", "serie", "ip", "connexionMode", "direction", "assignedTo",
                "purchaseDate");
        renameComputerDeviceTableHeader(crud);

        // Additional columns
        // crud.getGrid().addColumn(sqlDateTimeRenderer.createSqlDateTimeComponentRenderer()).setHeader("Achat");
        // crud.getGrid().addColumn(deviceStatusRenderer.createDeviceStatusComponentRenderer()).setHeader("Statut");
        crud.getGrid().getColumnByKey("ip").setComparator(Computer::getIpToInteger);

        crud.getGrid().getColumns().forEach(column -> column.setAutoWidth(true));
        crud.setCrudListener(computerService);
        CommonsService.crudActionsControl(auth, crud);

        // Crud form
        crud.getCrudFormFactory().setVisibleProperties("name", "brand", "serie", "label", "ip", "connexionMode",
                "direction", "assignedTo");
        crud.getCrudFormFactory().setFieldCaptions("Nom/Hostname", "Marque", "Numéro de série", "Nom commercial",
                "Addresse IP", "Mode de connexion", "Direction", "Bénéficiaire");

        setSizeFull();
        add(crud);
    }

    private void renameComputerDeviceTableHeader(GridCrud<Computer> crud) {
        crud.getGrid().getColumnByKey("name").setHeader("Nom de l'ordinateur");
        crud.getGrid().getColumnByKey("ip").setHeader("Adresse IP");
        crud.getGrid().getColumnByKey("brand").setHeader("Marque");
        crud.getGrid().getColumnByKey("connexionMode").setHeader("Connexion");
        crud.getGrid().getColumnByKey("serie").setHeader("Numéro de série");
        crud.getGrid().getColumnByKey("direction").setHeader("Direction");
        crud.getGrid().getColumnByKey("assignedTo").setHeader("Bénéficiaire");
    }
}
