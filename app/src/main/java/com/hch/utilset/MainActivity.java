package com.hch.utilset;

import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hch.myutils.interfaces.ConnectedBluetoothDeviceInterface;
import com.hch.myutils.utils.BlueToothUtil;
import com.hch.myutils.utils.MLog;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BlueToothUtil mBlueToothUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBlueToothUtil = new BlueToothUtil(MainActivity.this);
        findViewById(R.id.getConnectBlue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBlueToothUtil.getConnectBt(new ConnectedBluetoothDeviceInterface() {
                    @Override
                    public void connectedBluetoothDevice(List<BluetoothDevice> connectedDevices) {
                        MLog.d("size:"+connectedDevices.size());
                        if (connectedDevices != null && connectedDevices.size() > 0) {
                            for (BluetoothDevice device : connectedDevices) {
                                MLog.d(""+device.getBluetoothClass().getMajorDeviceClass());
                                switch (device.getBluetoothClass().getMajorDeviceClass()){

                                }

                                MLog.d(device.getName() + "," + device.getAddress());
                            }
                        } else {
                            MLog.d("mDevices is null");
                        }
                    }
                });
            }
        });
    }
}
