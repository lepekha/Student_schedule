package ruslep.student_schedule.architecture.model.entity;

import com.orm.SugarRecord;

/**
 * Created by Ruslan on 11.08.2016.
 */
public class Subject extends SugarRecord {
    private Long id;
    private String phoneMD5;
    private boolean status;
    private String shedule;

    public Subject(Long id, String phoneMD5, boolean status, String shedule) {
        this.id = id;
        this.phoneMD5 = phoneMD5;
        this.status = status;
        this.shedule = shedule;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneMD5() {
        return phoneMD5;
    }

    public void setPhoneMD5(String phoneMD5) {
        this.phoneMD5 = phoneMD5;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getShedule() {
        return shedule;
    }

    public void setShedule(String shedule) {
        this.shedule = shedule;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", phoneMD5='" + phoneMD5 + '\'' +
                ", status=" + status +
                ", shedule='" + shedule + '\'' +
                '}';
    }
}
