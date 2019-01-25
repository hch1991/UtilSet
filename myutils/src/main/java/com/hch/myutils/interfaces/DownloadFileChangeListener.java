package com.hch.myutils.interfaces;

import com.hch.myutils.download.DownloadFileInfoObj;

public interface DownloadFileChangeListener {
    void onDownloadFileCreated(DownloadFileInfoObj downloadFileInfoObj);

    void onDownloadFileUpdated(DownloadFileInfoObj downloadFileInfoObj);

    void onDownloadFileDeleted(DownloadFileInfoObj downloadFileInfoObj);
}
