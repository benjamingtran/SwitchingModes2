package com.example.switchingmodes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button tmButton;
    private Button lmButton;
    TextView txtSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tmButton = (Button) findViewById(R.id.trainingButton);
        lmButton = (Button) findViewById(R.id.loggingButton);

        txtSize = (TextView) findViewById(R.id.keySpy);
        txtSize.setTextSize(40);
        tmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });
        lmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });

    }

    private void openActivity2() {
        Intent intent = new Intent(this, TDGController.class);
        startActivity(intent);

    }
    private void openActivity3() {
        Intent intent = new Intent(this, LoggerController.class);
        startActivity(intent);

    }
}