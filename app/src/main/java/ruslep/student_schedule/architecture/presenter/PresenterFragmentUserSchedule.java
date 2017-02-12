package ruslep.student_schedule.architecture.presenter;

import java.util.List;

import ruslep.student_schedule.architecture.model.entity.Subject;
import ruslep.student_schedule.architecture.model.entity.User;

/**
 * Created by Lepekha.R.O on 20.08.2016.
 */
public interface PresenterFragmentUserSchedule {

    List<User> getUser(String typeOfWeek, int dayOfWeek);

}

