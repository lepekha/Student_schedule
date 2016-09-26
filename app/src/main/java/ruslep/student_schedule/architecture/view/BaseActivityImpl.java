package ruslep.student_schedule.architecture.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.ContactsCallback;
import com.digits.sdk.android.ContactsUploadService;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.digits.sdk.android.models.Contacts;
import com.digits.sdk.android.models.ContactsUploadResult;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.fabric.sdk.android.Fabric;
import ruslep.student_schedule.R;
import ruslep.student_schedule.architecture.model.entity.Subject;
import ruslep.student_schedule.architecture.other.Event.ChangeTypeOfWeek;
import ruslep.student_schedule.architecture.other.MyPrefs_;
import ruslep.student_schedule.architecture.presenter.Base.PresenterBaseImpl;
import ruslep.student_schedule.architecture.presenter.PresenterPresenterFragmentScheduleImpl;
import ruslep.student_schedule.architecture.view.Custom_dialog.Add_schedule_dialog;
import ruslep.student_schedule.architecture.view.Custom_dialog.Add_schedule_dialog_;
import ruslep.student_schedule.architecture.view.FragmentSchedule.FragmentScheduleImpl_;

@EActivity
public class BaseActivityImpl extends AppCompatActivity implements BaseActivity,NavigationView.OnNavigationItemSelectedListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private static final String TWITTER_KEY = "5nyNFNF2jq8p4FaKV0t9tr3Cy";
    private static final String TWITTER_SECRET = "vhrRcQZb9w4DvTrjLnxVkgLGFeGplGw6XcCRm6jCIUpiADAGai";

    private ViewPager mViewPager;

    private TabLayout tabLayout;

    private DigitsAuthButton digitsButton;

    @ViewById(R.id.fab)
    FloatingActionButton floatingActionButton;

    @ViewById(R.id.main_content)
    CoordinatorLayout coordinatorLayout;

    @ViewById(R.id.txtTypeOfWeek)
    TextView txtTypeOfWeek;

    @ViewById(R.id.progressBar)
    ProgressBar progressBar;

    @Pref
    MyPrefs_ myPrefs;

    private DrawerLayout drawer;

    private NavigationView navigationView;

    @Bean
    PresenterPresenterFragmentScheduleImpl presenterPresenterFragmentSchedule;

    @Bean
    PresenterBaseImpl presenterBase;

    int currentPage;

    private Subject subject = new Subject();

    private AuthCallback authCallback;

    @AfterInject
    public void afterView(){
        Log.e("dff","1");
        presenterBase.setView(this);

    }


    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Digits.Builder digitsBuilder = new Digits.Builder().withTheme(android.R.style.Theme_Material);
        Fabric.with(this, new TwitterCore(authConfig), digitsBuilder.build());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);
        //digitsButton.setAuthTheme(R.style.CustomDigitsTheme);
        digitsButton.setCallback(new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                if(presenterBase.auth(phoneNumber)){
                    presenterBase.hideAuthBtn();
                }
            }

            @Override
            public void failure(DigitsException exception) {
                showMessage("Авторизация прошла неудачно.");
            }
        });



        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);



        presenterBase.hideAuthBtn();
        presenterBase.initTyteOfWeek();
        myPrefs.day().put(0);
        mViewPager = (ViewPager) findViewById(R.id.container);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        Log.e("dff","5");
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.e("ddd",position+"");
                myPrefs.day().put(mViewPager.getCurrentItem());
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        floatingActionButton.hide();
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        floatingActionButton.show();
                        break;
                }

            }
        });
    }


    @Click(R.id.fab)
    public void clickFab(){
        // Add_schedule_dialog add_schedule_dialog = Add_schedule_dialog.newInstance(mViewPager.getCurrentItem());
        Add_schedule_dialog add_schedule_dialog = Add_schedule_dialog_.builder()
                .build();
        add_schedule_dialog.show(getSupportFragmentManager(), "add_subject");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_base, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.typeOfWeek:
                presenterBase.setTextTypeOfWeek();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public int getFragmentPage() {
        return currentPage;
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return FragmentScheduleImpl_.builder()
                    .currentPage(position)
                    .build();
        }

        @Override
        public int getCount() {
            // Show 7 total pages.
            return 7;
        }



        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Понеділок";
                case 1:
                    return "Вівторок";
                case 2:
                    return "Середа";
                case 3:
                    return "Четверг";
                case 4:
                    return "П\'ятниця";
                case 5:
                    return "Суббота";
                case 6:
                    return "Неділя";
            }
            return null;
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (item.getItemId()){
            case R.id.nav_enter:
                digitsButton.callOnClick();
                break;
            case R.id.nav_save:
                presenterBase.registerUser(true,presenterBase.getMyPhone());
                break;
            case R.id.nav_load:
                presenterBase.registerUser(false,presenterBase.getMyPhone());
                break;
            case R.id.nav_friends:
                startActivity(new Intent(this, ContactsActivityImpl_.class));
                break;
            case R.id.nav_exit:
                finish();
                break;
            case R.id.nav_review:
                presenterBase.review();
                break;
            case R.id.nav_share:
                presenterBase.share();
                break;
            case R.id.nav_setting:
                startActivity(new Intent(this, SettingActivityImpl_.class));
                break;
            default:
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void showMessage(String text){
        Snackbar.make(coordinatorLayout,text, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void hideAuthBtn() {
       navigationView.getMenu().findItem(R.id.nav_enter).setVisible(false);
    }

    @Override
    public void setTextTypeOfWeek(String typeOfWeek) {
        txtTypeOfWeek.setText(typeOfWeek);
    }

    @Override
    public String getTextTypeOfWeek() {
        return txtTypeOfWeek.getText().toString();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }


}


