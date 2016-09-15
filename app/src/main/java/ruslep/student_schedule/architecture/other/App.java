package ruslep.student_schedule.architecture.other;

import android.app.Application;

import org.androidannotations.annotations.EApplication;
import org.androidannotations.annotations.EBean;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Lepekha.R.O on 21.08.2016.
 */
@EApplication
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration config = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);
    }
}
