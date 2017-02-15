package ruslep.student_schedule.architecture.view;

/**
 * Created by Ruslan on 11.08.2016.
 */
public interface UserActivity extends View {
    void showMessage(String text);
    void setTextTypeOfWeek(String typeOfWeek);
    String getTextTypeOfWeek();
    void showProgressBar();
    void hideProgressBar();

}
