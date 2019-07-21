package com.vokasi.sensorapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensorProximity;
    private Sensor sensorAccelerometer;
    private TextView txSensorProximity;
    private TextView txSensorAccelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensorList=sensorManager.getSensorList(Sensor.TYPE_ALL);

        StringBuilder sensorText=new StringBuilder();
        for(Sensor sensor:sensorList){
            sensorText.append(sensor.getName())
                    .append(System.getProperty("line.separator"));
        }

        TextView sensorTextView=(TextView) findViewById(R.id.sensor_list);
        sensorTextView.setText(sensorText);

        txSensorProximity=(TextView)findViewById(R.id.sensor_proximity);
        txSensorAccelerometer=(TextView)findViewById(R.id.sensor_accelerometer);
        sensorProximity=sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorAccelerometer=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(sensorProximity == null){
            Toast.makeText(this,"No Sensor",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(sensorProximity!=null){
            sensorManager.registerListener(this,sensorProximity
                    ,SensorManager.SENSOR_DELAY_NORMAL);
            sensorManager.registerListener(this,sensorAccelerometer
                    ,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType=sensorEvent.sensor.getType();
        float value=sensorEvent.values[0];
        if(sensorType==Sensor.TYPE_PROXIMITY){
            txSensorProximity.setText("Proximity Sensor: "+value);
        }
        if(sensorType==Sensor.TYPE_ACCELEROMETER){
            txSensorAccelerometer.setText("Accelerometer Sensor: "+value);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
