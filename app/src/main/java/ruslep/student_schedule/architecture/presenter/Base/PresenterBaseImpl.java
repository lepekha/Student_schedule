package ruslep.student_schedule.architecture.presenter.Base;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.greenrobot.eventbus.EventBus;
import ruslep.student_schedule.architecture.other.Event.ChangeTypeOfWeek;
import ruslep.student_schedule.architecture.other.MyPrefs_;

/**
 * Created by Lepekha.R.O on 25.08.2016.
 */
@EBean
public class PresenterBaseImpl implements PresenterBase {

    @Pref
    MyPrefs_ myPrefs;

    @Override
    public void changeTypeOfWeek(boolean typeOfWeek) {
        if(typeOfWeek) {
            myPrefs.typeOfWeek().put("Знаменник");
        }else{
            myPrefs.typeOfWeek().put("Чисельник");
        }

        EventBus.getDefault().post(new ChangeTypeOfWeek());
    }
}
