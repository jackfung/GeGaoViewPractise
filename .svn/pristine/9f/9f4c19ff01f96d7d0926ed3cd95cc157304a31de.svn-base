【andriod View机制自己测试笔记】
1:main中指定

 <com.orange.test.textImage.HelloView
         	android:layout_width="50dp"
        	android:layout_height="120dp"
        />
 
【分辨率实验】       
 a:
 在nexus 上运行，调用HelloView getWidth()和getHeight()的 width: 75,height: 180。
 分别是xml中定义的值的1.5倍。
 
DisplayMetrics dm = new DisplayMetrics();  
getWindowManager().getDefaultDisplay().getMetrics(dm);  
Log.d("metric", "widthPixels: " + dm.widthPixels + ",heightPixels: " + dm.heightPixels + dm.density);

结果：widthPixels: 480,heightPixels: 800,dm.density: 1.5
 

 b:
 在LG大屏说给上运行，调用HelloView getWidth()和getHeight()的 width: 75,height: 180。
 分别是xml中定义的值的1.5倍。
 
DisplayMetrics dm = new DisplayMetrics();  
getWindowManager().getDefaultDisplay().getMetrics(dm);  
Log.d("metric", "widthPixels: " + dm.widthPixels + ",heightPixels: " + dm.heightPixels + dm.density);

结果：widthPixels: 480,heightPixels: 800,dm.density: 1.5

【结论】：发现代码中getHeight()和getWidth()得到的都是像素值，而在xml中定义*dp的时候，并不是指定该
view有多少个像素，具体有多少个像素，要通过*dp乘以dm.density的值才能得到。


【手势响应事件实验】
(一)

父View MyViewGroup,子view: HelloView,都覆盖onTouchEvent()方法
1:点击HelloView时，执行顺序 HelloView->onTouchEvent(),MyViewGroup->onTouchEvent()
2:点击MyViewGroup时，只执行MyViewGroup->onTouchEvent()

(二)
父View MyViewGroup,子view: HelloView,都覆盖onTouchEvent()方法,并且父类覆盖onInterceptTouchEvent()方法

**********************************************************************************************
前提：MyViewGroup->onInterceptTouchEvent()返回 false(默认值，也就是super.onInterceptTouchEvent(event))
HelloView->onTouchEvent()返回false,MyViewGroup->onTouchEvent()返回false

1:点击HelloView时，执行顺序MyViewGroup->onInterceptTouchEvent(), HelloView->onTouchEvent(),MyViewGroup->onTouchEvent()
2:点击MyViewGroup时，只执行MyViewGroup->onInterceptTouchEvent(),MyViewGroup->onTouchEvent()

**********************************************************************************************

前提：MyViewGroup->onInterceptTouchEvent()返回false;
HelloView->onTouchEvent()返回true,MyViewGroup->onTouchEvent()返回false


1:点击HelloView时，执行顺序MyViewGroup->onInterceptTouchEvent(),HelloView->onTouchEvent()
MyViewGroup->onInterceptTouchEvent(),HelloView->onTouchEvent()
MyViewGroup->onInterceptTouchEvent(),HelloView->onTouchEvent()
MyViewGroup->onInterceptTouchEvent(),HelloView->onTouchEvent()
如此循环，因为HelloView->onTouchEvent()返回true表示HelloView->onTouchEvent()消费了ACTION_DOWN事件
所以以后的ACTION_MOVE,ACTION_UP事件都会发送给HelloView的onTouchEvent()函数进行处理。因为MyViewGroup->onTouchEvent()
的执行顺序在HelloView的onTouchEvent()后边，所以MyViewGroup->onTouchEvent()函数就不会再被执行(和下面的情况对比着看)


2:点击MyViewGroup时，只执行MyViewGroup->onInterceptTouchEvent(),MyViewGroup->onTouchEvent()

**********************************************************************************************
前提：MyViewGroup->onInterceptTouchEvent()返回false;
HelloView->onTouchEvent()返回false,MyViewGroup->onTouchEvent()返回true

1:点击HelloView时，执行顺序MyViewGroup->onInterceptTouchEvent(),HelloView->onTouchEvent()
MyViewGroup->onTouchEvent()
MyViewGroup->onTouchEvent()
MyViewGroup->onTouchEvent()

如此循环，因为MyViewGroup->onTouchEvent()返回true表示MyViewGroup->onTouchEvent()消费了ACTION_DOWN事件
所以以后的ACTION_MOVE,ACTION_DOWN都会发送给MyViewGroup->onTouchEvent()进行处理；但是因为HelloView->onTouchEvent()
的执行顺序在MyViewGroup->onTouchEvent()之前，所以每次点击的时候HelloView->onTouchEvent()就有机会再执行一次。


2:点击MyViewGroup时，只执行MyViewGroup->onInterceptTouchEvent(),
MyViewGroup->onTouchEvent()
MyViewGroup->onTouchEvent()
MyViewGroup->onTouchEvent()

如此循环，因为MyViewGroup->onTouchEvent()返回true表示MyViewGroup->onTouchEvent()消费了ACTION_DOWN事件
所以以后的ACTION_MOVE,ACTION_DOWN都会发送给MyViewGroup->onTouchEvent()进行处理；因为这里是点击的
MyViewGroup除了 HelloView以外的地方，所以不会调用HelloView->onTouchEvent()

**********************************************************************************************

**********************************************************************************************
前提：MyViewGroup->onInterceptTouchEvent()返回true;
HelloView->onTouchEvent()返回false,MyViewGroup->onTouchEvent()返回false

1:点击HelloView时，执行顺序MyViewGroup->onInterceptTouchEvent(),MyViewGroup->onTouchEvent()
2:点击MyViewGroup时，只执行MyViewGroup->onInterceptTouchEvent(),MyViewGroup->onTouchEvent()

**********************************************************************************************



【android View布局属性】



【WindowManager添加view】
问题描述：
当通过WindowManager添加了一个view后，正常的隐藏状态栏的代码就不生效了！！！


项目中往WindowManager中添加一个view后，在Hierarchy View视图里看到有两个应用：
com.orange.test.textImage/com.gaoge.view.practise.TestStatusBar
com.orange.test.textImage/com.gaoge.view.practise.TestStatusBar

其中一个是TestStatusBar本身ayout的内容，一个是添加到WindowManager中的view.这两个钟都没有statusBar
