package ruslep.student_schedule.architecture.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import ruslep.student_schedule.R;
import ruslep.student_schedule.architecture.model.entity.Contacts;
import ruslep.student_schedule.architecture.presenter.Contacts.PresenterContacts;
import ruslep.student_schedule.architecture.presenter.Contacts.PresenterContactsImpl;
import ruslep.student_schedule.architecture.view.CustomAdapters.ContactsAdapter;

@EActivity
public class ContactsActivityImpl extends AppCompatActivity implements ContactsActivity,ContactsAdapter.OnItemMenuClickListener {

    private ContactsAdapter adapter;
    private RecyclerView list;
    private RecyclerView.Adapter listAdapter;
    private RecyclerView.LayoutManager listManager;

    @ViewById(R.id.holderView)
    RelativeLayout holderView;

    @ViewById(R.id.txtLastUpdate)
    TextView txtLastUpdate;



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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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


    public void getData() {
        presenterContacts.showHolderView();
        if(presenterContacts.checkContacts() == false) {
            presenterContacts.getContacts(presenterContacts.getJsonFromPhoneList(presenterContacts.getPhoneContacts()));
        }
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

    @Override
    public void onItemMenuClick(View view, Contacts contacts, int position) {
        Intent openUser = new Intent(this, UserActivityImpl_.class);
        openUser.putExtra("name",contacts.getName());
        openUser.putExtra("phone",contacts.getPhone());
        startActivity(openUser);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contacts, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.update_contacts:
                presenterContacts.showHolderView();
                presenterContacts.getContacts(presenterContacts.getJsonFromPhoneList(presenterContacts.getPhoneContacts()));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setTimeAndDate(String TimeAndDate) {
        txtLastUpdate.setText(TimeAndDate);
    }
}
