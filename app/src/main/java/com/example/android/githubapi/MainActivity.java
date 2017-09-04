package com.example.android.githubapi;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.githubapi.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    // COMPLETED (26) Create an EditText variable called mSearchBoxEditText
    private EditText mSearchBoxEditText;

    // COMPLETED (27) Create a TextView variable called mUrlDisplayTextView
    private TextView mUrlDisplayTextView;
    // COMPLETED (28) Create a TextView variable called mSearchResultsTextView
    private TextView mSearchResultsTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // COMPLETED (29) Use findViewById to get a reference to mSearchBoxEditText
        mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);

        // COMPLETED (30) Use findViewById to get a reference to mUrlDisplayTextView
        mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);
        // COMPLETED (31) Use findViewById to get a reference to mSearchResultsTextView
        mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_results_json);
    }

    private void makeGithubSearchQuery() {
        String githubQuery = mSearchBoxEditText.getText().toString();
        URL githubSearchUrl = NetworkUtils.buildUrl(githubQuery);
        mUrlDisplayTextView.setText(githubSearchUrl.toString());
        String githubSearchResults = null;
//        try {
//            githubSearchResults = NetworkUtils.getResponseFromHttpUrl(githubSearchUrl);
//            mSearchResultsTextView.setText(githubSearchResults);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        new GithubQueryTask().execute(githubSearchUrl);
    }

    public class GithubQueryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... urls){
            URL searchUrl = urls[0];
            String githubSearchResults = null;
            try {
                githubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e){
                e.printStackTrace();
            } return githubSearchResults;

        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null && !s.equals("")) {
                mSearchResultsTextView.setText(s);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // COMPLETED (9) Within onCreateOptionsMenu, use getMenuInflater().inflate to inflate the menu
        getMenuInflater().inflate(R.menu.main, menu);
        // COMPLETED (10) Return true to display your menu
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.android_search) {
            makeGithubSearchQuery();
//            Context context = MainActivity.this;
//            String textToShow = "Search clicked";
//            Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show();
//            return true;
        }
        return super.onOptionsItemSelected(item);


    }
}