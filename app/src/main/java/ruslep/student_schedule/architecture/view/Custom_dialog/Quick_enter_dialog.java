package ruslep.student_schedule.architecture.view.Custom_dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ruslep.student_schedule.R;
import ruslep.student_schedule.architecture.model.entity.Subject;
import ruslep.student_schedule.architecture.other.Const;
import ruslep.student_schedule.architecture.other.Event.ChangeTypeOfWeek;
import ruslep.student_schedule.architecture.other.Event.PasteDataToEdit;
import ruslep.student_schedule.architecture.other.MyPrefs_;
import ruslep.student_schedule.architecture.presenter.AddDialog.AddDialog;
import ruslep.student_schedule.architecture.presenter.AddDialog.AddDialogImpl;
import ruslep.student_schedule.architecture.presenter.QuickEnter.PresenterQuickEnter;
import ruslep.student_schedule.architecture.presenter.QuickEnter.PresenterQuickEnterImpl;
import ruslep.student_schedule.architecture.view.FragmentSchedule.CustomFragmentAdapter;

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