package com.snacourse.androstream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;


public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

       // Pushe.initialize(this,true);
       // if(SharedPerfrenceUtil.getInstance(this).runInFirstTime())
            //Toast.makeText(this,"first time",Toast.LENGTH_LONG).show();
        TextView txHLS=findViewById(R.id.textView2);
        TextView txAdaptiveBitrate=findViewById(R.id.textView3);
        TextView txAparat=findViewById(R.id.textView5);
        TextView textViewKhodam=findViewById(R.id.textViewKhodam);


        txAparat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(AnimationUtils.loadAnimation(SplashActivity.this, R.anim.fad_in));


                startActivity(new Intent(SplashActivity.this,PlayerActivityAparat.class));
            }
        });




        textViewKhodam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(AnimationUtils.loadAnimation(SplashActivity.this, R.anim.fad_in));


                startActivity(new Intent(SplashActivity.this,WithoutAdaptiveActivity.class));
            }
        });





         txHLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(AnimationUtils.loadAnimation(SplashActivity.this, R.anim.fad_in));

                startActivity(new Intent(SplashActivity.this,HLSPlayerActivity.class));
               // startActivity(new Intent(SplashActivity.this,TestBottomBarActivity.class));
            }
        });





        txAdaptiveBitrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(SplashActivity.this, R.anim.fad_in));

                startActivity(new Intent(SplashActivity.this,AdaptivePlayerActivity.class));
            }
        });


     /*   new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
             startActivity(new Intent(SplashActivity.this,CategoryActivity.class));
              // startActivity(new Intent(SplashActivity.this,TestBottomBarActivity.class));
                finish();
            }

        }.start();*/
    }
}
