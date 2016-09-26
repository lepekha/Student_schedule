package ruslep.student_schedule.architecture.other.network;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ruslep.student_schedule.architecture.model.entity.Subject;
import rx.Observable;

/**
 * Created by Ruslan on 11.08.2016.
 */
public interface API {

    @Headers("Content-Type: application/x-www-form-urlencoded; charset=utf-8")
    @FormUrlEncoded
    @POST("rest/phone/register")
    Observable<Response<ResponseBody>> registerUser(@Field("phoneMD5") String phoneMD5);

    @Headers("Content-Type: application/x-www-form-urlencoded; charset=utf-8")
    @FormUrlEncoded
    @POST("rest/phone/setSchedule")
    Observable<Response<ResponseBody>>  setSchedule(@Field("phoneMD5") String phoneMD5, @Field("schedule") String schedule);

    @Headers("Content-Type: application/x-www-form-urlencoded; charset=utf-8")
    @FormUrlEncoded
    @POST("rest/phone/getSchedule")
    Observable<List<Subject>> getSchedule(@Field("phoneMD5") String phoneMD5);

}
