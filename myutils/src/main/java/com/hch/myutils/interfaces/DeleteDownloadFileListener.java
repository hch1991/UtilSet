package com.hch.myutils.interfaces;

import com.hch.myutils.download.DownloadFileInfoObj;

import org.wlf.filedownloader.DownloadFileInfo;
import org.wlf.filedownloader.listener.OnDeleteDownloadFileListener;

public interface DeleteDownloadFileListener {
    void onDeleteDownloadFilePrepared(DownloadFileInfoObj downloadFileInfoObj);

    void onDeleteDownloadFileSuccess(DownloadFileInfoObj downloadFileInfoObj);

    void onDeleteDownloadFileFailed(DownloadFileInfoObj downloadFileInfoObj);
}
