package ruslep.student_schedule.architecture.presenter;

import ruslep.student_schedule.architecture.view.BaseActivity;

/**
 * Created by Ruslan on 21.08.2017.
 */

public interface Firebase {
    void setView(BaseActivity view);
    void auth();
    void exit();
    void delete();
}
