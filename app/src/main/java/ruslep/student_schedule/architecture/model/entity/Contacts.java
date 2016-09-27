package ruslep.student_schedule.architecture.model.entity;

import com.google.gson.annotations.SerializedName;

import org.androidannotations.annotations.EBean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ruslan on 26.09.2016.
 */
public class Contacts extends RealmObject {

    @PrimaryKey
    private int id;
    private String name;
    private String phone;

    @Override
    public String toString() {
        return "Contacts{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}