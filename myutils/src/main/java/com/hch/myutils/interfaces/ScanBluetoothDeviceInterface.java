package com.hch.myutils.interfaces;

import android.bluetooth.BluetoothDevice;

/**
 * @author hechuang
 * @ClassName: ScanBluetoothDeviceI.java
 * @Description: TODO
 * @date 2019/1/3 16:06
 */
public interface ScanBluetoothDeviceInterface {
    void scanBluetoothDevice(BluetoothDevice bluetoothDevice);
    // true 扫描中   false未扫描
    void scanStatus(boolean status);
}
