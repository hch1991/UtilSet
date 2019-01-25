package com.hch.myutils.download;

import android.database.Cursor;

import org.wlf.filedownloader.base.Status;

public class DownloadFileInfoObj {
    /**
     * file url
     */
    protected String mUrl;
    /**
     * file total size
     */
    protected long mFileSize;
    /**
     * file eTag
     */
    protected String mETag;
    /**
     * file last modified datetime(in server)
     */
    protected String mLastModified;
    /**
     * accept range type
     */
    protected String mAcceptRangeType;
    /**
     * save file dir
     */
    protected String mFileDir;
    /**
     * save file name
     */
    protected String mFileName;
    /**
     * create download datetime, yyyy-MM-dd HH:mm:ss
     */
    protected String mCreateDatetime;

    /**
     * downloadedSize
     */
    private long mDownloadedSize;
    /**
     * TempFileName
     */
    private String mTempFileName;
    /**
     * download status，ref{@link Status}
     */
    private int mStatus = Status.DOWNLOAD_STATUS_UNKNOWN;

    public DownloadFileInfoObj(String mUrl, long mFileSize, String mETag, String mLastModified, String mAcceptRangeType, String mFileDir, String mFileName, String mCreateDatetime, long mDownloadedSize, String mTempFileName, int mStatus) {
        this.mUrl = mUrl;
        this.mFileSize = mFileSize;
        this.mETag = mETag;
        this.mLastModified = mLastModified;
        this.mAcceptRangeType = mAcceptRangeType;
        this.mFileDir = mFileDir;
        this.mFileName = mFileName;
        this.mCreateDatetime = mCreateDatetime;
        this.mDownloadedSize = mDownloadedSize;
        this.mTempFileName = mTempFileName;
        this.mStatus = mStatus;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public long getmFileSize() {
        return mFileSize;
    }

    public void setmFileSize(long mFileSize) {
        this.mFileSize = mFileSize;
    }

    public String getmETag() {
        return mETag;
    }

    public void setmETag(String mETag) {
        this.mETag = mETag;
    }

    public String getmLastModified() {
        return mLastModified;
    }

    public void setmLastModified(String mLastModified) {
        this.mLastModified = mLastModified;
    }

    public String getmAcceptRangeType() {
        return mAcceptRangeType;
    }

    public void setmAcceptRangeType(String mAcceptRangeType) {
        this.mAcceptRangeType = mAcceptRangeType;
    }

    public String getmFileDir() {
        return mFileDir;
    }

    public void setmFileDir(String mFileDir) {
        this.mFileDir = mFileDir;
    }

    public String getmFileName() {
        return mFileName;
    }

    public void setmFileName(String mFileName) {
        this.mFileName = mFileName;
    }

    public String getmCreateDatetime() {
        return mCreateDatetime;
    }

    public void setmCreateDatetime(String mCreateDatetime) {
        this.mCreateDatetime = mCreateDatetime;
    }

    public long getmDownloadedSize() {
        return mDownloadedSize;
    }

    public void setmDownloadedSize(long mDownloadedSize) {
        this.mDownloadedSize = mDownloadedSize;
    }

    public String getmTempFileName() {
        return mTempFileName;
    }

    public void setmTempFileName(String mTempFileName) {
        this.mTempFileName = mTempFileName;
    }

    public int getmStatus() {
        return mStatus;
    }

    public void setmStatus(int mStatus) {
        this.mStatus = mStatus;
    }
}
