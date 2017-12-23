package com.igniva.genererics.fireworks;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import com.igniva.genererics.R;

/**
 * Created by ignivaandroid23 on 31/10/17.
 */

public class FireworksActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Setup does not display a title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Set the full screen mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ParticleView pv = new ParticleView(this);
        setContentView(pv);

        int mWidth= this.getResources().getDisplayMetrics().widthPixels;
        int mHeight= this.getResources().getDisplayMetrics().heightPixels;

        Log.d("FireworksActivity","mWidth-"+mWidth+" mHeight-"+mHeight);

        playAudio();

    }

    public void playAudio()
    {
        try {

            MediaPlayer mp = MediaPlayer.create(FireworksActivity.this, R.raw.fur_elis_music_box);
            mp.start();
            MediaPlayer mp2 = MediaPlayer.create(FireworksActivity.this, R.raw.zara_tasveer_se_tu);
            mp2.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void showDialogueResendVerification() {

        try {

            AlertDialog alertDialog=null;

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(FireworksActivity.this);
            LayoutInflater inflater = getLayoutInflater();
//            View dialogView = inflater.inflate(R.layout.custom_dialog_resend_verification, null);

            ParticleView pv = new ParticleView(FireworksActivity.this);

            dialogBuilder.setView(pv);
            dialogBuilder.setCancelable(false);

//            ButterKnife.bind(DialogViews.class, dialogView);
            alertDialog = dialogBuilder.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));






            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}