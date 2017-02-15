package ruslep.student_schedule.architecture.view.CustomAdapters;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ruslep.student_schedule.R;
import ruslep.student_schedule.architecture.model.entity.User;


public class CustomUserFragmentAdapter extends RecyclerView.Adapter<CustomUserFragmentAdapter.ViewHolder>{

    private static final int IMG_RADIUS = 10;

    private List<User> user = new ArrayList<>();


    private OnItemMenuClickListener onItemMenuClickListener;

    private ColorGenerator generator = ColorGenerator.MATERIAL;


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
            onItemMenuClickListener.onItemMenuClick(view, user.get(getPosition()), getPosition());
        }
    }


    public interface  OnItemMenuClickListener{
        public void onItemMenuClick(View view, User user, int position);
    }

    public void SetOnItemMenuClick(final OnItemMenuClickListener onItemMenuClickListener){
        this.onItemMenuClickListener = onItemMenuClickListener;
    }



    public CustomUserFragmentAdapter(List<User> user) {
        Collections.sort(user, User.Comparators.NUMBER);
        this.user = user;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_schedule, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    public void refresh(){
        Collections.sort(this.user, User.Comparators.NUMBER);
        notifyDataSetChanged();
    }

    public void add(User user){
        this.user.add(user);
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        int color = generator.getColor("abs" + user.get(position).getNumberSubject());
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .textColor(Color.WHITE)
                .useFont(Typeface.DEFAULT)
                .width(50)  // width in px
                .height(50) // height in px
                .toUpperCase()
                .endConfig()
                .buildRoundRect(user.get(position).getNumberSubject(), color,IMG_RADIUS);
        viewHolder.imgSubjectNumber.setImageDrawable(drawable);
        viewHolder.txtNazvanie.setText(user.get(position).getNameSubject());
        viewHolder.btnItemMenu.setVisibility(View.INVISIBLE);

        /**проверка на пустоту поля Аудитория*/
        if(checkField(user.get(position).getRoomSubject())){
            viewHolder.lineTime.setVisibility(View.VISIBLE);
            viewHolder.txtAud.setText(user.get(position).getRoomSubject());
        }else{
            viewHolder.lineAud.setVisibility(View.GONE);
        }

        viewHolder.txtType.setText(user.get(position).getTypeSubject());

        /**проверка на пустоту поля Время*/
        if(!user.get(position).getTimeStartSubject().equals(R.string.dialogAdd_timeStart) || !user.get(position).getTimeEndSubject().equals(R.string.dialogAdd_timeEnd)){
            viewHolder.lineTime.setVisibility(View.VISIBLE);
            viewHolder.txtTime.setText(user.get(position).getTimeStartSubject()+" - "+user.get(position).getTimeEndSubject());
        }else{
            viewHolder.lineTime.setVisibility(View.GONE);
        }

        /**проверка на пустоту поля Имя преподавателя*/
        if(checkField(user.get(position).getTeacherSubject())){
            viewHolder.lineName.setVisibility(View.VISIBLE);
            viewHolder.txtName.setText(user.get(position).getTeacherSubject());
        }else{
            viewHolder.lineName.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return user.size();
    }

    public boolean checkField(String text){
        if(text.replaceAll("[\\s]+", "").length()!=0){
            return true;
        }else{
            return false;
        }
    }


}
