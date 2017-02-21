package ruslep.student_schedule.architecture.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.EProvider;
import org.androidannotations.annotations.EView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ruslep.student_schedule.R;
import ruslep.student_schedule.architecture.model.Preferens.MyPreferens;
import ruslep.student_schedule.architecture.model.Preferens.MyPreferensImpl;
import ruslep.student_schedule.architecture.model.Preferens.MyPreferensImpl_;
import ruslep.student_schedule.architecture.model.entity.Subject;
import ruslep.student_schedule.architecture.model.entity.SubjectParcelable;
import ruslep.student_schedule.architecture.other.Event.DeleteSubject;
import ruslep.student_schedule.architecture.other.Event.SubjectsToWidget;
import ruslep.student_schedule.architecture.presenter.Base.PresenterBase;
import ruslep.student_schedule.architecture.presenter.Base.PresenterBaseImpl;
import ruslep.student_schedule.architecture.presenter.PresenterFragmentScheduleImpl;
import ruslep.student_schedule.architecture.presenter.PresenterWidgetImpl;
import ruslep.student_schedule.architecture.view.BaseActivityImpl;

/**
 * Implementation of App Widget functionality.
 */

public class MySchedule extends AppWidgetProvider {

    PresenterBaseImpl presenterBase = new PresenterBaseImpl();
    SharedPreferences sPref;

    List<Subject> subjects;



    final static String ACTION_CHANGE_LEFT = "ruslep.student_schedule.widget.left";
    final static String ACTION_CHANGE_RIGHT = "ruslep.student_schedule.widget.right";
    final static String ACTION_CHANGE_DATA = "ruslep.student_schedule.widget.getData";


    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {

       // CharSequence widgetText = presenterBase.getWeekName(0);
        // Construct the RemoteViews object
        sPref = context.getSharedPreferences("Preferens", 0);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_schedule);
       // preferens.setWidgetWeek(presenterBase.getDayOfWeek());
        views.setTextViewText(R.id.txtWeekName,context.getString(presenterBase.getWeekName(sPref.getInt("weekDay",0))));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.layout.widget_schedule);
            setList(views, context, appWidgetId);

        }
        setUpdateTV(views, context, appWidgetId);
       // EventBus.getDefault().unregister(this);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

   /* @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSubjectsToWidget(SubjectsToWidget event) {
        Log.e("intentService",""+event.message.toString());
        //subjects = event.message;
    }*/


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
       // Log.e("sdfsdf",presenterBase.getWeekName(0)+"");
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        sPref = context.getSharedPreferences("Preferens", 0);
        sPref.edit().putInt("weekDay",presenterBase.getDayOfWeek()).commit();

        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {

        // Enter relevant functionality for when the last widget is disabled
    }

    void setUpdateTV(RemoteViews rv, Context context, int appWidgetId) {


        // нажатие left
        Intent countIntentLeft = new Intent(context, MyWidgetService_.class);
        countIntentLeft.setAction(ACTION_CHANGE_LEFT);
        countIntentLeft.putExtra("id", appWidgetId);
        PendingIntent pIntentLeft  = PendingIntent.getService(context, 0, countIntentLeft, 0);
        rv.setOnClickPendingIntent(R.id.btnLeft, pIntentLeft);

        // нажатие right
        Intent countIntentRight = new Intent(context, MyWidgetService_.class);
        countIntentRight.setAction(ACTION_CHANGE_RIGHT);
        countIntentRight.putExtra("id", appWidgetId);
        PendingIntent pIntentRight  = PendingIntent.getService(context, 0, countIntentRight, 0);
        rv.setOnClickPendingIntent(R.id.btnRight, pIntentRight);



    }

    void setList(RemoteViews rv, Context context, int appWidgetId) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int appWidgetIds[] = appWidgetManager.getAppWidgetIds(
                new ComponentName(context, MySchedule.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.lvList);

        Intent adapter = new Intent(context, MyService.class);

        adapter.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        rv.setRemoteAdapter(R.id.lvList, adapter);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        sPref = context.getSharedPreferences("Preferens", 0);
        ///left click
        if (intent.getAction().equalsIgnoreCase(ACTION_CHANGE_LEFT)) {

                sPref.edit().putInt("weekDay",sPref.getInt("weekDay",0) - 1).commit();
                if(sPref.getInt("weekDay",0) < 0){
                    sPref.edit().putInt("weekDay",6).commit();
                }

           // EventBus.getDefault().register(this);
            updateAppWidget(context, AppWidgetManager.getInstance(context),
                    intent.getIntExtra("idd",0));
        }


        ///right click
        if (intent.getAction().equalsIgnoreCase(ACTION_CHANGE_RIGHT)) {


            sPref.edit().putInt("weekDay",sPref.getInt("weekDay",0) + 1).commit();
            if(sPref.getInt("weekDay",0) > 6){
                sPref.edit().putInt("weekDay",0).commit();
            }

                Log.e("intentService","+"+ intent.getParcelableArrayListExtra("data").toString());

                // Обновляем виджет
                updateAppWidget(context, AppWidgetManager.getInstance(context),
                        intent.getIntExtra("idd",0));
        }

    }

}

