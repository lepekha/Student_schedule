package ruslep.student_schedule.architecture.widget;

/**
 * Created with IntelliJ IDEA.
 * User: rusla_000
 * Date: 30.11.13
 * Time: 15:29
 * To change this template use File | Settings | File Templates.
 */

import android.content.Intent;
import android.widget.RemoteViewsService;

public class MyService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MyFactory(getApplicationContext(), intent);
    }

}