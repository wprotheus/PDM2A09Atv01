package com.wprotheus.pdm2a10atv01timepicker.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PedrasNumeros implements Serializable {
    private List<String> pedras = new ArrayList<>();
    private List<Integer> numeros = new ArrayList<>();
}