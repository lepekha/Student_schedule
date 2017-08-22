package ruslep.student_schedule.architecture.presenter.Base;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;
import ruslep.student_schedule.R;
import ruslep.student_schedule.architecture.model.DB.Subject.SubjectRealm;
import ruslep.student_schedule.architecture.model.DB.Subject.SubjectRealmImpl;
import ruslep.student_schedule.architecture.model.Preferens.MyPreferens;
import ruslep.student_schedule.architecture.model.Preferens.MyPreferensImpl;
import ruslep.student_schedule.architecture.model.REST.Model;
import ruslep.student_schedule.architecture.model.REST.ModelImpl;
import ruslep.student_schedule.architecture.model.entity.Subject;
import ruslep.student_schedule.architecture.other.Const;
import ruslep.student_schedule.architecture.other.Event.AddSubject;
import ruslep.student_schedule.architecture.other.Event.ChangeTypeOfWeek;
import ruslep.student_schedule.architecture.other.Event.GetSubjectFromServer;
import ruslep.student_schedule.architecture.presenter.PresenterFragmentSchedule;
import ruslep.student_schedule.architecture.presenter.PresenterFragmentScheduleImpl;
import ruslep.student_schedule.architecture.view.BaseActivity;
import ruslep.student_schedule.architecture.view.View;
import rx.Observer;

/**
 * Created by Lepekha.R.O on 25.08.2016.
 */
@EBean(scope = EBean.Scope.Singleton)
public class PresenterBaseImpl implements PresenterBase {


    @RootContext
    Context context;

    @Bean(ModelImpl.class)
    Model model;

    @Bean(MyPreferensImpl.class)
    MyPreferens preferens;

    @Bean(SubjectRealmImpl.class)
    SubjectRealm subjectRealm;

    @StringRes(R.string.baseActivity_typeOfWeek_Cheslitel)
    String CHESLITEL;

    @StringRes(R.string.baseActivity_typeOfWeek_Znamenatel)
    String ZNAMENATEL;

    @StringRes(R.string.baseActivity_share_message)
    String SHARE_MESSAEG;

    @StringRes(R.string.baseActivity_share_header)
    String SHARE_HEADER;

    @StringRes(R.string.baseActivity_Schedule_error)
    String SCHEDULE_ERROR;

    @StringRes(R.string.baseActivity_getSchedule_compl)
    String GET_SCHEDULE_COMPL;

    @StringRes(R.string.baseActivity_Schedule_needAuth)
    String SCHEDULE_NEED_AUTH;

    @StringRes(R.string.baseActivity_registerSchedule_error)
    String REGISTER_SCHEDULE_ERROR;

    @StringRes(R.string.baseActivity_setSchedule_compl)
    String SET_SCHEDULE_COMPL;



    @Bean(PresenterFragmentScheduleImpl.class)
    PresenterFragmentSchedule presenterFragmentSchedule;



    public final int CREATED = 201;
    public final int OK = 200;
    public final int NOT_FOUND = 404;

    private BaseActivity view;


    @Override
    public void setView(BaseActivity view) {
        this.view = view;
    }

    @Override
    public void hideAuthBtn() {
        if(preferens.getAuth()){
            view.hideAuthBtn();
        }
    }

    @Override
    public void changeTypeOfWeek() {
            EventBus.getDefault().post(new ChangeTypeOfWeek());
    }

    @Override
    public void updateTypeOfWeek(String typeOfWeek) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setFirstDayOfWeek(Calendar.MONDAY);
        int weekOfYear = cal1.get(Calendar.WEEK_OF_YEAR);

        if(typeOfWeek.equals(CHESLITEL)){
            preferens.setTypeOfWeek(CHESLITEL);
            if(weekOfYear % 2 == 0){
                preferens.setInvertTypeOfWeek(false);
            }else{
                preferens.setInvertTypeOfWeek(true);
            }
        }else{
            preferens.setTypeOfWeek(ZNAMENATEL);
            if(weekOfYear % 2 == 0){
                preferens.setInvertTypeOfWeek(true);
            }else{
                preferens.setInvertTypeOfWeek(false);
            }
        }
    }

    @Override
    public void deletAllFromDB() {
        subjectRealm.deleteAllFromDB();
    }

    @Override
    public void initTyteOfWeek() {
        Calendar cal1 = Calendar.getInstance();
        cal1.setFirstDayOfWeek(Calendar.MONDAY);
        int weekOfYear = cal1.get(Calendar.WEEK_OF_YEAR);
        switch (weekOfYear % 2){
            case 0:
                if(preferens.getInvertTypeOfWeek() == false){
                    preferens.setTypeOfWeek(CHESLITEL);
                    view.setTextTypeOfWeek(CHESLITEL);
                }else{
                    preferens.setTypeOfWeek(ZNAMENATEL);
                    view.setTextTypeOfWeek(ZNAMENATEL);
                }
                break;
            case 1:
                if(preferens.getInvertTypeOfWeek() == false){
                    preferens.setTypeOfWeek(ZNAMENATEL);
                    view.setTextTypeOfWeek(ZNAMENATEL);
                }else{
                    preferens.setTypeOfWeek(CHESLITEL);
                    view.setTextTypeOfWeek(CHESLITEL);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void review() {
        final String appPackageName = context.getPackageName();
        Intent mobileIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName));
        mobileIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName));
        webIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(mobileIntent);
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(webIntent);
        }
    }

    @Override
    public void share() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, SHARE_MESSAEG);
        Intent new_intent = Intent.createChooser(shareIntent, SHARE_HEADER);
        new_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(new_intent);
    }

    @Override
    public boolean auth(String phoneNumber) {
        preferens.setAuth(true);
        preferens.setPhoneNumber(phoneNumber);
        return true;
    }



    @Override
    public void setSchedule(String phoneMD5) {
        Gson gson = new Gson();
        model
                .setSchedule(phoneMD5, gson.toJson(subjectRealm.getAllFromDB()),preferens.getHideSchedule())
                .subscribe(new Observer<Response<ResponseBody>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMessage(SCHEDULE_ERROR);
                        endLoading();
                    }

                    @Override
                    public void onNext(Response<ResponseBody> responseBodyResponse) {
                        switch (responseBodyResponse.code()){
                            case OK:
                                view.showMessage(SET_SCHEDULE_COMPL);
                                break;
                            case NOT_FOUND:
                                preferens.setRegistrations(false);
                                registerUser(true,phoneMD5);
                                break;
                            default:
                                view.showMessage(SCHEDULE_ERROR);
                                break;
                        }
                        endLoading();
                    }
                });
    }

    @Override
    public void delete(String phoneMD5) {
        Gson gson = new Gson();
        model
                .delete(phoneMD5)
                .subscribe(new Observer<Response<ResponseBody>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Response<ResponseBody> responseBodyResponse) {
                        switch (responseBodyResponse.code()){
                            case OK:
                                view.showMessage(SET_SCHEDULE_COMPL);
                                break;
                            case NOT_FOUND:
                                preferens.setRegistrations(false);
                                registerUser(true,phoneMD5);
                                break;
                            default:
                                view.showMessage(SCHEDULE_ERROR);
                                break;
                        }
                        endLoading();
                    }
                });
    }

    @Override
    public void registerUser(boolean whereReturn, String phoneMD5) {
        startLoading();
        if(preferens.getAuth()) {
            if (!preferens.getRegistrations()) {
                model
                        .registerUser(preferens.getPhoneNumber())
                        .subscribe(new Observer<Response<ResponseBody>>() {

                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                endLoading();
                            }

                            @Override
                            public void onNext(Response<ResponseBody> responseBodyResponse) {
                                if (responseBodyResponse.code() == OK || responseBodyResponse.code() == CREATED) {
                                    preferens.setRegistrations(true);

                                    if(whereReturn){
                                        setSchedule(phoneMD5);
                                    }else{
                                        getSchedule(phoneMD5);
                                    }
                                } else {
                                    view.showMessage(REGISTER_SCHEDULE_ERROR);
                                    endLoading();
                                }
                            }
                        });
            } else {
                if(whereReturn){
                    setSchedule(phoneMD5);
                }else{
                    getSchedule(phoneMD5);
                }
            }
        }else {
            view.showMessage(SCHEDULE_NEED_AUTH);
            endLoading();
        }
    }

    @Override
    public void getSchedule(String phoneMD5) {
        model
                .getSchedule(preferens.getPhoneNumber(),true)
                .subscribe(new Observer<List<Subject>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMessage(SCHEDULE_ERROR);
                        endLoading();
                    }

                    @Override
                    public void onNext(List<Subject> list) {
                        subjectRealm.deleteAllFromDB();
                        subjectRealm.saveAllToDB(list);
                        view.showMessage(GET_SCHEDULE_COMPL);
                        EventBus.getDefault().post(new GetSubjectFromServer());
                        endLoading();
                    }
                });
    }

    @Override
    public String getMyPhone() {
        return preferens.getPhoneNumber();
    }

    @Override
    public void setTextTypeOfWeek() {
        if(view.getTextTypeOfWeek().equals(CHESLITEL)) {
            view.setTextTypeOfWeek(ZNAMENATEL);
        }else{
            view.setTextTypeOfWeek(CHESLITEL);
        }
        changeTypeOfWeek();
    }

    @Override
    public void startLoading() {
        view.showProgressBar();
    }

    @Override
    public void endLoading() {
        view.hideProgressBar();
    }

    @Override
    public String getTextTuypeOfWeek() {
        return view.getTextTypeOfWeek();
    }

    @Override
    public void setDrawerHeaderPhone() {
        view.setDrawerHeaderPhoneNumber(preferens.getPhoneNumber());
    }

    @Override
    public int getDayOfWeek() {
        Calendar c = Calendar.getInstance();
        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                return 0;
            case Calendar.TUESDAY:
                return 1;
            case Calendar.WEDNESDAY:
                return 2;
            case Calendar.THURSDAY:
                return 3;
            case Calendar.FRIDAY:
                return 4;
            case Calendar.SATURDAY:
                return 5;
            case Calendar.SUNDAY:
                return 6;
            default:
                return 1;
        }
    }

    @Override
    public int getWeekName(int DayOfWeek) {

        switch (DayOfWeek) {
            case 0:
                return R.string.baseActivity_dayOfWeek_monday;
            case 1:
                return R.string.baseActivity_dayOfWeek_tuesday;
            case 2:
                return R.string.baseActivity_dayOfWeek_wednesday;
            case 3:
                return R.string.baseActivity_dayOfWeek_thursday;
            case 4:
                return R.string.baseActivity_dayOfWeek_friday;
            case 5:
                return R.string.baseActivity_dayOfWeek_saturday;
            case 6:
                return R.string.baseActivity_dayOfWeek_sunday;
            default:
                return R.string.baseActivity_dayOfWeek_monday;
        }
    }

    @Override
    public void setWidgetDay(int day) {
        preferens.setWidgetWeek(day);
    }

    @Override
    public int getWidgetDay() {
        return preferens.getWidgetWeek();
    }
}
