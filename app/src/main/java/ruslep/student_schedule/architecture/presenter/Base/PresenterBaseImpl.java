package ruslep.student_schedule.architecture.presenter.Base;

import android.content.Context;
import android.content.Intent;
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
import ruslep.student_schedule.architecture.other.MyPrefs_;
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

        switch (Const.TYPE_OF_WEEK.valueOf(typeOfWeek)){
            case Чисельник:
                preferens.setTypeOfWeek("Чисельник");
                if(weekOfYear % 2 == 0){
                    preferens.setInvertTypeOfWeek(false);
                }else{
                    preferens.setInvertTypeOfWeek(true);
                }
                break;
            case Знаменник:
                preferens.setTypeOfWeek("Знаменник");
                if(weekOfYear % 2 == 0){
                    preferens.setInvertTypeOfWeek(true);
                }else{
                    preferens.setInvertTypeOfWeek(false);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void initTyteOfWeek() {
        Calendar cal1 = Calendar.getInstance();
        cal1.setFirstDayOfWeek(Calendar.MONDAY);
        int weekOfYear = cal1.get(Calendar.WEEK_OF_YEAR);
        Log.e("zzz",(weekOfYear % 2)+"");
        switch (weekOfYear % 2){
            case 0:
                if(preferens.getInvertTypeOfWeek() == false){
                    preferens.setTypeOfWeek("Чисельник");
                    view.setTextTypeOfWeek("Чисельник");
                }else{
                    preferens.setTypeOfWeek("Знаменник");
                    view.setTextTypeOfWeek("Знаменник");
                }
                break;
            case 1:
                if(preferens.getInvertTypeOfWeek() == false){
                    preferens.setTypeOfWeek("Знаменник");
                    view.setTextTypeOfWeek("Знаменник");
                }else{
                    preferens.setTypeOfWeek("Чисельник");
                    view.setTextTypeOfWeek("Чисельник");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void review() {
        final String appPackageName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    @Override
    public void share() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Очень классная программа");
        context.startActivity(Intent.createChooser(sharingIntent, "qweqweqwe"));
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
                .setSchedule(phoneMD5, gson.toJson(subjectRealm.getAllFromDB()))
                .subscribe(new Observer<Response<ResponseBody>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        endLoading();
                        view.showMessage("Ошибка. Нет соединения с интернетом или проблемы на сервере.");
                    }

                    @Override
                    public void onNext(Response<ResponseBody> responseBodyResponse) {
                        switch (responseBodyResponse.code()){
                            case OK:
                                view.showMessage("Сохранение завершено.");
                                break;
                            case NOT_FOUND:
                                preferens.setRegistrations(false);
                                registerUser(true,phoneMD5);
                                break;
                            default:
                                view.showMessage("Проблемы на сервере");
                                break;
                        }
                        endLoading();
                    }
                });
    }

    @Override
    public void registerUser(boolean whereReturn, String phoneMD5) {
        Log.e("errr","presenter");
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
                                    view.showMessage("Ошибка регистрации.");
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
            view.showMessage("Для начала работы нужно авторизироваться.");
        }
    }

    @Override
    public void getSchedule(String phoneMD5) {
        model
                .getSchedule(preferens.getPhoneNumber())
                .subscribe(new Observer<List<Subject>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMessage("Ошибка. Нет соединения с интернетом или проблемы на сервере.");
                    }

                    @Override
                    public void onNext(List<Subject> list) {
                        subjectRealm.deleteAllFromDB();
                        subjectRealm.saveAllToDB(list);
                        view.showMessage("Загрузка завершена.");
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
        if(view.getTextTypeOfWeek().equals("Чисельник")) {
            view.setTextTypeOfWeek("Знаменник");
        }else{
            view.setTextTypeOfWeek("Чисельник");
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
        Log.e("dff","3");
        return view.getTextTypeOfWeek();
    }
}
