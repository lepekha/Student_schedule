package ruslep.student_schedule.architecture.presenter.QuickEnter;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ruslep.student_schedule.architecture.model.DB.QuickEnter.QuickEnterRealm;
import ruslep.student_schedule.architecture.model.DB.QuickEnter.QuickEnterRealmImpl;
import ruslep.student_schedule.architecture.other.Const;

/**
 * Created by Lepekha.R.O on 25.08.2016.
 */
@EBean
public class PresenterQuickEnterImpl implements PresenterQuickEnter {

    @Bean(QuickEnterRealmImpl.class)
    QuickEnterRealm quickEnterRealm;

    @Override
    public void setData(String text, int type) {
        quickEnterRealm.setData(text, type);
    }

    @Override
    public void setAllData(String name, String room, String teacher, String type, String time) {
        setData(name , Const.NAME_TYPE);
        setData(type, Const.TYPE_TYPE);
        setData(teacher, Const.TEACHER_TYPE);
        setData(room, Const.ROOM_TYPE);
        setData(time, Const.TIME_TYPE);
    }

    @Override
    public ArrayList<String> getData(int type) {
        return quickEnterRealm.getData(type);
    }

    @Override
    public void deleteData(int type) {
        quickEnterRealm.deleteData(type);
    }
}


