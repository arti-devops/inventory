package com.arti.inventory.ui.views;

import com.arti.inventory.backend.service.ComputerService;
import com.arti.inventory.backend.service.PhoneService;
import com.arti.inventory.backend.service.PrinterService;
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

    public Dashboard(ComputerService computerService, 
                        PrinterService printerService, 
                        PhoneService phoneService){

        title = new H2("Dashboard");
        add(title);
        add(new Paragraph("Le tableau de board n'est pas encore implementé."));

        HorizontalLayout cardContainer = new HorizontalLayout();
        Card card = new Card("Ordinateurs", computerService.getDeviceCount().toString(), "computers");
        Card card2 = new Card("Imprimantes", printerService.getDeviceCount().toString(), "printers");
        Card card3 = new Card("Téléphones IP", phoneService.getDeviceCount().toString(), "phones");
        
        cardContainer.add(card, card2, card3);
        //cardContainer.add(card2);
        //cardContainer.add(card3);
        cardContainer.setFlexGrow(1, card, card2, card3);
        //cardContainer.setFlexGrow(1, card2);
        //cardContainer.setFlexGrow(1, card3);
        //add(card);
        cardContainer.addClassName(LumoUtility.FlexWrap.WRAP);
        add(cardContainer);
    }

    public class Card extends VerticalLayout {
    
        public Card(String label, String stat, String link){
            // CARD
            Div cardContent = new Div();
            H1 statNumber = new H1(stat);
            Anchor tableLink = new Anchor(link, "Voir le tableau");
            
            // Card Strucutre
            cardContent.add(new Paragraph(label));
            cardContent.add(new Hr());
            cardContent.add(statNumber);
            cardContent.add(new Hr());
            cardContent.add(tableLink);
            
            // Card Style
            statNumber.getStyle().set("font-size", "80px");
            statNumber.getStyle().set("text-align", "center");
            statNumber.getStyle().set("padding", "20px 0px 20px 0px");
            cardContent.setSizeFull();
            
            getStyle().set("box-shadow", "0 1px 2px 0 rgba(0, 0, 0, 0.2), 0 1px 5px 0 rgba(0, 0, 0, 0.19)");
            getStyle().set("border-radius", "2px");
            getStyle().set("width", "25%");
            getStyle().set("min-width", "300px");
            
            add(cardContent);
        }
    
    }


}
