package com.arti.inventory.ui.views;

import com.arti.inventory.ui.MainAppLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

@Route(value = "", layout = MainAppLayout.class)
@PageTitle("INV-ARTI | Dasboard")
public class Dashboard extends VerticalLayout{

    H2 title;

    public Dashboard(){

        title = new H2("Dashboard");
        add(title);
        add(new Paragraph("Le tableau de board n'est pas encore implementé."));

        HorizontalLayout cardContainer = new HorizontalLayout();
        for(int i = 0; i < 15; i++){
            Card card = new Card();
            cardContainer.add(card);
            cardContainer.setFlexGrow(1, card);
        }
        //add(card);
        cardContainer.addClassName(LumoUtility.FlexWrap.WRAP);
        add(cardContainer);
    }

    public class Card extends VerticalLayout {
    
        public Card(){
            // CARD
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
            add(cardContent);

            getStyle().set("box-shadow", "0 1px 2px 0 rgba(0, 0, 0, 0.2), 0 1px 5px 0 rgba(0, 0, 0, 0.19)");
            getStyle().set("border-radius", "2px");
            getStyle().set("width", "25%");
            getStyle().set("min-width", "300px");
            
        }
    
    }


}
