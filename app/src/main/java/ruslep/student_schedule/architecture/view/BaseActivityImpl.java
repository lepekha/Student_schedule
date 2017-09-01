package ruslep.student_schedule.architecture.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ruslep.student_schedule.R;
import ruslep.student_schedule.architecture.model.Preferens.MyPreferens;
import ruslep.student_schedule.architecture.model.Preferens.MyPreferensImpl;
import ruslep.student_schedule.architecture.other.Const;
import ruslep.student_schedule.architecture.other.Theme.UseTheme;
import ruslep.student_schedule.architecture.other.Theme.UseThemeImpl;
import ruslep.student_schedule.architecture.presenter.Base.PresenterBaseImpl;
import ruslep.student_schedule.architecture.presenter.Firebase;
import ruslep.student_schedule.architecture.presenter.FirebaseImpl;
import ruslep.student_schedule.architecture.presenter.PresenterFragmentScheduleImpl;
import ruslep.student_schedule.architecture.view.Custom_dialog.Add_schedule_dialog;
import ruslep.student_schedule.architecture.view.Custom_dialog.Add_schedule_dialog_;
import ruslep.student_schedule.architecture.view.FragmentMySchedule.FragmentScheduleImpl_;

@EActivity
public class BaseActivityImpl extends AppCompatActivity implements BaseActivity,NavigationView.OnNavigationItemSelectedListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private static final String ADD_DIALOG_TAG = "add_dialog_tag";
    private static final int TOTAL_PAGE = 7;
    public static final int MULTIPLE_PERMISSIONS = 1; // code you want.


    private static final String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS};

    private ViewPager mViewPager;

    private TabLayout tabLayout;

    private static final int RC_SIGN_IN = 123;


    @ViewById(R.id.fab)
    FloatingActionButton floatingActionButton;

    @ViewById(R.id.main_content)
    CoordinatorLayout coordinatorLayout;

    @ViewById(R.id.txtTypeOfWeek)
    TextView txtTypeOfWeek;

    @StringRes(R.string.baseActivity_Schedule_needAuth)
    String SCHEDULE_NEED_AUTH;

    @StringRes(R.string.baseActivity_registerSchedule_cancel)
    String SCHEDULE_REGISTER_CANCEL;

    @StringRes(R.string.baseActivity_registerSchedule_inetError)
    String SCHEDULE_REGISTER_INETERROR;

    @StringRes(R.string.baseActivity_registerSchedule_succefful)
    String SCHEDULE_REGISTER_SUCCEFFUL;

    @StringRes(R.string.baseActivity_registerSchedule_error)
    String SCHEDULE_REGISTER_ERROR;

    @ViewById(R.id.progressBar)
    ProgressBar progressBar;

    @Bean(MyPreferensImpl.class)
    MyPreferens preferens;

    private DrawerLayout drawer;

    private NavigationView navigationView;

    @Bean
    PresenterBaseImpl presenterBase;

    private int currentPage;


    @Bean(UseThemeImpl.class)
    UseTheme useTheme;

    @AfterInject
    public void afterView(){
        presenterBase.setView(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        /**Если настройки были изменены, пересоздаем активити*/
        if(preferens.getReCreateMainActivity()){
            preferens.setReCreateMainActivity(false);
            recreate();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTheme(useTheme.getTheme());
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FirebaseApp.initializeApp(this);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        /** устанавливаем номер телефона в хедер бокового меню */
        presenterBase.setDrawerHeaderPhone();

        presenterBase.hideAuthBtn();
        presenterBase.initTyteOfWeek();
        preferens.setDay(0);
        mViewPager = (ViewPager) findViewById(R.id.container);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        currentPage = presenterBase.getDayOfWeek();
        preferens.setDay(currentPage);
        mViewPager.setCurrentItem(presenterBase.getDayOfWeek());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               preferens.setDay(mViewPager.getCurrentItem());
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

        View hView =  navigationView.getHeaderView(0);
        FloatingActionButton btnQuite = (FloatingActionButton) hView.findViewById(R.id.btnQuite);
        RelativeLayout navigationBackground  = (RelativeLayout) hView.findViewById(R.id.navigationBackground);
        navigationBackground.setBackgroundColor(ContextCompat.getColor(this,useTheme.setNavigationBackground()));
        btnQuite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Click(R.id.fab)
    public void clickFab(){
        // Add_schedule_dialog add_schedule_dialog = Add_schedule_dialog.newInstance(mViewPager.getCurrentItem());
        Add_schedule_dialog add_schedule_dialog = Add_schedule_dialog_.builder()
                .build();
        add_schedule_dialog.show(getSupportFragmentManager(), ADD_DIALOG_TAG);

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
                restartArapter();
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
            return TOTAL_PAGE;
        }



        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.baseActivity_dayOfWeek_monday);
                case 1:
                    return getString(R.string.baseActivity_dayOfWeek_tuesday);
                case 2:
                    return getString(R.string.baseActivity_dayOfWeek_wednesday);
                case 3:
                    return getString(R.string.baseActivity_dayOfWeek_thursday);
                case 4:
                    return getString(R.string.baseActivity_dayOfWeek_friday);
                case 5:
                    return getString(R.string.baseActivity_dayOfWeek_saturday);
                case 6:
                    return getString(R.string.baseActivity_dayOfWeek_sunday);
            }
            return null;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);


            // Successfully signed in
            if (resultCode == ResultCodes.OK) {
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                if(presenterBase.auth(response.getPhoneNumber())){
                                    presenterBase.hideAuthBtn();
                                    presenterBase.setDrawerHeaderPhone();
                                    showMessage(SCHEDULE_REGISTER_SUCCEFFUL);
                                }

                            }
                        });
                return;
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    showMessage(SCHEDULE_REGISTER_CANCEL);
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showMessage(SCHEDULE_REGISTER_INETERROR);
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    showMessage(getString(R.string.baseActivity_auth_error));
                    return;
                }
            }

            showMessage(SCHEDULE_REGISTER_ERROR);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (item.getItemId()){
            case R.id.nav_enter:
               // digitsButton.callOnClick();

                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setTheme(useTheme.getTheme())
                                .setAvailableProviders(
                                        Arrays.asList(
                                                new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build()))
                                .setPrivacyPolicyUrl(Const.POLITICS_LINK)
                                .build(),
                        RC_SIGN_IN);
                break;
            case R.id.nav_save:
                presenterBase.registerUser(true,presenterBase.getMyPhone());
                break;
            case R.id.nav_load:
                presenterBase.registerUser(false,presenterBase.getMyPhone());
                break;
            case R.id.nav_friends:
                if(preferens.getAuth())
                {
                    if (checkPermissions()){
                        startActivity(new Intent(this, ContactsActivityImpl_.class));
                    }
                }else{
                    showMessage(SCHEDULE_NEED_AUTH);
                }
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
        showMessage(typeOfWeek);
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

    @Override
    public void setDrawerHeaderPhoneNumber(String phoneNumber) {
        View hView = navigationView.getHeaderView(0);
        TextView txtPhoneNumber = (TextView) hView.findViewById(R.id.txtPhoneNumber);
        txtPhoneNumber.setText(phoneNumber);
    }

    public boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:PERMISSIONS) {
            result = ContextCompat.checkSelfPermission(this,p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MULTIPLE_PERMISSIONS );
            return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(this, ContactsActivityImpl_.class));
                } else {
                    // no permissions granted.
                }
                return;
            }
        }
    }

    public void restartArapter(){
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(currentPage);
    }


}


