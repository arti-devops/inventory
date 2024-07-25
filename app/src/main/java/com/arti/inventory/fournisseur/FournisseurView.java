package com.arti.inventory.fournisseur;

import org.vaadin.crudui.crud.impl.GridCrud;

import com.arti.inventory.MainAppLayout;
import com.arti.inventory.fournisseur.components.FournisseurComponents.RenderMediaFiles;
import com.arti.inventory.fournisseur.components.FournisseurComponents.RenderModeReglement;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "/management/fournisseurs", layout = MainAppLayout.class)
@PageTitle("SGI ARTI | Portail")
@RolesAllowed({"APP_FOURNISSEUR_USER","ADMIN"})
public class FournisseurView extends VerticalLayout {

    public FournisseurView(FournisseurService fournisseurService) {
        
        add(new H2("Fournisseurs ARTI"), new Paragraph("Tableau des fournisseurs de service de l'ARTI"));

        GridCrud<Fournisseur> crud = new GridCrud<>(Fournisseur.class);
        crud.getGrid().setColumns("nom","forme_juridique","regime","mode_de_reglement","responsable_financier","telephone_du_responsable_financier");
        // crud.getGrid().addColumns("media_registre_de_commerce","media_dfe","media_exoneration_tva","media_attestation_bancaire","media_code_import_export","media_rib_banque_01");
        crud.setCrudListener(fournisseurService);
        crud.setAddOperationVisible(false);
        crud.setUpdateOperationVisible(false);
        crud.setDeleteOperationVisible(false);
        crud.setFindAllOperationVisible(false);
        crud.setSizeFull();
        // crud.getGrid().getColumns().forEach(col -> col.setAutoWidth(true));
        
        crud.getGrid().getColumnByKey("nom").setRenderer(new ComponentRenderer<Span, Fournisseur>(fournisseur -> {
            Span span = new Span(fournisseur.getNom());
            span.getStyle().setFontSize("13px");
            return span;
        })).setHeader("Entreprise").setWidth("200px");

        crud.getGrid().getColumnByKey("forme_juridique").setRenderer(new ComponentRenderer<Span, Fournisseur>(fournisseur -> {
            Span span = new Span();
            span.getStyle().setFontSize("13px");
            switch(fournisseur.getForme_juridique()){
                case "Entreprise unipersonnelle":span.setText("UNIPERSONNEL");break;
                case "Société à responsabilité limitée (SARL)":span.setText("SARL");break;
                case "Société anonyme (SA)":span.setText("SA");break;
                case "Société unipersonnelle à responsabilité limitée (SUARL)":span.setText("SAURL");break;
                default: span.setText(fournisseur.getForme_juridique()); break;
            }
            return span;
        })).setHeader("Forme");

        crud.getGrid().getColumnByKey("regime").setRenderer(new ComponentRenderer<Span, Fournisseur>(fournisseur -> {
            Span span = new Span(fournisseur.getRegime());
            span.getStyle().setFontSize("13px");
            return span;
        })).setHeader("Régime");

        crud.getGrid().getColumnByKey("responsable_financier").setRenderer(new ComponentRenderer<Span, Fournisseur>(fournisseur -> {
            Span span = new Span(fournisseur.getResponsable_financier());
            try {
                String split = span.getText().split(" ")[0] + " " + span.getText().split(" ")[1];
                span.getStyle().setFontSize("13px");
                span.setText(split);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return span;
        })).setHeader("Responsable Fin.").setAutoWidth(true);

        crud.getGrid().getColumnByKey("telephone_du_responsable_financier").setRenderer(new ComponentRenderer<Span, Fournisseur>(fournisseur -> {
            Span span = new Span(fournisseur.getTelephone_du_responsable_financier().replaceAll("\\s+", ""));
            span.getStyle().setFontSize("13px");
            return span;
        })).setHeader("Contact RF");

        crud.getGrid().addColumn(new ComponentRenderer<>(fournisseur -> {
            return new RenderMediaFiles(fournisseur);
        })).setHeader("Fichhiers joints").setAutoWidth(true);

        crud.getGrid().getColumnByKey("mode_de_reglement").setRenderer(new ComponentRenderer<RenderModeReglement, Fournisseur>(fournisseur -> {
            return new RenderModeReglement(fournisseur);
        })).setHeader("Règlement");

        // crud.getGrid().getColumnByKey("media_registre_de_commerce").setRenderer(new ComponentRenderer<RenderMediaFile, Fournisseur>(fournisseur -> {
        //     return new RenderMediaFile(fournisseur.getMedia_registre_de_commerce(), "RCCM");
        // })).setHeader("RCCM").setAutoWidth(true);
        // crud.getGrid().getColumnByKey("media_dfe").setRenderer(new ComponentRenderer<RenderMediaFile, Fournisseur>(fournisseur -> {
        //     return new RenderMediaFile(fournisseur.getMedia_dfe(), "DFE");
        // })).setHeader("DFE").setAutoWidth(true);
        // crud.getGrid().getColumnByKey("media_exoneration_tva").setRenderer(new ComponentRenderer<RenderMediaFile, Fournisseur>(fournisseur -> {
        //     return new RenderMediaFile(fournisseur.getMedia_exoneration_tva(), "Exo TVA");
        // })).setHeader("Exo. TVA").setAutoWidth(true);
        // crud.getGrid().getColumnByKey("media_attestation_bancaire").setRenderer(new ComponentRenderer<RenderMediaFile, Fournisseur>(fournisseur -> {
        //     return new RenderMediaFile(fournisseur.getMedia_attestation_bancaire(), "AB");
        // })).setHeader("AB").setAutoWidth(true);
        // crud.getGrid().getColumnByKey("media_code_import_export").setRenderer(new ComponentRenderer<RenderMediaFile, Fournisseur>(fournisseur -> {
        //     return new RenderMediaFile(fournisseur.getMedia_code_import_export(), "Code I/E");
        // })).setHeader("Code I/E").setAutoWidth(true);
        // crud.getGrid().getColumnByKey("media_rib_banque_01").setRenderer(new ComponentRenderer<RenderMediaFile, Fournisseur>(fournisseur -> {
        //     return new RenderMediaFile(fournisseur.getMedia_rib_banque_01(), "RIB");
        // })).setHeader("RIB").setAutoWidth(true);

        setSizeFull();
        add(crud);
    }
}
