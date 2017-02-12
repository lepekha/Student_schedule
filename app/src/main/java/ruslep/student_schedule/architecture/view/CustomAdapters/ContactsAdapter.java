package ruslep.student_schedule.architecture.view.CustomAdapters;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import ruslep.student_schedule.architecture.model.entity.Contacts;
import ruslep.student_schedule.architecture.model.entity.Subject;


public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder>{


    private List<Contacts> contactsList = new ArrayList<>();


    OnItemMenuClickListener onItemMenuClickListener;

    ColorGenerator generator = ColorGenerator.MATERIAL;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private TextView txtName;
        private ImageView imgContactsName;
        private LinearLayout main_content;



        public ViewHolder(View holderView) {
            super(holderView);

            txtName = (TextView) holderView.findViewById(R.id.txtContactName);
            imgContactsName = (ImageView) holderView.findViewById(R.id.imgContactsName);
            main_content = (LinearLayout) holderView.findViewById(R.id.main_content);

            main_content.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onItemMenuClickListener.onItemMenuClick(view,contactsList.get(getPosition()), getPosition());
        }
    }


    public interface  OnItemMenuClickListener{
        public void onItemMenuClick(View view, Contacts contacts, int position);
    }

    public void SetOnItemMenuClick(final OnItemMenuClickListener onItemMenuClickListener){
        this.onItemMenuClickListener = onItemMenuClickListener;
    }



    public ContactsAdapter(List<Contacts> contactsList) {
        this.contactsList = contactsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        int color = generator.getColor(contactsList.get(position).getName());
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .textColor(Color.WHITE)
                .useFont(Typeface.DEFAULT)
                .width(50)  // width in px
                .height(50) // height in px
                .toUpperCase()
                .endConfig()
                .buildRoundRect(String.valueOf(contactsList.get(position).getName().charAt(0)),color,10);
        viewHolder.imgContactsName.setImageDrawable(drawable);
        viewHolder.txtName.setText(contactsList.get(position).getName());

    }


    @Override
    public int getItemCount() {
        return contactsList.size();
    }

}
