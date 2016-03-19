package com.tennmatrix.sagurakirux.iridium;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


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
        view.setWebViewClient(new MainWebViewClient());
        view.setWebChromeClient(new ChromeClient());


        String url = "http://forum.iridiumbased.com/";
        view.getSettings().setJavaScriptEnabled(true);
        // Some web pages need these settings to render properly in WebView
        view.getSettings().setLoadWithOverviewMode(true);
        view.getSettings().setUseWideViewPort(true);
        view.getSettings().setBuiltInZoomControls(true);
        view.getSettings().setDisplayZoomControls(false);
        view.loadUrl(url);

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

    private class MainWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return false;
        }
    }

    private class ChromeClient extends WebChromeClient {
        public void onProgressChanged(WebView view, int progress) {
            //Make the bar disappear after URL is loaded, and changes string to Loading...
            setTitle("Loading...");
            setProgress(progress * 100); //Make the bar disappear after URL is loaded
            // Return the app name after finish loading
            if (progress == 100) {
                setTitle(R.string.app_name);
            }
        }
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

        if (id == R.id.search) {
            view.loadUrl("http://forum.iridiumbased.com/search/?type=post");
            return true;
        } else if (id == R.id.account) {
            view.loadUrl("http://forum.iridiumbased.com/account/");
            return true;
        } else if (id == R.id.forums) {
            view.loadUrl("http://forum.iridiumbased.com/");
            return true;
        } else if (id == R.id.new_posts) {
            view.loadUrl("http://forum.iridiumbased.com/find-new/posts/");
            return true;
        } else if (id == R.id.shoutbox) {
            view.loadUrl("http://forum.iridiumbased.com/taigachat/");
            return true;
        } else if (id == R.id.pop_content) {
            view.loadUrl("http://forum.iridiumbased.com/find-popular/content/");
            return true;
        } else if (id == R.id.inbox) {
            view.loadUrl("http://forum.iridiumbased.com/conversations/");
            return true;
        } else if (id == R.id.alerts) {
            view.loadUrl("http://forum.iridiumbased.com/account/alerts/");
            return true;
        } else if (id == R.id.feed) {
            view.loadUrl("http://forum.iridiumbased.com/account/news-feed/");
            return true;

        // Forums Navigation
        } else if (id == R.id.announcements) {
            view.loadUrl("http://forum.iridiumbased.com/forums/announcements/");
            return true;
        } else if (id == R.id.progress) {
            view.loadUrl("http://forum.iridiumbased.com/forums/progress_updates/");
            return true;
        } else if (id == R.id.dev_blog) {
            view.loadUrl("http://forum.iridiumbased.com/forums/ramblings/");
            return true;
        } else if (id == R.id.message_board) {
            view.loadUrl("http://forum.iridiumbased.com/forums/world_bbs/");
            return true;
        } else if (id == R.id.bug_reports) {
            view.loadUrl("http://forum.iridiumbased.com/forums/bug-reports.26/");
            return true;
        } else if (id == R.id.team_info) {
            view.loadUrl("http://forum.iridiumbased.com/forums/dev_team/");
            return true;
        } else if (id == R.id.dev_info) {
            view.loadUrl("http://forum.iridiumbased.com/forums/dev_info/");
            return true;
        } else if (id == R.id.aqua) {
            view.loadUrl("http://forum.iridiumbased.com/forums/aqua_polis/");
            return true;
        } else if (id == R.id.events) {
            view.loadUrl("http://forum.iridiumbased.com/forums/Active_Spiraling_Abyss/");
            return true;
        } else if (id == R.id.art_gallery) {
            view.loadUrl("http://forum.iridiumbased.com/forums/art_gallery/");
            return true;
        } else if (id == R.id.multimedia) {
            view.loadUrl("http://forum.iridiumbased.com/forums/gaming_center/");
            return true;
        } else if (id == R.id.lost_grounds) {
            view.loadUrl("http://forum.iridiumbased.com/forums/Endless_Expansive_Cooperation/");
            return true;
        } else if (id == R.id.ooc) {
            view.loadUrl("http://forum.iridiumbased.com/forums/Reality_Expansive_Illusion/");
            return true;

        // General feedback
        } else if (id == R.id.feedback){
            String uriText =
                    "mailto:rpelger1004@gmail.com" +
                            "?subject=" + Uri.encode("Regarding Iridium App...");
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

        // Bug report item
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
                        "?subject=" + Uri.encode("Iridium App Bug Report...");

        Uri uri = Uri.parse(uriText);
        Intent i = new Intent(Intent.ACTION_SENDTO);
        i.setData(uri);
        try {
            startActivity(Intent.createChooser(i, "Send e-mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There are no e-mail clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
