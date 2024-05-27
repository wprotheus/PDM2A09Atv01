package com.wprotheus.pdm2a10atv01timepicker.util;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.wprotheus.pdm2a10atv01timepicker.R;
import com.wprotheus.pdm2a10atv01timepicker.view.ApresentandoDadosJSONActivity;

@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
public class NotificacaoUtil extends AppCompatActivity
        implements ActivityCompat.OnRequestPermissionsResultCallback {
    private static final String CHANNEL_ID = "8";
    private static final int NOTIFICATION_ID = 17;
    private static final int CODIGO_SOLICITACAO = 26;
    private static final String PERMISSAO = Manifest.permission.POST_NOTIFICATIONS;
    private NotificationManagerCompat managerCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        criarCanalNotificacao();
        solicitarPermissao();
    }

    private void criarCanalNotificacao() {
        CharSequence nome = getString(R.string.channel);
        String descricao = getString(R.string.desc_channel);
        int importancia = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel canal = new NotificationChannel(CHANNEL_ID, nome, importancia);
        canal.setDescription(descricao);
        NotificationManager nm = getSystemService(NotificationManager.class);
        nm.createNotificationChannel(canal);
    }

    private void solicitarPermissao() {
        int temPermissao = ContextCompat.checkSelfPermission(this, PERMISSAO);
        if (temPermissao != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{PERMISSAO}, CODIGO_SOLICITACAO);
        else
            gerarNotificacao();
    }

    private void gerarNotificacao() {
        Intent i = new Intent(this, ApresentandoDadosJSONActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pi = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_IMMUTABLE);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.olympus);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.title_not))
                .setContentText(getString(R.string.desc_not))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pi)
                .setLargeIcon(bitmap)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(getString(R.string.nome_classe)));
        managerCompat = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{PERMISSAO}, CODIGO_SOLICITACAO);
            return;
        }
        managerCompat.notify(NOTIFICATION_ID, builder.build());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != CODIGO_SOLICITACAO) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            gerarNotificacao();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    PERMISSAO)) {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle(R.string.perm_title)
                        .setMessage(R.string.msg_perm)
                        .setCancelable(false)
                        .setPositiveButton(R.string.btn_sim, (dialog, which) -> ActivityCompat.requestPermissions(this,
                                new String[]{PERMISSAO}, CODIGO_SOLICITACAO))
                        .setNegativeButton(R.string.btn_nao, (dialog, which) -> Toast.makeText(this, R.string.msg_sair,
                                Toast.LENGTH_SHORT).show());
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        }
    }
}