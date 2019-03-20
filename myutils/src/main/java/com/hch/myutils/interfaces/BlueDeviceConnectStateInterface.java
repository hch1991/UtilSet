package com.hch.myutils.interfaces;

import android.bluetooth.BluetoothDevice;

public interface BlueDeviceConnectStateInterface {
    void blueDeviceStateChanged(boolean connectState, BluetoothDevice connectBluetoothDevice);
}
