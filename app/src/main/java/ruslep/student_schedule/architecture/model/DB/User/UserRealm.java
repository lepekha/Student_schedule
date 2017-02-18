package ruslep.student_schedule.architecture.model.DB.User;


import java.util.List;

import ruslep.student_schedule.architecture.model.entity.Subject;
import ruslep.student_schedule.architecture.model.entity.User;

/**
 * Created by Ruslan on 21.08.2016.
 */
public interface UserRealm {

    User saveToDB(User user);

    List<User> getFromDB(String typeOfWeek, int dayOfWeek);

    List<User> getAllFromDB();

    void deleteAllFromDB();

    boolean saveAllToDB(List<User> userList);
}
