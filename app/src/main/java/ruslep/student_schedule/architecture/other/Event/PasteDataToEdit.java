package ruslep.student_schedule.architecture.other.Event;

import ruslep.student_schedule.architecture.model.entity.Subject;

/**
 * Created by Lepekha.R.O on 25.08.2016.
 */

public class PasteDataToEdit {
    public String text = null;
    public int type = 0;


    public PasteDataToEdit(String text, int type) {
        this.text = text;
        this.type = type;
    }
}
