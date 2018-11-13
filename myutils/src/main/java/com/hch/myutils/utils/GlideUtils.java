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

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


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
                    .crossFade()
                    .centerCrop()
                    .into(view);
        } else {
            Glide.with(context)
                    .load(url)
                    .crossFade()
                    .into(view);
        }
    }

    public static void load(Context context, String url, ImageView view, @DrawableRes int holder, boolean isCenterCrop) {
        if (isCenterCrop) {
            Glide.with(context)
                    .load(url)
                    .placeholder(holder)
                    .crossFade()
                    .centerCrop()
                    .into(view);
        } else {
            Glide.with(context)
                    .load(url)
                    .placeholder(holder)
                    .crossFade()
                    .into(view);
        }
    }

    public static void load(Fragment fragment, String url, ImageView view, boolean isCenterCrop) {
        if (isCenterCrop) {
            Glide.with(fragment)
                    .load(url)
                    .crossFade()
                    .centerCrop()
                    .into(view);
        } else {
            Glide.with(fragment)
                    .load(url)
                    .crossFade()
                    .into(view);
        }
    }

    public static void load(Context context, @DrawableRes int url, ImageView view) {
        Glide.with(context)
                .load(url)
                .crossFade()
                .into(view);
    }

    public static void load(Context context, @DrawableRes int url, @DrawableRes int holder, ImageView view) {
        Glide.with(context)
                .load(url)
                .placeholder(holder)
                .crossFade()
                .into(view);
    }

    public static void load(Context context, String url, @DrawableRes int holder, ImageView view) {
        Glide.with(context)
                .load(url)
                .placeholder(holder)
                .crossFade()
                .into(view);
    }

    public static void load(Fragment Fragment, String url, @DrawableRes int holder, ImageView view) {
        Glide.with(Fragment)
                .load(url)
                .placeholder(holder)
                .crossFade()
                .into(view);
    }

    public static void loadCircle(Context context, String url, @DrawableRes int holder, ImageView view) {
        Glide.with(context).
                load(url).
                placeholder(holder)
                .transform(new GlideCircleTransform(context)).
                crossFade().
                into(view);
    }


    public static void clearCache(Context context) {
        Glide.get(context).clearDiskCache();
        Glide.get(context).clearMemory();
    }
}
