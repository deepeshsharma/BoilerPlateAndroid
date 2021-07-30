package com.deep.app.network;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Url;

/**
 * Created by Deepesh on 02-11-2017.
 */

public interface ApiService {

    @GET
    Observable<Response<ResponseBody>> get(
            @Url
                    String url);

    @POST
    Observable<Response<ResponseBody>> post(
            @Url
                    String url,
            @Body
                    Object body);

    @FormUrlEncoded
    @POST
    Observable<Response<ResponseBody>> postWithFormData(
            @Url
                    String url,
            @FieldMap
                    Map<String, Object> parameters);

    @PUT
    Observable<Response<ResponseBody>> put(
            @Url
                    String url);
}
