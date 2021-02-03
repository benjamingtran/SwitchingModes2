package com.example.switchingmodes;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
//import android.widget.CompoundButton;
//import android.widget.ToggleButton;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TDGController extends AppCompatActivity {
    private TDGModel tdgMod;

    private HashMap<Character, Button> buttons;

    private void keyboardSetup() {
        // first row of keys
        buttons.put('q', (Button)findViewById(R.id.qButton));
        buttons.put('w', (Button)findViewById(R.id.wButton));
        buttons.put('e', (Button)findViewById(R.id.eButton));
        buttons.put('r', (Button)findViewById(R.id.rButton));
        buttons.put('t', (Button)findViewById(R.id.tButton));
        buttons.put('y', (Button)findViewById(R.id.yButton));
        buttons.put('u', (Button)findViewById(R.id.uButton));
        buttons.put('i', (Button)findViewById(R.id.iButton));
        buttons.put('o', (Button)findViewById(R.id.oButton));
        buttons.put('p', (Button)findViewById(R.id.pButton));
        // second row of keys
        buttons.put('a', (Button)findViewById(R.id.aButton));
        buttons.put('s', (Button)findViewById(R.id.sButton));
        buttons.put('d', (Button)findViewById(R.id.dButton));
        buttons.put('f', (Button)findViewById(R.id.fButton));
        buttons.put('g', (Button)findViewById(R.id.gButton));
        buttons.put('h', (Button)findViewById(R.id.hButton));
        buttons.put('j', (Button)findViewById(R.id.jButton));
        buttons.put('k', (Button)findViewById(R.id.kButton));
        buttons.put('l', (Button)findViewById(R.id.lButton));
        // third row of keys
        buttons.put('z', (Button)findViewById(R.id.zButton));
        buttons.put('x', (Button)findViewById(R.id.xButton));
        buttons.put('c', (Button)findViewById(R.id.cButton));
        buttons.put('v', (Button)findViewById(R.id.vButton));
        buttons.put('b', (Button)findViewById(R.id.bButton));
        buttons.put('n', (Button)findViewById(R.id.nButton));
        buttons.put('m', (Button)findViewById(R.id.mButton));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String timestr = SystemClock.elapsedRealtimeNanos()+"";
        String senfn = timestr+".tdg";
        String inpfn = timestr+".inp";
        File senf = new File(getExternalFilesDir(null), senfn);
        File inpf = new File(getExternalFilesDir(null), inpfn);
        try {
            tdgMod = new TDGModel((SensorManager) getSystemService(SENSOR_SERVICE), senf, inpf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.tdg_view);

        buttons = new HashMap<>();

        keyboardSetup();

        for (Map.Entry<Character, Button> entry : buttons.entrySet()) {
            Character c = entry.getKey();
            entry.getValue().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tdgMod.input(c, SystemClock.elapsedRealtimeNanos());
                }
            });
        }

        //btnInput1 = (Button)findViewById(R.id.input1);
        //btnInput2 = (Button)findViewById(R.id.input2);
        /*
        btnInput1.setOnClickListener(new View.OnClickListener(){

                                         @Override
                                         public void onClick(View v) {
                                             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                                 tdgMod.input('1', SystemClock.elapsedRealtimeNanos());
                                             }
                                         }
                                     }

        );

        btnInput2.setOnClickListener(new View.OnClickListener(){

                                         @Override
                                         public void onClick(View v) {
                                             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                                 tdgMod.input('2', SystemClock.elapsedRealtimeNanos());
                                             }
                                         }
                                     }

        );
        */
        try {
            tdgMod.setLogging(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void updateTextView(String toThis) {
        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText(toThis);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onDestroy() {
        try {
            tdgMod.setLogging(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        tdgMod.close();
        super.onDestroy();
    }


}