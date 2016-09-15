package ruslep.student_schedule.architecture.other.Event;

import ruslep.student_schedule.architecture.model.entity.Subject;

/**
 * Created by Ruslan on 28.08.2016.
 */
public class DeleteSubject {
    public final int position;

    public DeleteSubject(int position) {
        this.position = position;
    }
}
