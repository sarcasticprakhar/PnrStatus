package com.example.pnrstatus;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface Api {
    @Headers({
            "X-RapidAPI-Key: f9cc9d09bdmsha366e685f3f6015p1c40a3jsnb8c7e04b740d",
            "X-RapidAPI-Host: pnr-status-indian-railway.p.rapidapi.com"
    })
    @GET("pnr-check/{pnr}")
    Call<ModalRes> search(@Path("pnr") String pnr);
}
