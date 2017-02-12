package ruslep.student_schedule.architecture.view;

import java.util.ArrayList;
import java.util.List;

import ruslep.student_schedule.architecture.model.entity.Contacts;

/**
 * Created by Ruslan on 11.08.2016.
 */
public interface ContactsActivity extends View {
    void setAdapter(List<Contacts> list);
    void showHolderView();
    void hideHolderView();

}
