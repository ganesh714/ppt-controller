package com.hhdevs.ppt_controller_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        EditText ip_input = findViewById(R.id.ip_add_input);
        Button NxtBtn = findViewById(R.id.nxt_button);
        NxtBtn.setOnClickListener(v ->{
            String ip_str = ip_input.getText().toString();
            Log.d("Ip Addrr:",ip_str);
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("IP_Address",ip_str);
            startActivity(intent);
        });
    }
}
