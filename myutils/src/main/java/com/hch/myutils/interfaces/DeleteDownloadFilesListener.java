package com.hch.myutils.interfaces;

import com.hch.myutils.download.DownloadFileInfoObj;

import org.wlf.filedownloader.DownloadFileInfo;
import org.wlf.filedownloader.listener.OnDeleteDownloadFilesListener;

import java.util.List;

public interface DeleteDownloadFilesListener{
    void onDeleteDownloadFilesPrepared(List<DownloadFileInfoObj> downloadFileInfoObjs);

    void onDeletingDownloadFiles(List<DownloadFileInfoObj> downloadFilesNeedDelete, List<DownloadFileInfoObj> downloadFilesDeleted, List<DownloadFileInfoObj> downloadFilesSkip, DownloadFileInfoObj downloadFileDeleting);

    void onDeleteDownloadFilesCompleted(List<DownloadFileInfoObj> downloadFilesNeedDelete, List<DownloadFileInfoObj> downloadFilesDeleted);
}
