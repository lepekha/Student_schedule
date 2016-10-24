package ruslep.student_schedule.architecture.model.DB.Contacts;

import org.androidannotations.annotations.EBean;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import ruslep.student_schedule.architecture.model.entity.Contacts;
import ruslep.student_schedule.architecture.model.entity.Subject;

/**
 * Created by Ruslan on 26.09.2016.
 */
@EBean(scope = EBean.Scope.Singleton)
public class ContactsRealmImpl implements ContactsRealm {

    private Realm realm = Realm.getDefaultInstance();

    @Override
    public void setAllContacts(List<Contacts> contactsList) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Contacts contacts: contactsList) {
                    realm.copyToRealmOrUpdate(contacts);
                }
            }
        });
    }

    @Override
    public List<Contacts> getAllContacts() {
        return realm.copyFromRealm(realm.where(Contacts.class).findAll());
    }

    @Override
    public void deleteAllContacts() {
        final RealmResults<Contacts> results = realm.where(Contacts.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }
}
