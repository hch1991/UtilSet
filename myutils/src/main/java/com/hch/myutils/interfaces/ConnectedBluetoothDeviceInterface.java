package com.hch.myutils.interfaces;

import android.bluetooth.BluetoothDevice;

import java.util.List;

/**
 * @author hechuang
 * @ClassName: ConnectedBluetoothDeviceInterface.java
 * @Description: TODO
 * @date 2018/1/9 11:25
 */
public interface ConnectedBluetoothDeviceInterface {
    void connectedBluetoothDevice(List<BluetoothDevice> connectedDevices);
}
