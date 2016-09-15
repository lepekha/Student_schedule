package ruslep.student_schedule.architecture.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    ViewPager mViewPager;

    TabLayout tabLayout;

    @ViewById(R.id.fab)
    FloatingActionButton floatingActionButton;

    @ViewById(R.id.main_content)
    CoordinatorLayout coordinatorLayout;


    @Pref
    MyPrefs_ myPrefs;

    private DrawerLayout drawer;

    @Bean
    PresenterPresenterFragmentScheduleImpl presenterPresenterFragmentSchedule;

    @Bean
    PresenterBaseImpl presenterBase;

    int currentPage;

    private Subject subject = new Subject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        myPrefs.day().put(0);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeTypeOfWeek(ChangeTypeOfWeek changeTypeOfWeek) {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(myPrefs.day().get());
        Log.e("lll","sdfsdf");
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (item.getItemId()){
            case R.id.nav_enter:
                break;
            case R.id.nav_exit:
                finish();
                break;
            default:
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
