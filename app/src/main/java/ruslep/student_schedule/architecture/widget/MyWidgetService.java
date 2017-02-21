package ruslep.student_schedule.architecture.widget;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EIntentService;
import org.androidannotations.annotations.res.StringRes;
import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import ruslep.student_schedule.R;
import ruslep.student_schedule.architecture.model.Preferens.MyPreferensImpl;
import ruslep.student_schedule.architecture.model.entity.Subject;
import ruslep.student_schedule.architecture.model.entity.SubjectParcelable;
import ruslep.student_schedule.architecture.other.Event.GetSubjectFromServer;
import ruslep.student_schedule.architecture.other.Event.SubjectsToWidget;
import ruslep.student_schedule.architecture.presenter.PresenterFragmentScheduleImpl;
import ruslep.student_schedule.architecture.presenter.PresenterWidgetImpl;

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

    SharedPreferences sPref;

    @Bean
    PresenterFragmentScheduleImpl presenterFragmentSchedule;
    @Bean
    PresenterWidgetImpl presenterWidget;


    @StringRes(R.string.baseActivity_typeOfWeek_AllWeek)
    String ALL_WEEK;

    List<SubjectParcelable> subjects = new ArrayList<>();

    public MyWidgetService() {
        super("AllUniver");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        sPref = getSharedPreferences("Preferens", 0);



        if (intent.getAction().equalsIgnoreCase(ACTION_CHANGE_RIGHT)){


            int move = sPref.getInt("weekDay",0);
            int mAppWidgetId = intent.getIntExtra("id",0);
            Intent countIntentRight = new Intent(this, MySchedule.class);
            countIntentRight.setAction(ACTION_CHANGE_RIGHT);

                countIntentRight.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) presenterWidget.convertSubject(getSchedule(turnRight(move))));

            countIntentRight.putExtra("idd", mAppWidgetId);
            Log.e("intentService",""+presenterWidget.convertSubject(getSchedule(turnRight(move))).toString());
            startService(countIntentRight);
            PendingIntent pIntentRight  = PendingIntent.getBroadcast(this, 0, countIntentRight, 0);
            try {
                pIntentRight.send();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        }
        if (intent.getAction().equalsIgnoreCase(ACTION_CHANGE_LEFT)){

            int move = sPref.getInt("weekDay",0);
            int mAppWidgetId = intent.getIntExtra("id",0);

            Intent countIntentLeft = new Intent(this, MySchedule.class);
            countIntentLeft.setAction(ACTION_CHANGE_LEFT);
            if(getSchedule(turnRight(move)).size()>0){
            countIntentLeft.putExtra("data", (Serializable) getSchedule(turnRight(move)));}else{
                countIntentLeft.putExtra("data", "[]");
            }
            countIntentLeft.putExtra("idd", mAppWidgetId);
            startService(countIntentLeft);
            PendingIntent pIntentLeft  = PendingIntent.getBroadcast(this, 0, countIntentLeft, 0);
            Log.e("intentService",""+getSchedule(turnLeft(move)).toString());
            try {
                pIntentLeft.send();
                EventBus.getDefault().post(new SubjectsToWidget(getSchedule(turnLeft(move))));
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        }



    }


    public int turnLeft(int p){
        int i = p;
        i = i - 1;
        if(i < 0){
            i = 6;
        }
        return i;
    }

    public int turnRight(int p){
        int i = p;
        i = i + 1;
        if(i > 6){
            i = 0;
        }
        return i;
    }

    public List<Subject> getSchedule(int day){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Subject> result = realm.where(Subject.class)
                .equalTo("dayOfWeek",day)
                .beginGroup()
                .equalTo("typeOfWeek", "Знаменатель")
                .or()
                .equalTo("typeOfWeek", ALL_WEEK)
                .endGroup()
                .findAll();
        return realm.copyFromRealm(result);
    }

}
