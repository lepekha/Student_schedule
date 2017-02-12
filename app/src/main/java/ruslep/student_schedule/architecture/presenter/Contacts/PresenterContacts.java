package ruslep.student_schedule.architecture.presenter.Contacts;

import java.util.ArrayList;
import java.util.List;

import ruslep.student_schedule.architecture.model.entity.Contacts;
import ruslep.student_schedule.architecture.view.BaseActivity;
import ruslep.student_schedule.architecture.view.ContactsActivity;

/**
 * Created by Ruslan on 26.09.2016.
 */

public interface PresenterContacts {
    void setView(ContactsActivity view);
    List<Contacts> getPhoneContacts();
    String clearPhoneNumber(String oldPhone);
    String getJsonFromPhoneList(List<Contacts> contactsList);
    List<Contacts> getContactsList();
    void getContacts(String contactsMD5);

    ArrayList<String> getContactsName(List<Contacts> contactsList);

    void showHolderView();

    void hideHolderView();


}
