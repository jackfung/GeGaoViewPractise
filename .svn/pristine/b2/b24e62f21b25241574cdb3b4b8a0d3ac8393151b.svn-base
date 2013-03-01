【创建ContentProvider步骤】

1：
该类的onCreate()函数里声明一个SQLiteOpenHelper类对象，该对象会在ContentProvider类的
insert(),update(),delete()方法里边使用。

2：
在AndroidManifest.xml里添加<provider>标签

1:实际数据库是在什么时候创建的呢？
根据实验，在创建好ContentProvider的子类，并且在AndroidManifest.xml里添加<provider>标签后，
观察相关数据库没有创建；
在当调用了 insert()方法后，相关数据库才创建出来！
 
【ContentProvider Uri注册ContentObserver】
13716119987 




注册代码：
mContentResolver.registerContentObserver(GaoGeProviderConstants.TABLE1_CONTENT_URI, true, new MyProviderContentObserver());

这个在GaoGeProviderConstants.TABLE1_CONTENT_URI发生变化的时候，不会自动执行MyProviderContentObserver的onChange()方法，还需要
在ContentProvier的insert(),update(),delete()方法中显示调用notifyChange()方法，具体代码如下：

this.getContext().getContentResolver().notifyChange(uri, null);第二个参数可以为null

【dbname和authority的关系】
content://com.gaoge.test.provider/gaoge_table1

因为com.gaoge.test.provider对应的CotentProvider 是MyTestContentProvider，而在该类的onCreate()
函数里调用了DatabaseHelper,该类的构造函数里创建了一个名字为gaoge_test_provider.db的数据库，
所以ContentReslover就可以通过Uri content://com.gaoge.test.provider/gaoge_table1来找到对应
的gaoge_test_provider.db的表gaoge_table1

【ContentProvider中文乱码问题】
String zhName = "大侯";

values.put(GaoGeProviderConstants.Impl.COLUMN_DATA_1, zhName);
mContentResolver.insert(GaoGeProviderConstants.TABLE1_CONTENT_URI, values);

插入后:
一：
sqlite命令行查看数据库（select * from gaoge_table1）：
23|澶т警|

可见为乱码！

二:
通过ContentProver.query()方法查询：
 private void query() {

        Cursor c = mContentResolver.query(GaoGeProviderConstants.TABLE1_CONTENT_URI,
                GaoGeProviderConstants.PROJECTIONS, null, null, null);

        LogHelper.d(LogHelper.TAG_PROVIDER, "################## Query Result");
        while (c.moveToNext()) {
            String id = c.getString(c.getColumnIndex(GaoGeProviderConstants.Impl._ID));
            String data1 = c.getString(c.getColumnIndex(GaoGeProviderConstants.Impl.COLUMN_DATA_1));
            LogHelper.d(LogHelper.TAG_PROVIDER, "@@@@@@@@@@@@@@ id: " + id + ",data1: " + data1);
        }
    }
    
    得到结果：
    D/provider( 3462): @@@@@@@@@@@@@@ id: 23,data1: 澶т警
 
 可见也为乱码！
 
 但是虽然为乱码，删除的时候（mContentResolver.delete(GaoGeProviderConstants.TABLE1_CONTENT_URI, GaoGeProviderConstants.Impl.COLUMN_DATA_1 +  "='" + zhName + "'", null);）
 确可以正常删除！结果：
 
 D/provider( 3462): ################## Query Result
D/provider( 3462): @@@@@@@@@@@@@@ id: 22,data1: gaoge
D/provider( 3462): @@@@@@@@@@@@@@ id: 23,data1: 澶т警
D/provider( 3462): ################## Delete Succesfully!
D/provider( 3462): ################## Query Result


那么如何在查询的时候，中文部乱码，可以正常显示呢？(eclipse的Logcat可以正常显示中文)
又经测试，发现将查询出来的字符串显示在TextView等控件中也是可以正常显示的。看来android系统本身对各种字符编码的支持已经很好了！！！！！！

但是假如画蛇添足，如在往数据库增加数据的时候，调用TestProviderActivity的insertChinese()方法，对插入的String
进行一次转码操作，如values.put(GaoGeProviderConstants.Impl.COLUMN_DATA_1, new String(zhName_china.getBytes(),encode));
则反而会造成查询的时候，TextView控件和Logcat产生乱码!
        