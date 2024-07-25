package com.arti.inventory.dashboard.views;

import com.arti.inventory.MainAppLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import jakarta.annotation.security.PermitAll;

@Route(value = "dashboards", layout = MainAppLayout.class)
@PermitAll
public class Dashboards extends VerticalLayout {

    public Dashboards() {

        add(new H2("Les tableaux de bords"));

        Card tbDGPECRP = new Card("Tableau", "DGPECRP", "/dashboards/dgpecrp");
        Card tbDAAF = new Card("Tableau", "DAAF", "/dashboards/daaf");
        VerticalLayout cardContainer = new VerticalLayout();
        cardContainer.addClassNames(
            LumoUtility.Display.GRID,
            LumoUtility.Grid.FLOW_ROW,
            LumoUtility.Grid.Column.COLUMNS_1,
            LumoUtility.Grid.Breakpoint.Medium.COLUMNS_2,
            LumoUtility.Grid.Breakpoint.Large.COLUMNS_2
        );

        cardContainer.setSizeFull();
        cardContainer.add(tbDGPECRP, tbDAAF);
        
        add(cardContainer);
        setSizeFull();
    }

    public class Card extends VerticalLayout {

        Div cardContent = new Div();
        HorizontalLayout statLayout = new HorizontalLayout();
        H1 statNumber;
        H1 statNumber2;
        H1 statNumber3;
        Anchor tableLink;
        String label;

        public Card(String label, String stat, String link) {
            this.label = label;
            statNumber = new H1(stat);
            tableLink = new Anchor(link, "Voir le tableau");

            cardStructure();
            setCustomStyle();
            add(cardContent);
        }

        public Card(String label, String stat, String online, String link) {
            this(label, stat, link);
            statNumber2 = new H1(online);
            statNumber2.getElement().getThemeList().add("badge success");
            statNumber2.getStyle().set("font-size", "30px");
            statLayout.add(statNumber2);
        }

        public Card(String label, String stat, String online, String offline, String link) {
            this(label, stat, online, link);
            statNumber3 = new H1(offline);
            statLayout.add(statNumber3);
            statNumber3.getElement().getThemeList().add("badge error");
            statNumber3.getStyle().set("font-size", "30px");
        }

        private void cardStructure() {
            // Card Strucutre
            cardContent.add(new Paragraph(label));
            cardContent.add(new Hr());
            cardContent.add(statLayout);
            statLayout.add(statNumber);
            cardContent.add(new Hr());
            cardContent.add(tableLink);
        }

        private void setCustomStyle() {

            statLayout.setWidthFull();
            statLayout.setAlignItems(Alignment.CENTER);
            statLayout.setJustifyContentMode(JustifyContentMode.CENTER);

            // Card Style
            // statNumber.getStyle().set("font-size", "80px");
            statNumber.getStyle().set("text-align", "center");
            statNumber.getStyle().set("padding", "20px 0px 20px 0px");
            cardContent.setSizeFull();

            getStyle().set("box-shadow", "0 1px 2px 0 rgba(0, 0, 0, 0.2), 0 1px 5px 0 rgba(0, 0, 0, 0.19)");
            getStyle().set("border-radius", "2px");
            // getStyle().set("min-width", "300px");
            // getStyle().set("width", "25%");
        }

    }
}
