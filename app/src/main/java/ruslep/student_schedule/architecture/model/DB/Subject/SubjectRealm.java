package ruslep.student_schedule.architecture.model.DB.Subject;


import java.util.List;

import ruslep.student_schedule.architecture.model.entity.Subject;
import ruslep.student_schedule.architecture.model.entity.User;

/**
 * Created by Ruslan on 21.08.2016.
 */
public interface SubjectRealm {

    Subject saveToDB(Subject subject);

    List<Subject> getFromDB(String typeOfWeek, int dayOfWeek);

    void deleteSubject(Subject subject);

    Subject getSubjectByID(int id);

    void editSubject(Subject subject);

    List<Subject> getAllFromDB();

    void deleteAllFromDB();

    void saveAllToDB(List<Subject> subjectList);

    void saveAllFromUser(List <User> userList);
}
