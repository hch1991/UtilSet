# MyUtils

使用

    project build.gradle下添加：

maven { url 'https://jitpack.io' }

如下：

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

    app build.gradle下添加依赖 implementation 'com.github.hch1991:MyUtils:v1.0.1'
  
  
# 2018.9.17

**工具类集合**
* MLog工具类 
* 日期获取工具类  
* 网络检查工具类
* 权限检查工具类
* MD5工具类
* 日志保存工具类
* SheetDialog 底部弹出框
```
LogcatHelper.getInstance(this).start(filePath); 开启保存日志
LogcatHelper.getInstance(this).stop(); 停止保存日志
```


# 2018.9.18
* 添加检查工具类 
* 图片工具类
* 屏幕工具类 
* 存储工具类 
* 正则校验工具类
* String工具类
* Toast工具类
