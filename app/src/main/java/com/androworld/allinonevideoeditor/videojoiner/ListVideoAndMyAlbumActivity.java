package com.androworld.allinonevideoeditor.videojoiner;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.androworld.allinonevideoeditor.Helper;
import com.androworld.allinonevideoeditor.R;
import com.androworld.allinonevideoeditor.StartActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ListVideoAndMyAlbumActivity extends AppCompatActivity {
    static final boolean a = true;
    private TabLayout b;
    private ViewPager c;
    private int[] d = {R.mipmap.icon_video, R.mipmap.icon_myalbum};

    class a extends FragmentPagerAdapter {
        private final List<Fragment> b = new ArrayList();
        private final List<String> c = new ArrayList();

        public a(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public Fragment getItem(int i) {
            return (Fragment) this.b.get(i);
        }

        public int getCount() {
            return this.b.size();
        }

        public void a(Fragment fragment, String str) {
            this.b.add(fragment);
            this.c.add(str);
        }

        public CharSequence getPageTitle(int i) {
            return (CharSequence) this.c.get(i);
        }
    }

    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView( R.layout.liststatusactivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Video Joiner");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (a || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(a);
            supportActionBar.setDisplayShowTitleEnabled(false);
            this.c = (ViewPager) findViewById(R.id.viewpager);
            a(this.c);
            this.b = (TabLayout) findViewById(R.id.tabs);


            this.b.setupWithViewPager(this.c);
            a();
            return;
        }
        throw new AssertionError();
    }

    private void a() {
        this.b.getTabAt(0).setIcon(this.d[0]);
        this.b.getTabAt(1).setIcon(this.d[1]);
    }

    private void a(ViewPager viewPager) {
        a aVar = new a(getSupportFragmentManager());
        aVar.a(new SelectVideoFragment(), "VIDEO");
        aVar.a(new MyVideoFragment(), "MY ALBUM");
        viewPager.setAdapter(aVar);
    }

    @Override public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), StartActivity.class);
        intent.setFlags(67108864);
        startActivity(intent);
        finish();
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return a;
    }

   @Override public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 16908332) {
            onBackPressed();
            return a;
        }
        if (itemId == R.id.shareapp) {
            StringBuilder sb = new StringBuilder();
            sb.append(Helper.share_string);
            sb.append(Helper.package_name);
            String sb2 = sb.toString();
            Intent intent = new Intent();
            intent.setAction("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.TEXT", sb2);
            startActivity(intent);
        } else if (itemId == R.id.moreapp) {
            try {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(Helper.account_string)));
            } catch (ActivityNotFoundException unused) {
                Toast.makeText(getApplicationContext(), " unable to find market app", Toast.LENGTH_LONG).show();
            }
        } else if (itemId == R.id.rateus) {
            try {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(Helper.package_name)));
            } catch (ActivityNotFoundException unused2) {
                Toast.makeText(getApplicationContext(), " unable to find market app", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() == null || !connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting()) {
            return false;
        }
        return a;
    }
}
