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

    app build.gradle下添加依赖 implementation 'com.github.hch1991:MyUtils:v1.0.7'
  
  
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
* 自定义圆形imageView (CircleImageView)
* 自定义loadingDialog
* 自定义带旋转动画的loadingDialog

# 2018.10.27
* 修改Toast工具类 添加可以在服务中进行提示的toast
* 添加设备信息工具类
* 添加时间管理工具类

# 2018.11.13
* 修改屏幕工具类 添加dpi px sp 等转换方法
* 添加button防多点工具类
* 添加文件读写工具类
* 添加glide网络图片加载工具类
* 添加wifi操作工具类

# 2018.1.3
* 丰富了手机工具类的方法集
* 添加添加蓝牙工具类方法集、蓝牙扫描回调接口
* wifi工具类添加连接指令wifi方法
