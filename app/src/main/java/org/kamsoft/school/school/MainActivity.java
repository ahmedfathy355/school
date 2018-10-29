package org.kamsoft.school.school;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {


    Button login , new_login , our_services , album;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (Button)findViewById(R.id.btn_login);
        new_login = (Button)findViewById(R.id.btn_signup);
        our_services = (Button)findViewById(R.id.btn_services);
        album = (Button)findViewById(R.id.btn_album);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this , Main3Activity.class);
                startActivity(in);
            }
        });

        new_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this , AddHomwork.class);
                startActivity(in);
            }
        });

        our_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this , Main3Activity.class);
                startActivity(in);
            }
        });

        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this , Main3Activity.class);
                startActivity(in);
            }
        });
    }
}
