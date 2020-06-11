package com.example.activitymonitor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button buttonCoach;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonCoach = findViewById(R.id.buttonCoach);
        buttonCoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCoach();
            }
        });
    }

    public void openCoach(){
        Intent intent = new Intent (this, CoachPage.class);
        startActivity(intent);
    }
}