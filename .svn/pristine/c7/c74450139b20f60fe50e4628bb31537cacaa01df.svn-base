com.orange.browser.search.SearchEngines是管理类，该类的方法getSearchEngineInfos()根据系统
当前语言，从文件res/values-zh-rCN/donottranslate-search_engines.xml
中获得当前系统支持的搜索引擎，该文件内容是：

<resources xmlns:xliff="urn:oasis:names:tc:xliff:document:1.2">
  <string-array name="search_engines" translatable="false">
    <item>baidu</item>
    <item>google</item>
    <item>yahoo_cn</item>
    <item>bing_zh_CN</item>
    <item>yunyun</item>
  </string-array>
</resources>

对于每一个搜索引擎，如baidu,都会实例化出一个对象SearchEngineInfo，在该类的构造函数里，通过方法
调用
int id_data = res.getIdentifier(name, "array", context.getPackageName());
mSearchEngineData = res.getStringArray(id_data);

来从文件res\values\all_search_engines.xml中获得某一个搜索引擎的具体信息，如对百度搜索引擎来说，
信息如下：
  <string-array name="baidu" translatable="false">
    <item>&#x767e;&#x5ea6;</item>
    <item>baidu.com</item>
    <item>http://www.baidu.com/favicon.ico</item>
    <item>http://m.baidu.com/s?from=1798a&amp;word={searchTerms}</item>
    <item>UTF-8</item>
    <item>http://m.baidu.com/su?from=1798a&amp;wd={searchTerms}&amp;action=opensearch&amp;ie=utf-8</item>
  </string-array>
  
  

接口SearchEngine有两个实现子类，DefaultSearchEngine和OpenSearchSearchEngine。
对于类DefaultSearchEngine来说，从该类的create()方法里可以看到：
 SearchManager searchManager =
                (SearchManager) context.getSystemService(Context.SEARCH_SERVICE);
//        ComponentName name = searchManager.getWebSearchActivity();
        ComponentName name = (ComponentName) InvokeSearchManagerMethod.getWebSearchActivity(searchManager);

        if (name == null) return null;
        SearchableInfo searchable = searchManager.getSearchableInfo(name);
        
是获得系统当前的搜索引擎信息，因此是一个固定的特定的搜索引擎。而对于OpenSearchSearchEngine来说，
该类的构造函数代码如下：

public OpenSearchSearchEngine(Context context, SearchEngineInfo searchEngineInfo) {
        mSearchEngineInfo = searchEngineInfo;
        mHttpClient = AndroidHttpClient.newInstance(USER_AGENT);
        HttpParams params = mHttpClient.getParams();
        params.setLongParameter(HTTP_TIMEOUT, HTTP_TIMEOUT_MS);
    }
    
可见，这是一个开放的搜索引擎，接受任意一个SearchEngineInfo对象作为参数。



