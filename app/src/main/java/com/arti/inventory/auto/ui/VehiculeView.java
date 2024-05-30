package com.arti.inventory.auto.ui;

import org.vaadin.crudui.crud.impl.GridCrud;

import com.arti.inventory.auto.backend.model.Vehicule;
import com.arti.inventory.auto.backend.service.VehiculeService;
import com.arti.inventory.device.ui.MainAppLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

@Route(value = "vehicules", layout = MainAppLayout.class)
public class VehiculeView extends VerticalLayout {

    public VehiculeView(VehiculeService service) {
        add(new H2("Véhicules ARTI"),
                new Paragraph("Relevé de kilométrage et de consommation des véhicules de l'ARTI"));

        GridCrud<Vehicule> crud = new GridCrud<>(Vehicule.class);
        crud.setCrudListener(service);
        crud.getGrid().setColumns("plate", "type", "brand", "model", "color", "currentMileage", "driverName");
        crud.getGrid().getColumnByKey("plate").setHeader("Immatriculation");
        crud.getGrid().getColumnByKey("type").setHeader("Type");
        crud.getGrid().getColumnByKey("brand").setHeader("Marque");
        crud.getGrid().getColumnByKey("model").setHeader("Modèle");
        crud.getGrid().getColumnByKey("color").setHeader("Couleur");
        crud.getGrid().getColumnByKey("currentMileage").setRenderer(new ComponentRenderer<>(vehicule -> {
            String theme = String.format("badge %s", getColorForNumber(vehicule.getCurrentMileage()));
            Paragraph paragraph = new Paragraph();
            paragraph.setText(vehicule.getCurrentMileage() + " km");
            paragraph.getElement().getThemeList().add(theme);
            return paragraph;
        })).setHeader("Kilométrage actuel");
        crud.getGrid().getColumnByKey("driverName").setHeader("Nom du conducteur");

        setSizeFull();
        add(crud);
    }

    public String getColorForNumber(Long number) {
        long remainder = number % 10000;
        long distanceToNextMultiple = 10000 - remainder;

        if (distanceToNextMultiple <= 1500) {
            return "error";
        } else if (distanceToNextMultiple <= 2000) {
            return "warning";
        } else {
            return " ";
        }
    }
}
