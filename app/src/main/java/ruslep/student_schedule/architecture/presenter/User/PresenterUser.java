package ruslep.student_schedule.architecture.presenter.User;

import ruslep.student_schedule.architecture.view.BaseActivity;
import ruslep.student_schedule.architecture.view.UserActivity;

/**
 * Created by Lepekha.R.O on 25.08.2016.
 */
public interface PresenterUser {
    void setView(UserActivity view);
    void changeTypeOfWeek();
    void initTyteOfWeek();
    void getSchedule(String phoneMD5);
    void updateTypeOfWeek(String typeOfWeek);
    void setTextTypeOfWeek();
    String getTextTuypeOfWeek();
    String getMyPhone();
    void deletAllFromDB();
    void startLoading();
    void endLoading();

}
