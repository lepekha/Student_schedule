package ruslep.student_schedule.architecture.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;
import java.util.List;

import ruslep.student_schedule.R;
import ruslep.student_schedule.architecture.model.entity.Contacts;
import ruslep.student_schedule.architecture.other.MyPrefs_;
import ruslep.student_schedule.architecture.presenter.Base.PresenterBaseImpl;
import ruslep.student_schedule.architecture.presenter.Contacts.PresenterContacts;
import ruslep.student_schedule.architecture.presenter.Contacts.PresenterContactsImpl;
import ruslep.student_schedule.architecture.view.CustomAdapters.ContactsAdapter;
import ruslep.student_schedule.architecture.view.FragmentSchedule.CustomFragmentAdapter;

@EActivity
public class ContactsActivityImpl extends AppCompatActivity implements ContactsActivity,ContactsAdapter.OnItemMenuClickListener {

    private ContactsAdapter adapter;
    private RecyclerView list;
    private RecyclerView.Adapter listAdapter;
    private RecyclerView.LayoutManager listManager;

    @ViewById(R.id.holderView)
    RelativeLayout holderView;


    @Bean(PresenterContactsImpl.class)
    PresenterContacts presenterContacts;


    @AfterInject
    public void afterView(){
        presenterContacts.setView(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        list = (RecyclerView) findViewById(R.id.listView);
        listManager = new LinearLayoutManager(this);
        list.setLayoutManager(listManager);
        getData();

    }


    @Override
    public void setAdapter(List<Contacts> listContacts) {
        presenterContacts.hideHolderView();
        if (!listContacts.isEmpty()) {
            adapter = new ContactsAdapter(listContacts);
            adapter.SetOnItemMenuClick(this);
            list.setAdapter(adapter);
        }
    }

    @Background
    public void getData() {
        presenterContacts.showHolderView();
        presenterContacts.getContacts(presenterContacts.getJsonFromPhoneList(presenterContacts.getPhoneContacts()));
    }

    @Override
    public void onItemMenuClick(View view, Contacts contacts, int position) {

    }

    @Override
    public void showHolderView() {
        list.setVisibility(View.GONE);
        holderView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideHolderView() {
        list.setVisibility(View.VISIBLE);
        holderView.setVisibility(View.GONE);
    }
}
