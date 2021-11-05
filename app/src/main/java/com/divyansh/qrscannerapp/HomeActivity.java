package com.divyansh.qrscannerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button generateQRBtn, scanQRBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        generateQRBtn = findViewById(R.id.btnGenerateQR);
        scanQRBtn = findViewById(R.id.btnScanQR);
        generateQRBtn.setOnClickListener(this);
        scanQRBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnGenerateQR:
                Intent i1 = new Intent(HomeActivity.this,GenerateQRCodeActivity.class);
                startActivity(i1);
                break;
            case R.id.btnScanQR:
                Intent i2 = new Intent(HomeActivity.this,ScanQRCodeActivity.class);
                startActivity(i2);
                break;
        }
    }
}