package com.hch.myutils.interfaces;

import android.net.wifi.WifiInfo;

public interface WifiConnectStatusListener {
    void wifiStatus(int wifiState);
    void wifiConnectInfo(boolean isConnect, WifiInfo wifiInfo);
}
