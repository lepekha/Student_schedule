package ruslep.student_schedule.architecture.other.Event;

import java.util.List;

import ruslep.student_schedule.architecture.model.entity.Subject;

/**
 * Created by Lepekha.R.O on 25.08.2016.
 */

public class AddSubject {
    public final Subject message;

    public AddSubject(Subject message) {
        this.message = message;
    }
}
