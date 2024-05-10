package com.arti.inventory.ui.views;

import com.arti.inventory.ui.MainAppLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainAppLayout.class)
public class Dashboard extends VerticalLayout{

    H2 title;

    public Dashboard(){

        title = new H2("Dashboard");
        add(title);
        add(new Paragraph("Le tableau de board n'est pas encore implementé."));

    }

}
