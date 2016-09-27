package ruslep.student_schedule.architecture.presenter.QuickEnter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Lepekha.R.O on 25.08.2016.
 */
public interface PresenterQuickEnter {

    void setData(String text, int type);

    void setAllData(String name, String room, String teacher, String type, String time);

    ArrayList<String> getData(int type);

    void deleteData(int type);
}
