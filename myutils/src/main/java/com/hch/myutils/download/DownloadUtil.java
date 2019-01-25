package com.hch.myutils.download;

import android.content.Context;
import android.os.Environment;

import com.hch.myutils.interfaces.DeleteDownloadFileListener;
import com.hch.myutils.interfaces.DeleteDownloadFilesListener;
import com.hch.myutils.interfaces.DownloadFileChangeListener;
import com.hch.myutils.interfaces.DownloadStateListener;
import com.hch.myutils.utils.MLog;

import org.wlf.filedownloader.DownloadFileInfo;
import org.wlf.filedownloader.FileDownloadConfiguration;
import org.wlf.filedownloader.FileDownloadConfiguration.Builder;
import org.wlf.filedownloader.FileDownloader;
import org.wlf.filedownloader.listener.OnDeleteDownloadFileListener;
import org.wlf.filedownloader.listener.OnDeleteDownloadFilesListener;
import org.wlf.filedownloader.listener.OnDetectBigUrlFileListener;
import org.wlf.filedownloader.listener.OnDownloadFileChangeListener;
import org.wlf.filedownloader.listener.OnFileDownloadStatusListener;
import org.wlf.filedownloader.listener.simple.OnSimpleFileDownloadStatusListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DownloadUtil {

    private OnSimpleFileDownloadStatusListener onSimpleFileDownloadStatusListener;
    private OnDownloadFileChangeListener onDownloadFileChangeListener;
    private OnDeleteDownloadFileListener onDeleteDownloadFileListener;
    private OnDeleteDownloadFilesListener onDeleteDownloadFilesListener;
    private DownloadStateListener downloadStateListener;
    private DownloadFileChangeListener downloadFileChangeListener;
    private DeleteDownloadFileListener deleteDownloadFileListener;
    private DeleteDownloadFilesListener deleteDownloadFilesListener;


    // 私有构造
    private DownloadUtil() {
        initListener();
    }

    private static DownloadUtil downloadUtil = null;

    // 双重检查
    public static DownloadUtil getInstance() {
        if (downloadUtil == null) {
            synchronized (DownloadUtil.class) {
                if (downloadUtil == null) {
                    downloadUtil = new DownloadUtil();
                }
            }
        }
        return downloadUtil;
    }

    /**
     * @param savePath 保存路径
     * @return 返回类型
     * @throws
     * @Description: TODO 初始化下载服务
     * @author hechuang
     * @create 2019/1/24
     */
    public static void initDownload(Context context, String savePath) {
        initDownload(context, savePath, 2);
    }

    /**
     * @param savePath 保存路径  downloadTaskSize同时下载任务数量
     * @return 返回类型
     * @throws
     * @Description: TODO 初始化下载服务
     * @author hechuang
     * @create 2019/1/24
     */
    public static void initDownload(Context context, String savePath, int downloadTaskSize) {
        initDownload(context, savePath, downloadTaskSize, 0);
    }

    /**
     * @param savePath 保存路径  downloadTaskSize同时下载任务数量  retrySize失败时尝试重试的次数
     * @return 返回类型
     * @throws
     * @Description: TODO 初始化下载服务
     * @author hechuang
     * @create 2019/1/24
     */
    public static void initDownload(Context context, String savePath, int downloadTaskSize, int retrySize) {
        initDownload(context, savePath, 0, retrySize, 15);
    }

    /**
     * @param savePath 保存路径  downloadTaskSize同时下载任务数量  retrySize失败时尝试重试的次数 timeOut 超时时间
     * @return 返回类型
     * @throws
     * @Description: TODO 初始化下载服务
     * @author hechuang
     * @create 2019/1/24
     */
    public static void initDownload(Context context, String savePath, int downloadTaskSize, int retrySize, int timeOut) {
        initDownload(context, savePath, 0, retrySize, timeOut, false);
    }

    /**
     * @param savePath 保存路径  downloadTaskSize同时下载任务数量  retrySize失败时尝试重试的次数 timeOut 超时时间 debugMode是否开启debug
     * @return 返回类型
     * @throws
     * @Description: TODO 初始化下载服务
     * @author hechuang
     * @create 2019/1/24
     */
    public static void initDownload(Context context, String savePath, int downloadTaskSize, int retrySize, int timeOut, boolean debugMode) {
        // 1、创建Builder
        Builder builder = new FileDownloadConfiguration.Builder(context);
        // 2.配置Builder
        // 配置下载文件保存的文件夹
        builder.configFileDownloadDir(savePath);
        // 配置同时下载任务数量，如果不配置默认为2
        builder.configDownloadTaskSize(downloadTaskSize);
        // 配置失败时尝试重试的次数，如果不配置默认为0不尝试
        builder.configRetryDownloadTimes(retrySize);
        // 开启调试模式，方便查看日志等调试相关，如果不配置默认不开启
        builder.configDebugMode(debugMode);
        // 配置连接网络超时时间，如果不配置默认为15秒
        builder.configConnectTimeout(timeOut);// 25秒
        // 3、使用配置文件初始化FileDownloader
        FileDownloadConfiguration configuration = builder.build();
        FileDownloader.init(configuration);
    }

    private void initListener() {
        onSimpleFileDownloadStatusListener = new OnSimpleFileDownloadStatusListener() {
            @Override
            public void onFileDownloadStatusRetrying(DownloadFileInfo downloadFileInfo, int retryTimes) {
                // 正在重试下载（如果你配置了重试次数，当一旦下载失败时会尝试重试下载），retryTimes是当前第几次重试
                downloadStateListener.onFileDownloadStatusRetrying(convertObj(downloadFileInfo), retryTimes);
            }

            @Override
            public void onFileDownloadStatusWaiting(DownloadFileInfo downloadFileInfo) {
                // 等待下载（等待其它任务执行完成，或者FileDownloader在忙别的操作）
                downloadStateListener.onFileDownloadStatusWaiting(convertObj(downloadFileInfo));
            }

            @Override
            public void onFileDownloadStatusPreparing(DownloadFileInfo downloadFileInfo) {
                // 准备中（即，正在连接资源）
                downloadStateListener.onFileDownloadStatusPreparing(convertObj(downloadFileInfo));
            }

            @Override
            public void onFileDownloadStatusPrepared(DownloadFileInfo downloadFileInfo) {
                // 已准备好（即，已经连接到了资源）
                downloadStateListener.onFileDownloadStatusPrepared(convertObj(downloadFileInfo));
            }

            @Override
            public void onFileDownloadStatusDownloading(DownloadFileInfo downloadFileInfo, float downloadSpeed, long remainingTime) {
                // 正在下载，downloadSpeed为当前下载速度，单位KB/s，remainingTime为预估的剩余时间，单位秒
                downloadStateListener.onFileDownloadStatusDownloading(convertObj(downloadFileInfo), downloadSpeed, remainingTime);
            }

            @Override
            public void onFileDownloadStatusPaused(DownloadFileInfo downloadFileInfo) {
                // 下载已被暂停
                downloadStateListener.onFileDownloadStatusPaused(convertObj(downloadFileInfo));
            }

            @Override
            public void onFileDownloadStatusCompleted(DownloadFileInfo downloadFileInfo) {
                // 下载完成（整个文件已经全部下载完成）
                downloadStateListener.onFileDownloadStatusCompleted(convertObj(downloadFileInfo));
            }

            @Override
            public void onFileDownloadStatusFailed(String url, DownloadFileInfo downloadFileInfo, FileDownloadStatusFailReason failReason) {
                // 下载失败了，详细查看失败原因failReason，有些失败原因你可能必须关心
                downloadStateListener.onFileDownloadStatusFailed(url, convertObj(downloadFileInfo), failReason.getType());
//                String failType = failReason.getType();
//                String failUrl = failReason.getUrl();// 或：failUrl = url，url和failReason.getType()会是一样的
//
//                if (FileDownloadStatusFailReason.TYPE_URL_ILLEGAL.equals(failType)) {
//                    // 下载failUrl时出现url错误
//                } else if (FileDownloadStatusFailReason.TYPE_STORAGE_SPACE_IS_FULL.equals(failType)) {
//                    // 下载failUrl时出现本地存储空间不足
//                } else if (FileDownloadStatusFailReason.TYPE_NETWORK_DENIED.equals(failType)) {
//                    // 下载failUrl时出现无法访问网络
//                } else if (FileDownloadStatusFailReason.TYPE_NETWORK_TIMEOUT.equals(failType)) {
//                    // 下载failUrl时出现连接超时
//                } else {
//                    // 更多错误....
//                }
            }
        };

        onDownloadFileChangeListener = new OnDownloadFileChangeListener() {
            @Override
            public void onDownloadFileCreated(DownloadFileInfo downloadFileInfo) {
                // 一个新下载文件被创建，也许你需要同步你自己的数据存储，比如在你的业务数据库中增加一条记录
                downloadFileChangeListener.onDownloadFileCreated(convertObj(downloadFileInfo));
            }

            @Override
            public void onDownloadFileUpdated(DownloadFileInfo downloadFileInfo, Type type) {
                // 一个下载文件被更新，也许你需要同步你自己的数据存储，比如在你的业务数据库中更新一条记录
                downloadFileChangeListener.onDownloadFileUpdated(convertObj(downloadFileInfo));
            }

            @Override
            public void onDownloadFileDeleted(DownloadFileInfo downloadFileInfo) {
                // 一个下载文件被删除，也许你需要同步你自己的数据存储，比如在你的业务数据库中删除一条记录
                downloadFileChangeListener.onDownloadFileDeleted(convertObj(downloadFileInfo));
            }
        };

        onDeleteDownloadFileListener = new OnDeleteDownloadFileListener() {
            @Override
            public void onDeleteDownloadFilePrepared(DownloadFileInfo downloadFileNeedDelete) {
                deleteDownloadFileListener.onDeleteDownloadFilePrepared(convertObj(downloadFileNeedDelete));
            }

            @Override
            public void onDeleteDownloadFileSuccess(DownloadFileInfo downloadFileDeleted) {
                deleteDownloadFileListener.onDeleteDownloadFileSuccess(convertObj(downloadFileDeleted));
            }

            @Override
            public void onDeleteDownloadFileFailed(DownloadFileInfo downloadFileInfo, DeleteDownloadFileFailReason failReason) {
                deleteDownloadFileListener.onDeleteDownloadFileFailed(convertObj(downloadFileInfo));
            }
        };

        onDeleteDownloadFilesListener = new OnDeleteDownloadFilesListener() {
            @Override
            public void onDeleteDownloadFilesPrepared(List<DownloadFileInfo> downloadFilesNeedDelete) {
                deleteDownloadFilesListener.onDeleteDownloadFilesPrepared(convertObjs(downloadFilesNeedDelete));
            }

            @Override
            public void onDeletingDownloadFiles(List<DownloadFileInfo> downloadFilesNeedDelete, List<DownloadFileInfo> downloadFilesDeleted, List<DownloadFileInfo> downloadFilesSkip, DownloadFileInfo downloadFileDeleting) {
                deleteDownloadFilesListener.onDeletingDownloadFiles(convertObjs(downloadFilesNeedDelete),convertObjs(downloadFilesDeleted),convertObjs(downloadFilesSkip),convertObj(downloadFileDeleting));
            }

            @Override
            public void onDeleteDownloadFilesCompleted(List<DownloadFileInfo> downloadFilesNeedDelete, List<DownloadFileInfo> downloadFilesDeleted) {
                deleteDownloadFilesListener.onDeleteDownloadFilesCompleted(convertObjs(downloadFilesNeedDelete),convertObjs(downloadFilesDeleted));
            }
        };
    }

    /**
     * @param
     * @return 返回类型
     * @throws
     * @Description: TODO 添加下载状态监听
     * @author hechuang
     * @create 2019/1/24
     */
    public void addDownloadStateListener(final DownloadStateListener stateListener) {
        this.downloadStateListener = stateListener;
        if (onSimpleFileDownloadStatusListener != null) {
            FileDownloader.registerDownloadStatusListener(onSimpleFileDownloadStatusListener);
        } else {
            MLog.d("添加监听器失败");
        }
    }

    /**
     * @param
     * @return 返回类型
     * @throws
     * @Description: TODO 添加下载文件变化监听
     * @author hechuang
     * @create 2019/1/24
     */
    public void addDownloadFileChangeListener(final DownloadFileChangeListener fileChangeListener) {
        this.downloadFileChangeListener = fileChangeListener;
        if (onDownloadFileChangeListener != null) {
            FileDownloader.registerDownloadFileChangeListener(onDownloadFileChangeListener);
        } else {
            MLog.d("添加监听器失败");
        }
    }

    /**
     * @param
     * @return 返回类型
     * @throws
     * @Description: TODO 开始下载 如果文件没被下载过，将创建并开启下载，否则继续下载，自动会断点续传（如果服务器无法支持断点续传将从头开始下载）
     * @author hechuang
     * @create 2019/1/24
     */
    public void startDownload(String url) {
        FileDownloader.start(url);
    }

    /**
     * @param
     * @return 返回类型
     * @throws
     * @Description: TODO 开始下载 指定保存下载目录和保存下载文件名
     * @author hechuang
     * @create 2019/1/24
     */
    public void startDownload(String url, final String savePath, final String saveFileName) {
        FileDownloader.detect(url, new OnDetectBigUrlFileListener() {
            @Override
            public void onDetectNewDownloadFile(String url, String fileName, String saveDir, long fileSize) {
                // 如果有必要，可以改变文件名称fileName和下载保存的目录saveDir
                FileDownloader.createAndStart(url, savePath, saveFileName);
            }

            @Override
            public void onDetectUrlFileExist(String url) {
                // 继续下载，自动会断点续传（如果服务器无法支持断点续传将从头开始下载）
                FileDownloader.start(url);
            }

            @Override
            public void onDetectUrlFileFailed(String url, DetectBigUrlFileFailReason failReason) {
                // 探测一个网络文件失败了，具体查看failReason
            }
        });
    }

    /**
     * @param
     * @return 返回类型
     * @throws
     * @Description: TODO 暂停单个下载
     * @author hechuang
     * @create 2019/1/24
     */
    public void pause(String url) {
        FileDownloader.pause(url);
    }

    /**
     * @param
     * @return 返回类型
     * @throws
     * @Description: TODO 暂停多个下载
     * @author hechuang
     * @create 2019/1/24
     */
    public void pause(List<String> urls) {
        FileDownloader.pause(urls);
    }

    /**
     * @param
     * @return 返回类型
     * @throws
     * @Description: TODO 暂停所有下载
     * @author hechuang
     * @create 2019/1/24
     */
    public void pauseAll() {
        FileDownloader.pauseAll();
    }

    /**
     * @param
     * @return 返回类型
     * @throws
     * @Description: TODO 删除单个下载文件
     * @author hechuang
     * @create 2019/1/24
     */
    public void deleteDownload(String url, DeleteDownloadFileListener deleteDownloadFileListener) {
        this.deleteDownloadFileListener = deleteDownloadFileListener;
        FileDownloader.delete(url, true, onDeleteDownloadFileListener);
    }

    /**
     * @param
     * @return 返回类型
     * @throws
     * @Description: TODO 删除多个下载文件
     * @author hechuang
     * @create 2019/1/24
     */
    public void deleteDownload(List<String> urls, DeleteDownloadFilesListener deleteDownloadFilesListener) {
        this.deleteDownloadFilesListener = deleteDownloadFilesListener;
        FileDownloader.delete(urls, true, onDeleteDownloadFilesListener);
    }

    /**
     * @param
     * @return 返回类型
     * @throws
     * @Description: TODO 取消注册下载状态监听器
     * @author hechuang
     * @create 2019/1/24
     */
    public void unregisterDownloadStateListener(OnFileDownloadStatusListener onFileDownloadStatusListener) {
        FileDownloader.unregisterDownloadStatusListener(onFileDownloadStatusListener);
    }

    /**
     * @param
     * @return 返回类型
     * @throws
     * @Description: TODO 取消注册文件数据变化监听器
     * @author hechuang
     * @create 2019/1/24
     */
    public void unregisterDownloadFileChangeListener(OnDownloadFileChangeListener onDownloadFileChangeListener) {
        FileDownloader.unregisterDownloadFileChangeListener(onDownloadFileChangeListener);
    }

    private DownloadFileInfoObj convertObj(DownloadFileInfo d) {
        return new DownloadFileInfoObj(d.getUrl(), d.getFileSizeLong(), d.getETag(), d.getLastModified(), d.getAcceptRangeType(), d.getFileDir(), d.getTempFileName(), d.getCreateDatetime(), d.getDownloadedSizeLong(), d.getTempFileName(), d.getStatus());
    }
    private List<DownloadFileInfoObj> convertObjs(List<DownloadFileInfo> ds){
        List<DownloadFileInfoObj> downloadFileInfoObjs = new ArrayList<>();
        for(DownloadFileInfo d:ds){
            downloadFileInfoObjs.add(new DownloadFileInfoObj(d.getUrl(), d.getFileSizeLong(), d.getETag(), d.getLastModified(), d.getAcceptRangeType(), d.getFileDir(), d.getTempFileName(), d.getCreateDatetime(), d.getDownloadedSizeLong(), d.getTempFileName(), d.getStatus()));
        }
        return downloadFileInfoObjs;
    }
}
