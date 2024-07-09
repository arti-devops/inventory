package com.arti.inventory;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import java.util.ArrayList;
import java.util.Collection;

@Route("")
@PageTitle("SGI ARTI | Portail")
@AnonymousAllowed
public class PortalView extends VerticalLayout {
   VerticalLayout itemContainer;
   VerticalLayout externalAppsContainer;

   public PortalView() {
      this.addClassName("portal-view");
      VerticalLayout logoLayout = new VerticalLayout();
      Image logo = new Image("images/logo.png", "SGI ARTI");
      logo.setWidth("100%");
      logoLayout.setPadding(true);
      logoLayout.setClassName("portal-logo-layout");
      logoLayout.add(new Component[]{logo});
      logoLayout.setWidthFull();
      logoLayout.getStyle().setBackgroundColor("white");
      this.externalAppsContainer = this.getInitializedContainer();
      Collection<PortalItemList> externalApps = new ArrayList<>();
      externalApps.add(new PortalItemList("SYGFP", "http://finances.arti.ci", VaadinIcon.MONEY.create(), "_blank"));
      externalApps.add(new PortalItemList("Emeraude", "http://courrier.arti.ci", VaadinIcon.ENVELOPES.create(), "_blank"));
      externalApps.add(new PortalItemList("Congés et Absences", "https://tinyurl.com/arti-conges", VaadinIcon.COMPILE.create(), "_blank"));
      externalApps.add(new PortalItemList("Equipements", "http://localhost:8080/dashboard", VaadinIcon.RECORDS.create(), "_blank"));
      externalApps.add(new PortalItemList("Véhicules", "https://tinyurl.com/arti-vehicules", VaadinIcon.CAR.create(), "_blank"));
      externalApps.add(new PortalItemList("Missions", "http://169.239.64.130:8002/missions", VaadinIcon.AIRPLANE.create(), "_blank"));
      externalApps.add(new PortalItemList("Immobilisations", "http://immobilisations.arti.ci", VaadinIcon.BUILDING.create(), "_blank"));
      externalApps.add(new PortalItemList("Santé MCI", "http://isanet.mcicareci.com:8036/", VaadinIcon.HEALTH_CARD.create(), "_blank"));
      externalApps.forEach((item) -> {
         this.addToExternalAppsContainer(item);
      });
      this.itemContainer = this.getInitializedContainer();
      Collection<PortalItemList> items = new ArrayList<>();
      items.add(new PortalItemList("Accueil", "/", VaadinIcon.HOME.create()));
      items.add(new PortalItemList("Dashboard", "/dashboard", VaadinIcon.DASHBOARD.create()));
      items.add(new PortalItemList("Ordinateurs", "/computers", VaadinIcon.RECORDS.create()));
      items.add(new PortalItemList("Imprimantes", "/printers", VaadinIcon.RECORDS.create()));
      items.add(new PortalItemList("Téléphones", "/phones", VaadinIcon.RECORDS.create()));
      items.add(new PortalItemList("Véhicules", "/vehicules", VaadinIcon.CAR.create()));
      items.add(new PortalItemList("Kilométrages", "/mileages", VaadinIcon.ROAD.create()));
      items.add(new PortalItemList("Missions", "/missions", VaadinIcon.AIRPLANE.create()));
      items.forEach((item) -> {
         this.addToItemsContainer(item);
      });
      MasterContent masterContent = new MasterContent();
      DetailsContent detailsContent = new DetailsContent();
      detailsContent.add(new Component[]{new VerticalLayout(new Component[]{logo}), this.externalAppsContainer});
      HorizontalLayout mainLayout = new HorizontalLayout();
      mainLayout.setSizeFull();
      mainLayout.setClassName("portal-main-layout");
      mainLayout.addClassName("flex-wrap");
      mainLayout.add(new Component[]{masterContent, detailsContent});
      mainLayout.setFlexGrow(1.0, new HasElement[]{masterContent, detailsContent});
      this.add(new Component[]{mainLayout});
   }

   private VerticalLayout getInitializedContainer() {
      VerticalLayout container = new VerticalLayout();
      container.setSpacing(true);
      container.setPadding(true);
      container.getStyle().set("border-radius", "5px");
      return container;
   }

   private void addToItemsContainer(PortalItemList item) {
      this.itemContainer.add(new Component[]{item});
      this.itemContainer.setFlexGrow(1.0, new HasElement[]{item});
   }

   private void addToExternalAppsContainer(PortalItemList item) {
      this.externalAppsContainer.add(new Component[]{item});
      this.externalAppsContainer.setFlexGrow(1.0, new HasElement[]{item});
   }

    private class MasterContent extends VerticalLayout{
        public MasterContent(){
            setPadding(false);
            setClassName("portal-master-content");
           
            H1 title = new H1("PORTAIL ARTI");
            title.getStyle().set("color", "white");
            title.getStyle().setFont("bold 50px oswald");
            
            Span subtitle1 = this.createTexte("Bienvenue sur le portail de l'Autorité de Régulation du Transport Intérieur.");
            subtitle1.setWidth("80%");
            subtitle1.getStyle().setFont("bold italic 40px Arial, sans-serif");
            addClassName("justify-center");
            addClassName("items-center");
            
            Span cr = new Span("Copyright \u00a9 2024 ARTI");
            cr.addClassName("copy-right");
            cr.getStyle().set("color", "white");
            add(title, subtitle1, cr);
        }
        
        private Span createTexte(String text) {
            Span span = new Span(text);
            span.getStyle().set("color", "white");
            span.getStyle().set("font-size", "35px");
            span.getStyle().set("text-align", "center");
            return span;
        }
    }

    private class DetailsContent extends VerticalLayout {
        public DetailsContent(){
            setClassName("portal-details-content");
            getStyle().set("border-radius", "5px");
        }
        
    }

    private class PortalItemList extends Anchor {

        Icon icon;
        Span label;
        String link;
        
        public PortalItemList(String label, String link, Icon icon){
            this.icon = icon;
            this.link = link;
            this.label = new Span(label);
            HorizontalLayout item = this.configureItem();
            this.add(item);
        }

        public PortalItemList(String label, String link, Icon icon, String target){
            this(label, link, icon);
            setTarget(target);
        }

        private HorizontalLayout configureItem() {
            this.icon.setSize("15px");
            this.label = new Span(new Component[]{this.label});
            this.getStyle().set("border-radius", "5px");
            this.setClassName("portal-item");
            VerticalLayout containerLeft = new VerticalLayout();
            containerLeft.add(new Component[]{this.icon});
            containerLeft.setWidth("35px");
            containerLeft.getStyle().setBackgroundColor("white");
            VerticalLayout containerRight = new VerticalLayout();
            containerRight.add(new Component[]{this.label});
            containerLeft.setMargin(false);
            containerRight.setPadding(false);
            containerLeft.getStyle().set("padding", "0px 16px 0px 16px");
            containerRight.getStyle().set("padding", "5px 16px 5px 16px");
            containerRight.addClassName("portal-item-label");
            HorizontalLayout container = new HorizontalLayout();
            container.getStyle().setBackgroundColor("white");
            container.getStyle().setBorderRadius("5px");
            container.setMargin(false);
            container.setAlignItems(Alignment.CENTER);
            container.add(new Component[]{containerLeft, containerRight});
            container.setFlexGrow(1.0, new HasElement[]{containerRight});
            this.setHref(this.link);
            this.setSizeFull();
            return container;
        }  
    }
}
