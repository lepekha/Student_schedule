package ruslep.student_schedule.architecture.model.DB.Subject;

import android.util.Log;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import ruslep.student_schedule.R;
import ruslep.student_schedule.architecture.model.Preferens.MyPreferens;
import ruslep.student_schedule.architecture.model.Preferens.MyPreferensImpl;
import ruslep.student_schedule.architecture.model.entity.Subject;
import ruslep.student_schedule.architecture.model.entity.User;
import ruslep.student_schedule.architecture.other.Const;
import ruslep.student_schedule.architecture.other.MyPrefs_;
import ruslep.student_schedule.architecture.presenter.Base.PresenterBase;
import ruslep.student_schedule.architecture.presenter.Base.PresenterBaseImpl;

/**
 * Created by Ruslan on 21.08.2016.
 */
@EBean(scope = EBean.Scope.Singleton)
public class SubjectRealmImpl implements SubjectRealm {
    private Realm realm = Realm.getDefaultInstance();

    @Pref
    MyPrefs_ myPrefs;

    @Bean(MyPreferensImpl.class)
    MyPreferens preferens;

    @Bean(PresenterBaseImpl.class)
    PresenterBase presenterBase;

    @StringRes(R.string.baseActivity_typeOfWeek_Cheslitel)
    String CHESLITEL;

    @StringRes(R.string.baseActivity_typeOfWeek_Znamenatel)
    String ZNAMENATEL;



    public SubjectRealmImpl() {

    }

    @Override
    public Subject saveToDB(Subject subject) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                subject.setId(preferens.getID());
                realm.copyToRealmOrUpdate(subject);
                preferens.setID(preferens.getID() + 1);
            }
        });
        return subject;
    }

    @Override
    public List<Subject> getFromDB(String typeOfWeek, int dayOfWeek) {
        int realTypeOfWeek = 2;
        if(typeOfWeek.equals(CHESLITEL)){
            realTypeOfWeek = 0;
        }else{
            realTypeOfWeek = 1;
        }

        RealmResults<Subject> result = realm.where(Subject.class)
                .equalTo("dayOfWeek", dayOfWeek)
                .beginGroup()
                .equalTo("realTypeOfWeek", realTypeOfWeek)
                .or()
                .equalTo("realTypeOfWeek", Const.ALLWEEK)
                .endGroup()
                .findAll();
        return realm.copyFromRealm(result);

    }

    @Override
    public void deleteSubject(Subject subject) {
        final Subject res = realm.where(Subject.class).equalTo("id",subject.getId()).findFirst();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                res.deleteFromRealm();
            }
        });
    }

    @Override
    public Subject getSubjectByID(int id) {
        return realm.where(Subject.class).equalTo("id",id).findFirst();
    }

    @Override
    public void editSubject(Subject subject) {

        final Subject results = realm.where(Subject.class).equalTo("id",subject.getId()).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteFromRealm();
                realm.copyToRealmOrUpdate(subject);
            }
        });
    }

    @Override
    public List<Subject> getAllFromDB() {
        return realm.copyFromRealm(realm.where(Subject.class).findAll());
    }

    @Override
    public void deleteAllFromDB() {
        final RealmResults<Subject> results = realm.where(Subject.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
        preferens.setID(0);
    }

    @Override
    public void saveAllToDB(List<Subject> subjectList) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Subject subject: subjectList) {
                    subject.setId(preferens.getID());
                    realm.copyToRealmOrUpdate(subject);
                    preferens.setID(preferens.getID() + 1);
                }
            }
        });
    }

    @Override
    public void saveAllFromUser(List<User> userList) {
        realm.executeTransaction(new Realm.Transaction() {

            @Override
            public void execute(Realm realm) {

                for (User user: userList) {
                    Subject subject = new Subject();
                    subject.setId(user.getId());
                    subject.setTypeWeek(user.getTypeWeek());
                    subject.setDayOfWeek(user.getDayOfWeek());
                    subject.setNameSubject(user.getNameSubject());
                    subject.setNumberSubject(user.getNumberSubject());
                    subject.setOrderSubject(user.getOrderSubject());
                    subject.setRoomSubject(user.getRoomSubject());
                    subject.setTeacherSubject(user.getTeacherSubject());
                    subject.setTimeEndSubject(user.getTimeEndSubject());
                    subject.setTimeStartSubject(user.getTimeStartSubject());
                    subject.setTypeSubject(user.getTypeSubject());
                    realm.copyToRealm(subject);
                }
            }
        });
    }
}
