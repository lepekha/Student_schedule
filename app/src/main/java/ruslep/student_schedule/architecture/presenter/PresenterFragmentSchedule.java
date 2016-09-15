package ruslep.student_schedule.architecture.presenter;

import java.util.List;

import ruslep.student_schedule.architecture.model.entity.Subject;

/**
 * Created by Lepekha.R.O on 20.08.2016.
 */
public interface PresenterFragmentSchedule {

    List<Subject> getSubject(String typeOfWeek, int dayOfWeek);

    void deleteSubject(Subject subject, int position);

    void popupDeleteSubject(Subject subject);

    void popupCopySubject(Subject subject);

    void popupPasteSubject();

    Subject getSubjectByID(int id);

    void editSubject(Subject subject);

    boolean isCopy();

}

