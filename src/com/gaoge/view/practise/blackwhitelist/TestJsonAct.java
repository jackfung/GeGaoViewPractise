
package com.gaoge.view.practise.blackwhitelist;

import android.app.Activity;
import android.os.Bundle;

import com.gaoge.view.practise.utils.LogHelper;
import com.orange.test.textImage.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;

public class TestJsonAct extends Activity {
    private String TAG = "json"; 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildObjectFromFile();
    }

    void buildObjectFromFile()
    {
        try
        {
            String x = "";
//            InputStream is = this.getResources().openRawResource(R.raw.interview);
            InputStream is = getAssets().open("interview.txt");
            byte[] buffer = new byte[is.available()];
            while (is.read(buffer) != -1)
                ;
            String json = new String(buffer);
            JSONObject obj = new JSONObject(json);
            x = obj.getString("firstname") + " " + obj.getString("lastname") + "n";
            x += obj.getString("occupation") + "n";

            JSONObject interview = obj.getJSONObject("interview");
            x += "Interview source:" + interview.getString("source") + "n";

            JSONArray questions = interview.getJSONArray("questions");
            x += "Number of questions: " + questions.length() + "nn";

            int i;
            for (i = 0; i < questions.length(); i++)
            {
                JSONObject qa = questions.getJSONObject(i);
                x += "------------n";
                x += "Q" + (i + 1) + ". " + qa.getString("Question") + "nn";
                x += "A" + (i + 1) + ". " + qa.getString("Answer") + "n";
            }
//            setStatus(x);
            LogHelper.d(TAG, "JJJJJJJJJJJJJJJJJJJJ x: " + x);
        } catch (Exception je)
        {
            je.printStackTrace();
        }
    }

}
