package ruslep.student_schedule.architecture.model.DB.User;

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
import ruslep.student_schedule.architecture.other.MyPrefs_;
import ruslep.student_schedule.architecture.presenter.Base.PresenterBase;
import ruslep.student_schedule.architecture.presenter.Base.PresenterBaseImpl;

/**
 * Created by Ruslan on 21.08.2016.
 */
@EBean(scope = EBean.Scope.Singleton)
public class UserRealmImpl implements UserRealm {
    private Realm realm = Realm.getDefaultInstance();

    @Pref
    MyPrefs_ myPrefs;

    @Bean(MyPreferensImpl.class)
    MyPreferens preferens;



    @StringRes(R.string.baseActivity_typeOfWeek_AllWeek)
    String ALL_WEEK;

    public UserRealmImpl() {

    }

    @Override
    public User saveToDB(User user) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                user.setId(preferens.getID());
                realm.copyToRealmOrUpdate(user);
                preferens.setID(preferens.getID() + 1);
            }
        });
        return user;
    }

    @Override
    public List<User> getFromDB(String typeOfWeek, int dayOfWeek) {

        RealmResults<User> result = realm.where(User.class)
                .equalTo("dayOfWeek", dayOfWeek)
                .beginGroup()
                .equalTo("typeOfWeek", typeOfWeek)
                .or()
                .equalTo("typeOfWeek", ALL_WEEK)
                .endGroup()
                .findAll();
        return realm.copyFromRealm(result);

    }

    @Override
    public List<User> getAllFromDB() {
        return realm.copyFromRealm(realm.where(User.class).findAll());
    }



    @Override
    public void deleteAllFromDB() {
        final RealmResults<User> results = realm.where(User.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
        preferens.setID(0);
    }

    @Override
    public boolean saveAllToDB(List<User> userList) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (User user: userList) {
                    user.setId(preferens.getID());
                    realm.copyToRealmOrUpdate(user);
                    preferens.setID(preferens.getID() + 1);
                }
            }
        });
        return true;
    }
}
