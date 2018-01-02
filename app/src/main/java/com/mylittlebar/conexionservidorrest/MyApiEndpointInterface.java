package com.mylittlebar.conexionservidorrest;

/**
 * Created by enric on 29/12/17.
 */

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyApiEndpointInterface {
    @GET("ciudades/")
    Call<List<Ciudad>> getCiudades();

}