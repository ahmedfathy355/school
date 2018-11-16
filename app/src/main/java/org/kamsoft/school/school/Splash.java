package org.kamsoft.school.school;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import org.kamsoft.school.school.Session.SessionManager;

public class Splash extends AppCompatActivity {

    SessionManager session;
    ImageView imgsplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imgsplash = (ImageView) findViewById(R.id.imagesplash);

        final Intent iii = new Intent(Splash.this, HomeActivity.class);
        Thread timer = new Thread(){
            public void run()  {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(iii);

                    finish();
                }
            }
        };
        timer.start();


    }
}
