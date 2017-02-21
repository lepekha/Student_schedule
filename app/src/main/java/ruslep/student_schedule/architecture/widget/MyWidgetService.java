package ruslep.student_schedule.architecture.widget;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EIntentService;
import org.androidannotations.annotations.res.StringRes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import ruslep.student_schedule.R;
import ruslep.student_schedule.architecture.model.Preferens.MyPreferensImpl;
import ruslep.student_schedule.architecture.model.entity.Subject;
import ruslep.student_schedule.architecture.presenter.PresenterFragmentScheduleImpl;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
@EIntentService
public class MyWidgetService extends IntentService {

    final static String ACTION_CHANGE_LEFT = "ruslep.student_schedule.widget.left";
    final static String ACTION_CHANGE_RIGHT = "ruslep.student_schedule.widget.right";
    final static String ACTION_CHANGE_DATA = "ruslep.student_schedule.widget.getData";


    @Bean
    MyPreferensImpl preferens;

    @Bean
    PresenterFragmentScheduleImpl presenterFragmentSchedule;

    @StringRes(R.string.baseActivity_typeOfWeek_AllWeek)
    String ALL_WEEK;

    List<Subject> subjects = new ArrayList<>();

    public MyWidgetService() {
        super("AllUniver");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Realm realm = Realm.getDefaultInstance();

        RealmResults<Subject> result = realm.where(Subject.class)
                .equalTo("dayOfWeek", 0)
                .beginGroup()
                .equalTo("typeOfWeek", "Числитель")
                .or()
                .equalTo("typeOfWeek", ALL_WEEK)
                .endGroup()
                .findAll();


        subjects = realm.copyFromRealm(result);
        Log.e("intentService",""+subjects.get(0).toString());

        if (intent.getAction().equalsIgnoreCase(ACTION_CHANGE_RIGHT)){


            int mAppWidgetId = intent.getIntExtra("id",0);

            Intent countIntentRight = new Intent(this, MySchedule.class);
            countIntentRight.setAction(ACTION_CHANGE_RIGHT);
            countIntentRight.putExtra("idd", mAppWidgetId);
            startService(countIntentRight);
            PendingIntent pIntentRight  = PendingIntent.getBroadcast(this, 0, countIntentRight, 0);
            try {
                pIntentRight.send();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        }
        if (intent.getAction().equalsIgnoreCase(ACTION_CHANGE_LEFT)){


            int mAppWidgetId = intent.getIntExtra("id",0);

            Intent countIntentRight = new Intent(this, MySchedule.class);
            countIntentRight.setAction(ACTION_CHANGE_LEFT);
            countIntentRight.putExtra("idd", mAppWidgetId);
            Log.e("intentService","RIGHT"+mAppWidgetId);
            startService(countIntentRight);
            PendingIntent pIntentRight  = PendingIntent.getBroadcast(this, 0, countIntentRight, 0);
            try {
                pIntentRight.send();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        }

        if (intent.getAction().equalsIgnoreCase(ACTION_CHANGE_DATA)){


            int mAppWidgetId = intent.getIntExtra("id",0);

            Intent countIntentRight = new Intent(this, MySchedule.class);
            countIntentRight.setAction(ACTION_CHANGE_DATA);
            countIntentRight.putExtra("data", (Serializable) subjects);
            countIntentRight.putExtra("idd", mAppWidgetId);
            Log.e("intentService","RIGHT"+mAppWidgetId);
            startService(countIntentRight);
            PendingIntent pIntentRight  = PendingIntent.getBroadcast(this, 0, countIntentRight, 0);
            try {
                pIntentRight.send();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        }

    }
}
