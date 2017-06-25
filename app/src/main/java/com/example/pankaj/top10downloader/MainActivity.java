package com.example.pankaj.top10downloader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String testUrl =
            "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/" +
                    "topfreeapplications/limit=10/xml";

    private String mFileContents;
    private ListView listApps;
    private Button buttonParseXML;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        buttonParseXML = (Button) findViewById(R.id.buttonParseXML);
        listApps = (ListView) findViewById(R.id.xmlListView);

        buttonParseXML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseApplications parseApplications = new ParseApplications(mFileContents);
                parseApplications.process();
                ArrayAdapter<Application> arrayAdapter = new ArrayAdapter<Application>(
                        MainActivity.this, R.layout.list_item, parseApplications.process());
                listApps.setAdapter(arrayAdapter);
            }
        });

        (new MyDownloadData()).execute(testUrl, null, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class MyDownloadData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            System.out.println("I am in AsyncTask " + new Date());

            mFileContents = downloadXMLFile(urls[0]);
            return mFileContents;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
//
//            ParseApplications parseApplications = new ParseApplications(result);
//            List<Application> events = parseApplications.process();
//
//            ArrayAdapter<Application> arrayAdapter =
//                    new ArrayAdapter<Application>(MainActivity.this, R.layout.list_item, events);
//
//            ListView listView = (ListView) findViewById(R.id.xmlListView);
//
//            listView.setAdapter(arrayAdapter);

        }

        private String downloadXMLFile(String urlPath) {
            String result = null;
            try {
                URL url = new URL(urlPath);
                HttpURLConnection httpURLConnection =
                        (HttpURLConnection) url.openConnection();

                InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
                result = readStream(in);
                System.out.println("result  .... " + result);

               // int response = httpURLConnection.getResponseCode();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        private String readStream(InputStream in) {
            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();

            String line;
            try {

                br = new BufferedReader(new InputStreamReader(in));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return sb.toString();
        }
    }
}
