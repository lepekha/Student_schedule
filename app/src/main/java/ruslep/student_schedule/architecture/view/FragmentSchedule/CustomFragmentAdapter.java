package ruslep.student_schedule.architecture.view.FragmentSchedule;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ruslep.student_schedule.R;
import ruslep.student_schedule.architecture.model.entity.Subject;
import ruslep.student_schedule.architecture.other.Event.ChangeTypeOfWeek;
import ruslep.student_schedule.architecture.other.Event.ItemMenuClick;


public class CustomFragmentAdapter extends RecyclerView.Adapter<CustomFragmentAdapter.ViewHolder>{


    private List<Subject> subjects = new ArrayList<>();


    OnItemMenuClickListener onItemMenuClickListener;

    ColorGenerator generator = ColorGenerator.MATERIAL;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private TextView txtNazvanie, txtAud, txtType, txtTime, txtName;
        private LinearLayout lineName, lineAud, lineTime;
        private ImageView imgSubjectNumber;
        private ImageView btnItemMenu;



        public ViewHolder(View holderView) {
            super(holderView);
            txtNazvanie = (TextView) holderView.findViewById(R.id.txtNazvanie);
            txtAud = (TextView) holderView.findViewById(R.id.txtAud);
            txtType = (TextView) holderView.findViewById(R.id.txtType);
            txtName = (TextView) holderView.findViewById(R.id.txtName);
            txtTime = (TextView) holderView.findViewById(R.id.txtTime);
            lineName = (LinearLayout) holderView.findViewById(R.id.lineName);
            lineTime = (LinearLayout) holderView.findViewById(R.id.lineTime);
            lineAud = (LinearLayout) holderView.findViewById(R.id.lineAud);
            imgSubjectNumber = (ImageView) holderView.findViewById(R.id.imgSubjectNumber);
            btnItemMenu = (ImageView) holderView.findViewById(R.id.btnItemMenu);

            btnItemMenu.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemMenuClickListener.onItemMenuClick(view,subjects.get(getPosition()), getPosition());
        }
    }


    public interface  OnItemMenuClickListener{
        public void onItemMenuClick(View view, Subject subject, int position);
    }

    public void SetOnItemMenuClick(final OnItemMenuClickListener onItemMenuClickListener){
        this.onItemMenuClickListener = onItemMenuClickListener;
    }



    public CustomFragmentAdapter(List<Subject> subjects) {
        Collections.sort(subjects, Subject.Comparators.NUMBER);
        this.subjects = subjects;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_schedule, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    public void refresh(){
        Collections.sort(this.subjects, Subject.Comparators.NUMBER);
        notifyDataSetChanged();
    }

    public void add(Subject subject){
        this.subjects.add(subject);
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        int color = generator.getColor(subjects.get(position).getNumberSubject());
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .textColor(Color.WHITE)
                .useFont(Typeface.DEFAULT)
                .width(50)  // width in px
                .height(50) // height in px
                .toUpperCase()
                .endConfig()
                .buildRoundRect(subjects.get(position).getNumberSubject(), color,10);
        viewHolder.imgSubjectNumber.setImageDrawable(drawable);
        viewHolder.txtNazvanie.setText(subjects.get(position).getNameSubject());

        /**проверка на пустоту поля Аудитория*/
        if(checkField(subjects.get(position).getRoomSubject())){
            viewHolder.lineTime.setVisibility(View.VISIBLE);
            viewHolder.txtAud.setText(subjects.get(position).getRoomSubject());
        }else{
            viewHolder.lineAud.setVisibility(View.GONE);
        }

        viewHolder.txtType.setText(subjects.get(position).getTypeSubject());

        /**проверка на пустоту поля Время*/
        if(!subjects.get(position).getTimeStartSubject().equals("Початок") || !subjects.get(position).getTimeEndSubject().equals("Кінець")){
            viewHolder.lineTime.setVisibility(View.VISIBLE);
            viewHolder.txtTime.setText(subjects.get(position).getTimeStartSubject()+" - "+subjects.get(position).getTimeEndSubject());
        }else{
            viewHolder.lineTime.setVisibility(View.GONE);
        }

        /**проверка на пустоту поля Имя преподавателя*/
        if(checkField(subjects.get(position).getTeacherSubject())){
            viewHolder.lineName.setVisibility(View.VISIBLE);
            viewHolder.txtName.setText(subjects.get(position).getTeacherSubject());
        }else{
            viewHolder.lineName.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public boolean checkField(String text){
        if(text.replaceAll("[\\s]+", "").length()!=0){
            return true;
        }else{
            return false;
        }
    }


}
