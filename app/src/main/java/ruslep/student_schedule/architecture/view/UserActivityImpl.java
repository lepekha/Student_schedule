package ruslep.student_schedule.architecture.view;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.digits.sdk.android.AuthCallback;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import ruslep.student_schedule.R;
import ruslep.student_schedule.architecture.other.MyPrefs_;
import ruslep.student_schedule.architecture.presenter.Base.PresenterBaseImpl;
import ruslep.student_schedule.architecture.presenter.User.PresenterUserImpl;
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

    @Pref
    MyPrefs_ myPrefs;



    @Bean
    PresenterUserImpl presenterUser;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("name"));
        setSupportActionBar(toolbar);
        presenterUser.initTyteOfWeek();
        presenterUser.getSchedule(getIntent().getStringExtra("phone"));
        mViewPager = (ViewPager) findViewById(R.id.container);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                myPrefs.day().put(mViewPager.getCurrentItem());
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
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_base, menu);
        menu.findItem(R.id.paste_subject).setVisible(false);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.typeOfWeek:
                presenterUser.setTextTypeOfWeek();
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






}


