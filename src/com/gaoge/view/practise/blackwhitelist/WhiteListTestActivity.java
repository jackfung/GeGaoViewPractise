package com.gaoge.view.practise.blackwhitelist;



import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.gaoge.view.practise.blackwhitelist.operation.WhiteBlackList;
import com.orange.test.textImage.R;

public class WhiteListTestActivity extends Activity {
	
	private static final String[] TEST_URLS = {
		"http://www.qq.com",
		"http://www.baidu.com/",
		"http://sina.cn/nc.php",
		"http://book.douban.com",
		"http://www.36kr.com",
		"http://3g.kaixin001.com/home",
	};
	
	class TestUrlInWhiteListCommand extends CommandAsyncTask {

		private CharSequence mResultText;
		
		public TestUrlInWhiteListCommand(Context context) {
			super(context);
		}

		@Override
		protected void doCommand() {
			
			StringBuilder builder = new StringBuilder();
			
			boolean ret = false;
			for (String url: TEST_URLS) {
				ret = WhiteBlackList.getWhiteList().isInList(WhiteListTestActivity.this, url);
			
				builder.append(String.format(
						"<p><b>[%s]</b> <font color=\"#0000FF\">%s</font></p>", ret, url));
			}
			
			mResultText = Html.fromHtml(builder.toString());
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			if (mResultView == null) {
				return;
			}
			
			mResultView.setText(mResultText);
		}
		
	}
	
	private TextView mResultView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.activity_white_list_test);
		
		setupViews();
		
		new TestUrlInWhiteListCommand(this).execute((Void)null);
	}

	private void setupViews() {
		mResultView = (TextView) findViewById(R.id.test_result);
	}
	
}
