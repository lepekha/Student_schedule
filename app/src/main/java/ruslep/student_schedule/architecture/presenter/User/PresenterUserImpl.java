package ruslep.student_schedule.architecture.presenter.User;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.google.gson.Gson;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.res.StringRes;
import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;
import ruslep.student_schedule.R;
import ruslep.student_schedule.architecture.model.DB.Subject.SubjectRealm;
import ruslep.student_schedule.architecture.model.DB.Subject.SubjectRealmImpl;
import ruslep.student_schedule.architecture.model.DB.User.UserRealm;
import ruslep.student_schedule.architecture.model.DB.User.UserRealmImpl;
import ruslep.student_schedule.architecture.model.Preferens.MyPreferens;
import ruslep.student_schedule.architecture.model.Preferens.MyPreferensImpl;
import ruslep.student_schedule.architecture.model.REST.Model;
import ruslep.student_schedule.architecture.model.REST.ModelImpl;
import ruslep.student_schedule.architecture.model.entity.Subject;
import ruslep.student_schedule.architecture.model.entity.User;
import ruslep.student_schedule.architecture.other.Event.ChangeTypeOfWeek;
import ruslep.student_schedule.architecture.other.Event.GetSubjectFromServer;
import ruslep.student_schedule.architecture.other.Event.GetUserFromServer;
import ruslep.student_schedule.architecture.view.BaseActivity;
import ruslep.student_schedule.architecture.view.UserActivity;
import rx.Observer;

/**
 * Created by Lepekha.R.O on 25.08.2016.
 */
@EBean(scope = EBean.Scope.Singleton)
public class PresenterUserImpl implements PresenterUser {


    @RootContext
    Context context;

    @Bean(ModelImpl.class)
    Model model;

    @Bean(MyPreferensImpl.class)
    MyPreferens preferens;

    @Bean(UserRealmImpl.class)
    UserRealm userRealm;

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




    public final int CREATED = 201;
    public final int OK = 200;
    public final int NOT_FOUND = 404;

    private UserActivity view;


    @Override
    public void setView(UserActivity view) {
        this.view = view;
    }

    @Override
    public void deletAllFromDB() {
        userRealm.deleteAllFromDB();
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
    public void getSchedule(String phoneMD5) {
        startLoading();
        model
                .getUserSchedule(phoneMD5)
                .subscribe(new Observer<List<User>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMessage(SCHEDULE_ERROR);
                        endLoading();
                    }

                    @Override
                    public void onNext(List<User> list) {
                        userRealm.deleteAllFromDB();
                        userRealm.saveAllToDB(list);

                        view.showMessage(GET_SCHEDULE_COMPL);
                        endLoading();
                        EventBus.getDefault().post(new GetUserFromServer());
                    }
                });
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
    public String getTextTuypeOfWeek() {
        return view.getTextTypeOfWeek();
    }

    @Override
    public String getMyPhone() {
        return preferens.getPhoneNumber();
    }

    @Override
    public void startLoading() {
        view.showProgressBar();
    }

    @Override
    public void endLoading() {
        view.hideProgressBar();
    }
}
