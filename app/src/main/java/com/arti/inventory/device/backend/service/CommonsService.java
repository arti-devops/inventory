package com.arti.inventory.device.backend.service;

import org.vaadin.crudui.crud.impl.GridCrud;

import com.arti.inventory.device.backend.model.Device;
import com.arti.inventory.security.AuthService;

public class CommonsService {

    public static void crudActionsControl(AuthService auth, GridCrud<? extends Device> crud) {
        if (!auth.isAppManager()) {
            crud.setFindAllOperationVisible(true);
            crud.setAddOperationVisible(false);
            crud.setUpdateOperationVisible(false);
            crud.setDeleteOperationVisible(false);
        }
    }
}
