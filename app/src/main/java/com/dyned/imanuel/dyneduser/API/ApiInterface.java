package com.dyned.imanuel.dyneduser.API;

import com.dyned.imanuel.dyneduser.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by nuel4 on 09/09/2016.
 */
public interface ApiInterface {
    @GET("users")
    Call<List<User>> getUsers();
}
