package ruslep.student_schedule.architecture.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EBean;

import ruslep.student_schedule.R;
import ruslep.student_schedule.architecture.presenter.Base.PresenterBase;
import ruslep.student_schedule.architecture.presenter.Base.PresenterBaseImpl;

/**
 * Implementation of App Widget functionality.
 */
@EBean(scope = EBean.Scope.Singleton)
public class MySchedule extends AppWidgetProvider {

    @Bean(PresenterBaseImpl.class)
    PresenterBase presenterBase;

    final static String ACTION_CHANGE_LEFT = "ruslep.student_schedule.widget.left";
    final static String ACTION_CHANGE_RIGHT = "ruslep.student_schedule.widget.right";

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {

       // CharSequence widgetText = presenterBase.getWeekName(0);
        // Construct the RemoteViews object

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_schedule);
        views.setTextViewText(R.id.txtWeekName,presenterBase.getWeekName(0));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Log.e("sdfsdf",presenterBase.getWeekName(0)+"");
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



}

