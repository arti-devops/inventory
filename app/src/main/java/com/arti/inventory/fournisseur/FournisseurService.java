package com.arti.inventory.fournisseur;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vaadin.crudui.crud.CrudListener;

@Service
public class FournisseurService implements CrudListener<Fournisseur> {

    @Override
    public Collection<Fournisseur> findAll() {
        return fetchApi();
    }

    public Collection<Fournisseur> fetchApi() {
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        String url = "https://www.arti.ci/wp-json/jet-cct/fournisseurs_arti";

        Fournisseur[] fdata = restTemplate.getForObject(url, Fournisseur[].class);
        return Arrays.asList(fdata);
    }

    @Override
    public Fournisseur add(Fournisseur arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public void delete(Fournisseur arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public Fournisseur update(Fournisseur arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}
