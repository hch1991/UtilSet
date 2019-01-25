package com.hch.utilset;

import android.bluetooth.BluetoothDevice;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hch.myutils.download.DownloadFileInfoObj;
import com.hch.myutils.download.DownloadUtil;
import com.hch.myutils.interfaces.DeleteDownloadFileListener;
import com.hch.myutils.interfaces.DownloadStateListener;
import com.hch.myutils.utils.BlueToothUtil;
import com.hch.myutils.utils.MLog;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BlueToothUtil mBlueToothUtil;
    private String savePath = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/test/";
    private String downloadUrl = "http://xnxs.soft.vrshow.com/o_1d1svqkcdj751b2g1d301c4968c9.zip";
    private DownloadUtil downloadUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        downloadUtil = DownloadUtil.getInstance();
        DownloadUtil.initDownload(MainActivity.this, savePath);

        mBlueToothUtil = new BlueToothUtil(MainActivity.this);
        findViewById(R.id.getConnectBlue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<BluetoothDevice> connectedDevices = mBlueToothUtil.getConnectBt();
                if (connectedDevices != null && connectedDevices.size() > 0) {
                    for (BluetoothDevice device : connectedDevices) {
                        MLog.d("" + device.getBluetoothClass().getMajorDeviceClass());
                        MLog.d(device.getName() + "," + device.getAddress());
                    }
                } else {
                    MLog.d("mDevices is null");
                }
            }
        });

        findViewById(R.id.download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadUtil.startDownload(downloadUrl, savePath, "updata.zip");
            }
        });
        findViewById(R.id.pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadUtil.pause(downloadUrl);
            }
        });
        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadUtil.deleteDownload(downloadUrl, new DeleteDownloadFileListener() {
                    @Override
                    public void onDeleteDownloadFilePrepared(DownloadFileInfoObj downloadFileInfoObj) {
                        MLog.d("onDeleteDownloadFilePrepared");
                    }

                    @Override
                    public void onDeleteDownloadFileSuccess(DownloadFileInfoObj downloadFileInfoObj) {
                        MLog.d("onDeleteDownloadFileSuccess");
                    }

                    @Override
                    public void onDeleteDownloadFileFailed(DownloadFileInfoObj downloadFileInfoObj) {
                        MLog.d("onDeleteDownloadFileFailed");
                    }
                });
            }
        });

        downloadUtil.addDownloadStateListener(new DownloadStateListener() {
            @Override
            public void onFileDownloadStatusRetrying(DownloadFileInfoObj downloadFileInfo, int retryTimes) {
                MLog.d("onFileDownloadStatusRetrying");
            }

            @Override
            public void onFileDownloadStatusWaiting(DownloadFileInfoObj downloadFileInfoObj) {
                MLog.d("onFileDownloadStatusWaiting");
            }

            @Override
            public void onFileDownloadStatusPreparing(DownloadFileInfoObj downloadFileInfoObj) {
                MLog.d("onFileDownloadStatusPreparing");
            }

            @Override
            public void onFileDownloadStatusPrepared(DownloadFileInfoObj downloadFileInfoObj) {
                MLog.d("onFileDownloadStatusPrepared");
            }

            @Override
            public void onFileDownloadStatusDownloading(DownloadFileInfoObj downloadFileInfoObj, float downloadSpeed, long remainingTime) {
                MLog.d("onFileDownloadStatusDownloading downloadSpeed:" + downloadSpeed + "    remainingTime:" + remainingTime);
                double downloadSize = downloadFileInfoObj.getmDownloadedSize() / 1024f / 1024;
                double fileSize = downloadFileInfoObj.getmFileSize() / 1024f / 1024;
                MLog.d("downloadSize:" + downloadSize);
                MLog.d("fileSize:" + fileSize);
                double percent = downloadSize / fileSize * 100;
                float progress = ((float)(Math.round(percent * 100)) / 100);
                MLog.d("download:" + progress + "%");
            }

            @Override
            public void onFileDownloadStatusPaused(DownloadFileInfoObj downloadFileInfoObj) {
                MLog.d("onFileDownloadStatusPaused");
            }

            @Override
            public void onFileDownloadStatusCompleted(DownloadFileInfoObj downloadFileInfoObj) {
                MLog.d("onFileDownloadStatusCompleted");
            }

            @Override
            public void onFileDownloadStatusFailed(String url, DownloadFileInfoObj downloadFileInfoObj, String failType) {
                MLog.d("onFileDownloadStatusFailed");
            }
        });
    }
}
