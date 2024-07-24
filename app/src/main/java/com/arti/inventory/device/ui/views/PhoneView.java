package com.arti.inventory.device.ui.views;

import org.vaadin.crudui.crud.impl.GridCrud;

import com.arti.inventory.MainAppLayout;
import com.arti.inventory.device.backend.model.Phone;
import com.arti.inventory.device.backend.service.CommonsService;
import com.arti.inventory.device.backend.service.PhoneService;
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

@Route(value = "phones", layout = MainAppLayout.class)
@PageTitle("INV-ARTI | Téléphones")
@RolesAllowed({"ROLE_APP_DEVICE_USER","USER"})
public class PhoneView extends VerticalLayout {

    H2 title = new H2("Téléphones");
    Paragraph paragraph = new Paragraph("Inventaire des téléphones de l'ARTI");

    public PhoneView(AuthenticationContext context,
                    PhoneService phoneService,
                     DeviceStatusRenderer<Phone> deviceStatusRenderer,
                     SqlDateTimeRenderer<Phone> sqlDateTimeRenderer){  
        
        add(title, paragraph);

        // CRUD
        GridCrud<Phone> crud = new GridCrud<>(Phone.class);
        crud.getGrid().setColumns("assignedTo","name","brand","serie","ip","connexionMode","direction");
        renameComputerDeviceTableHeader(crud);
        CommonsService.crudActionsControl(new AuthService(context), crud);

        // Additional columns
        //crud.getGrid().addColumn(sqlDateTimeRenderer.createSqlDateTimeComponentRenderer()).setHeader("Date d'achat");
        crud.getGrid().addColumn(deviceStatusRenderer.createDeviceStatusComponentRenderer()).setHeader("Statut").setSortable(true);
        
        crud.getGrid().getColumns().forEach(column -> column.setAutoWidth(true));
        crud.setCrudListener(phoneService);

        // Crud form
        crud.getCrudFormFactory().setUseBeanValidation(true);
        crud.getCrudFormFactory().setVisibleProperties("name","brand","serie","ip","connexionMode","direction","assignedTo");
        crud.getCrudFormFactory().setFieldCaptions("Nom/Hostname","Marque","Numéro de série","Addresse IP","Mode de connexion","Direction","Bénéficiaire");
        
        setSizeFull();
        add(crud);
    }

    private void renameComputerDeviceTableHeader(GridCrud<Phone> crud){
        crud.getGrid().getColumnByKey("name").setHeader("Numéro");
        crud.getGrid().getColumnByKey("ip").setHeader("Adresse IP");
        crud.getGrid().getColumnByKey("brand").setHeader("Marque");
        crud.getGrid().getColumnByKey("connexionMode").setHeader("Connexion");
        crud.getGrid().getColumnByKey("serie").setHeader("Numéro de série");
        crud.getGrid().getColumnByKey("direction").setHeader("Direction");
        crud.getGrid().getColumnByKey("assignedTo").setHeader("Bénéficiaire");
    }  

}
