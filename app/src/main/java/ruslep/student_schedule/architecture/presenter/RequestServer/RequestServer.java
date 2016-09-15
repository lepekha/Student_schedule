package ruslep.student_schedule.architecture.presenter.RequestServer;

/**
 * Created by Ruslan on 12.08.2016.
 */
public interface RequestServer {
    void setSchedule(String phoneMD5, String schedule);
    void registerUser(String phoneMD5);
    void getSchedule(String phoneMD5);
}
