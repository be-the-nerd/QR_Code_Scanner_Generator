package com.divyansh.qrscannerapp;

import static android.Manifest.permission.VIBRATE;
import static android.Manifest.permission.CAMERA;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import eu.livotov.labs.android.camview.ScannerLiveView;
import eu.livotov.labs.android.camview.scanner.decoder.zxing.ZXDecoder;

public class ScanQRCodeActivity extends AppCompatActivity {

    private ScannerLiveView scannerLiveView;
    private TextView tvScanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qrcode);

        scannerLiveView = findViewById(R.id.camView);
        tvScanner = findViewById(R.id.tvScannedData);

        if (checkPermission()){
            Toast.makeText(this, "Permissions Granted", Toast.LENGTH_SHORT).show();
        }else {
            requestPermissions();
        }

        scannerLiveView.setScannerViewEventListener(new ScannerLiveView.ScannerViewEventListener() {
            @Override
            public void onScannerStarted(ScannerLiveView scanner) {
                Toast.makeText(ScanQRCodeActivity.this, "Scanner Started", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onScannerStopped(ScannerLiveView scanner) {
                Toast.makeText(ScanQRCodeActivity.this, "Scanner Stopped", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onScannerError(Throwable err) {
                Toast.makeText(ScanQRCodeActivity.this, "Scanner Error: " + err.getMessage(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCodeScanned(String data) {
                tvScanner.setText(data);
            }
        });
    }

    private boolean checkPermission(){
        int camera_permission = ContextCompat.checkSelfPermission(getApplicationContext(),CAMERA);
        int vibrate_permission = ContextCompat.checkSelfPermission(getApplicationContext(),VIBRATE);
        return camera_permission == PackageManager.PERMISSION_GRANTED && vibrate_permission == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions(){
        int PERMISSION_CODE = 200;
        ActivityCompat.requestPermissions(this, new String[]{CAMERA,VIBRATE}, PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
       super.onRequestPermissionsResult(requestCode, permissions, grantResults);
       if (grantResults.length>0){
           boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
           boolean vibrationAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
           if (cameraAccepted && vibrationAccepted){
               Toast.makeText(this, "Permissions Granted", Toast.LENGTH_SHORT).show();
           }else {
               Toast.makeText(this, "Permissions Denied \nYou cannot use the app without permissions", Toast.LENGTH_SHORT).show();
           }
       }
    }

    @Override
    protected void onPause() {
        scannerLiveView.stopScanner();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ZXDecoder decoder = new ZXDecoder();
        //0.8 is the area where we have to place red marker for scanning.
        decoder.setScanAreaPercent(0.8);
        scannerLiveView.setDecoder(decoder);
        scannerLiveView.startScanner();
    }
}