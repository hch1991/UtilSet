/**
 * Copyright (C) 2015  Haiyang Yu Android Source Project
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hch.myutils.utils;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * @ClassName: GlideUtils
 * @Description: TODO 网络图片加载的封装
 * @author hechuang
 * @date 2018/11/13 10:29
 *
 */
public class GlideUtils {

    public static void load(Context context, String url, ImageView view) {
        load(context, url, view, true);
    }

    public static void load(Context context, String url, ImageView view, boolean isCenterCrop) {
        if (isCenterCrop) {
            Glide.with(context)
                    .load(url)
                    .centerCrop()
                    .into(view);
        } else {
            Glide.with(context)
                    .load(url)
                    .into(view);
        }
    }

    public static void load(Context context, String url, ImageView view, @DrawableRes int holder, boolean isCenterCrop) {
        if (isCenterCrop) {
            Glide.with(context)
                    .load(url)
                    .placeholder(holder)
                    .centerCrop()
                    .into(view);
        } else {
            Glide.with(context)
                    .load(url)
                    .placeholder(holder)
                    .into(view);
        }
    }

    public static void load(Fragment fragment, String url, ImageView view, boolean isCenterCrop) {
        if (isCenterCrop) {
            Glide.with(fragment)
                    .load(url)
                    .centerCrop()
                    .into(view);
        } else {
            Glide.with(fragment)
                    .load(url)
                    .into(view);
        }
    }

    public static void load(Context context, @DrawableRes int url, ImageView view) {
        Glide.with(context)
                .load(url)
                .into(view);
    }

    public static void load(Context context, @DrawableRes int url, @DrawableRes int holder, ImageView view) {
        Glide.with(context)
                .load(url)
                .placeholder(holder)
                .into(view);
    }

    public static void load(Context context, String url, @DrawableRes int holder, ImageView view) {
        Glide.with(context)
                .load(url)
                .placeholder(holder)
                .into(view);
    }

    public static void load(Fragment Fragment, String url, @DrawableRes int holder, ImageView view) {
        Glide.with(Fragment)
                .load(url)
                .placeholder(holder)
                .into(view);
    }

    public static void loadCircle(Context context, String url, @DrawableRes int holder, ImageView view) {
        Glide.with(context).
                load(url).
                placeholder(holder)
                .transform(new GlideCircleTransform(context)).
                into(view);
    }


    public static void clearCache(Context context) {
        Glide.get(context).clearDiskCache();
        Glide.get(context).clearMemory();
    }

    private void saveImg(Context context, String imageUrl, final String savePath, final String saveName) {
        Glide.get(context).clearMemory();
        Glide.with(context).asBitmap()
                .load(imageUrl)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                        saveToSystemGallery(bitmap,savePath,saveName);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    public void saveToSystemGallery(Bitmap bmp,String savePath,String saveName) {
        // 首先保存图片
        File fileDir = new File(Environment.getExternalStorageDirectory(), savePath);
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
        File file = new File(fileDir, saveName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        // 其次把文件插入到系统图库
//        try {
//            MediaStore.Images.Media.insertImage(getContentResolver(),
//                    file.getAbsolutePath(), fileName, null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        // 最后通知图库更新
//        //sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(file.getAbsolutePath())));
//        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        Uri uri = Uri.fromFile(file);
//        intent.setData(uri);
//        sendBroadcast(intent);
//        //图片保存成功，图片路径：
//        Toast.makeText(this,
//                "图片保存路径：" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
    }

}
