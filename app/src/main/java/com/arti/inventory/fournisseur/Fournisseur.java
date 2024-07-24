package com.arti.inventory.fournisseur;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fournisseur {
    private String _ID;
    private String cct_status;
    private String nom;
    private String sigle;
    private String raison_sociale;
    private String addresse;
    private String cct_author_id;
    private String cct_created;
    private String cct_modified;
    private String ville;
    private String pays;
    private String code_postal;
    private String telephone;
    private String fax;
    private String forme_juridique;
    private String date_de_creation;
    private String montant_du_capital;
    private String rccm;
    private String activite_principale;
    private String regime;
    private String centre_dimpots;
    private String nom_du_groupe;
    private String exoneration_de_tva;
    private String nom_qualite_direction;
    private String email;
    private String telephone_dirigeant;
    private String responsable_financier;
    private String email_responsable_financier;
    private String telephone_du_responsable_financier;
    private String nom_interlocuteur;
    private String email_interlocuteur;
    private String telephone_interlocuteur;
    private List<String> mode_de_reglement;
    private String numero_de_paiement_electronique;
    private String conditions_de_reglement;
    private String nom_banque_01;
    private String rib_banque_01;
    private String nom_gestionnaire_banque_01;
    private String contact_gestionnaire_banque_01;
    private String nom_banque_02;
    private String rib_banque_02;
    private String nom_gestionnaire_banque_02;
    private String contact_gestionnaire_banque_02;
    private String media_registre_de_commerce;
    private String media_exoneration_tva;
    private String media_rib_banque_01;
    private String media_attestation_bancaire;
    private String media_code_import_export;
    private String media_dfe;
    private String compte_contribuable;
    private String autre_demande;
    private String cct_slug;
}
