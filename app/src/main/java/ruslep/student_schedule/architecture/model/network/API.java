package ruslep.student_schedule.architecture.model.network;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Ruslan on 11.08.2016.
 */
public interface API {

    @POST("rest/phone/register")
    Call<Response> registerUser(@Path("phoneMD5") String phoneMD5);

    @POST("rest/phone/setSchedule")
    Call<Response> saveSchedule(@Path("phoneMD5") String phoneMD5,@Path("schedule") String schedule);

    @POST("rest/phone/getSchedule")
    Call<Response> recoverSchedule(@Path("phoneMD5") String phoneMD5);

}
