package com.example.android.githubapi;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
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

    private TextView errorMessageTextView;

    private ProgressBar mLoadingIndicator;

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

        errorMessageTextView = (TextView) findViewById(R.id.tv_error_message_display);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
    }

    private void makeGithubSearchQuery() {
        String githubQuery = mSearchBoxEditText.getText().toString();
        URL githubSearchUrl = NetworkUtils.buildUrl(githubQuery);
        mUrlDisplayTextView.setText(githubSearchUrl.toString());
        String githubSearchResults = null;
        new GithubQueryTask().execute(githubSearchUrl);
    }

    // TODO (14) Create a method called showJsonDataView to show the data and hide the error

    public void ShowJsonDataView(){
        // First, make sure the error is invisible
        errorMessageTextView.setVisibility(View.INVISIBLE);

        // Then, make sure the JSON data is visible
        mSearchResultsTextView.setVisibility(View.VISIBLE);

    }

    private void ShowErrorMessage(){
        errorMessageTextView.setVisibility(View.VISIBLE);
        mSearchResultsTextView.setVisibility(View.INVISIBLE);
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
            }
            return githubSearchResults;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String githubSearchResults) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (githubSearchResults != null && !githubSearchResults.equals("")) {
                ShowJsonDataView();
                mSearchResultsTextView.setText(githubSearchResults);
            } else {
                ShowErrorMessage();

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