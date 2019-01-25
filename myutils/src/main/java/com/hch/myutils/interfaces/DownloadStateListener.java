package com.hch.myutils.interfaces;

import com.hch.myutils.download.DownloadFileInfoObj;

import org.wlf.filedownloader.DownloadFileInfo;
import org.wlf.filedownloader.listener.OnFileDownloadStatusListener;

public interface DownloadStateListener {
    void onFileDownloadStatusRetrying(DownloadFileInfoObj downloadFileInfo, int retryTimes);

    void onFileDownloadStatusWaiting(DownloadFileInfoObj downloadFileInfoObj);

    void onFileDownloadStatusPreparing(DownloadFileInfoObj downloadFileInfoObj);

    void onFileDownloadStatusPrepared(DownloadFileInfoObj downloadFileInfoObj);

    void onFileDownloadStatusDownloading(DownloadFileInfoObj downloadFileInfoObj, float downloadSpeed, long remainingTime);

    void onFileDownloadStatusPaused(DownloadFileInfoObj downloadFileInfoObj);

    void onFileDownloadStatusCompleted(DownloadFileInfoObj downloadFileInfoObj);

    void onFileDownloadStatusFailed(String url, DownloadFileInfoObj downloadFileInfoObj, String failType);
}
