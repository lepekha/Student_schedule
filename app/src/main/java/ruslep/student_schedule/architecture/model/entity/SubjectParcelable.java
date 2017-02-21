package ruslep.student_schedule.architecture.model.entity;


import android.os.Parcel;
import android.os.Parcelable;

import org.androidannotations.annotations.EBean;

import java.util.Comparator;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ruslan on 11.08.2016.
 */

public class SubjectParcelable implements Comparable<SubjectParcelable>,Parcelable {

    private int id;
    private String numberSubject;
    private String typeSubject;
    private String nameSubject;
    private String timeStartSubject;
    private String timeEndSubject;
    private String roomSubject;
    private String teacherSubject;
    private int orderSubject;
    private int dayOfWeek;
    private String typeOfWeek;



    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", numberSubject='" + numberSubject + '\'' +
                ", typeSubject='" + typeSubject + '\'' +
                ", nameSubject='" + nameSubject + '\'' +
                ", timeStartSubject='" + timeStartSubject + '\'' +
                ", timeEndSubject='" + timeEndSubject + '\'' +
                ", roomSubject='" + roomSubject + '\'' +
                ", teacherSubject='" + teacherSubject + '\'' +
                ", orderSubject=" + orderSubject +
                ", dayOfWeek=" + dayOfWeek +
                ", typeOfWeek='" + typeOfWeek + '\'' +
                '}';
    }


    public String getNumberSubject() {
        return numberSubject;
    }

    public void setNumberSubject(String numberSubject) {
        this.numberSubject = numberSubject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeSubject() {
        return typeSubject;
    }

    public void setTypeSubject(String typeSubject) {
        this.typeSubject = typeSubject;
    }

    public String getNameSubject() {
        return nameSubject;
    }

    public void setNameSubject(String nameSubject) {
        this.nameSubject = nameSubject;
    }

    public String getTimeEndSubject() {
        return timeEndSubject;
    }

    public void setTimeEndSubject(String timeEndSubject) {
        this.timeEndSubject = timeEndSubject;
    }

    public String getTimeStartSubject() {
        return timeStartSubject;
    }

    public void setTimeStartSubject(String timeStartSubject) {
        this.timeStartSubject = timeStartSubject;
    }

    public String getRoomSubject() {
        return roomSubject;
    }

    public void setRoomSubject(String roomSubject) {
        this.roomSubject = roomSubject;
    }

    public String getTeacherSubject() {
        return teacherSubject;
    }

    public void setTeacherSubject(String teacherSubject) {
        this.teacherSubject = teacherSubject;
    }

    public int getOrderSubject() {
        return orderSubject;
    }

    public void setOrderSubject(int orderSubject) {
        this.orderSubject = orderSubject;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getTypeWeek() {
        return typeOfWeek;
    }

    public void setTypeWeek(String typeWeek) {
        this.typeOfWeek = typeWeek;
    }



    public static Builder newBuilder() {
        return new SubjectParcelable().new Builder();
    }


    public class Builder {

        private Builder() {
            // private constructor
        }

        public Builder setId(int id) {
            SubjectParcelable.this.id = id;
            return this;
        }

        public Builder setSubjectNumber(String numberSubject) {
            SubjectParcelable.this.numberSubject = numberSubject;
            return this;
        }

        public Builder setTypeSubject(String typeSubject) {
            SubjectParcelable.this.typeSubject = typeSubject;
            return this;
        }

        public Builder setNameSubject(String nameSubject) {
            SubjectParcelable.this.nameSubject = nameSubject;
            return this;
        }

        public Builder setTimeStartSubject(String timeStartSubject) {
            SubjectParcelable.this.timeStartSubject = timeStartSubject;
            return this;
        }
        public Builder setTimeEndSubject(String timeEndSubject) {
            SubjectParcelable.this.timeEndSubject = timeEndSubject;
            return this;
        }

        public Builder setRoomSubject(String roomSubject) {
            SubjectParcelable.this.roomSubject = roomSubject;
            return this;
        }

        public Builder setTeacherSubject(String teacherSubject) {
            SubjectParcelable.this.teacherSubject = teacherSubject;
            return this;
        }

        public Builder setOrderSubject(int orderSubject) {
            SubjectParcelable.this.orderSubject = orderSubject;
            return this;
        }

        public Builder setDayOfWeek(int dayOfWeek) {
            SubjectParcelable.this.dayOfWeek = dayOfWeek;
            return this;
        }

        public Builder setTypeOfWeek(String typeOfWeek) {
            SubjectParcelable.this.typeOfWeek = typeOfWeek;
            return this;
        }

        public SubjectParcelable build() {
            return SubjectParcelable.this;
        }

    }

    @Override
    public int compareTo(SubjectParcelable subject) {
        return Comparators.NUMBER.compare(this, subject);
    }

    public static class Comparators {

        public static Comparator<SubjectParcelable> NUMBER = new Comparator<SubjectParcelable>() {
            @Override
            public int compare(SubjectParcelable o1, SubjectParcelable o2) {
                return Integer.valueOf(o1.numberSubject).compareTo(Integer.valueOf(o2.numberSubject));
            }
        };
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.numberSubject);
        dest.writeString(this.typeSubject);
        dest.writeString(this.nameSubject);
        dest.writeString(this.timeStartSubject);
        dest.writeString(this.timeEndSubject);
        dest.writeString(this.roomSubject);
        dest.writeString(this.teacherSubject);
        dest.writeInt(this.orderSubject);
        dest.writeInt(this.dayOfWeek);
        dest.writeString(this.typeOfWeek);
    }

    public SubjectParcelable() {
    }

    protected SubjectParcelable(Parcel in) {
        this.id = in.readInt();
        this.numberSubject = in.readString();
        this.typeSubject = in.readString();
        this.nameSubject = in.readString();
        this.timeStartSubject = in.readString();
        this.timeEndSubject = in.readString();
        this.roomSubject = in.readString();
        this.teacherSubject = in.readString();
        this.orderSubject = in.readInt();
        this.dayOfWeek = in.readInt();
        this.typeOfWeek = in.readString();
    }

    public static final Parcelable.Creator<SubjectParcelable> CREATOR = new Parcelable.Creator<SubjectParcelable>() {
        @Override
        public SubjectParcelable createFromParcel(Parcel source) {
            return new SubjectParcelable(source);
        }

        @Override
        public SubjectParcelable[] newArray(int size) {
            return new SubjectParcelable[size];
        }
    };
}


