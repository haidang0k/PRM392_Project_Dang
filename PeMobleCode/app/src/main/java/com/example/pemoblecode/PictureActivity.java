package com.example.pemoblecode;

import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class PictureActivity extends AppCompatActivity {
    ImageView myimg, btn_pictureBack;
    ImageButton btncapture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        myimg = findViewById(R.id.myimg);
        btncapture = findViewById(R.id.btncapture);
        btn_pictureBack = findViewById(R.id.btn_pictureBack);

        btncapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Khai báo intent ẩn để gọi đến  ACTION_IMAGE_CAPTURE
                Intent camerainent = new Intent(ACTION_IMAGE_CAPTURE);

                //Yêu cầu quyền truy cập camera
                if (ActivityCompat.checkSelfPermission(PictureActivity.this,
                        android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(PictureActivity.this,new
                            String[]{android.Manifest.permission.CAMERA}, 1);
                    return;
                }

                //Khởi động intent chờ kết quả
                startActivityForResult(camerainent, 99);
            }
        });

        btn_pictureBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 99 && resultCode == Activity.RESULT_OK){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            myimg.setImageBitmap(photo);
        }
    }
}