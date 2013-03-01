package com.gaoge.view.practise.activitytest.loaders;

import android.database.Cursor;
//import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.support.v4.content.Loader;

import com.orange.test.textImage.R;


public class TestContactLoaderFragment extends ListFragment implements LoaderCallbacks<Cursor>{
    
 // These are the Contacts rows that we will retrieve.
    static final String[] CONTACTS_SUMMARY_PROJECTION = new String[] {
        Contacts._ID,
        Contacts.DISPLAY_NAME,
        Contacts.CONTACT_STATUS,
        Contacts.CONTACT_PRESENCE,
        Contacts.PHOTO_ID,
        Contacts.LOOKUP_KEY,
    };
    ListView mListView;
    SimpleCursorAdapter mAdapter;
    String mCurFilter = null;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, null);
        return view;
    }
    
    @Override
    public  void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        mAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_2, null,
                new String[] { Contacts.DISPLAY_NAME, Contacts.CONTACT_STATUS },
                new int[] { android.R.id.text1, android.R.id.text2 }, 0);
        setListAdapter(mAdapter);
        
        
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        // This is called when a new Loader needs to be created.  This
      // sample only has one Loader, so we don't care about the ID.
      // First, pick the base URI to use depending on whether we are
      // currently filtering.
      Uri baseUri;
      if (mCurFilter != null) {
          baseUri = Uri.withAppendedPath(Contacts.CONTENT_FILTER_URI,
                  Uri.encode(mCurFilter));
      } else {
          baseUri = Contacts.CONTENT_URI;
      }

      // Now create and return a CursorLoader that will take care of
      // creating a Cursor for the data being displayed.
      String select = "((" + Contacts.DISPLAY_NAME + " NOTNULL) AND ("
              + Contacts.HAS_PHONE_NUMBER + "=1) AND ("
              + Contacts.DISPLAY_NAME + " != '' ))";
      return new CursorLoader(getActivity(), baseUri,
              CONTACTS_SUMMARY_PROJECTION, select, null,
              Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
    }

    
    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor data) {
        int count = data.getCount();
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        mAdapter.swapCursor(null);
    }

}
