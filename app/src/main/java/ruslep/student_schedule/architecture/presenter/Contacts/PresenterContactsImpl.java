package ruslep.student_schedule.architecture.presenter.Contacts;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.res.StringRes;
import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ruslep.student_schedule.R;
import ruslep.student_schedule.architecture.model.DB.Contacts.ContactsRealm;
import ruslep.student_schedule.architecture.model.DB.Contacts.ContactsRealmImpl;
import ruslep.student_schedule.architecture.model.Preferens.MyPreferens;
import ruslep.student_schedule.architecture.model.Preferens.MyPreferensImpl;
import ruslep.student_schedule.architecture.model.REST.Model;
import ruslep.student_schedule.architecture.model.REST.ModelImpl;
import ruslep.student_schedule.architecture.model.entity.Contacts;
import ruslep.student_schedule.architecture.model.entity.Subject;
import ruslep.student_schedule.architecture.other.Event.GetSubjectFromServer;
import ruslep.student_schedule.architecture.other.MD5;
import ruslep.student_schedule.architecture.view.ContactsActivity;
import rx.Observer;

/**
 * Created by Ruslan on 26.09.2016.
 */

@EBean(scope = EBean.Scope.Singleton)
public class PresenterContactsImpl implements  PresenterContacts {

    @RootContext
    Context context;

    private ContactsActivity contactsActivity;

    @Bean(ModelImpl.class)
    Model model;

    @Bean(ContactsRealmImpl.class)
    ContactsRealm contactsRealm;

    @Bean
    MD5 md5;

    @Bean(MyPreferensImpl.class)
    MyPreferens preferens;

    @StringRes(R.string.baseActivity_Schedule_error)
    String SCHEDULE_ERROR;

    @StringRes(R.string.contacts_last_update)
    String CONTACTS_LAST_UPDATE;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd  kk:mm");

    @Override
    public void setView(ContactsActivity view) {
        this.contactsActivity = view;
    }

    @Override
    public List<Contacts> getPhoneContacts() {
        List<Contacts> contactsList = new ArrayList<>();
        Contacts contacts;
        Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (phones.moveToNext())
        {
            try {
            contacts = new Contacts();
            contacts.setId(phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)));
            contacts.setName(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
            /**получаем номер с записной книжки, чистим его и берем мд5*/
            contacts.setPhone(clearPhoneNumber(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER))));
            contactsList.add(contacts);
            } catch (Exception e) {}
        }
        phones.close();
        return contactsList;
    }

    @Override
    public String clearPhoneNumber(String oldPhone) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String countryCode = tm.getNetworkCountryIso().toUpperCase();
        String newPhone = oldPhone.replace(" ","");
        newPhone = newPhone.replace("(","");
        newPhone = newPhone.replace(")","");
        newPhone = newPhone.replace("-","");

        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber swissNumberProto = null;
        try {
            swissNumberProto = phoneUtil.parse(newPhone, countryCode);
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }

       return phoneUtil.format(swissNumberProto, PhoneNumberUtil.PhoneNumberFormat.E164);
    }

    @Override
    public String getJsonFromPhoneList(List<Contacts> contactsList) {

        Gson gson = new Gson();
        return gson.toJson(contactsList);
    }


    @Override
    public void getContacts(String contactsMD5) {
        model
                .getContacts(contactsMD5)
                .subscribe(new Observer<List<Contacts>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        contactsActivity.showMessage(SCHEDULE_ERROR);
                        contactsActivity.hideHolderView();
                    }

                    @Override
                    public void onNext(List<Contacts> list) {
                        setContactList(list);
                        preferens.setContactsLastUpdate(CONTACTS_LAST_UPDATE +" " + simpleDateFormat.format(new Date()));
                        contactsActivity.setAdapter(list);
                    }
                });
    }

    @Override
    public ArrayList<String> getContactsName(List<Contacts> contactsList) {
        ArrayList<String> arrayList = new ArrayList<>();
        for(Contacts c: contactsList){
            arrayList.add(c.getName());
        }
        return arrayList;
    }

    @Override
    public boolean checkContacts() {
        if(getContactList().size()>0){
            contactsActivity.setTimeAndDate(preferens.getContactsLastUpdate());
            contactsActivity.setAdapter(getContactList());
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void showHolderView() {
        contactsActivity.showHolderView();
    }

    @Override
    public void hideHolderView() {
        contactsActivity.hideHolderView();
    }

    List<Contacts> getContactList(){
        return contactsRealm.getAllContacts();
    }

    void setContactList(List<Contacts> conList){
        contactsActivity.setTimeAndDate(preferens.getContactsLastUpdate());
        contactsRealm.deleteAllContacts();
        contactsRealm.setAllContacts(conList);

    }



}
