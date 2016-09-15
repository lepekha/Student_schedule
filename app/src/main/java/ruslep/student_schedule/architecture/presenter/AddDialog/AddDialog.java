package ruslep.student_schedule.architecture.presenter.AddDialog;

import ruslep.student_schedule.architecture.model.entity.Subject;
import ruslep.student_schedule.architecture.view.View;

/**
 * Created by Ruslan on 24.08.2016.
 */
public interface AddDialog {
    void saveSubject(Subject subject);

    void setView(View view);
}
