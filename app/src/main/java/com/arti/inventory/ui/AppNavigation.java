package com.arti.inventory.ui;

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
        adminNav.addItem(
            new SideNavItem("Adresses IP", "/ip", VaadinIcon.RECORDS.create()));

        VerticalLayout navWrapper = new VerticalLayout(deviceNav, adminNav);
        navWrapper.setSpacing(true);
        navWrapper.setSizeUndefined();
        deviceNav.setWidthFull();
        adminNav.setWidthFull();

        add(navWrapper);
    }
}
