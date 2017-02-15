package ruslep.student_schedule.architecture.view.Custom_dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
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

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;

import ruslep.student_schedule.R;
import ruslep.student_schedule.architecture.model.entity.Subject;
import ruslep.student_schedule.architecture.other.Const;
import ruslep.student_schedule.architecture.other.Event.PasteDataToEdit;
import ruslep.student_schedule.architecture.other.MyPrefs_;
import ruslep.student_schedule.architecture.presenter.AddDialog.AddDialog;
import ruslep.student_schedule.architecture.presenter.AddDialog.AddDialogImpl;
import ruslep.student_schedule.architecture.presenter.QuickEnter.PresenterQuickEnter;
import ruslep.student_schedule.architecture.presenter.QuickEnter.PresenterQuickEnterImpl;

/**
 * Created by Ruslan on 24.08.2016.
 */
@EFragment
public class Add_schedule_dialog extends DialogFragment {

    @ViewById
    EditText edSubjectName,edTypeSubject,edSubjectTeacher,edSubjectRoom,edSubjectNumber;

    @ViewById
    Button btnTimeStart, btnTimeEnd;

    @ViewById
    Spinner spTypeOfWeek;

    @Bean(AddDialogImpl.class)
    AddDialog addDialog;

    @Bean(PresenterQuickEnterImpl.class)
    PresenterQuickEnter presenterQuickEnter;

    @Pref
    MyPrefs_ myPrefs;

    @StringRes(R.string.dialogTime_btn_Ok)
    String TIME_DIALOG_OK;

    @StringRes(R.string.dialogTime_btn_Cancel)
    String TIME_DIALOG_CANCEL;

    @StringRes(R.string.dialogAdd_btn_OK)
    String DIALOG_ADD_OK;

    @StringRes(R.string.dialogAdd_check_error)
    String DIALOG_ADD_CHECK_ERROR;

    @StringRes(R.string.dialogAdd_check_error_ok)
    String DIALOG_ADD_CHECK_ERROR_OK;

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

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup conteiner, Bundle save){
        View view = inflater.inflate(R.layout.dialog_add_schedule, conteiner);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(null);
        return view;
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);

    }

    @Click(R.id.btnAdd)
    public void btnAddClick(){
        Subject subject = new Subject().newBuilder()
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
            presenterQuickEnter.setAllData(edSubjectName.getText().toString(),edSubjectRoom.getText().toString(),
                    edSubjectTeacher.getText().toString(), edTypeSubject.getText().toString(),btnTimeStart.getText().toString() + " - " + btnTimeEnd.getText().toString());
            addDialog.saveSubject(subject);
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

    @Click(R.id.btnCancel)
    public void btnCancelClick(){
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


    @Click(R.id.ibtnSubjectName)
    public void ibtnSubjectNameClick(){
        Quick_enter_dialog quick_enter_dialog = Quick_enter_dialog_.builder()
                .quickEnterType(Const.NAME_TYPE)
                .build();
        quick_enter_dialog.show(getActivity().getSupportFragmentManager(), "get_quickEnter");

    }

    @Click(R.id.ibtnTypeSubject)
    public void ibtnSubjectTypeClick(){
        Quick_enter_dialog quick_enter_dialog = Quick_enter_dialog_.builder()
                .quickEnterType(Const.TYPE_TYPE)
                .build();
        quick_enter_dialog.show(getActivity().getSupportFragmentManager(), "get_quickEnter");

    }

    @Click(R.id.ibtnSubjectTeacher)
    public void ibtnSubjectTeacherClick(){
        Quick_enter_dialog quick_enter_dialog = Quick_enter_dialog_.builder()
                .quickEnterType(Const.TEACHER_TYPE)
                .build();
        quick_enter_dialog.show(getActivity().getSupportFragmentManager(), "get_quickEnter");

    }

    @Click(R.id.ibtnSubjectRoom)
    public void ibtnSubjectRoomClick(){
        Quick_enter_dialog quick_enter_dialog = Quick_enter_dialog_.builder()
                .quickEnterType(Const.ROOM_TYPE)
                .build();
        quick_enter_dialog.show(getActivity().getSupportFragmentManager(), "get_quickEnter");

    }

    @Click(R.id.ibtnTime)
    public void ibtnSubjectTimeClick(){
        Quick_enter_dialog quick_enter_dialog = Quick_enter_dialog_.builder()
                .quickEnterType(Const.TIME_TYPE)
                .build();
        quick_enter_dialog.show(getActivity().getSupportFragmentManager(), "get_quickEnter");

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPasteDataToEdit(PasteDataToEdit event) {
       switch (event.type){
           case Const.NAME_TYPE: edSubjectName.setText(event.text);
               break;
           case Const.TYPE_TYPE: edTypeSubject.setText(event.text);
               break;
           case Const.TEACHER_TYPE: edSubjectTeacher.setText(event.text);
               break;
           case Const.ROOM_TYPE: edSubjectRoom.setText(event.text);
               break;
           case Const.TIME_TYPE:
               String[] time = event.text.split(" - ");
               btnTimeStart.setText(time[0]);
               btnTimeEnd.setText(time[1]);
               break;
           default:
               break;
       }
    }

    public boolean checkFields(){
        if(edSubjectNumber.getText().toString().replaceAll("[\\s]+", "").length()!=0 && edSubjectName.getText().toString().replaceAll("[\\s]+", "").length()!=0){
            return true;
        }else{
            return false;
        }

    }

}