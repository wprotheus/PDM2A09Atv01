package com.wprotheus.pdm2a10atv01timepicker.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.wprotheus.pdm2a10atv01timepicker.databinding.ActivityMainBinding;
import com.wprotheus.pdm2a10atv01timepicker.util.AlarmeUtil;

import java.util.Calendar;

/*
 * Crie um app que apresente um TimerPicker para configurar um horário.
 * Após as configurações, o app deve gerar uma notificação que ao ser clicada
 * faça o consumo de dados de uma URL defina no JSONSERVER
 * (pode usar alguma já criada em atividade anteriores).
 *  ----------------------------------------------------
 * Você vai usar o timepicker para definir um horário,
 * quando der o horário uma notificação vai ser enviada.
 * Ao clicar na notificação uma tela será aberta (uma nova activity),
 * dessa forma deve-se consumir o JSON e mostrar na tela os dados.
 */

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding amb;
    private TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        amb = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(amb.getRoot());
        timePicker = amb.tpHorario;
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(this::configurarHorario);
    }

    @SuppressLint("DefaultLocale")
    public void configurarHorario(View v, int hora, int minuto) {
        Calendar horaEscolhida = Calendar.getInstance();
        horaEscolhida.set(Calendar.HOUR_OF_DAY, hora);
        horaEscolhida.set(Calendar.MINUTE, minuto);
        horaEscolhida.set(Calendar.SECOND, 0);

        long horario = horaEscolhida.getTimeInMillis();
        amb.tvHorarioEscolhido.setText(String.format("%02d:%02d", hora, minuto));
        amb.button.setOnClickListener(v1 -> gerarAlarme(horario));
    }

    private void gerarAlarme(long horario) {
        AlarmeUtil.adicionarAlarme(MainActivity.this, horario);
    }
}