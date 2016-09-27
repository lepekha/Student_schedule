package ruslep.student_schedule.architecture.view.Custom_dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.Calendar;

import ruslep.student_schedule.R;
import ruslep.student_schedule.architecture.model.entity.Subject;
import ruslep.student_schedule.architecture.other.MyPrefs_;
import ruslep.student_schedule.architecture.presenter.AddDialog.AddDialog;
import ruslep.student_schedule.architecture.presenter.AddDialog.AddDialogImpl;
import ruslep.student_schedule.architecture.presenter.PresenterFragmentScheduleImpl;
import ruslep.student_schedule.architecture.view.BaseActivityImpl;

/**
 * Created by Ruslan on 24.08.2016.
 */
@EFragment
public class Edit_schedule_dialog extends Add_schedule_dialog {

    @ViewById
    EditText edSubjectName,edTypeSubject,edSubjectTeacher,edSubjectRoom,edSubjectNumber;

    @ViewById
    Button btnTimeStart, btnTimeEnd;

    @ViewById
    Spinner spTypeOfWeek;

    @Bean(AddDialogImpl.class)
    AddDialog addDialog;

    @ViewById
    Button btnAdd;

    @FragmentArg("id")
    int id;

    @Bean
    PresenterFragmentScheduleImpl presenterFragmentSchedule;

    @Pref
    MyPrefs_ myPrefs;


    Subject subject;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup conteiner, Bundle save){
        View view = inflater.inflate(R.layout.dialog_add_schedule, conteiner);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        return view;
    }

    @AfterViews
    public void afterView(){
        subject = presenterFragmentSchedule.getSubjectByID(id);
        btnAdd.setText("ЗБЕРЕГТИ");
        edSubjectName.setText(subject.getNameSubject());
        edSubjectNumber.setText(subject.getNumberSubject());
        edSubjectRoom.setText(subject.getRoomSubject());
        edSubjectTeacher.setText(subject.getTeacherSubject());
        edTypeSubject.setText(subject.getTypeSubject());
        btnTimeStart.setText(subject.getTimeStartSubject());
        btnTimeEnd.setText(subject.getTimeEndSubject());
        switch (subject.getTypeWeek()){
            case "Чисельник": spTypeOfWeek.setSelection(0);
                break;
            case "Знаменник": spTypeOfWeek.setSelection(1);
                break;
            case "Кожна неділя": spTypeOfWeek.setSelection(2);
                break;
            default:
                break;
        }

    }

    @Click(R.id.btnAdd)
    public void btnAddClick(){
        Subject sb = new Subject().newBuilder()
                .setId(subject.getId())
                .setSubjectNumber(edSubjectNumber.getText().toString())
                .setNameSubject(edSubjectName.getText().toString())
                .setTypeSubject(edTypeSubject.getText().toString())
                .setTeacherSubject(edSubjectTeacher.getText().toString())
                .setRoomSubject(edSubjectRoom.getText().toString())
                .setTimeStartSubject(btnTimeStart.getText().toString())
                .setTimeEndSubject(btnTimeEnd.getText().toString())
                .setTypeOfWeek(spTypeOfWeek.getSelectedItem().toString())
                .setDayOfWeek(myPrefs.day().get())
                .build();
        presenterFragmentSchedule.editSubject(sb);
        dismiss();
    }

    @Click(R.id.btnTimeStart)
    public void clickBtnTimeStart(){
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                        String time = hourOfDay+":"+minute;
                        btnTimeStart.setText(time);
                    }
                },
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );
        tpd.setOkText("OK");
        tpd.setCancelText("Відміна");
        tpd.show(getActivity().getFragmentManager(),"TimeStartDialogPicked");
    }

    @Click(R.id.btnTimeEnd)
    public void clickBtnTimeEnd(){
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                        String time = hourOfDay+":"+minute;
                        btnTimeEnd.setText(time);
                    }
                },
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );
        tpd.setOkText("OK");
        tpd.setCancelText("Відміна");
        tpd.show(getActivity().getFragmentManager(),"TimeEndDialogPicked");
    }

}