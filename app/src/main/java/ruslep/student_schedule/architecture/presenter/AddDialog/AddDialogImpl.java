package ruslep.student_schedule.architecture.presenter.AddDialog;

import android.util.Log;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.greenrobot.eventbus.EventBus;
import ruslep.student_schedule.architecture.model.DB.Subject.SubjectRealm;
import ruslep.student_schedule.architecture.model.DB.Subject.SubjectRealmImpl;
import ruslep.student_schedule.architecture.model.entity.Subject;
import ruslep.student_schedule.architecture.other.Event.AddSubject;
import ruslep.student_schedule.architecture.other.MyPrefs_;
import ruslep.student_schedule.architecture.presenter.PresenterPresenterFragmentScheduleImpl;
import ruslep.student_schedule.architecture.view.FragmentSchedule.FragmentScheduleImpl;
import ruslep.student_schedule.architecture.view.FragmentSchedule.FragmentScheduleImpl_;
import ruslep.student_schedule.architecture.view.View;

/**
 * Created by Ruslan on 24.08.2016.
 */
@EBean
public class AddDialogImpl implements AddDialog {
    View view;

    @Bean(SubjectRealmImpl.class)
    SubjectRealm subjectRealm;

    @Bean
    PresenterPresenterFragmentScheduleImpl presenterPresenterFragmentSchedule;

    @Pref
    MyPrefs_ myPrefs;

    FragmentScheduleImpl fragmentSchedule = new FragmentScheduleImpl_();

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void saveSubject(Subject subject) {
        subjectRealm.saveToDB(subject);
        Log.e("zzz",myPrefs.day().get()+"");
        //update list subject
        EventBus.getDefault().post(new AddSubject(subject));
    }
}
