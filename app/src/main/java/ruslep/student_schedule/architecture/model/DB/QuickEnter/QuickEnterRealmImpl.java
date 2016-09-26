package ruslep.student_schedule.architecture.model.DB.QuickEnter;

import android.util.Log;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import ruslep.student_schedule.architecture.model.entity.QuickEnter;
import ruslep.student_schedule.architecture.other.MyPrefs_;

/**
 * Created by Ruslan on 21.08.2016.
 */
@EBean(scope = EBean.Scope.Singleton)
public class QuickEnterRealmImpl implements QuickEnterRealm {
    private Realm realm = Realm.getDefaultInstance();

    @Pref
    MyPrefs_ myPrefs;

    @Override
    public void setData(String text, int type) {
        final QuickEnter quickEnter = realm.where(QuickEnter.class).equalTo("text",text).findFirst();
        if(quickEnter == null) {
            QuickEnter newQuickEnter= new QuickEnter();
            newQuickEnter.setId(myPrefs.idQuickEnter().getOr(0));
            newQuickEnter.setText(text);
            newQuickEnter.setType(type);
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(newQuickEnter);
                    myPrefs.edit().idQuickEnter().put(myPrefs.idQuickEnter().get()+1).apply();
                }
            });
        }
    }

    @Override
    public ArrayList<String> getData(int type) {

        RealmResults<QuickEnter> result = realm.where(QuickEnter.class)
                .equalTo("type", type)
                .findAll();

        List<QuickEnter> ls = realm.copyFromRealm(result);

        ArrayList<String> returnList = new ArrayList<>();
        for (QuickEnter q : ls) {
            returnList.add(q.getText());
        }
        return returnList;
    }

    @Override
    public void deleteData(int type) {
        RealmResults<QuickEnter> result = realm.where(QuickEnter.class).equalTo("type", type).findAll();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                result.deleteAllFromRealm();
            }
        });

    }
}
