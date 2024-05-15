package com.arti.inventory.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record InkColor(Long black, Long red, Long yellow, Long cyan) {}
