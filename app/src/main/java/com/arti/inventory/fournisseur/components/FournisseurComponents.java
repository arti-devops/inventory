package com.arti.inventory.fournisseur.components;

import com.arti.inventory.fournisseur.Fournisseur;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class FournisseurComponents {

    public static class RenderMediaFile extends Anchor {
        public RenderMediaFile(String mediaLink, String label) {
            Button btn = new Button(label);
            btn.getStyle().setFontSize("12px");
            btn.setIcon(VaadinIcon.FILE.create());
            if (mediaLink.isEmpty()) {
                btn.setEnabled(false);
            } else {
                setHref(mediaLink);
                btn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
            }
            btn.setSizeFull();
            setSizeFull();
            add(btn);
        }
    }

    public static class RenderMediaFiles extends HorizontalLayout {
        public RenderMediaFiles(Fournisseur fournisseur) {
            add(renderMediaFile(fournisseur.getMedia_registre_de_commerce(), "RCCM"));
            add(renderMediaFile(fournisseur.getMedia_dfe(), "DFE"));
            add(renderMediaFile(fournisseur.getMedia_exoneration_tva(), "Exo. TVA"));
            add(renderMediaFile(fournisseur.getMedia_attestation_bancaire(), "AB"));
            add(renderMediaFile(fournisseur.getMedia_code_import_export(), "Code I/E"));
            add(renderMediaFile(fournisseur.getMedia_rib_banque_01(), "RIB"));
        }
    }

    public static class RenderModeReglement extends VerticalLayout {
        public RenderModeReglement(Fournisseur fournisseur) {
            setSpacing(false);
            setPadding(false);
            fournisseur.getMode_de_reglement().forEach(m -> {
                Span span = new Span(m);
                span.getStyle().setFontSize("12px");
                add(span);
            });
        }
    }

    private static Anchor renderMediaFile(String mediaLink, String label) {
        Anchor a = new Anchor();
        Button btn = new Button(label);
        btn.setIcon(VaadinIcon.FILE.create());
        if (mediaLink.isEmpty()) {
            btn.setEnabled(false);
        } else {
            a.setHref(mediaLink);
            btn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        }
        btn.setSizeFull();
        a.setSizeFull();
        a.add(btn);
        return a;
    }
}
