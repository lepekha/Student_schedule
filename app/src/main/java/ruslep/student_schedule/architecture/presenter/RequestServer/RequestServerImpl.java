package ruslep.student_schedule.architecture.presenter.RequestServer;

import android.util.Log;



import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;
import ruslep.student_schedule.architecture.model.REST.Model;
import ruslep.student_schedule.architecture.model.REST.ModelImpl;
import ruslep.student_schedule.architecture.model.entity.Subject;
import ruslep.student_schedule.architecture.other.Const;
import rx.Observer;

/**
 * Created by Ruslan on 12.08.2016.
 */
@EBean
public class RequestServerImpl implements RequestServer {
    @Bean(ModelImpl.class)
    Model model;

    @Override
    public void setSchedule(String phoneMD5, String schedule) {
        model
                .setSchedule(phoneMD5, schedule)
                .subscribe(new Observer<Response<ResponseBody>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Response<ResponseBody> responseBodyResponse) {

                    }
                });
    }

    @Override
    public void registerUser(String phoneMD5) {
            model
                .registerUser(phoneMD5)
                .subscribe(new Observer<Response<ResponseBody>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Response<ResponseBody> responseBodyResponse) {

                    }
                });
    }

    @Override
    public void getSchedule(String phoneMD5) {
            model
                .getSchedule(Const.PHONE)
                .subscribe(new Observer<List<Subject>>() {
                    @Override
                    public void onCompleted() {
                        Log.e("errr","OK");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("errr","No");
                    }

                    @Override
                    public void onNext(List<Subject> list) {
                        //save to bd

                    }
                });
    }
}
