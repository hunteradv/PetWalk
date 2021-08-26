package com.example.labinfo.pet_walk;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;


public class SplashTela extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_tela);


    getSupportActionBar().hide();
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(getBaseContext(), TelaInicial.class));
            finish();
        }
    }, 500);}
}
