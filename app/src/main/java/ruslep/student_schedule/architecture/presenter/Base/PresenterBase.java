package ruslep.student_schedule.architecture.presenter.Base;

import ruslep.student_schedule.architecture.view.BaseActivity;
import ruslep.student_schedule.architecture.view.View;

/**
 * Created by Lepekha.R.O on 25.08.2016.
 */
public interface PresenterBase {
    void setView(BaseActivity view);
    void changeTypeOfWeek();
    void updateTypeOfWeek(String typeOfWeek);
    void initTyteOfWeek();
    void review();
    void share();
    boolean auth(String phoneNumber);
    void hideAuthBtn();

    void setSchedule(String phoneMD5);
    void registerUser(boolean whereReturn,String phoneMD5);
    void getSchedule(String phoneMD5);

    String getMyPhone();
    void deletAllFromDB();
    void setTextTypeOfWeek();
    void startLoading();
    void endLoading();
    void setDrawerHeaderPhone();
    int getDayOfWeek();
    String getWeekName(int DayOfWeek);
    String getTextTuypeOfWeek();

}
