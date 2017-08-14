package ruslep.student_schedule.architecture.view;

import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.view.View;
import android.view.animation.GridLayoutAnimationController;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;

import ruslep.student_schedule.R;
import ruslep.student_schedule.architecture.model.Preferens.MyPreferens;
import ruslep.student_schedule.architecture.model.Preferens.MyPreferensImpl;
import ruslep.student_schedule.architecture.other.MyPrefs_;
import ruslep.student_schedule.architecture.other.Theme.UseTheme;
import ruslep.student_schedule.architecture.other.Theme.UseThemeImpl;
import ruslep.student_schedule.architecture.presenter.Base.PresenterBase;
import ruslep.student_schedule.architecture.presenter.Base.PresenterBaseImpl;

@EActivity
public class SettingActivityImpl extends AppCompatActivity implements SettingActivity, View.OnClickListener {

    @ViewById
    RadioButton rbCheslitel;

    @ViewById
    RadioButton rbZnamenatel;

    @ViewById
    RadioGroup rgTypeofWeek;

    @Bean(MyPreferensImpl.class)
    MyPreferens preferens;

    @ViewById
    Button btnBlue;

    @ViewById
    Button btnRed;

    @ViewById
    Button btnGrey;

    @ViewById
    Button btnAmber;

    @ViewById
    Button btnPurple;

    @ViewById
    Button btnGreen;

    @ViewById
    Button btnExit;

    @ViewById
    Button btnDelete;

    @Bean
    PresenterBaseImpl presenterBase;

    @Bean(UseThemeImpl.class)
    UseTheme useTheme;

    @StringRes(R.string.setting_dialog_exit)
    String SETTING_DIALOG_EXIT;

    @StringRes(R.string.setting_dialog_delete)
    String SETTING_DIALOG_DELETE;

    @StringRes(R.string.setting_dialog_OK)
    String SETTING_DIALOG_OK;

    @StringRes(R.string.setting_dialog_delete_ok)
    String SETTING_DIALOG_DELETE_OK;

    @StringRes(R.string.setting_dialog_NO)
    String SETTING_DIALOG_NO;

    @StringRes(R.string.setting_dialog_enter)
    String SETTING_DIALOG_ENTER;

    @StringRes(R.string.setting_auth)
    String SETTING_AUTH;




    private static final int BLUE = 0;
    private static final int RED = 1;
    private static final int GREEN = 2;
    private static final int AMBER = 3;
    private static final int PURPLE = 4;
    private static final int GREY = 5;



    @AfterViews
    public void afterView(){
        switch (preferens.getTypeOfWeek()){
            case "Числитель": rbCheslitel.setChecked(true);
                break;
            case "Знаменатель": rbZnamenatel.setChecked(true);
                break;
            default:
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTheme(useTheme.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rgTypeofWeek.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.rbCheslitel){
                    presenterBase.updateTypeOfWeek("Числитель");
                    preferens.setReCreateMainActivity(true);
                }else{
                    presenterBase.updateTypeOfWeek("Знаменатель");
                    preferens.setReCreateMainActivity(true);
                }
            }
        });

        btnAmber.setOnClickListener(this);
        btnRed.setOnClickListener(this);
        btnGreen.setOnClickListener(this);
        btnGrey.setOnClickListener(this);
        btnPurple.setOnClickListener(this);
        btnBlue.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

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


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAmber:
                preferens.setReCreateMainActivity(true);
                useTheme.setTheme(AMBER);
                recreate();
                break;
            case R.id.btnBlue:
                preferens.setReCreateMainActivity(true);
                useTheme.setTheme(BLUE);
                recreate();
                break;
            case R.id.btnRed:
                preferens.setReCreateMainActivity(true);
                useTheme.setTheme(RED);
                recreate();
                break;
            case R.id.btnGreen:
                preferens.setReCreateMainActivity(true);
                useTheme.setTheme(GREEN);
                recreate();
                break;
            case R.id.btnGrey:
                preferens.setReCreateMainActivity(true);
                useTheme.setTheme(GREY);
                recreate();
                break;
            case R.id.btnPurple:
                preferens.setReCreateMainActivity(true);
                useTheme.setTheme(PURPLE);
                recreate();
                break;
            case R.id.btnExit:
                /** диалог подтверждения выхода с аккаунта*/
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(this);
                builder.setMessage(SETTING_DIALOG_EXIT);
                builder.setPositiveButton(SETTING_DIALOG_OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        preferens.setAuth(false);
                        preferens.setRegistrations(false);
                        preferens.setPhoneNumber(SETTING_AUTH);
                        preferens.setReCreateMainActivity(true);
                    }
                });
                builder.setNegativeButton(SETTING_DIALOG_NO, null);
                builder.show();
                break;
            case R.id.btnDelete:
                /** диалог подтверждения удаления всех данных*/
                AlertDialog.Builder builder2 =
                        new AlertDialog.Builder(this);
                builder2.setMessage(SETTING_DIALOG_DELETE);
                builder2.setPositiveButton(SETTING_DIALOG_DELETE_OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenterBase.deletAllFromDB();
                        preferens.setReCreateMainActivity(true);
                    }
                });
                builder2.setNegativeButton(SETTING_DIALOG_NO, null);
                builder2.show();
                break;
            default:
                break;
        }
    }

}
