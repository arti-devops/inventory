package com.arti.inventory.ui;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;

public class AppNavigation extends SideNav{
    
    public AppNavigation(){
        addItem(new SideNavItem("Dashboard", "", VaadinIcon.DASHBOARD.create()),
                new SideNavItem("Ordinateurs", "/computers", VaadinIcon.RECORDS.create()),
                new SideNavItem("Imprimantes", "/printers", VaadinIcon.RECORDS.create()),
                new SideNavItem("Téléphones IP", "/phones", VaadinIcon.RECORDS.create()),
                new SideNavItem("Serveurs", "/servers", VaadinIcon.RECORDS.create()),
                new SideNavItem("Adresses IP", "/ip", VaadinIcon.RECORDS.create()));
    }
}
