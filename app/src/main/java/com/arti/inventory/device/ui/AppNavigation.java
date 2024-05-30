package com.arti.inventory.device.ui;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;

public class AppNavigation extends Div{
    
    public AppNavigation(){

        SideNav deviceNav = new SideNav();
        deviceNav.setLabel("Equipements");
        deviceNav.addItem(
            new SideNavItem("Dashboard", "", VaadinIcon.DASHBOARD.create()),
            new SideNavItem("Ordinateurs", "/computers", VaadinIcon.RECORDS.create()),
            new SideNavItem("Imprimantes", "/printers", VaadinIcon.RECORDS.create()),
            new SideNavItem("Téléphones IP", "/phones", VaadinIcon.RECORDS.create()));
                
        SideNav adminNav = new SideNav();
        adminNav.setLabel("Administrateur");
        adminNav.setCollapsible(true);
        adminNav.setExpanded(false);
        adminNav.addItem(
            new SideNavItem("Adresses IP", "/ip", VaadinIcon.RECORDS.create()));

        SideNav vehiculeNav = new SideNav();
        vehiculeNav.setLabel("Véhicules ARTI");
        vehiculeNav.setSizeFull();
        vehiculeNav.addItem(
            new SideNavItem("Véhicules", "/vehicules", VaadinIcon.CAR.create()),
            new SideNavItem("Kilométrages", "/mileages", VaadinIcon.ROAD.create()),
            new SideNavItem("Modèles", "/modeles", VaadinIcon.RECORDS.create()),
            new SideNavItem("Carburants", "/carburants", VaadinIcon.RECORDS.create()),
            new SideNavItem("Types", "/types", VaadinIcon.RECORDS.create()));

        VerticalLayout navWrapper = new VerticalLayout(deviceNav, adminNav, vehiculeNav);
        navWrapper.setSpacing(true);
        navWrapper.setSizeUndefined();
        deviceNav.setWidthFull();
        adminNav.setWidthFull();

        add(navWrapper);
    }
}
