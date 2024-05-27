package com.wprotheus.pdm2a10atv01timepicker.model;

import com.google.gson.Gson;
import com.wprotheus.pdm2a10atv01timepicker.util.Auxiliar;
import com.wprotheus.pdm2a10atv01timepicker.util.Conexao;

import java.io.InputStream;

public class DataSetJSON {
    private final String URL = "https://my-json-server.typicode.com/wprotheus/PDM2A10Atv01/db";

    public PedrasNumeros baixarJSON() {
        try {
            Conexao conexao = new Conexao();
            InputStream inputStream = conexao.obterRespostaHTTP(URL);
            Auxiliar auxilia = new Auxiliar();
            String textoFromJson = auxilia.converter(inputStream);

            if (!textoFromJson.isEmpty())
                return new Gson().fromJson(textoFromJson, PedrasNumeros.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}