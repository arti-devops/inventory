package com.arti.inventory.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.router.Route;

@Route("")
public class MainAppLayout extends AppLayout {

    H1 title;
    Scroller scroller;
    DrawerToggle toggle;
    AppNavigation nav;

    public MainAppLayout(){
        toggle = new DrawerToggle();
        title = new H1("Inventaire ARTI");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)").set("margin", "0");

        scroller = new Scroller(new AppNavigation());

        addToDrawer(scroller);
        addToNavbar(toggle, title);
    }

}
