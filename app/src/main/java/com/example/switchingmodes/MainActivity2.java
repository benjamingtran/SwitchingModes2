package com.example.switchingmodes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    TextView txtSize;
    TextView tmode;
    TextView logMode;
    TextView export;
    TextView Tdata;
    TextView Ldata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        txtSize = (TextView) findViewById(R.id.keySpy);
        txtSize.setTextSize(30);
        tmode = (TextView) findViewById(R.id.tmode);
        tmode.setTextSize(30);
        logMode = (TextView) findViewById(R.id.logMode);
        logMode.setTextSize(30);
        export = (TextView) findViewById(R.id.Export);
        export.setTextSize(30);
        Tdata = (TextView) findViewById(R.id.tdata);
        Tdata.setTextSize(30);
        Ldata = (TextView) findViewById(R.id.ldata);
        Ldata.setTextSize(30);
    }

}