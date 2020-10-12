package com.hch.utilset;

import android.bluetooth.BluetoothDevice;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hch.myutils.download.DownloadFileInfoObj;
import com.hch.myutils.download.DownloadUtil;
import com.hch.myutils.interfaces.DeleteDownloadFileListener;
import com.hch.myutils.interfaces.DownloadStateListener;
import com.hch.myutils.utils.BlueToothUtil;
import com.hch.myutils.utils.MLog;

import java.util.ArrayList;
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

        mBlueToothUtil =BlueToothUtil.getInstance(MainActivity.this);
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

    /**
     * 打字机效果播放文字
     *
     */
//    public void startPrint() {
//        mPrinterTextView.setPrintText("草原上有对狮子母子。小狮子问母狮子：“妈，幸福在哪里?”母狮子说：“幸福就在你的尾巴上。”\n" +
//                "　　于是小狮子不断追着尾巴跑，但始终咬不到。母狮子笑道：“傻瓜!幸福不是这样得到的!只要你昂首向前走，幸福就会一直跟随着你!”。", 50, "|");
//        mPrinterTextView.startPrint();
//
//    }

//        飘落动画
//        flowerFlaySfv.setSnowDuration(300);
//        ArrayList<Drawable> imageList = new ArrayList<>();
//        imageList.add(mContext.getResources().getDrawable(R.mipmap.snowflake1,null));
//        imageList.add(mContext.getResources().getDrawable(R.mipmap.snowflake2,null));
//        imageList.add(mContext.getResources().getDrawable(R.mipmap.snowflake3,null));
//        imageList.add(mContext.getResources().getDrawable(R.mipmap.snowflake4,null));
//        imageList.add(mContext.getResources().getDrawable(R.mipmap.snowflake5,null));
//        imageList.add(mContext.getResources().getDrawable(R.mipmap.snowflake6,null));
//        flowerFlaySfv.setImageDrawableList(imageList);
//        flowerFlaySfv.startAnimation();
}
