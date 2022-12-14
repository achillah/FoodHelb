package com.helb.foodhelb;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class DeliveryActivity extends AppCompatActivity {
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
   /*     ImageView img= (ImageView) findViewById(R.id.imageView12);
        img.setImageResource(R.drawable.delivery22);*/

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(DeliveryActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }
}