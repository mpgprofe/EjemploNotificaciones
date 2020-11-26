package com.example.ejemplonotificaciones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationCompat.InboxStyle;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String ID_CANAL =  "canal del profesor";
    private static final int CODIGO_RESPUESTA = 1 ;
    Button buttonNotificacion, buttonNotificacion2,buttonNotificacion3;

    int contador =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       if(getIntent().getExtras()!=null){
           String mensaje = getIntent().getExtras().get("NOMBRE").toString();
           Toast.makeText(this, "Mensaje de la notificación: "+mensaje, Toast.LENGTH_LONG).show();
       }

        buttonNotificacion = findViewById(R.id.buttonNotificacion);
        buttonNotificacion2 = findViewById(R.id.buttonNotificacion2);
        buttonNotificacion3 = f

        buttonNotificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lanzarNotificacion();
            }
        });
        buttonNotificacion2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lanzarNotificacionTextoLargo();
            }
        });

    }

    private void lanzarNotificacionTextoLargo() {
        String idChannel = "Notificaciones largas";
        String nombreCanal = "Notificaciones largas";
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, ID_CANAL)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Notificación con texto en varias líneas")
                    .setAutoCancel(true)
                    .setContentText("Texto inicial");

            NotificationCompat.InboxStyle notificationCompatInboxStyle;
            CharSequence charSequence;
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            inboxStyle.setBigContentTitle("Este mensaje tiene más datos");
            inboxStyle.addLine("En un lugar de la Macha....");
            inboxStyle.addLine("Hoy llueve");
            inboxStyle.addLine("Pedro tu teclado suena tela");
            inboxStyle.addLine("Cristian despierta!!!");

            builder.setStyle(inboxStyle);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel =
                    new NotificationChannel(idChannel, nombreCanal, NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(true);
            builder.setChannelId(idChannel);
            notificationManager.createNotificationChannel(notificationChannel);


        }else{
            builder.setDefaults(Notification.DEFAULT_SOUND| Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS);

        }

        notificationManager.notify(contador++, builder.build());


    }

    private void lanzarNotificacion() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Context context;

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, ID_CANAL).
                setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Ejemplo muy simple de una notificacion")
                .setAutoCancel(false)
                .setContentText("Aquí un texto bien sencillo");

        String idChannel = "1";
        String nombreCanal = "Canal de notificaciones sencillas";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel =
                    new NotificationChannel(idChannel, nombreCanal, NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(true);
            builder.setChannelId(idChannel);
            notificationManager.createNotificationChannel(notificationChannel);


        }else{
            builder.setDefaults(Notification.DEFAULT_SOUND| Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS);

        }


        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(getApplicationContext());
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("NOMBRE", "Notificación sencilla");

        taskStackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(CODIGO_RESPUESTA, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        notificationManager.notify(1, builder.build());


    }
}