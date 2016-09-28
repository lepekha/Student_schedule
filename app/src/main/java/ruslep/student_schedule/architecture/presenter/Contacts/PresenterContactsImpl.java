package ruslep.student_schedule.architecture.presenter.Contacts;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
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
import org.androidannotations.annotations.RootContext;
import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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

    @Bean
    MD5 md5;

    private List<Contacts> conList;

    @Override
    public void setView(ContactsActivity view) {
        this.contactsActivity = view;
    }

    @Override
    public List<Contacts> getPhoneContacts() {
        List<Contacts> contactsList = new ArrayList<>();
        Contacts contacts = new Contacts();

        Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (phones.moveToNext())
        {
            //contacts.setId(phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)));
            contacts.setName(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
            /**получаем номер с записной книжки, чистим его и берем мд5*/
            contacts.setPhone(clearPhoneNumber(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER))));
            //contacts.setPhone(md5.getMD5(clearPhoneNumber(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)))));
            contactsList.add(contacts);
        }
        phones.close();
        Log.e("qaz","++++++"+contactsList.get(50).getPhone());
        Log.e("qaz","++++++"+contactsList.get(60).getPhone());
        Log.e("qaz","++++++"+contactsList.get(70).getPhone());
        Log.e("qaz","++++++"+contactsList.get(80).getPhone());
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
            Log.e("qaz","+");
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
        Log.e("qaz","++++++");
        model
                .getContacts(contactsMD5)
                .subscribe(new Observer<List<Contacts>>() {
                    @Override
                    public void onCompleted() {
                        Log.e("qaz","------");
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<Contacts> list) {
                        Log.e("qaz","------");
                        Log.e("qaz","------"+list.size());
                        conList = list;
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
    public List<Contacts> getContactsList() {
        return conList;
    }

    @Override
    public void showHolderView() {
        contactsActivity.showHolderView();
    }

    @Override
    public void hideHolderView() {
        contactsActivity.hideHolderView();
    }
}
