package com.hhdevs.ppt_controller_android;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.OutputStream;
import java.net.Socket;

public class PPTControlService extends Service {

    private static final String CHANNEL_ID = "PPTControlServiceChannel";
    private static  String SERVER_IP = "192.168.251.104";  // Replace with your PC's IP
    private static final int SERVER_PORT = 8080;
    private final Handler handler = new Handler();
    private static final String TAG = "PPTControlService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: Service created");
        createNotificationChannel();
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("PPT Controller Running")
                .setContentText("Listening for volume button events")
                .setSmallIcon(R.drawable.ic_notification)
                .build();
        startForeground(1, notification);
        Log.d(TAG, "onCreate: Notification created and service running in foreground");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.hasExtra("command")) {
            String command = intent.getStringExtra("command");
            SERVER_IP = intent.getStringExtra("IP_Address");
            Log.d(TAG, "onStartCommand: Received command: " + command);
            sendCommand(command);
        } else {
            Log.w(TAG, "onStartCommand: No command received in intent");
        }
        return START_STICKY;
    }

    private void sendCommand(String command) {
        Log.d(TAG, "sendCommand: Attempting to send command: " + command);
        Toast.makeText(this, "Attempting to send command: " + command, Toast.LENGTH_SHORT).show();

        new Thread(() -> {
            try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
                 OutputStream outputStream = socket.getOutputStream()) {

                outputStream.write(command.getBytes());
                outputStream.flush();
                handler.post(() -> {
                    Log.d(TAG, "sendCommand: Command sent successfully: " + command);
                    Toast.makeText(this, "Command sent: " + command, Toast.LENGTH_SHORT).show();
                });

            } catch (Exception e) {
                Log.e(TAG, "sendCommand: Failed to send command due to exception", e);
                handler.post(() -> Toast.makeText(this, "Failed to send command", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    public boolean onKeyEvent(KeyEvent event) {
        Log.d(TAG, "onKeyEvent: Key event detected: " + event.getKeyCode());
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_VOLUME_UP:
                    sendCommand("NEXT");
                    Toast.makeText(this, "Next Slide", Toast.LENGTH_SHORT).show();
                    return true;

                case KeyEvent.KEYCODE_VOLUME_DOWN:
                    sendCommand("PREVIOUS");
                    Toast.makeText(this, "Previous Slide", Toast.LENGTH_SHORT).show();
                    return true;
            }
        }
        return false;
    }

    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "PPT Control Service Channel",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
                Log.d(TAG, "createNotificationChannel: Notification channel created");
            } else {
                Log.w(TAG, "createNotificationChannel: NotificationManager is null");
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: Service bind requested");
        return null;
    }
}
