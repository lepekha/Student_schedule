package ruslep.student_schedule.architecture.model.entity;


import org.androidannotations.annotations.EBean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ruslan on 11.08.2016.
 */
@EBean
public class QuickEnter extends RealmObject {
    @PrimaryKey
    private int id;
    private int type;
    private String text;


    @Override
    public String toString() {
        return "QuickEnter{" +
                "id=" + id +
                ", type=" + type +
                ", text='" + text + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}


