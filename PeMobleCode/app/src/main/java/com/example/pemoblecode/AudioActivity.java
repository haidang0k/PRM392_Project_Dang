package com.example.pemoblecode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class AudioActivity extends AppCompatActivity {
    private  static final int READ_REQUEST_CODE = 42;
    private  static final int PERMISSION_REQUEST_STORAGE = 1000;
    ImageButton btnplay, btnstop;
    Boolean flag = true;

    Button btnSelectAudio;

    public Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        btnplay = findViewById(R.id.btnplay);
        btnstop = findViewById(R.id.btnStop);
        btnSelectAudio = findViewById(R.id.btnSelectAudio);
        uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.trentinhbanduoitinhyeu);;

        //Xử lý audio select
        btnSelectAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(AudioActivity.this,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(AudioActivity.this,new
                            String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    return;
                }
                performFileSearch();
            }
        });


        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Khai báo Intent công khai để khởi động Service
                Intent intent1 = new Intent(AudioActivity.this,
                        MyService.class);
                intent1.putExtra("audioUri", uri.toString());
                startService(intent1);
                if (flag == true)
                {
                    btnplay.setImageResource(R.drawable.pause);
                    flag = false;
                }
                else
                {
                    btnplay.setImageResource(R.drawable.play);
                    flag = true;
                }
            }
        });
        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new
                        Intent(AudioActivity.this,MyService.class);
                intent2.putExtra("audioUri", uri.toString());
                stopService(intent2);
                btnplay.setImageResource(R.drawable.play);
                flag = true;
            }
        });
    }

    private void performFileSearch(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("audio/*"); // Đổi thành audio/* để mở các tệp âm thanh
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri uri2 = data.getData();
                this.uri = uri2;
                Toast.makeText(this, ""+ uri.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}