package com.arti.inventory;

import java.util.ArrayList;
import java.util.Collection;

import com.arti.inventory.device.ui.MainAppLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.LumoUtility;

@Route(value = "", layout = MainAppLayout.class)
@Theme(value = "arti")
@PageTitle("SGI ARTI | Portail")
public class PortalView extends VerticalLayout implements AppShellConfigurator {

    HorizontalLayout itemContainer;
    HorizontalLayout externalAppsContainer;

    public PortalView() {
        add(new H2("Portail ARTI"), new Paragraph("Bienvenue sur le portail de l'Autorité de Régulation du Transport Intérieur"));

        externalAppsContainer = getInitializedContainer();
        Collection<PortalItem> externalApps = new ArrayList<>();
        externalApps.add(new PortalItem("SYGFP", "http://finances.arti.ci", VaadinIcon.MONEY.create()));
        externalApps.add(new PortalItem("Emeraude", "http://courrier.arti.ci", VaadinIcon.ENVELOPES.create()));
        externalApps.add(new PortalItem("Congés et Réservations", "https://tinyurl.com/gestion-arti", VaadinIcon.COMPILE.create()));
        externalApps.add(new PortalItem("Portail MCI", "http://isanet.mcicareci.com:8036/", VaadinIcon.HEALTH_CARD.create()));
        externalApps.forEach(item -> addToExternalAppsContainer(item));
        
        itemContainer = getInitializedContainer();
        Collection<PortalItem> items = new ArrayList<>();
        items.add(new PortalItem("Accueil", "/", VaadinIcon.HOME.create()));
        items.add(new PortalItem("Dashboard", "/dashboard", VaadinIcon.DASHBOARD.create()));
        items.add(new PortalItem("Ordinateurs", "/computers", VaadinIcon.RECORDS.create()));
        items.add(new PortalItem("Imprimantes", "/printers", VaadinIcon.RECORDS.create()));
        items.add(new PortalItem("Téléphones IP", "/phones", VaadinIcon.RECORDS.create()));
        items.add(new PortalItem("Véhicules", "/vehicules", VaadinIcon.CAR.create()));
        items.add(new PortalItem("Kilométrages", "/mileages", VaadinIcon.ROAD.create()));
        items.add(new PortalItem("Missions", "/missions", VaadinIcon.AIRPLANE.create()));
        items.forEach(item -> addToItemsContainer(item));
        
        add(externalAppsContainer, new Hr(), itemContainer);
    }

    private HorizontalLayout getInitializedContainer() {
        HorizontalLayout container = new HorizontalLayout();
        container.setSpacing(true);
        container.setSizeFull();
        container.setClassName(LumoUtility.FlexWrap.WRAP);
        return container;
    }

    private void addToItemsContainer(PortalItem item) {
        itemContainer.add(item);
        itemContainer.setFlexGrow(1, item);
    }

    private void addToExternalAppsContainer(PortalItem item) {
        externalAppsContainer.add(item);
        externalAppsContainer.setFlexGrow(1, item);
    }

    private class PortalItem extends Anchor{

        Icon icon;
        Span label;
        String link;
        
        public PortalItem(String label, String link, Icon icon){
            // getStyle().set("border", "1px solid black");
            getStyle().set("border-radius", "5px");
            getStyle().set("box-shadow", "0 1px 2px 0 rgba(0, 0, 0, 0.2), 0 1px 5px 0 rgba(0, 0, 0, 0.19)");
            setWidth("150px");
            setMinWidth("150px");
            setClassName("portal-item");
            // setHeight("150px");
            
            VerticalLayout container = new VerticalLayout();
            //<theme-editor-local-classname>
            container.addClassName("portal-view-vertical-layout-1");
            container.setAlignItems(Alignment.CENTER);
            
            this.icon = icon;
            icon.setSize("40px");
            this.label = new Span(label);
            this.link = link;
            container.add(this.icon, this.label);

            setHref(this.link);
            setTarget("_blank");
            add(container);
        }  
    }
}
