package ruslep.student_schedule.architecture.presenter;

import java.util.List;

import ruslep.student_schedule.architecture.model.entity.Subject;
import ruslep.student_schedule.architecture.model.entity.SubjectParcelable;

/**
 * Created by Ruslan on 20.02.2017.
 */

public interface PresenterWidget {
    List<Subject> getSchedule(String TypeOfWeek, int dayOfWeek);

    List<SubjectParcelable> convertSubject(List<Subject> subjects);
}
