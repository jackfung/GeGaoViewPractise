【步骤】
用三方类库来进行android 数据库操作。

1:创建一个类BlackWhiteListProvider，该类继承自三方类中的DatabaseConnectivityProvider

2：清单文件里声明该provider
 <provider
            android:name="com.gaoge.view.practise.provider.BlackWhiteListProvider"
            android:authorities="com.gaoge.test.provider.urllist" />
        
        
【注意】
这里有个问题，就是再添加三方类库dailycorelib.jar的时候，不要通过properties->java build path
的方式来添加，这样的话，在运行程序的时候，会报异常

java.lang.NoClassDefFoundError
com.gaoge.view.practise.provider.BlackWhiteListProvider

而应该是在Navigator视图中，把三方类dailycorelib.jar放到lib文件夹下，这样才不会报上面的异常。

===================================================================================

生成的数据库的名字是：
com.gaoge.view.practise.provider.BlackWhiteListObject.db

该数据库里只有一张表：BlackWhiteListObject

其中BlackWhiteListObject是一个类名，继承DatabaseObject

===================================================================================
类比：对于CygnusBrowser项目，provider声明如下：

<provider
            android:name=".provider.BlackWhiteListProvider"
            android:authorities="com.orange.browser.blackWhiteList" />
            
            
    生成的数据库的名字是：
com.orange.browser.provider.BlackWhiteListObject.db

该数据库里只有一张表：BlackWhiteListObject     
其中BlackWhiteListObject是一个类名，继承DatabaseObject

综上两者所述，规律好像是：
【数据库的名字】：packageName + "provider" + 类名 + ".db"
【表名】：类名   


===================================================================================
【关于继承的问题】
项目当中要用到4个表，那么默认的就需要4个object,4个model。刚开始自己想都用继承，
4个object都继承自UrlObject(因为4个object其字段都一样)。
4个model都继承自UrlDatabase.（各个model只是操作数据的authority和class不一样，所以在
生成的时候通过构造函数传递进来），示例代码是：

mWhiteExclusiondataModel = new UrlDatabaseModel(Constants.WHITE_EXCLUSION_DATABASE_AUTHORITY,WhiteUrlExclusionObject.class);
        mBlackUrldataModel = new UrlDatabaseModel(Constants.BLACK_URL_DATABASE_AUTHORITY,BlackUrlObject.class);
        mBlackExclusiondataModel = new UrlDatabaseModel(Constants.BLACK_EXCLUSION_DATABASE_AUTHORITY,BlackExclusionObject.class);

但是测试的时候发现不能正确生成数据库。

后来，经过一步步的调试,发现问题是：可以4个object都继承自UrlObject，但是4个model不能都继承
自UrlDatabaseModel（猜测可能是class的问题），所以解决方案就是必须创建四个独立的model


【继续验证】
经过进一步的验证，发现不需要那么多provider文件(在src下），也不需要那么多的authority(在Constants类里）,如：
public static final String WHITE_URL_DATABASE_AUTHORITY = "com.gaoge.test.provider.whiteUrl";
//    public static final String WHITE_EXCLUSION_DATABASE_AUTHORITY = "com.gaoge.test.provider.whiteExclusion";
//    public static final String BLACK_URL_DATABASE_AUTHORITY = "com.gaoge.test.provider.blackUrl";
//    public static final String BLACK_EXCLUSION_DATABASE_AUTHORITY = "com.gaoge.test.provider.blackExclusion";

在AndroidManifest.xml里也不需要那么多的<provider>声明，如

    <!-- 
        <provider
            android:name="com.gaoge.view.practise.provider.entity.WhiteUrlExclusionProvider"
            android:authorities="com.gaoge.test.provider.whiteExclusion" />
         
         
        <provider
            android:name="com.gaoge.view.practise.provider.entity.BlackUrlProvider"
            android:authorities="com.gaoge.test.provider.blackUrl" />
        
        <provider
            android:name="com.gaoge.view.practise.provider.entity.BlackUrlExclusionProvider"
            android:authorities="com.gaoge.test.provider.blackExclusion" />
        -->
        
 都只需要一份即可！这也证明了一个authority不一定非得对应一个db文件，甚至一个provider也
 不一定非得对应一个db文件，是一个1对多的关系。在这里  android:authorities="com.gaoge.test.provider.blackUrl
 好像就是被利用一下就可以。
        

