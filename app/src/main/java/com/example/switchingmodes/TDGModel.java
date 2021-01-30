package com.example.switchingmodes;

import android.hardware.Sensor;
import android.hardware.SensorDirectChannel;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.MemoryFile;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedList;
import java.util.Queue;

public class TDGModel {
    private class PressEvent{
        private char press;
        private long timestamp;

        public PressEvent(char p, long t){
            press = p;
            timestamp = t;
        }

        public char getPress(){
            return press;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }

    private SensorManager mSensorManager;
    private Sensor mGyro;
    private MemoryFile mem;
    private SensorDirectChannel mSDC;
    private File sensorFile;
    private File inputFile;
    private Queue<PressEvent> peQ;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public TDGModel(SensorManager sm, File senf, File inpf) throws IOException {
        mSensorManager = sm;
        sensorFile = senf;
        inputFile = inpf;
        mGyro = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        int entries = 100000;

        mem = new MemoryFile(null, 104*entries);

        mSDC = sm.createDirectChannel(mem);
        peQ  = new LinkedList<PressEvent>();

    }

    public void input(char pressed, long timestamp){
        peQ.add(new PressEvent(pressed,timestamp));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setLogging(boolean b) throws IOException {
        if(b)
        {
            mSDC.configure(mGyro,SensorDirectChannel.RATE_NORMAL);
            System.out.println("on");

        }
        else
        {
            mSDC.configure(mGyro,SensorDirectChannel.RATE_STOP);
            InputStream is = mem.getInputStream();

            FileOutputStream fos = new FileOutputStream(sensorFile);

            byte[] buf = new byte[8192];
            int length;
            while ((length = is.read(buf)) > 0) {
                fos.write(buf, 0, length);
            }
            fos.close();
            FileOutputStream fos2 = new FileOutputStream(inputFile);

            ByteBuffer bb2 = ByteBuffer.allocate(10);
            bb2.order(ByteOrder.LITTLE_ENDIAN);
            bb2.clear();
            while(!peQ.isEmpty()){
                PressEvent pe = peQ.remove();

                bb2.putLong(pe.getTimestamp());
                bb2.putChar(pe.getPress());

                fos2.write(bb2.array());
                bb2.clear();



            }
            fos2.close();

            System.out.println("off");

        }
    }

    public void close(){
        mSDC.close();
    }
}
