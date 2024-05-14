package com.arti.inventory.ui.views;

import com.arti.inventory.ui.MainAppLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainAppLayout.class)
@PageTitle("INV-ARTI | Dasboard")
public class Dashboard extends VerticalLayout{

    H2 title;

    public Dashboard(){

        title = new H2("Dashboard");
        add(title);
        add(new Paragraph("Le tableau de board n'est pas encore implementé."));

        // CARD
        VerticalLayout card = new VerticalLayout();
        Div cardContent = new Div();
        cardContent.add(new Paragraph("Ordinateurs"));
        cardContent.add(new Hr());
        H1 statNumber = new H1("49");
        Anchor tableLink = new Anchor("localhost:8080", "Voir le tableau");
        statNumber.getStyle().set("font-size", "80px");
        statNumber.getStyle().set("text-align", "center");
        statNumber.getStyle().set("padding", "20px 0px 20px 0px");
        cardContent.add(statNumber);
        cardContent.add(new Hr());
        cardContent.add(tableLink);
        cardContent.setSizeFull();
        card.add(cardContent);

        card.getStyle().set("box-shadow", "0 2px 4px 0 rgba(0, 0, 0, 0.2), 0 3px 10px 0 rgba(0, 0, 0, 0.19)");
        card.getStyle().set("border-radius", "2px");
        card.getStyle().set("max-width", "300px");

        add(card);
    }

}
