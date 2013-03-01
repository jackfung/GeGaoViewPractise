【android touch事件处理】

------------------------------------------第一步：------------------------------------------


View类，ParentView extends RelativeLayout，覆盖方法：onTouchEvent(),dispatchTouchEvent()方法，
onInterceptTouchEvent()方法，

xml文件：

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"
    android:orientation="vertical" >

    <com.gaoge.view.practise.event.touch.ParentView
        android:id="@+id/parentView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        
        />

</LinearLayout>


代码：
@Override
    public boolean onTouchEvent(MotionEvent event) {
        LogHelper.d(LogHelper.TAG_TOUCH,"############# RarentView onTouchEvent");
        return super.onTouchEvent(event);
    }
    
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogHelper.d(LogHelper.TAG_TOUCH,"############# RarentView dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }
    
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogHelper.d(LogHelper.TAG_TOUCH,"############# RarentView onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }
    
    


运行，点击后，执行顺序：
ParentView->dispatchTouchEvent()
ParentView->onInterceptTouchEvent
ParentView->onTouchEvent()

------------------------------------------第二步：------------------------------------------
给ParentView添加一个子view,添加后,xml如下：

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"
    android:orientation="vertical" >

    <com.gaoge.view.practise.event.touch.ParentView
        android:id="@+id/parentView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@color/white"
        >
   		     
        <com.gaoge.view.practise.event.touch.SonView
	        android:id="@+id/sonView"
	        android:layout_width="match_parent"
	        android:layout_height="250dp"
	        android:background="@color/red"
        >
   		</com.gaoge.view.practise.event.touch.SonView>
   
   </com.gaoge.view.practise.event.touch.ParentView>

</LinearLayout>

在SonView中也覆盖放onTouchEvent(),dispatchTouchEvent()方法，
onInterceptTouchEvent()方法，点击SonView的时候，执行顺序：

ParentView->dispatchTouchEvent()
ParentView->onInterceptTouchEvent
SonView->dispatchTouchEvent()
SonView->onInterceptTouchEvent()
SonView->onTouchEvent()
ParentView->onTouchEvent()