package com.gaoge.view.practise.blackwhitelist;

import android.content.Context;

import com.dailystudio.compat.CompatAsyncTask;

public abstract class CommandAsyncTask extends CompatAsyncTask<Void, Void, Void> {

	protected Context mContext;
	
	public CommandAsyncTask(Context context) {
		mContext = context.getApplicationContext();
	}
	
	@Override
	protected Void doInBackground(Void... arg0) {
		doCommand();
		return null;
	}

	abstract protected void doCommand();

}
