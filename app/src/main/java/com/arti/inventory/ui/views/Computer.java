package com.arti.inventory.ui.views;

import com.arti.inventory.ui.MainAppLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "computers", layout = MainAppLayout.class)
public class Computer extends VerticalLayout{

    H2 title;

    public Computer(){
        
        title = new H2("Liste des ordinateurs");
        add(title);
        add(new Paragraph("Inventaire des ordinateurs de l'ARTI"));
    }
}
