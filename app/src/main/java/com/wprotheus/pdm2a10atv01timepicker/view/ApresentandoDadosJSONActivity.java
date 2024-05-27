package com.wprotheus.pdm2a10atv01timepicker.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wprotheus.pdm2a10atv01timepicker.R;
import com.wprotheus.pdm2a10atv01timepicker.databinding.ActivityApresentandoDadosJsonactivity2Binding;
import com.wprotheus.pdm2a10atv01timepicker.model.DataSetJSON;
import com.wprotheus.pdm2a10atv01timepicker.model.PedrasNumeros;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApresentandoDadosJSONActivity extends AppCompatActivity {
    private ActivityApresentandoDadosJsonactivity2Binding aJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aJson = ActivityApresentandoDadosJsonactivity2Binding.inflate(getLayoutInflater());
        setContentView(aJson.getRoot());
        iniciarDownloadDadosJson();
    }

    private void iniciarDownloadDadosJson() {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        service.execute(() -> {
            try {
                PedrasNumeros tempo = new DataSetJSON().baixarJSON();
                if (tempo != null) {
                    handler.post(() -> {
                        aJson.tvPedras.setText(tempo.getPedras().toString().replaceAll("[(\\[),(\\])]", ""));
                        aJson.tvNumeros.setText(tempo.getNumeros().toString().replaceAll("[(\\[),(\\])]", ""));
                        Toast.makeText(getApplicationContext(), R.string.dados_baixados, Toast.LENGTH_SHORT).show();
                    });
                } else
                    handler.post(() -> Toast.makeText(getApplicationContext(),
                            R.string.problema_end, Toast.LENGTH_LONG).show());
            } catch (Exception e) {
                e.printStackTrace();
                handler.post(() -> Toast.makeText(getApplicationContext(),
                        "Erro ao baixar dados: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }
        });
    }
}