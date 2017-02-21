package ruslep.student_schedule.architecture.widget;

/**
 * Created with IntelliJ IDEA.
 * User: rusla_000
 * Date: 30.11.13
 * Time: 15:26
 * To change this template use File | Settings | File Templates.
 */

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import ruslep.student_schedule.R;
import ruslep.student_schedule.architecture.model.Preferens.MyPreferensImpl;


public class MyFactory extends BroadcastReceiver implements RemoteViewsFactory {

    Context context;
    int widgetID;



    MyFactory(Context ctx, Intent intent) {
        context = ctx;
        widgetID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public int getCount() {
       // return list_para.size();
        return 3;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.d("TAG", "getViewAt");
        RemoteViews rView = new RemoteViews(context.getPackageName(),
                R.layout.widget_schedule_item);
      /*  rView.setTextViewText(R.id.text3, list_para.get(position));
        rView.setTextViewText(R.id.text, list_name.get(position));
        rView.setTextViewText(R.id.text2, list_aud.get(position));
        rView.setTextViewText(R.id.textView2, list_timeStart.get(position)+"-"+list_time_End.get(position));

        Intent clickIntent = new Intent();
        clickIntent.putExtra(schedule.ITEM_POSITION, position);
        rView.setOnClickFillInIntent(R.id.text, clickIntent);
        rView.setOnClickFillInIntent(R.id.text3, clickIntent);
        rView.setOnClickFillInIntent(R.id.text2, clickIntent);
        rView.setOnClickFillInIntent(R.id.textView2, clickIntent);
        rView.setOnClickFillInIntent(R.id.men, clickIntent);
*/
        return rView;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onDataSetChanged() {

    }




    @Override
    public void onDestroy() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {

    }
}