package ruslep.student_schedule.architecture.view;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import ruslep.student_schedule.R;
import ruslep.student_schedule.architecture.model.Preferens.MyPreferens;
import ruslep.student_schedule.architecture.model.Preferens.MyPreferensImpl;
import ruslep.student_schedule.architecture.other.MyPrefs_;
import ruslep.student_schedule.architecture.presenter.Base.PresenterBase;
import ruslep.student_schedule.architecture.presenter.Base.PresenterBaseImpl;

@EActivity
public class SettingActivityImpl extends AppCompatActivity implements SettingActivity {

    @ViewById
    Spinner spTypeOfWeek;

    @Bean(MyPreferensImpl.class)
    MyPreferens preferens;

    @Bean
    PresenterBaseImpl presenterBase;



    @AfterViews
    public void afterView(){
        switch (preferens.getTypeOfWeek()){
            case "Числитель": spTypeOfWeek.setSelection(0);
                break;
            case "Знаменатель": spTypeOfWeek.setSelection(1);
                break;
            default:
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spTypeOfWeek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    presenterBase.updateTypeOfWeek("Числитель");
                    preferens.setReCreateMainActivity(true);
                } else {
                    presenterBase.updateTypeOfWeek("Знаменатель");
                    preferens.setReCreateMainActivity(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
