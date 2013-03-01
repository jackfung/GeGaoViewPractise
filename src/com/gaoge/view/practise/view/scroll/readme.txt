调用mScroller.startScroll((curScreen-1) *getWidth(), 0, getWidth(), 0,3000);后，
好像是系统自动不间断执行computeScroll()，在该方法里执行实质的改变view视图坐标的代码
View.scrollTo()，根据log来看：

D/scroll  (25194): llllllllllllllll computeScroll() mScroller.getCurrX(): 397,mScroller.getCurrY(): 0
D/scroll  (25194): llllllllllllllll computeScroll() mScroller.getCurrX(): 401,mScroller.getCurrY(): 0
D/scroll  (25194): llllllllllllllll computeScroll() mScroller.getCurrX(): 405,mScroller.getCurrY(): 0
D/scroll  (25194): llllllllllllllll computeScroll() mScroller.getCurrX(): 408,mScroller.getCurrY(): 0
D/scroll  (25194): llllllllllllllll computeScroll() mScroller.getCurrX(): 411,mScroller.getCurrY(): 0
D/scroll  (25194): llllllllllllllll computeScroll() mScroller.getCurrX(): 414,mScroller.getCurrY(): 0
D/scroll  (25194): llllllllllllllll computeScroll() mScroller.getCurrX(): 417,mScroller.getCurrY(): 0
D/scroll  (25194): llllllllllllllll computeScroll() mScroller.getCurrX(): 419,mScroller.getCurrY(): 0


【总结】

这个Scroll看起来就像起了一个存储的作用，存储当前currX,currY的值。
在computeScroll()方法里，通过获取currX,currY的值来改变当前view的视图坐标。

【疑问】
为什么调用mScroller.startScroll后，就会有线程不停的调用computeScroll()方法呢？


