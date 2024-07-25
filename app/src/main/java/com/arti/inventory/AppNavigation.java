package com.arti.inventory;

import com.arti.inventory.security.AuthService;
import com.arti.inventory.security.model.AppRole;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;

public class AppNavigation extends Div{
    
    public AppNavigation(AuthService auth){

        SideNav portal = new SideNav();
        portal.setLabel("Portail");
        portal.setWidthFull();
        portal.addItem(
            new SideNavItem("Accueil", "", VaadinIcon.HOME.create()));

        SideNav managementNav = new SideNav();
        managementNav.setLabel("Gestion");
        managementNav.setWidthFull();
        managementNav.addItem(
            new SideNavItem("Congés & Absences", "/management/conges-absences", VaadinIcon.HOME.create()),
            new SideNavItem("Fournisseurs", "/management/fournisseurs", VaadinIcon.USERS.create())
            );

        SideNav dashboardsNav = new SideNav();
        dashboardsNav.setLabel("Tableaux de boards");
        collapseSection(dashboardsNav);
        dashboardsNav.addItem(
            new SideNavItem("Tous les tableaux", "/dashboards", VaadinIcon.DASHBOARD.create()),
            new SideNavItem("Suivi du Personnel", "dashboards/suivi-personnel", VaadinIcon.EYE.create()),
            new SideNavItem("Comptes & Finances", "/dashboards/finances", VaadinIcon.EYE.create()),
            new SideNavItem("Suivi du Parc Auto", "/dashboards/parc-auto", VaadinIcon.EYE.create()),
            new SideNavItem("Suivi des MP", "/dashboards/mp", VaadinIcon.EYE.create()),
            new SideNavItem("Fiscalité ARTI", "/dashboards/fiscalite-arti", VaadinIcon.EYE.create()),
            new SideNavItem("Transport en chiffres", "/dashboards/chiffres-transport", VaadinIcon.EYE.create())
            // new SideNavItem("Ordinateurs", "/computers", VaadinIcon.RECORDS.create()),
            // new SideNavItem("Imprimantes", "/printers", VaadinIcon.RECORDS.create()),
            // new SideNavItem("Téléphones IP", "/phones", VaadinIcon.RECORDS.create())
            );

        SideNav deviceNav = new SideNav();
        deviceNav.setLabel("Equipements");
        collapseSection(deviceNav);
        deviceNav.addItem(
            new SideNavItem("Dashboard", "/dashboard", VaadinIcon.DASHBOARD.create()),
            new SideNavItem("Ordinateurs", "/computers", VaadinIcon.RECORDS.create()),
            new SideNavItem("Imprimantes", "/printers", VaadinIcon.RECORDS.create()),
            new SideNavItem("Téléphones IP", "/phones", VaadinIcon.RECORDS.create()));
                
        SideNav adminNav = new SideNav();
        adminNav.setLabel("Administrateur");
        collapseSection(adminNav);
        adminNav.addItem(
            new SideNavItem("Adresses IP", "/ip", VaadinIcon.RECORDS.create()));

        SideNav vehiculeNav = new SideNav();
        vehiculeNav.setLabel("Véhicules ARTI");
        collapseSection(vehiculeNav);
        vehiculeNav.addItem(
            new SideNavItem("Parc Auto", "/auto/parc-auto", VaadinIcon.CAR.create()),
            new SideNavItem("Réservation", "auto/booking", VaadinIcon.CAR.create())
            // new SideNavItem("Kilométrages", "/mileages", VaadinIcon.ROAD.create())
            );

        SideNav missionNav = new SideNav();
        missionNav.setLabel("Missions ARTI");
        collapseSection(missionNav);
        missionNav.addItem(
            new SideNavItem("A valider", "/missions/pending", VaadinIcon.CHECK_CIRCLE_O.create()),
            new SideNavItem("Missions", "/missions", VaadinIcon.AIRPLANE.create()));

        VerticalLayout navWrapper = new VerticalLayout(portal, managementNav, dashboardsNav, deviceNav, vehiculeNav); //, missionNav, vehiculeNav, adminNav);
        navWrapper.setSpacing(true);
        navWrapper.setSizeUndefined();
        deviceNav.setWidthFull();
        adminNav.setWidthFull();
        managementNav.setSizeFull();
        dashboardsNav.setWidthFull();

        if (auth != null) {
            if (auth.is(AppRole.APP_MISSION_USER.name()) || auth.isAdmin()) {
                navWrapper.add(missionNav);
            }

            if (auth.isAdmin()) {
                navWrapper.add(adminNav);
            }

            if (auth.isAdmin()) {
                navWrapper.addComponentAtIndex(2, dashboardsNav);
            }

            if (auth.isAdmin() || auth.is("APP_FOUNISSEUR_USER")) {
                //
            }
        }

        add(navWrapper);
    }

    private void collapseSection(SideNav adminNav) {
        adminNav.setSizeFull();
        adminNav.setCollapsible(true);
        // adminNav.setExpanded(false);
    }
}
