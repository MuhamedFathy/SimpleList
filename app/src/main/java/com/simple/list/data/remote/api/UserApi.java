package com.simple.list.data.remote.api;

import com.simple.list.data.remote.Endpoints;
import com.simple.list.data.remote.UserResponse;
import io.reactivex.Single;
import java.util.List;
import retrofit2.http.GET;

public interface UserApi {

  @GET(Endpoints.USERS) Single<List<UserResponse>> getUsers();
}
