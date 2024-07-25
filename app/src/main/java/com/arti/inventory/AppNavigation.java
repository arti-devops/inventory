package com.arti.inventory;

import java.util.Arrays;

import com.arti.inventory.security.AuthService;
import com.arti.inventory.security.model.AppRole;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;

public class AppNavigation extends Div{

    private AuthService auth;
    private static final String fontSize = "14px";
    
    public AppNavigation(AuthService auth){

        this.auth = auth;

        SideNav portal = new SideNav();
        configureSideNav(portal,"Portail");
        portal.addItem(new SideNavItem("Accueil", "", VaadinIcon.HOME.create()));

        SideNav servicesNav = new SideNav();
        configureSideNav(servicesNav, "Services");
        servicesNav.addItem(
            new SideNavItem("Réservation de course", "auto/booking", VaadinIcon.CAR.create()),
            new SideNavItem("Congés & Absences", "/management/conges-absences", VaadinIcon.HOME.create())
            );

        SideNav dashboardsNav = new SideNav();
        configureSideNav(dashboardsNav, "Tableaux de boards");
        // dashboardsNav.addItem(new SideNavItem("Tous les tableaux", "/dashboards", VaadinIcon.DASHBOARD.create()));
        SideNavItem sniDboardSuiviPersonnel = new SideNavItem("Suivi du Personnel", "dashboards/suivi-personnel", VaadinIcon.EYE.create());
        SideNavItem sniDboardComptesFinances = new SideNavItem("Comptes & Finances", "/dashboards/finances", VaadinIcon.EYE.create());
        SideNavItem sniDboardSuiviParcAuto = new SideNavItem("Suivi du Parc Auto", "/dashboards/parc-auto", VaadinIcon.EYE.create());
        SideNavItem sniDboardSuiviMP = new SideNavItem("Suivi des MP", "/dashboards/mp", VaadinIcon.EYE.create());
        SideNavItem sniDboardFiscaliteArti = new SideNavItem("Fiscalité ARTI", "/dashboards/fiscalite-arti", VaadinIcon.EYE.create());
        SideNavItem sniDboardTransportEnChiffre = new SideNavItem("Transport en chiffres", "/dashboards/chiffres-transport", VaadinIcon.EYE.create());
        if (this.auth!=null) {
            addRoleBasedMenuItem(dashboardsNav, sniDboardSuiviPersonnel, new AppRole[] {AppRole.APP_DBOARD_SUIVIDUPERSONNEL, AppRole.ADMIN});
            addRoleBasedMenuItem(dashboardsNav, sniDboardComptesFinances, new AppRole[] {AppRole.APP_DBOARD_COMPTESFINANCES, AppRole.ADMIN});
            addRoleBasedMenuItem(dashboardsNav, sniDboardSuiviParcAuto, new AppRole[] {AppRole.APP_DBOARD_PARCAUTO, AppRole.ADMIN});
            addRoleBasedMenuItem(dashboardsNav, sniDboardSuiviMP, new AppRole[] {AppRole.APP_DBOARD_MARCHESPUBLICS, AppRole.ADMIN});
            addRoleBasedMenuItem(dashboardsNav, sniDboardFiscaliteArti, new AppRole[] {AppRole.APP_DBOARD_FISCALITE, AppRole.ADMIN});
            addRoleBasedMenuItem(dashboardsNav, sniDboardTransportEnChiffre, new AppRole[]{AppRole.APP_DBOARD_CHIFFRESTRANSPORT, AppRole.ADMIN});
        }
        

        SideNav deviceNav = new SideNav();
        configureSideNav(deviceNav, "Equipements");
        deviceNav.addItem(new SideNavItem("Dashboard", "/devices/dashboard", VaadinIcon.DASHBOARD.create()));
        SideNavItem sniDeviceComputers = new SideNavItem("Ordinateurs", "/devices/computers", VaadinIcon.RECORDS.create());
        SideNavItem sniDevicePrinters = new SideNavItem("Imprimantes", "/devices/printers", VaadinIcon.RECORDS.create());
        SideNavItem sniDevicePhones = new SideNavItem("Téléphones IP", "/devices/phones", VaadinIcon.RECORDS.create());
        if (this.auth!=null) {
            addRoleBasedMenuItem(deviceNav, sniDeviceComputers, new AppRole[] {AppRole.APP_DEVICE_USER, AppRole.ADMIN});        
            addRoleBasedMenuItem(deviceNav, sniDevicePrinters, new AppRole[] {AppRole.APP_DEVICE_USER, AppRole.ADMIN});        
            addRoleBasedMenuItem(deviceNav, sniDevicePhones, new AppRole[] {AppRole.APP_DEVICE_USER, AppRole.ADMIN});        
        }

        SideNav adminNav = new SideNav();
        configureSideNav(adminNav, "Administrateur");
        collapseSection(adminNav);
        adminNav.addItem(new SideNavItem("Adresses IP", "/devices/ip", VaadinIcon.RECORDS.create()));

        SideNav gestionNav = new SideNav();
        configureSideNav(gestionNav, "Gestion");
        collapseSection(gestionNav);
        SideNavItem sniAutoParc = new SideNavItem("Parc Auto", "/auto/parc-auto", VaadinIcon.CAR.create());
        SideNavItem sniManagementFournisseurs = new SideNavItem("Fournisseurs", "/management/fournisseurs", VaadinIcon.USERS.create());
        if (this.auth!=null) {
            addRoleBasedMenuItem(gestionNav, sniAutoParc, new AppRole[]{AppRole.APP_PARCAUTO_USER, AppRole.ADMIN});
            addRoleBasedMenuItem(gestionNav, sniManagementFournisseurs, new AppRole[]{AppRole.APP_FOURNISSEUR_USER, AppRole.ADMIN});
        }

        SideNav missionNav = new SideNav();
        configureSideNav(missionNav, "Missions ARTI");
        collapseSection(missionNav);
        missionNav.addItem(
            new SideNavItem("A valider", "/missions/pending", VaadinIcon.CHECK_CIRCLE_O.create()),
            new SideNavItem("Missions", "/missions", VaadinIcon.AIRPLANE.create()));

        VerticalLayout navWrapper = new VerticalLayout(servicesNav, dashboardsNav, deviceNav, gestionNav);
        navWrapper.addComponentAtIndex(0, portal);

        if (auth != null) {
            if (auth.is(AppRole.APP_MISSION_USER.name()) || auth.isAdmin()) {
                navWrapper.add(missionNav);
            }

            if (auth.isAdmin()) {
                navWrapper.add(adminNav);
            }

            if (auth.isDG()) {
                navWrapper.addComponentAtIndex(1, dashboardsNav);
            }

            if (auth.isAppManager()) {
                navWrapper.addComponentAtIndex(1, deviceNav);
            }
        }

        add(navWrapper);
    }

    private void addRoleBasedMenuItem(SideNav sNav, SideNavItem sNavItem, AppRole[] roles) {
        Arrays.asList(roles).forEach(role -> {
            if (auth.is(role.name())) {
                sNav.addItem(sNavItem);
            }
        });
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
