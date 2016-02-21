package com.tennmatrix.sagurakirux.iridium;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private WebView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        view = (WebView) this.findViewById(R.id.Web_View);
        view.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                //Make the bar disappear after URL is loaded, and changes string to Loading...
                setTitle("Loading...");
                setProgress(progress * 100); //Make the bar disappear after URL is loaded
                // Return the app name after finish loading
                if (progress == 100) {
                    setTitle(R.string.app_name);
                }
            }
        });
        String url = "http://forum.iridiumbased.com/";
        view.getSettings().setJavaScriptEnabled(true);
        // Some web pages need these settings to render properly in WebView
        view.getSettings().setLoadWithOverviewMode(true);
        view.getSettings().setUseWideViewPort(true);
        view.getSettings().setBuiltInZoomControls(true);
        view.getSettings().setDisplayZoomControls(false);
        view.loadUrl(url);

        // Create a WebView client to handle URLs locally
        view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        drawer.bringToFront();
        drawer.requestLayout();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (view.canGoBack()) {
            view.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.refresh) {
            view.reload();
            return true;
        } else if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "Nothing to set...", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.report){
            BugReport();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action

         // General feedback
        } else if (id == R.id.feedback){
            String uriText =
                    "mailto:rpelger1004@gmail.com" +
                            "?subject=" + Uri.encode("Regarding PFRef...");
            Uri uri = Uri.parse(uriText);
            Intent i = new Intent(Intent.ACTION_SENDTO);
            i.setData(uri);
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(MainActivity.this, "There are no email clients installed.",
                        Toast.LENGTH_SHORT).show();
            }
            return true;

            // Feedback item
        } else if (id == R.id.bugs) {
            BugReport();
            return true;

            // About app
        } else if (id == R.id.about) {
            Intent aboutIntent = new Intent(this, AboutActivity.class);
            startActivity(aboutIntent);
            return true;
        }
        return onNavigationItemSelected(item);
    }

    public void BugReport() {
        String uriText =
                "mailto:rpelger1004@gmail.com" +
                        "?subject=" + Uri.encode("PFRef Bug Report...");
        File outputFile = new File(Environment.getExternalStorageDirectory(),
                "logcat.txt");
        try {
            Runtime.getRuntime().exec(
                    "logcat -f " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Uri uri = Uri.parse(uriText);
        Intent i = new Intent(Intent.ACTION_SENDTO);
        i.setData(uri);
        i.putExtra(Intent.EXTRA_STREAM, outputFile.getAbsolutePath());
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
