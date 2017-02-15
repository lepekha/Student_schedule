package ruslep.student_schedule.architecture.view.Custom_dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ItemClick;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import ruslep.student_schedule.R;
import ruslep.student_schedule.architecture.other.Event.PasteDataToEdit;
import ruslep.student_schedule.architecture.presenter.QuickEnter.PresenterQuickEnter;
import ruslep.student_schedule.architecture.presenter.QuickEnter.PresenterQuickEnterImpl;

/**
 * Created by Ruslan on 24.08.2016.
 */
@EFragment
public class Quick_enter_dialog extends DialogFragment {

    private ListView list;

    @Bean(PresenterQuickEnterImpl.class)
    PresenterQuickEnter presenterQuickEnter;

    @FragmentArg("quickEnterType")
    int quickEnterType;

    private ArrayList<String> quickEnterList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup conteiner, Bundle save){
        View view = inflater.inflate(R.layout.fragment_quick_enter_impl, conteiner);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        list = (ListView) view.findViewById(R.id.listView);

        quickEnterList = presenterQuickEnter.getData(quickEnterType);

        ArrayAdapter adp = new ArrayAdapter(getActivity(), R.layout.item_quick_enter, quickEnterList);
        if (!quickEnterList.isEmpty()) {
            list.setAdapter(adp);
        }

        return view;
    }

    @ItemClick(R.id.listView)
    public void itemClick(int position){
        EventBus.getDefault().post(new PasteDataToEdit(quickEnterList.get(position), quickEnterType));
        dismiss();
    }

    @Click(R.id.btnCancel)
    public void cancelClick(){
        dismiss();
    }

    @Click(R.id.btnClear)
    public void cancelClear(){
        presenterQuickEnter.deleteData(quickEnterType);
        dismiss();
    }

}