package com.arti.inventory.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PrinterDetail(Long status, String url, InkColor color) {}