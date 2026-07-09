package pe.reciclaya.app.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import pe.reciclaya.app.R;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences prefs = getSharedPreferences("INICIAL", MODE_PRIVATE);
        int id = prefs.getInt("id", 0);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;

                if(id == 0) intent = new Intent(Splash.this, LoginActivity.class);
                else intent = new Intent(Splash.this, MainActivity.class);

                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}