package ruslep.student_schedule.architecture.other.Event;

import android.view.View;

import ruslep.student_schedule.architecture.model.entity.Subject;

/**
 * Created by Lepekha.R.O on 25.08.2016.
 */

public class ItemMenuClick {
    public final View view;
    public final int IdSubject;

    public ItemMenuClick(int IdSubject, View view) {
        this.IdSubject = IdSubject;
        this.view = view;
    }
}
