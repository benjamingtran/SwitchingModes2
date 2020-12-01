package com.example.switchingmodes;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorDirectChannel;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.MemoryFile;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LoggerModel {
    private SensorManager mSensorManager;
    private Sensor mGyro;
    private MemoryFile mem;
    private SensorDirectChannel mSDC;
    private File file;

    @SuppressLint("NewApi")
    public LoggerModel(SensorManager sm, File f) throws IOException {
        mSensorManager = sm;
        file = f;
        mGyro = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        mem = new MemoryFile(null, 1048575);

        mSDC = sm.createDirectChannel(mem);

    }

    @SuppressLint("NewApi")
    public void setLogging(boolean b) throws IOException {
        if(b)
        {
            mSDC.configure(mGyro,SensorDirectChannel.RATE_NORMAL);
            System.out.println("on");
            //return null;
        }
        else
        {
            mSDC.configure(mGyro,SensorDirectChannel.RATE_STOP);
            InputStream is = mem.getInputStream();
            //Path path;
            FileOutputStream fos = new FileOutputStream(file);

            byte[] buf = new byte[8192];
            int length;
            while ((length = is.read(buf)) > 0) {
                fos.write(buf, 0, length);
            }

            fos.close();

            System.out.println("off");
            //return i;
        }
    }

    public void close(){
        mSDC.close();
    }
}

