package ruslep.student_schedule.architecture.other;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.DefaultInt;
import org.androidannotations.annotations.sharedpreferences.DefaultLong;
import org.androidannotations.annotations.sharedpreferences.DefaultRes;
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


    @DefaultRes(R.string.baseActivity_typeOfWeek_Cheslitel)
    String typeOfWeek();

    @DefaultInt(0)
    int day();

    @DefaultInt(0)
    int userDay();

    @DefaultBoolean(false)
    boolean invertWeek();

    @DefaultBoolean(false)
    boolean reCreate();

    @DefaultBoolean(false)
    boolean flagAuth();

    @DefaultBoolean(false)
    boolean flagRegistration();

    @DefaultRes(R.string.setting_auth)
    String MyPhoneNumber();

    @DefaultRes(R.string.contacts_last_update)
    String ContactsLastUpdate();

    @DefaultInt(0)
    int currentTheme();

    @DefaultInt(0)
    int widgetWeek();

}