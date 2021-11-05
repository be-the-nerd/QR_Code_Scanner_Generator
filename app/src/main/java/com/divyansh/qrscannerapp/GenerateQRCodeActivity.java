package com.divyansh.qrscannerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.PointerIcon;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class GenerateQRCodeActivity extends AppCompatActivity {

    private TextView tvQRCode;
    private ImageView ivQRCode;
    private TextInputEditText etData;
    private Button btnGenerateQR;
    private QRGEncoder qrgEncoder;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qrcode_acticity);

        tvQRCode = findViewById(R.id.tvGenerateQR);
        ivQRCode = findViewById(R.id.ivQrCode);
        etData = findViewById(R.id.etData);
        btnGenerateQR = findViewById(R.id.btnGenerateQRCode);
        
        btnGenerateQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = etData.getText().toString();
                if (data.isEmpty()){
                    Toast.makeText(GenerateQRCodeActivity.this, "Please enter some text to generate QR code", Toast.LENGTH_SHORT).show();
                }else {
                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point =  new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int dimen = width<height ? width:height;
                    dimen = dimen * 3/4;

                    qrgEncoder = new QRGEncoder(etData.getText().toString(),null, QRGContents.Type.TEXT,dimen);
                    try {
                        bitmap = qrgEncoder.encodeAsBitmap();
                        tvQRCode.setVisibility(View.GONE);
                        ivQRCode.setImageBitmap(bitmap);
                    }catch (WriterException e){
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}