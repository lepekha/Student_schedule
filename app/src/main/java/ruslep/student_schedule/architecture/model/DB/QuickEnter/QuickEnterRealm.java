package ruslep.student_schedule.architecture.model.DB.QuickEnter;

import java.util.ArrayList;
import java.util.List;

import ruslep.student_schedule.architecture.model.entity.QuickEnter;

/**
 * Created by Ruslan on 21.08.2016.
 */
public interface QuickEnterRealm {

    void setData(String text, int type);
    ArrayList<String> getData(int type);

    void deleteData(int type);

}
