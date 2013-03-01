【android keyevent事件处理顺序】
TestKeyEvent Activity中继承onKeyDown(),onKeyUp(),该Activity中有两个view,
TestKeyUrlInputView1，TestKeyUrlInputView2。这两个类都实现了onKeyDown()
和onKeyUp()方法。

运行，
1:
如果焦点在TestKeyUrlInputView1里，且TestKeyUrlInputView1
onKeyDown()和onKeyUp()都返回false,则按下返回键的执行顺序是：
TestKeyUrlInputView1->onKeyDown()
TestKeyEvent->onKeyDown()
TestKeyUrlInputView1->onKeyUp()
TestKeyEvent->onKeyUp()


2:
如果焦点在TestKeyUrlInputView2里，且TestKeyUrlInputView2
onKeyDown()和onKeyUp()都返回false,则按下返回键的执行顺序是：
TestKeyUrlInputView2->onKeyDown()
TestKeyEvent->onKeyDown()
TestKeyUrlInputView2->onKeyUp()
TestKeyEvent->onKeyUp()

3:
如果焦点在TestKeyUrlInputView1里，且TestKeyUrlInputView1
onKeyDown()返回true,onKeyUp()都返回false,则按下返回键的执行顺序是：
TestKeyUrlInputView1->onKeyDown()
TestKeyUrlInputView1->onKeyUp()
TestKeyEvent->onKeyUp()

4:
如果焦点在TestKeyUrlInputView1里，且TestKeyUrlInputView1
onKeyDown()返回false,onKeyUp()都返回true,则按下返回键的执行顺序是：
TestKeyUrlInputView1->onKeyDown()
TestKeyEvent->onKeyDown()
TestKeyUrlInputView1->onKeyUp()

5:
在TestKeyEvent里覆盖方法dispatchKeyEvent()，则调用顺序是
TestKeyEvent->dispatchKeyEvent()
TestKeyUrlInputView1->onKeyDown()
TestKeyEvent->onKeyDown()

TestKeyEvent->dispatchKeyEvent()
TestKeyUrlInputView1->onKeyUp()
TestKeyEvent->onKeyUp()

api:override dispatchKeyEvent to intercept all key events before they are dispatched to the window


6:
接着5在TestKeyUrlInputView1覆盖方法dispatchKeyEvent()，则调用顺序是
TestKeyEvent->dispatchKeyEvent()
TestKeyUrlInputView1->dispatchKeyEvent()
TestKeyUrlInputView1->onKeyDown()
TestKeyEvent->onKeyDown()

TestKeyEvent->dispatchKeyEvent()
TestKeyUrlInputView1->dispatchKeyEvent()
TestKeyUrlInputView1->onKeyUp()
TestKeyEvent->onKeyUp()

7:
接着6在TestKeyUrlInputView覆盖方法dispatchKeyEventPreIme，按下返回键，则调用顺序是：
TestKeyUrlInputView1->dispatchKeyEventPreIme()
TestKeyEvent->dispatchKeyEvent()
TestKeyUrlInputView1->dispatchKeyEvent()
TestKeyUrlInputView1->onKeyDown()
TestKeyEvent->onKeyDown()

TestKeyUrlInputView1->dispatchKeyEventPreIme()
TestKeyEvent->dispatchKeyEvent()
TestKeyUrlInputView1->dispatchKeyEvent()
TestKeyUrlInputView1->onKeyUp()
TestKeyEvent->onKeyUp()