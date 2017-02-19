package ruslep.student_schedule.architecture.model.Preferens;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;

import ruslep.student_schedule.R;
import ruslep.student_schedule.architecture.other.MyPrefs_;

/**
 * Created by Lepekha.R.O on 18.09.2016.
 */
@EBean(scope = EBean.Scope.Singleton)
public class MyPreferensImpl implements MyPreferens {

    @StringRes(R.string.navigation_drawer_not_auth)
    String DRAWER_NOT_AUTH;

    @Pref
    MyPrefs_ myPrefs;

    @Override
    public void setAuth(boolean auth) {
        myPrefs.flagAuth().put(auth);
    }

    @Override
    public boolean getAuth() {
        return myPrefs.flagAuth().get();
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        myPrefs.MyPhoneNumber().put(phoneNumber);
    }

    @Override
    public String getPhoneNumber() {
        return myPrefs.MyPhoneNumber().getOr(DRAWER_NOT_AUTH);
    }

    @Override
    public void setInvertTypeOfWeek(boolean invertTypeOfWeek) {
        myPrefs.invertWeek().put(invertTypeOfWeek);
    }

    @Override
    public boolean getInvertTypeOfWeek() {
        return myPrefs.invertWeek().get();
    }

    @Override
    public void setTypeOfWeek(String typeOfWeek) {
        myPrefs.typeOfWeek().put(typeOfWeek);
    }

    @Override
    public String getTypeOfWeek() {
        return myPrefs.typeOfWeek().get();
    }

    @Override
    public void setRegistrations(boolean registrations) {
        myPrefs.flagRegistration().put(registrations);
    }

    @Override
    public boolean getRegistrations() {
        return myPrefs.flagRegistration().get();
    }

    @Override
    public void setID(int id) {
        myPrefs.id().put(id);
    }

    @Override
    public int getID() {
        return myPrefs.id().get();
    }

    @Override
    public void setContactsLastUpdate(String TimeAndDate) {
        myPrefs.ContactsLastUpdate().put(TimeAndDate);
    }

    @Override
    public String getContactsLastUpdate() {
        return myPrefs.ContactsLastUpdate().get();
    }

    @Override
    public void setReCreateMainActivity(boolean reCreate) {
        myPrefs.reCreate().put(reCreate);
    }

    @Override
    public boolean getReCreateMainActivity() {
        return myPrefs.reCreate().get();
    }

    @Override
    public void setDay(int day) {
        myPrefs.day().put(day);
    }

    @Override
    public int getDay() {
        return myPrefs.day().get();
    }

    @Override
    public void setCurrentTheme(int theme) {
        myPrefs.currentTheme().put(theme);
    }

    @Override
    public int getCurrentTheme() {
        return myPrefs.currentTheme().get();
    }

    @Override
    public void setUserDay(int day) {
        myPrefs.userDay().put(day);
    }

    @Override
    public int getUserDay() {
        return myPrefs.userDay().get();
    }
}
