package pe.reciclaya.app.services;

import pe.reciclaya.app.models.User;
import pe.reciclaya.app.requests.UserExistsEmail;
import pe.reciclaya.app.requests.UserRegister;
import pe.reciclaya.app.requests.UserValidate;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("user/login")
    Call<User> validateUser(@Body UserValidate requestModel);

    @POST("user/validate")
    Call<Boolean> existsEmail(@Body UserExistsEmail requestModel);

    @POST("user/register")
    Call<Integer> registerUser(@Body UserRegister requestModel);
}
