package com.example.q.subway;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

public class ThumbsUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thumbs_up);

        ImageView iv = (ImageView)findViewById(R.id.iv);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(iv);
        Glide.with(this).load(R.raw.tmbup).into(imageViewTarget);

        final SoundPool sound = new SoundPool(1, AudioManager.STREAM_ALARM, 0);// maxStreams, streamType, srcQuality
        final int soundId = sound.load(this, R.raw.diyong, 1);
        int streamId = sound.play(soundId, 1.0F, 1.0F,  0,  0,  1.0F);
        //sound.stop(streamId);
        sound.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                sound.play(soundId, 1.0F, 1.0F,  0,  0,  1.0F);
            }
        });

        startLoading();
    }

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                finish();
            }
        }, 3000);
    }
}
