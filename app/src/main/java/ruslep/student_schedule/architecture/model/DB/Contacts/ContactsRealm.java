package ruslep.student_schedule.architecture.model.DB.Contacts;

import java.util.List;

import ruslep.student_schedule.architecture.model.entity.Contacts;

/**
 * Created by Ruslan on 26.09.2016.
 */

public interface ContactsRealm {
    void setAllContacts(List<Contacts> contactsList);
    List<Contacts> getAllContacts();
    void deleteAllContacts();
}
