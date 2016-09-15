package ruslep.student_schedule.architecture.model.entity;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ruslan on 11.08.2016.
 */
public class Subject extends RealmObject {
    @PrimaryKey
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
        return new Subject().new Builder();
    }

    public class Builder {

        private Builder() {
            // private constructor
        }

        public Builder setId(int id) {
            Subject.this.id = id;
            return this;
        }

        public Builder setSubjectNumber(String numberSubject) {
            Subject.this.numberSubject = numberSubject;
            return this;
        }

        public Builder setTypeSubject(String typeSubject) {
            Subject.this.typeSubject = typeSubject;
            return this;
        }

        public Builder setNameSubject(String nameSubject) {
            Subject.this.nameSubject = nameSubject;
            return this;
        }

        public Builder setTimeStartSubject(String timeStartSubject) {
            Subject.this.timeStartSubject = timeStartSubject;
            return this;
        }
        public Builder setTimeEndSubject(String timeEndSubject) {
            Subject.this.timeEndSubject = timeEndSubject;
            return this;
        }

        public Builder setRoomSubject(String roomSubject) {
            Subject.this.roomSubject = roomSubject;
            return this;
        }

        public Builder setTeacherSubject(String teacherSubject) {
            Subject.this.teacherSubject = teacherSubject;
            return this;
        }

        public Builder setOrderSubject(int orderSubject) {
            Subject.this.orderSubject = orderSubject;
            return this;
        }

        public Builder setDayOfWeek(int dayOfWeek) {
            Subject.this.dayOfWeek = dayOfWeek;
            return this;
        }

        public Builder setTypeOfWeek(String typeOfWeek) {
            Subject.this.typeOfWeek = typeOfWeek;
            return this;
        }

        public Subject build() {
            return Subject.this;
        }

    }

}


