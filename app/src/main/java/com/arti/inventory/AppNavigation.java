package com.arti.inventory;

import com.arti.inventory.security.AuthService;
import com.arti.inventory.security.model.AppRole;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;

public class AppNavigation extends Div{

    private static final String fontSize = "14px";
    
    public AppNavigation(AuthService auth){

        SideNav portal = new SideNav();
        configureSideNav(portal,"Portail");
        portal.addItem(
            new SideNavItem("Accueil", "", VaadinIcon.HOME.create()));

        SideNav managementNav = new SideNav();
        configureSideNav(managementNav, "Services");
        managementNav.addItem(
            new SideNavItem("Réservation de course", "auto/booking", VaadinIcon.CAR.create()),
            new SideNavItem("Congés & Absences", "/management/conges-absences", VaadinIcon.HOME.create())
            );

        SideNav dashboardsNav = new SideNav();
        configureSideNav(dashboardsNav, "Tableaux de boards");
        // collapseSection(dashboardsNav);
        dashboardsNav.addItem(
            // new SideNavItem("Tous les tableaux", "/dashboards", VaadinIcon.DASHBOARD.create()),
            new SideNavItem("Suivi du Personnel", "dashboards/suivi-personnel", VaadinIcon.EYE.create()),
            new SideNavItem("Comptes & Finances", "/dashboards/finances", VaadinIcon.EYE.create()),
            new SideNavItem("Suivi du Parc Auto", "/dashboards/parc-auto", VaadinIcon.EYE.create()),
            new SideNavItem("Suivi des MP", "/dashboards/mp", VaadinIcon.EYE.create()),
            new SideNavItem("Fiscalité ARTI", "/dashboards/fiscalite-arti", VaadinIcon.EYE.create()),
            new SideNavItem("Transport en chiffres", "/dashboards/chiffres-transport", VaadinIcon.EYE.create())
            );

        SideNav deviceNav = new SideNav();
        configureSideNav(deviceNav, "Equipements");
        // collapseSection(deviceNav);
        deviceNav.addItem(
            new SideNavItem("Dashboard", "/devices/dashboard", VaadinIcon.DASHBOARD.create()),
            new SideNavItem("Ordinateurs", "/devices/computers", VaadinIcon.RECORDS.create()),
            new SideNavItem("Imprimantes", "/devices/printers", VaadinIcon.RECORDS.create()),
            new SideNavItem("Téléphones IP", "/devices/phones", VaadinIcon.RECORDS.create()));
                
        SideNav adminNav = new SideNav();
        configureSideNav(adminNav, "Administrateur");
        collapseSection(adminNav);
        adminNav.addItem(
            new SideNavItem("Adresses IP", "/ip", VaadinIcon.RECORDS.create()));

        SideNav vehiculeNav = new SideNav();
        configureSideNav(vehiculeNav, "Gestion");
        collapseSection(vehiculeNav);
        vehiculeNav.addItem(
            new SideNavItem("Parc Auto", "/auto/parc-auto", VaadinIcon.CAR.create()),
            new SideNavItem("Fournisseurs", "/management/fournisseurs", VaadinIcon.USERS.create())
            // new SideNavItem("Kilométrages", "/mileages", VaadinIcon.ROAD.create())
            );

        SideNav missionNav = new SideNav();
        configureSideNav(missionNav, "Missions ARTI");
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

    private void configureSideNav(SideNav portal, String title) {
        portal.getStyle().setFontSize(fontSize);
        portal.setLabel(title);
        portal.setWidthFull();
        portal.setCollapsible(true);
    }

    private void collapseSection(SideNav adminNav) {
        adminNav.setExpanded(false);
    }
}
