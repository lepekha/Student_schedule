package ruslep.student_schedule.architecture.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.widget.TextView;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import ruslep.student_schedule.R;
import ruslep.student_schedule.architecture.model.Preferens.MyPreferens;
import ruslep.student_schedule.architecture.model.Preferens.MyPreferensImpl;
import ruslep.student_schedule.architecture.other.Event.GetUserFromServer;
import ruslep.student_schedule.architecture.other.MyPrefs_;
import ruslep.student_schedule.architecture.other.Theme.UseTheme;
import ruslep.student_schedule.architecture.other.Theme.UseThemeImpl;
import ruslep.student_schedule.architecture.presenter.Base.PresenterBaseImpl;
import ruslep.student_schedule.architecture.presenter.User.PresenterUserImpl;
import ruslep.student_schedule.architecture.view.CustomAdapters.CustomUserFragmentAdapter;
import ruslep.student_schedule.architecture.view.FragmentUserSchedule.FragmentScheduleImpl_;

@EActivity
public class UserActivityImpl extends AppCompatActivity implements UserActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private static final int TOTAL_PAGE = 7;



    private ViewPager mViewPager;

    private TabLayout tabLayout;



    @ViewById(R.id.main_content)
    CoordinatorLayout coordinatorLayout;

    @ViewById(R.id.txtTypeOfWeek)
    TextView txtTypeOfWeek;

    @ViewById(R.id.progressBar)
    me.zhanghai.android.materialprogressbar.MaterialProgressBar progressBar;

    @Bean(MyPreferensImpl.class)
    MyPreferens preferens;

    @Bean(UseThemeImpl.class)
    UseTheme useTheme;

    @Bean
    PresenterUserImpl presenterUser;

    @StringRes(R.string.user_dialog_download)
    String USER_DIALOG_DOWNLOAD;

    @StringRes(R.string.user_dialog_OK)
    String USER_DIALOG_OK;

    @StringRes(R.string.user_dialog_NO)
    String USER_DIALOG_NO;

    @StringRes(R.string.user_dialog_sucefull)
    String USER_DIALOG_SUCEFULL;

    private int currentPage;



    @AfterInject
    public void afterView(){
        presenterUser.setView(this);

    }


    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTheme(useTheme.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("name"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presenterUser.initTyteOfWeek();
        presenterUser.getSchedule(getIntent().getStringExtra("phone"));
        mViewPager = (ViewPager) findViewById(R.id.container);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        currentPage = presenterUser.getDayOfWeek();
        preferens.setUserDay(mViewPager.getCurrentItem());
        mViewPager.setCurrentItem(presenterUser.getDayOfWeek());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                preferens.setUserDay(mViewPager.getCurrentItem());
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        break;
                }

            }
        });
        restartAdapter();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.typeOfWeek:
                presenterUser.setTextTypeOfWeek();
                restartAdapter();
                break;
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.download_subject:
                /** диалог подтверждения замены своего расписания*/
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(this);
                builder.setMessage(USER_DIALOG_DOWNLOAD);
                builder.setPositiveButton(USER_DIALOG_OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenterUser.saveToMe();
                        preferens.setReCreateMainActivity(true);
                        showMessage(USER_DIALOG_SUCEFULL);
                    }
                });
                builder.setNegativeButton(USER_DIALOG_NO, null);
                builder.show();
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



    @Override
    public void showMessage(String text){
        Snackbar.make(coordinatorLayout,text, Snackbar.LENGTH_SHORT).show();
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
    public void onBackPressed() {
        super.onBackPressed();
        presenterUser.deletAllFromDB();
        finish();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(android.view.View.GONE);
    }



    public void restartAdapter(){
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(currentPage);
    }
}


