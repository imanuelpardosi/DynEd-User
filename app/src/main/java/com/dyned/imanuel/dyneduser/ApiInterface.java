package com.dyned.imanuel.dyneduser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by nuel4 on 09/09/2016.
 */
public interface ApiInterface {
    @GET("users")
    Call<List<User>> getUsers();
}
