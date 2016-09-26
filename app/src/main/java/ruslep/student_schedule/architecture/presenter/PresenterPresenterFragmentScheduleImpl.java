package ruslep.student_schedule.architecture.presenter;

import android.app.Activity;
import android.content.Context;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.greenrobot.eventbus.EventBus;

import java.util.List;

import ruslep.student_schedule.architecture.model.DB.Subject.SubjectRealm;
import ruslep.student_schedule.architecture.model.DB.Subject.SubjectRealmImpl;
import ruslep.student_schedule.architecture.model.entity.Subject;
import ruslep.student_schedule.architecture.other.Event.DeleteSubject;
import ruslep.student_schedule.architecture.other.Event.EditSubject;
import ruslep.student_schedule.architecture.other.Event.PasteSubject;
import ruslep.student_schedule.architecture.other.MyPrefs_;
import ruslep.student_schedule.architecture.view.BaseActivity;

/**
 * Created by Lepekha.R.O on 20.08.2016.
 */
@EBean(scope = EBean.Scope.Singleton)
public class PresenterPresenterFragmentScheduleImpl implements PresenterFragmentSchedule {

    private Subject subject = null;

    @Bean(SubjectRealmImpl.class)
    SubjectRealm subjectRealm;

    @RootContext
    Context context;

    @Pref
    MyPrefs_ myPrefs;

    private BaseActivity view;



    @Override
    public List<Subject> getSubject(String typeOfWeek, int dayOfWeek) {
        return subjectRealm.getFromDB(typeOfWeek,dayOfWeek);
    }

    @Override
    public void deleteSubject(Subject subject, int position) {
        subjectRealm.deleteSubject(subject);
        EventBus.getDefault().post(new DeleteSubject(position));
    }

    @Override
    public void popupDeleteSubject(Subject subject) {

    }

    @Override
    public void popupCopySubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public void popupPasteSubject() {
        if(isCopy()) {
            subject.setDayOfWeek(myPrefs.day().get());
            EventBus.getDefault().post(new PasteSubject(subjectRealm.saveToDB(subject)));
        }
    }


    @Override
    public Subject getSubjectByID(int id) {
        return subjectRealm.getSubjectByID(id);
    }

    @Override
    public void editSubject(Subject subject) {
        subjectRealm.editSubject(subject);
        EventBus.getDefault().post(new EditSubject());
    }

    @Override
    public boolean isCopy() {
        if(subject != null) {
            return true;
        }else{
            return false;
        }
    }

}
