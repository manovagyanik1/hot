package com.hotactress.hot.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hotactress.hot.activities.helpers.PresenceActivity;
import com.hotactress.hot.adapters.SectionsPagerAdapter;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.hotactress.hot.R;
import com.hotactress.hot.utils.AnalyticsManager;
import com.hotactress.hot.utils.Gen;

/**
 * Created by shubhamagrawal on 16/07/18.
 */

public class ChatMainActivity extends PresenceActivity {

    private static final String TAG = "ChatMainActivity";

    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private DatabaseReference mUserRef;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    Activity activity;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;

        setContentView(R.layout.activity_chat_main);
        mAuth = FirebaseAuth.getInstance();

        AnalyticsManager.log(AnalyticsManager.Event.CHAT_MAIN_ACTIVITY_LAUNCHED, "", "");

        mToolbar = findViewById(R.id.chat_main_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Hot Chat");

        if (mAuth.getCurrentUser() != null) {
            mUserRef = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid());
        }

        //Tabs
        mViewPager = (ViewPager) findViewById(R.id.main_tabPager);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabLayout = (TabLayout) findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        findViewById(R.id.more_apps).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gen.startActivity(activity, false, MoreAppsActivity.class);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null) {
            Gen.startActivity(this, true, RegisterActivity.class);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    public void logout() {
        Gen.saveLogOutInLocalStorage();
//        FirebaseAuth.getInstance().signOut();
        Gen.startActivity(this, true, RegisterActivity.class);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.chat_logout){
            logout();
        } else if(item.getItemId() == R.id.all_users) {
            Gen.startActivity(this, false, UsersActivity.class);
        } else if(item.getItemId() == R.id.account_settings) {
            Gen.startActivity(this, false, SettingsActivity.class);
        } else if(item.getItemId() == R.id.view_photos) {
            Gen.startActivity(this, false, GridActivity.class);
        } else if(item.getItemId() == R.id.play_game) {
            Gen.startActivity(this, false, PuzzleSolvingActivity.class);
        } else if(item.getItemId() == R.id.share_app) {
            Gen.startActivity(this, false, SettingsActivity.class);
        } else if(item.getItemId() == R.id.view_videos) {
            Gen.startActivity(this, false, VideoMainActivity.class);
        }

        return true;
    }
}
