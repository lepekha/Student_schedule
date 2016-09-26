package ruslep.student_schedule.architecture.model.Preferens;

/**
 * Created by Lepekha.R.O on 18.09.2016.
 */
public interface MyPreferens {
    void setAuth(boolean auth);
    boolean getAuth();

    void setRegistrations(boolean registrations);
    boolean getRegistrations();

    void setPhoneNumber(String phoneNumber);
    String getPhoneNumber();

    void setInvertTypeOfWeek(boolean invertTypeOfWeek);
    boolean getInvertTypeOfWeek();

    void setTypeOfWeek(String typeOfWeek);
    String getTypeOfWeek();

    void setID(int id);
    int getID();
}
