package ruslep.student_schedule.architecture.presenter;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import ruslep.student_schedule.architecture.model.entity.Subject;
import ruslep.student_schedule.architecture.model.entity.SubjectParcelable;

/**
 * Created by Ruslan on 20.02.2017.
 */
@EBean
public class PresenterWidgetImpl implements PresenterWidget {
    List<Subject> subjects;

    @Bean(PresenterFragmentScheduleImpl.class)
    PresenterFragmentSchedule presenterFragmentSchedule;


    @Override
    public List<Subject> getSchedule(String typeOfWeek, int dayOfWeek) {
        subjects = presenterFragmentSchedule.getSubject(typeOfWeek, dayOfWeek);
        return subjects;
    }

    @Override
    public List<SubjectParcelable> convertSubject(List<Subject> subjects) {
        int i = 0;
        List<SubjectParcelable> subjectParcelablesList = new ArrayList<>();
        for(Subject tempSubject : subjects ){
            SubjectParcelable subjectParcelables = new SubjectParcelable().newBuilder()
                    .setId(tempSubject.getId())
                    .setSubjectNumber(tempSubject.getNumberSubject())
                    .setNameSubject(tempSubject.getNameSubject())
                    .setTypeSubject(tempSubject.getTypeSubject())
                    .setTeacherSubject(tempSubject.getTeacherSubject())
                    .setRoomSubject(tempSubject.getRoomSubject())
                    .setTimeStartSubject(tempSubject.getTimeStartSubject())
                    .setTimeEndSubject(tempSubject.getTimeEndSubject())
                    .setTypeOfWeek(tempSubject.getTypeWeek())
                    .setDayOfWeek(tempSubject.getDayOfWeek())
                    .build();
            subjectParcelablesList.add(subjectParcelables);
        }

        return subjectParcelablesList;
    }
}
