package cafebazar.challenge.cafebazar.api;

import java.util.List;

import cafebazar.challenge.cafebazar.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MyApiEndpointInterface {
    // Request method and URL specified in the annotation

    @GET("users/{username}")
    Call<User> getUser(@Path("username") String username);

    @GET("group/{id}/users")
    Call<List<User>> groupList(@Path("id") int groupId, @Query("sort") String sort);

    @POST("Services/Like")
    Call<User> createUser(@Body User user);
}