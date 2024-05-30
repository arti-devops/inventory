package com.arti.inventory.auto.ui;

import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;

import com.arti.inventory.auto.backend.model.MileageHistory;
import com.arti.inventory.auto.backend.model.Vehicule;
import com.arti.inventory.auto.backend.service.MileageHistoryService;
import com.arti.inventory.auto.backend.service.VehiculeService;
import com.arti.inventory.device.ui.MainAppLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;

@Route(value = "mileages", layout = MainAppLayout.class)
public class MileageView extends VerticalLayout {

    public MileageView(MileageHistoryService service, VehiculeService vehiculeService) {
        add(new H2("Historique des kilométrages"),
                new Paragraph("Historique des relevés de kilométrage des véhicules de l'ARTI"));

        GridCrud<MileageHistory> crud = new GridCrud<>(MileageHistory.class);
        crud.getGrid().setColumns("mileage", "statementDate", "vehicule");
        crud.getGrid().getColumnByKey("mileage").setRenderer(new ComponentRenderer<>(mileage -> {
            return new Paragraph(mileage.getMileage() + " km");
        })).setHeader("Kilométrage");
        crud.getGrid().getColumnByKey("statementDate").setHeader("Date du relevé");
        crud.getGrid().getColumnByKey("vehicule").setRenderer(new ComponentRenderer<>(mileage -> {
            return new Paragraph(mileage.getVehicule().getPlate());
        })).setHeader("Véhicule");
        crud.getCrudFormFactory().setVisibleProperties("mileage", "vehicule");
        crud.getCrudFormFactory().setFieldProvider("vehicule", new ComboBoxProvider<>("Véhicule", vehiculeService.findAll(), new TextRenderer<>(Vehicule::getPlate), Vehicule::getPlate));
        crud.setCrudListener(service);

        crud.setWidth("30%");
        crud.setMinWidth("360px");
        crud.setHeight("100%");

        setSizeFull();
        add(crud);
    }
}
