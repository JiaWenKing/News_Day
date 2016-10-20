package com.example.utils;

import android.content.Context;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
/**
 * 
 * image  初始化   并没卵用
 * */
public class Imageutils {
	 /**
	 * 初始化图片缓存组件
	 * 
	 * @param context
	 */
	protected static void initImageLoader(Context context) {
		// Create default options which will be used for every
		// displayImage(...) call if no options will be passed to this method
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(true)
				.cacheOnDisc(true)
				.build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPoolSize(3)	// default
				.threadPriority(Thread.NORM_PRIORITY - 1)	// default
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				.memoryCacheSizePercentage(13) 	// default
				.defaultDisplayImageOptions(defaultOptions)
				.writeDebugLogs() 	// Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
		/*
		 * url 
		 * 1 去Lur里面找 有就直接返回 没有继续
		 * 2 存储在数据库 url 作为唯一表示 有就直接返回
		 * 这两步都没找到  下载
		 * 在Lur 里保存一份
		 * 同时也会在 本地数据库存一份
		 * Lur
		 * 数据库 Lur
		 *  Lur
		 * */
	
	}
}
