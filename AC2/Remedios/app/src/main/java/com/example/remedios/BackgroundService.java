package com.example.remedios;

import static android.app.PendingIntent.FLAG_MUTABLE;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class BackgroundService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void showNotification(String text) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_MUTABLE);
        Notification notification = new NotificationCompat.Builder(this, "default")
                .setContentTitle("Notificação de app")
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .build();
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BancoHelper db = new BancoHelper(getApplicationContext());

                while (true) {
                    Cursor cursor = db.listarRemedios();

                    if (cursor.moveToFirst()) {
                        do {
                            String nome = cursor.getString(1);
                            String horario = cursor.getString(3);

                            Calendar agora = Calendar.getInstance();
                            int horaAtual = agora.get(Calendar.HOUR_OF_DAY);
                            int minutoAtual = agora.get(Calendar.MINUTE);

                            String[] partesHora = horario.split(":");
                            int horaRemedio = Integer.parseInt(partesHora[0]);
                            int minutoRemedio = Integer.parseInt(partesHora[1]);

                            if (horaAtual == horaRemedio && minutoAtual == minutoRemedio) {
                                showNotification("Tome seu remédio: " + nome);
                                try {
                                    Thread.sleep(60000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                        } while (cursor.moveToNext());
                    }

                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        return START_STICKY;
    }

}
