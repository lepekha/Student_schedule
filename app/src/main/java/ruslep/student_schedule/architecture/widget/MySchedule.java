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

import java.util.Calendar;

import ruslep.student_schedule.R;
import ruslep.student_schedule.architecture.model.Preferens.MyPreferens;
import ruslep.student_schedule.architecture.model.Preferens.MyPreferensImpl;
import ruslep.student_schedule.architecture.presenter.Base.PresenterBase;
import ruslep.student_schedule.architecture.presenter.Base.PresenterBaseImpl;

/**
 * Implementation of App Widget functionality.
 */
@EBean(scope = EBean.Scope.Singleton)
public class MySchedule extends AppWidgetProvider {

    PresenterBaseImpl presenterBase = new PresenterBaseImpl();
    int move = 0;

    final static String ACTION_CHANGE_LEFT = "ruslep.student_schedule.widget.left";
    final static String ACTION_CHANGE_RIGHT = "ruslep.student_schedule.widget.right";

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {

       // CharSequence widgetText = presenterBase.getWeekName(0);
        // Construct the RemoteViews object

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_schedule);
        views.setTextViewText(R.id.txtWeekName,context.getString(presenterBase.getWeekName(presenterBase.getDayOfWeek() + move)));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
            Log.d("TAG", "TAG1");
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.layout.widget_schedule);
            setList(views, context, appWidgetId);

        }
        setUpdateTV(views, context, appWidgetId);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

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
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    void setUpdateTV(RemoteViews rv, Context context, int appWidgetId) {

        // нажатие left
        Intent countIntentLeft = new Intent(context, MySchedule.class);
        countIntentLeft.setAction(ACTION_CHANGE_LEFT);
        countIntentLeft.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent pIntentLeft  = PendingIntent.getBroadcast(context, appWidgetId, countIntentLeft, 0);
        rv.setOnClickPendingIntent(R.id.btnLeft, pIntentLeft);

        // нажатие right
        Intent countIntentRight = new Intent(context, MySchedule.class);
        countIntentRight.setAction(ACTION_CHANGE_RIGHT);
        countIntentRight.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent pIntentRight  = PendingIntent.getBroadcast(context, appWidgetId, countIntentRight, 0);
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

        ///left click
        if (intent.getAction().equalsIgnoreCase(ACTION_CHANGE_LEFT)) {

            // извлекаем ID экземпляра
            int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
            Bundle extras = intent.getExtras();
            if (extras != null) {
                mAppWidgetId = extras.getInt(
                        AppWidgetManager.EXTRA_APPWIDGET_ID,
                        AppWidgetManager.INVALID_APPWIDGET_ID);

            }
            if (mAppWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {

               move--;
                // Обновляем виджет
                updateAppWidget(context, AppWidgetManager.getInstance(context),
                        mAppWidgetId);
            }
        }

        ///right click
        if (intent.getAction().equalsIgnoreCase(ACTION_CHANGE_RIGHT)) {

            // извлекаем ID экземпляра
            int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
            Bundle extras = intent.getExtras();
            if (extras != null) {
                mAppWidgetId = extras.getInt(
                        AppWidgetManager.EXTRA_APPWIDGET_ID,
                        AppWidgetManager.INVALID_APPWIDGET_ID);

            }
            if (mAppWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {

                move++;
                // Обновляем виджет
                updateAppWidget(context, AppWidgetManager.getInstance(context),
                        mAppWidgetId);
            }
        }
    }

}

