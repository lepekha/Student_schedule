package ruslep.student_schedule.architecture.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import java.util.Arrays;

import ruslep.student_schedule.R;
import ruslep.student_schedule.architecture.model.Preferens.MyPreferens;
import ruslep.student_schedule.architecture.model.Preferens.MyPreferensImpl;
import ruslep.student_schedule.architecture.other.Theme.UseTheme;
import ruslep.student_schedule.architecture.other.Theme.UseThemeImpl;
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
    Switch schHideSchedule;

    @ViewById
    Button btnBlue, btnRed, btnGrey, btnAmber, btnPurple, btnGreen, btnExit, btnPrivate, btnDelete, btnDeleteAuth;

    @ViewById(R.id.main_content)
    CoordinatorLayout coordinatorLayout;

    @Bean
    PresenterBaseImpl presenterBase;

    @Bean(UseThemeImpl.class)
    UseTheme useTheme;

    @StringRes(R.string.setting_dialog_exit)
    String SETTING_DIALOG_EXIT;

    @StringRes(R.string.setting_dialog_delete)
    String SETTING_DIALOG_DELETE;

    @StringRes(R.string.setting_dialog_deleteAuth)
    String SETTING_DIALOG_DELETE_AUTH;

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

    @StringRes(R.string.setting_dialog_hideSchedule)
    String SETTING_DIALOG_HIDESCHEDULE;

    @StringRes(R.string.setting_dialog_hideSchedule_ok)
    String SETTING_DIALOG_HIDESCHEDULE_OK;

    @StringRes(R.string.setting_dialog_enterSchedule)
    String SETTING_DIALOG_ENTER_SCHEDULE;

    @StringRes(R.string.setting_dialog_errorAuth)
    String SETTING_DIALOG_ERRORAUTH;

    @StringRes(R.string.setting_dialog_succesAuth)
    String SETTING_DIALOG_SUCCESAUTH;

    private static final int RC_SIGN_IN = 123;
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

        if(!preferens.getAuth()){
            schHideSchedule.setEnabled(false);
            schHideSchedule.setClickable(false);
            btnDeleteAuth.setEnabled(false);
            btnDeleteAuth.setClickable(false);
            btnExit.setEnabled(false);
            btnExit.setClickable(false);
        }

        schHideSchedule.setChecked(preferens.getHideSchedule());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTheme(useTheme.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        schHideSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** диалог предупреждающий о смене настройки скрывать рассписание*/
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(SettingActivityImpl.this, useTheme.getDialogStyle());
                builder.setMessage(SETTING_DIALOG_HIDESCHEDULE);
                builder.setPositiveButton(SETTING_DIALOG_HIDESCHEDULE_OK,null);
                builder.show();
            }
        });
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
        btnPrivate.setOnClickListener(this);
        btnDeleteAuth.setOnClickListener(this);

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
            case R.id.btnPrivate:
                String url="http://raspisanie-lruslan.rhcloud.com/";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
                break;
            case R.id.btnExit:
                /** диалог подтверждения выхода с аккаунта*/
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(this, useTheme.getDialogStyle());
                builder.setMessage(SETTING_DIALOG_EXIT);
                builder.setPositiveButton(SETTING_DIALOG_OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        preferens.setAuth(false);
                        preferens.setRegistrations(false);
                        preferens.setPhoneNumber(SETTING_AUTH);
                        preferens.setReCreateMainActivity(true);
                        showMessage(SETTING_DIALOG_ENTER);
                    }
                });
                builder.setNegativeButton(SETTING_DIALOG_NO, null);
                builder.show();
                break;
            case R.id.btnDelete:
                /** диалог подтверждения удаления всех данных*/
                AlertDialog.Builder builder2 =
                        new AlertDialog.Builder(this, useTheme.getDialogStyle());
                builder2.setMessage(SETTING_DIALOG_DELETE);
                builder2.setPositiveButton(SETTING_DIALOG_DELETE_OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenterBase.deletAllFromDB();
                        preferens.setReCreateMainActivity(true);
                        showMessage(SETTING_DIALOG_ENTER_SCHEDULE);
                    }
                });
                builder2.setNegativeButton(SETTING_DIALOG_NO, null);
                builder2.show();
                break;
            case R.id.btnDeleteAuth:
                             /** диалог подтверждения удаления аккаунта*/
                             AlertDialog.Builder builder3 =
                                     new AlertDialog.Builder(this, useTheme.getDialogStyle());
                             builder3.setMessage(SETTING_DIALOG_DELETE_AUTH);
                             builder3.setPositiveButton(SETTING_DIALOG_DELETE_OK, new DialogInterface.OnClickListener() {
                                 @Override
                                 public void onClick(DialogInterface dialogInterface, int i) {
                                     startActivityForResult(
                                             AuthUI.getInstance()
                                                     .createSignInIntentBuilder()
                                                     .setTheme(useTheme.getTheme())
                                                     .setAvailableProviders(
                                                             Arrays.asList(
                                                                     new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build()))
                                                     .setPrivacyPolicyUrl("http://raspisanie-lruslan.rhcloud.com")
                                                     .build(),
                                             RC_SIGN_IN);
                                 }
                             });
                             builder3.setNegativeButton(SETTING_DIALOG_NO, null);
                             builder3.show();
                break;
            default:
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            // Successfully signed in
            if (resultCode == ResultCodes.OK) {
                                //удаление аккаунта
                                AuthUI.getInstance()
                                        .delete(SettingActivityImpl.this)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    presenterBase.delete(preferens.getPhoneNumber());
                                                    preferens.setAuth(false);
                                                    preferens.setRegistrations(false);
                                                    preferens.setPhoneNumber(SETTING_AUTH);
                                                    preferens.setReCreateMainActivity(true);
                                                    showMessage(SETTING_DIALOG_SUCCESAUTH);
                                                } else {
                                                    showMessage(SETTING_DIALOG_ERRORAUTH);
                                                }
                                            }
                                        });

                return;
            } else {
                showMessage(SETTING_DIALOG_ERRORAUTH);
            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        preferens.setHideSchedule(schHideSchedule.isChecked());
    }

    @Override
    public void showMessage(String text) {
        Snackbar.make(coordinatorLayout,text, Snackbar.LENGTH_SHORT).show();
    }
}
