package ruslep.student_schedule.architecture.view.Custom_dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
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

    private static final int SP_POS_CHESLITEL = 0;
    private static final int SP_POS_ZNAMENATEL = 1;
    private static final int SP_POS_ALLWEEK = 2;

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

    @StringRes(R.string.dialogEdit_btn_Save)
    String BTN_SAVE;

    @StringRes(R.string.baseActivity_typeOfWeek_Cheslitel)
    String CHESLITEL;

    @StringRes(R.string.baseActivity_typeOfWeek_Znamenatel)
    String ZNAMENATEL;

    @StringRes(R.string.dialogTime_btn_Ok)
    String TIME_DIALOG_OK;

    @StringRes(R.string.dialogTime_btn_Cancel)
    String TIME_DIALOG_CANCEL;

    @StringRes(R.string.dialogAdd_check_error_ok)
    String DIALOG_ADD_CHECK_ERROR_OK;

    @Bean
    PresenterFragmentScheduleImpl presenterFragmentSchedule;

    @Pref
    MyPrefs_ myPrefs;


    Subject subject;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup conteiner, Bundle save){
        View view = inflater.inflate(R.layout.dialog_add_schedule, conteiner);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(null);

        ImageButton ibtnSubjectName = (ImageButton)view.findViewById(R.id.ibtnSubjectName);
        DrawableCompat.setTint(ibtnSubjectName.getDrawable(), ContextCompat.getColor(getContext(), useTheme.getAccentColor()));

        ImageButton ibtnTypeSubject = (ImageButton)view.findViewById(R.id.ibtnTypeSubject);
        DrawableCompat.setTint(ibtnTypeSubject.getDrawable(), ContextCompat.getColor(getContext(), useTheme.getAccentColor()));

        ImageButton ibtnSubjectTeacher = (ImageButton)view.findViewById(R.id.ibtnSubjectTeacher);
        DrawableCompat.setTint(ibtnSubjectTeacher.getDrawable(), ContextCompat.getColor(getContext(), useTheme.getAccentColor()));

        ImageButton ibtnSubjectRoom = (ImageButton)view.findViewById(R.id.ibtnSubjectRoom);
        DrawableCompat.setTint(ibtnSubjectRoom.getDrawable(), ContextCompat.getColor(getContext(), useTheme.getAccentColor()));

        ImageButton ibtnTime = (ImageButton)view.findViewById(R.id.ibtnTime);
        DrawableCompat.setTint(ibtnTime.getDrawable(), ContextCompat.getColor(getContext(), useTheme.getAccentColor()));

        return view;
    }

    @AfterViews
    public void afterView(){
        subject = presenterFragmentSchedule.getSubjectByID(id);
        btnAdd.setText(BTN_SAVE);
        edSubjectName.setText(subject.getNameSubject());
        edSubjectNumber.setText(subject.getNumberSubject());
        edSubjectRoom.setText(subject.getRoomSubject());
        edSubjectTeacher.setText(subject.getTeacherSubject());
        edTypeSubject.setText(subject.getTypeSubject());
        btnTimeStart.setText(subject.getTimeStartSubject());
        btnTimeEnd.setText(subject.getTimeEndSubject());

        if(subject.getTypeWeek().equals(CHESLITEL)){
            spTypeOfWeek.setSelection(SP_POS_CHESLITEL);
        }else if (subject.getTypeWeek().equals(ZNAMENATEL)){
            spTypeOfWeek.setSelection(SP_POS_ZNAMENATEL);
        }else {
            spTypeOfWeek.setSelection(SP_POS_ALLWEEK);
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

        if(checkFields()){
            presenterFragmentSchedule.editSubject(sb);
            dismiss();
        }else{
            /** диалог если не введены номер предмета и его название*/
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(getActivity());
            builder.setMessage(DIALOG_ADD_CHECK_ERROR);
            builder.setPositiveButton(DIALOG_ADD_CHECK_ERROR_OK, null);
            builder.show();
        }
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
        tpd.setOkText(TIME_DIALOG_OK);
        tpd.setCancelText(TIME_DIALOG_CANCEL);
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
        tpd.setOkText(TIME_DIALOG_OK);
        tpd.setCancelText(TIME_DIALOG_CANCEL);
        tpd.show(getActivity().getFragmentManager(),"TimeEndDialogPicked");
    }
    public boolean checkFields(){
        if(edSubjectNumber.getText().toString().replaceAll("[\\s]+", "").length()!=0 && edSubjectName.getText().toString().replaceAll("[\\s]+", "").length()!=0){
            return true;
        }else{
            return false;
        }

    }
}