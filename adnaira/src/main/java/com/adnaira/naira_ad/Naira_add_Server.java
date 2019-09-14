package com.adnaira.naira_ad;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Naira_add_Server {
    @GET("{id}/{ip}")
    Call<AddInfo> getAddInfo(@Path("id") String token, @Path("ip") String ipAdd);
}
