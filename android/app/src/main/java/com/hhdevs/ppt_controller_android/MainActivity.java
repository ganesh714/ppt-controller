package com.hhdevs.ppt_controller_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static  String SERVER_IP;   // Replace with your PC's IP address
    private static final int SERVER_PORT = 8080;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        SERVER_IP = getIntent().getStringExtra("IP_Address");
        if (SERVER_IP != null && !SERVER_IP.isEmpty()) {
            Log.d(TAG, "Server IP received: " + SERVER_IP);
        }
        else {
            SERVER_IP = "192.168.251.104";
        }


        Log.d(TAG, "onCreate: Initializing buttons and service");

        Button buttonNext = findViewById(R.id.buttonNext);
        Button buttonPrevious = findViewById(R.id.buttonPrevious);
        Button buttonStart = findViewById(R.id.buttonStart);
        Button buttonStop = findViewById(R.id.buttonStop);

        buttonNext.setOnClickListener(v -> sendCommand("NEXT"));
        buttonPrevious.setOnClickListener(v -> sendCommand("PREVIOUS"));
        buttonStart.setOnClickListener(v -> sendCommand("START"));
        buttonStop.setOnClickListener(v -> sendCommand("STOP"));

        // Start Foreground Service
        Intent serviceIntent = new Intent(this, PPTControlService.class);
        startService(serviceIntent);

        Log.d(TAG, "onCreate: PPT Control Service started");
        Toast.makeText(this, "Service started to control PPT!", Toast.LENGTH_SHORT).show();
    }

    private void sendCommand(String command) {
        Log.d(TAG, "sendCommand: Sending command: " + command);
        Toast.makeText(this, "Sending command: " + command, Toast.LENGTH_SHORT).show();
        Intent serviceIntent = new Intent(this, PPTControlService.class);
        serviceIntent.putExtra("command", command);
        serviceIntent.putExtra("IP_Address",SERVER_IP);
        startService(serviceIntent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyDown: Key event detected: " + keyCode);
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                sendCommand("NEXT");
                Toast.makeText(this, "Next Slide", Toast.LENGTH_SHORT).show();
                return true;

            case KeyEvent.KEYCODE_VOLUME_DOWN:
                sendCommand("PREVIOUS");
                Toast.makeText(this, "Previous Slide", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onKeyDown(keyCode, event);
        }
    }
}
