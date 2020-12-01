package com.example.switchingmodes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.hardware.SensorManager;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import java.io.File;
import java.io.IOException;

public class LoggerController extends AppCompatActivity {

    private TextView txtSize;
    private TextView lmode;
    private LoggerModel logMod;
    private ToggleButton tbLogMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logger_view);
        txtSize = (TextView) findViewById(R.id.keySpy);
        txtSize.setTextSize(30);
        lmode = (TextView) findViewById(R.id.lmode);
        lmode.setTextSize(30);
        File f = new File(getExternalFilesDir(null), "logsen.txt");
        try {
            logMod = new LoggerModel((SensorManager) getSystemService(SENSOR_SERVICE), f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        tbLogMode = (ToggleButton)findViewById(R.id.tbLogMode);
        tbLogMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Intent i =

                try {
                        logMod.setLogging(isChecked);
                    } catch (IOException e) {
                        e.printStackTrace();
                }
                 /*
                 if(i!=null){
                     startActivity(i);
                 }
                 */
            }
        }

        );
    }
    @Override
    protected void onDestroy() {
        logMod.close();
        super.onDestroy();
    }
}

