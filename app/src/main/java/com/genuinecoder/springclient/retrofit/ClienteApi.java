package com.genuinecoder.springclient.retrofit;

import com.genuinecoder.springclient.model.Cliente;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ClienteApi {
    @GET("/cliente/get-all")
    Call<List<Cliente>> getAllClientes();

    @POST("/cliente/save")
    Call<Cliente> save(@Body Cliente cliente);

    @PUT("/cliente/update/{id}")
    Call<Cliente> update(@Path("id") int idCliente, @Body Cliente cliente);

    @DELETE("/cliente/delete/{id}")
    Call<Void> delete(@Path("id") int idCliente);


}
