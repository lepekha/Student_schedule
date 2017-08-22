package ruslep.student_schedule.architecture.model.REST;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;
import ruslep.student_schedule.architecture.model.entity.Contacts;
import ruslep.student_schedule.architecture.model.entity.Subject;
import ruslep.student_schedule.architecture.model.entity.User;
import ruslep.student_schedule.architecture.other.RxUtil;
import ruslep.student_schedule.architecture.other.network.API;
import ruslep.student_schedule.architecture.other.network.APIhelper;
import rx.Observable;

/**
 * Created by Ruslan on 12.08.2016.
 */
@EBean
public class ModelImpl implements Model {
    @Bean
    APIhelper apIhelper;


    private API service;



    @AfterInject
    public void init(){
        service = apIhelper.getService();

    }

    @Override
    public Observable<Response<ResponseBody>> registerUser(String phoneMD5) {
        return service
                .registerUser(phoneMD5)
                .compose(RxUtil.applyIOToMainThreadSchedulers());
    }

    @Override
    public Observable<List<Subject>> getSchedule(String phoneMD5, boolean mySchedule) {
        return service
                .getSchedule(phoneMD5, mySchedule)
                .compose(RxUtil.applyIOToMainThreadSchedulers());
    }

    @Override
    public Observable<Response<ResponseBody>> setSchedule(String phoneMD5, String schedule, boolean hide) {
        return service
                .setSchedule(phoneMD5,schedule, hide)
                .compose(RxUtil.applyIOToMainThreadSchedulers());
    }

    @Override
    public Observable<Response<ResponseBody>> delete(String phoneMD5) {
        return service
                .delete(phoneMD5)
                .compose(RxUtil.applyIOToMainThreadSchedulers());
    }

    @Override
    public Observable<List<Contacts>> getContacts(String contactsMD5) {
        return service
                .getContacts(contactsMD5)
                .compose(RxUtil.applyIOToMainThreadSchedulers());
    }

    @Override
    public Observable<List<User>> getUserSchedule(String phoneMD5, boolean mySchedule) {
        return service
                .getUserSchedule(phoneMD5, mySchedule)
                .compose(RxUtil.applyIOToMainThreadSchedulers());
    }
}
