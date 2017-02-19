package ruslep.student_schedule.architecture.view.FragmentUserSchedule;


import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import ruslep.student_schedule.R;
import ruslep.student_schedule.architecture.model.Preferens.MyPreferens;
import ruslep.student_schedule.architecture.model.Preferens.MyPreferensImpl;
import ruslep.student_schedule.architecture.model.entity.User;
import ruslep.student_schedule.architecture.other.Event.ChangeTypeOfWeek;
import ruslep.student_schedule.architecture.other.Event.GetSubjectFromServer;
import ruslep.student_schedule.architecture.other.Event.GetUserFromServer;
import ruslep.student_schedule.architecture.other.MyPrefs_;
import ruslep.student_schedule.architecture.presenter.PresenterFragmentUserScheduleImpl;
import ruslep.student_schedule.architecture.presenter.User.PresenterUser;
import ruslep.student_schedule.architecture.presenter.User.PresenterUserImpl;
import ruslep.student_schedule.architecture.view.CustomAdapters.CustomMyFragmentAdapter;
import ruslep.student_schedule.architecture.view.CustomAdapters.CustomUserFragmentAdapter;

@EFragment
public class FragmentScheduleImpl extends Fragment implements FragmentScheduleView{

    private static final String EDIT_DIALOG_TAG = "edit_dialog_tag";

    private CustomUserFragmentAdapter adapter;
    private RecyclerView list;
    private RecyclerView.Adapter listAdapter;
    private RecyclerView.LayoutManager listManager;


    private LinearLayout empty_view;

    @FragmentArg("currentPage")
    int currentPage;

    @Bean(PresenterUserImpl.class)
    PresenterUser presenterUser;

    @Bean
    PresenterFragmentUserScheduleImpl presenterFragmentUserSchedule;

    private List<User> user = null;

    @ViewById(R.id.main_content)
    CoordinatorLayout coordinatorLayout;


    @Bean(MyPreferensImpl.class)
    MyPreferens preferens;

    public FragmentScheduleImpl() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void setPlaceholder(){
        if (user.isEmpty()) {
            list.setVisibility(View.GONE);
            empty_view.setVisibility(View.VISIBLE);
        }
        else {
            list.setVisibility(View.VISIBLE);
            empty_view.setVisibility(View.GONE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule_impl, container, false);

        empty_view = (LinearLayout) view.findViewById(R.id.empty_view);
        list=(RecyclerView) view.findViewById(R.id.listView);
        listManager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(listManager);

        user = presenterFragmentUserSchedule.getUser(presenterUser.getTextTuypeOfWeek(),currentPage);

        setPlaceholder();
        //if (!subjects.isEmpty()) {
            adapter = new CustomUserFragmentAdapter(user);
            list.setAdapter(adapter);
       // }
        return view;
    }


    /** событие изменение типа недели*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeTypeOfWeek(ChangeTypeOfWeek event) {
        if(currentPage == preferens.getUserDay()) {
            user.clear();
            if (!presenterFragmentUserSchedule.getUser(presenterUser.getTextTuypeOfWeek(), preferens.getUserDay()).isEmpty()) {
                user.addAll(presenterFragmentUserSchedule.getUser(presenterUser.getTextTuypeOfWeek(), preferens.getUserDay()));
                setPlaceholder();
                adapter = new CustomUserFragmentAdapter(user);
                list.setAdapter(adapter);
            }else{
                setPlaceholder();
            }
        }
    }

    /**событие загрузки занятий с сервера*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetUserFromServer(GetUserFromServer event) {
        if(currentPage == preferens.getUserDay()) {
            if (user.isEmpty()) {
                user.clear();
                user.addAll(presenterFragmentUserSchedule.getUser(presenterUser.getTextTuypeOfWeek(), preferens.getUserDay()));
                setPlaceholder();
                adapter = new CustomUserFragmentAdapter(user);
                list.setAdapter(adapter);
            } else {
                user.clear();
                user.addAll(presenterFragmentUserSchedule.getUser(presenterUser.getTextTuypeOfWeek(), preferens.getUserDay()));
                adapter.refresh();
            }
        }
    }
}
