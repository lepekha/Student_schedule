package ruslep.student_schedule.architecture.presenter;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

import ruslep.student_schedule.architecture.model.entity.Subject;

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
}
