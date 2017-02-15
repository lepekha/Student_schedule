package ruslep.student_schedule.architecture.other;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.DefaultInt;
import org.androidannotations.annotations.sharedpreferences.DefaultLong;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

import ruslep.student_schedule.R;

/**
 * Created by Ruslan on 24.08.2016.
 */
@SharedPref(SharedPref.Scope.UNIQUE)
public interface MyPrefs {


    @DefaultInt(0)
    int id();

    @DefaultInt(0)
    int idQuickEnter();


    @DefaultString("Чисельник")
    String typeOfWeek();

    @DefaultInt(0)
    int day();

    @DefaultBoolean(false)
    boolean invertWeek();

    @DefaultBoolean(false)
    boolean flagAuth();

    @DefaultBoolean(false)
    boolean flagRegistration();

    @DefaultString("Вы не авторизированы")
    String MyPhoneNumber();

    @DefaultString("Последнее обновление: ")
    String ContactsLastUpdate();

}