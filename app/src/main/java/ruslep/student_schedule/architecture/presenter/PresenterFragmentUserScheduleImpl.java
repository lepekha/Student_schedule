package ruslep.student_schedule.architecture.presenter;

import android.content.Context;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.greenrobot.eventbus.EventBus;

import java.util.List;

import ruslep.student_schedule.architecture.model.DB.Subject.SubjectRealm;
import ruslep.student_schedule.architecture.model.DB.Subject.SubjectRealmImpl;
import ruslep.student_schedule.architecture.model.DB.User.UserRealm;
import ruslep.student_schedule.architecture.model.DB.User.UserRealmImpl;
import ruslep.student_schedule.architecture.model.entity.Subject;
import ruslep.student_schedule.architecture.model.entity.User;
import ruslep.student_schedule.architecture.other.Event.DeleteSubject;
import ruslep.student_schedule.architecture.other.Event.EditSubject;
import ruslep.student_schedule.architecture.other.Event.PasteSubject;
import ruslep.student_schedule.architecture.other.MyPrefs_;
import ruslep.student_schedule.architecture.view.BaseActivity;
import ruslep.student_schedule.architecture.view.UserActivity;

/**
 * Created by Lepekha.R.O on 20.08.2016.
 */
@EBean(scope = EBean.Scope.Singleton)
public class PresenterFragmentUserScheduleImpl implements PresenterFragmentUserSchedule {


    @Bean(UserRealmImpl.class)
    UserRealm userRealm;

    @RootContext
    Context context;

    @Pref
    MyPrefs_ myPrefs;

    private UserActivity view;



    @Override
    public List<User> getUser(String typeOfWeek, int dayOfWeek) {
        return userRealm.getFromDB(typeOfWeek,dayOfWeek);
    }

}
