package ruslep.student_schedule.architecture.view.FragmentSchedule;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import ruslep.student_schedule.R;
import ruslep.student_schedule.architecture.model.entity.Subject;
import ruslep.student_schedule.architecture.other.Event.AddSubject;
import ruslep.student_schedule.architecture.other.Event.ChangeTypeOfWeek;
import ruslep.student_schedule.architecture.other.Event.DeleteSubject;
import ruslep.student_schedule.architecture.other.Event.EditSubject;
import ruslep.student_schedule.architecture.other.Event.GetSubjectFromServer;
import ruslep.student_schedule.architecture.other.Event.ItemMenuClick;
import ruslep.student_schedule.architecture.other.Event.PasteSubject;
import ruslep.student_schedule.architecture.other.MyPrefs_;
import ruslep.student_schedule.architecture.presenter.Base.PresenterBase;
import ruslep.student_schedule.architecture.presenter.Base.PresenterBaseImpl;
import ruslep.student_schedule.architecture.presenter.PresenterFragmentScheduleImpl;
import ruslep.student_schedule.architecture.view.Custom_dialog.Edit_schedule_dialog;
import ruslep.student_schedule.architecture.view.Custom_dialog.Edit_schedule_dialog_;

@EFragment
public class FragmentScheduleImpl extends Fragment implements FragmentScheduleView, CustomFragmentAdapter.OnItemMenuClickListener{

    private CustomFragmentAdapter adapter;
    private RecyclerView list;
    private RecyclerView.Adapter listAdapter;
    private RecyclerView.LayoutManager listManager;

    private Menu menu;

    private LinearLayout empty_view;

    @FragmentArg("currentPage")
    int currentPage;

    @Bean(PresenterBaseImpl.class)
    PresenterBase presenterBase;

    @Bean
    PresenterFragmentScheduleImpl presenterFragmentSchedule;

    private List<Subject> subjects = null;

    @ViewById(R.id.main_content)
    CoordinatorLayout coordinatorLayout;


    @Pref
    MyPrefs_ myPrefs;

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
        if (subjects.isEmpty()) {
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

        subjects = presenterFragmentSchedule.getSubject(presenterBase.getTextTuypeOfWeek(),currentPage);
        setPlaceholder();
        Log.e("zzz",subjects.size()+""+myPrefs.typeOfWeek().get()+" +"+currentPage);
        if (!subjects.isEmpty()) {
            adapter = new CustomFragmentAdapter(subjects);
            adapter.SetOnItemMenuClick(this);
            list.setAdapter(adapter);
        }
        return view;
    }


    /** событие изменение типа недели*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeTypeOfWeek(ChangeTypeOfWeek event) {
        if(currentPage == myPrefs.day().get()) {
            subjects.clear();
            if (!presenterFragmentSchedule.getSubject(presenterBase.getTextTuypeOfWeek(), myPrefs.day().get()).isEmpty()) {
                subjects.addAll(presenterFragmentSchedule.getSubject(presenterBase.getTextTuypeOfWeek(), myPrefs.day().get()));
                setPlaceholder();
                adapter.refresh();
            }
        }
    }

   /**  событие нажатие на меню в списке */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onItemMenuClick(ItemMenuClick event) {
        Log.e("rty","sdfgsd");
    }


    /**событие добавление занятия*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AddSubject event) {
        if(currentPage == myPrefs.day().get()) {
            if (subjects.isEmpty()) {
                subjects.clear();
                subjects.addAll(presenterFragmentSchedule.getSubject(presenterBase.getTextTuypeOfWeek(), myPrefs.day().get()));
                setPlaceholder();
                adapter = new CustomFragmentAdapter(subjects);
                adapter.SetOnItemMenuClick(this);
                list.setAdapter(adapter);
            } else {
                subjects.clear();
                subjects.addAll(presenterFragmentSchedule.getSubject(presenterBase.getTextTuypeOfWeek(), myPrefs.day().get()));
                adapter.refresh();
            }
        }
    }

    /**событие изменения занятия*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEditSubject(EditSubject event) {
        if(currentPage == myPrefs.day().get()) {
            subjects.clear();
            subjects.addAll(presenterFragmentSchedule.getSubject(presenterBase.getTextTuypeOfWeek(), myPrefs.day().get()));
            adapter.refresh();
        }
    }

    /**событие загрузки занятий с сервера*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetSubjectFromServer(GetSubjectFromServer event) {
        if(currentPage == myPrefs.day().get()) {
            if (subjects.isEmpty()) {
                subjects.clear();
                subjects.addAll(presenterFragmentSchedule.getSubject(presenterBase.getTextTuypeOfWeek(), myPrefs.day().get()));
                setPlaceholder();
                adapter = new CustomFragmentAdapter(subjects);
                adapter.SetOnItemMenuClick(this);
                list.setAdapter(adapter);
            } else {
                subjects.clear();
                subjects.addAll(presenterFragmentSchedule.getSubject(presenterBase.getTextTuypeOfWeek(), myPrefs.day().get()));
                adapter.refresh();
            }
        }
    }

    /***событие удаления занятия*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeleteSubject(DeleteSubject event) {
        if(currentPage == myPrefs.day().get()) {
            List<Subject> ls = new ArrayList<>();
            ls.addAll(subjects);
            subjects.clear();
            subjects.addAll(deleteElement(ls, event.position));
            adapter.refresh();
            setPlaceholder();
        }
    }

    /**событие вставка занятия после копирования*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPasteSubject(PasteSubject event) {
        if(currentPage == myPrefs.day().get()) {
            if (subjects.isEmpty()) {
                subjects.clear();
                subjects.addAll(presenterFragmentSchedule.getSubject(presenterBase.getTextTuypeOfWeek(), myPrefs.day().get()));
                setPlaceholder();
                adapter = new CustomFragmentAdapter(subjects);
                adapter.SetOnItemMenuClick(this);
                list.setAdapter(adapter);
            } else {
                subjects.clear();
                subjects.addAll(presenterFragmentSchedule.getSubject(presenterBase.getTextTuypeOfWeek(), myPrefs.day().get()));
                adapter.refresh();
            }
        }
    }

    /**удаление елемента со списка*/
    public List<Subject> deleteElement(List<Subject> subjects, int position) {
        List<Subject> ls = new ArrayList<>();
        for (int i = 0; i < subjects.size(); i++) {
            if (i != position) {
                ls.add(subjects.get(i));
            }
        }
        return ls;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.paste_subject:
                presenterFragmentSchedule.popupPasteSubject();
                break;
            default:
                break;
        }

        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        this.menu = menu;
        if(!presenterFragmentSchedule.isCopy()) {
            menu.findItem(R.id.paste_subject).setVisible(false);
        }
    }

    @Override
    public void onItemMenuClick(View view, Subject subject, int position) {
        try {
            //создание попут меня при нажатии на елемент списка
            PopupMenu popupMenu = new PopupMenu(getActivity(), view, Gravity.CENTER_HORIZONTAL);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.delete_subject:
                            /** диалог подтверждения удаления сообщения*/
                            AlertDialog.Builder builder =
                                    new AlertDialog.Builder(getActivity());
                            builder.setMessage("Вы действительно хотите удалить это занятие?");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    presenterFragmentSchedule.deleteSubject(subject,position);
                                }
                            });
                            builder.setNegativeButton("ОТМЕНА", null);
                            builder.show();
                            return true;
                        case R.id.copy_subject:
                            presenterFragmentSchedule.popupCopySubject(subject);
                            menu.findItem(R.id.paste_subject).setVisible(true);
                            return true;
                        case R.id.edit_subject:
                            Edit_schedule_dialog edit_schedule_dialog = Edit_schedule_dialog_.builder()
                                    .id(subject.getId())
                                    .build();
                            edit_schedule_dialog.show(getActivity().getSupportFragmentManager(), "add_subject");
                            return true;
                        default:
                            return true;
                    }
                }
            });
            popupMenu.inflate(R.menu.popup_menu);
            /**если обьект не скопирован, отключить пункт меню ВСТАВИТЬ*/
            popupMenu.show();
        }catch (Exception e){

        }
    }
}
