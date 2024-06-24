package com.example.pemoblecode;

import static android.content.Intent.getIntent;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.widget.Toast;

import java.io.IOException;

public class MyService extends Service {
    //Khai báo đối tượng mà Service quản lý
    MediaPlayer mediaPlayer;

    @Override
    public IBinder onBind(Intent intent) {
    // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

//    //Gọi Hàm onStartCommand để khởi chạy đối tượng mà
//    @Override
//    public int onStartCommand(Intent intent, int flags, int
//            startId) {
//        super.onStartCommand(intent, flags, startId);
//
//        if (mymedia.isPlaying())
//            mymedia.pause();
//        else
//            mymedia.start();
//        return super.onStartCommand(intent, flags, startId);
//    }

    //Gọi Hàm onStartCommand để khởi chạy đối tượng mà
    @Override
    public int onStartCommand(Intent intent, int flags, int
            startId) {
        if (intent != null && intent.hasExtra("audioUri")) {
            String audioUriString = intent.getStringExtra("audioUri");
            Uri audioUri = Uri.parse(audioUriString);
            playAudio(audioUri);
        }
        return START_NOT_STICKY;
    }

    private void playAudio(Uri audioUri) {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(getApplicationContext(), audioUri);
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                stopSelf();
            }
        });
    }

    //Gọi Hàm onDestroy để dừng đối tượng mà Service quản lý
    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }
}
