package com.genuinecoder.springclient;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.genuinecoder.springclient.adapter.ClienteAdapter;
import com.genuinecoder.springclient.model.Cliente;
import com.genuinecoder.springclient.retrofit.ClienteApi;
import com.genuinecoder.springclient.retrofit.RetrofitService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClienteListActivity extends AppCompatActivity implements ClienteAdapter.OnItemClickListener  {

  private RecyclerView recyclerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cliente_list);

    recyclerView = findViewById(R.id.listaClientes);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    FloatingActionButton floatingActionButton = findViewById(R.id.ClienteList_fab);
    floatingActionButton.setOnClickListener(view -> {
      Intent intent = new Intent(this, ClienteForm.class);
      startActivity(intent);
    });


  }

  private void loadClientes() {
    RetrofitService retrofitService = new RetrofitService();
    ClienteApi clienteApi = retrofitService.getRetrofit().create(ClienteApi.class);
    clienteApi.getAllClientes()
        .enqueue(new Callback<List<Cliente>>() {
          @Override
          public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {
            populateListView(response.body());
          }

          @Override
          public void onFailure(Call<List<Cliente>> call, Throwable t) {
            Toast.makeText(ClienteListActivity.this, "Fallo al cargar Clientes", Toast.LENGTH_SHORT).show();
          }
        });
  }

  private void populateListView(List<Cliente> clienteList) {
    ClienteAdapter clienteAdapter = new ClienteAdapter(clienteList, this);
    recyclerView.setAdapter(clienteAdapter);
  }

  @Override
  protected void onResume() {
    super.onResume();
    loadClientes();
  }


  @Override
  public void onItemClick(Cliente cliente) {
    // Convierte el objeto Cliente a JSON
    Gson gson = new Gson();
    String clienteJson = gson.toJson(cliente);

    // Navega a la actividad de edición pasando el cliente como extra
    Intent intent = new Intent(ClienteListActivity.this, ClienteForm.class);
    intent.putExtra("cliente", clienteJson);
    intent.putExtra("modoEdicion", true);
    startActivity(intent);
  }

  public void onDeleteClick(Cliente cliente) {
    RetrofitService retrofitService = new RetrofitService();
    ClienteApi clienteApi = retrofitService.getRetrofit().create(ClienteApi.class);

    clienteApi.delete(cliente.getIdCliente()).enqueue(new Callback<Void>() {
      @Override
      public void onResponse(Call<Void> call, Response<Void> response) {
        if (response.isSuccessful()) {
          // Cliente eliminado con éxito, puedes actualizar la vista o realizar otras acciones necesarias
          loadClientes(); // Vuelve a cargar la lista de clientes
        } else {
          // Error al eliminar el cliente, muestra un mensaje de error
          Toast.makeText(ClienteListActivity.this, "Error al eliminar el cliente", Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onFailure(Call<Void> call, Throwable t) {
        // Error de red o solicitud fallida
        Toast.makeText(ClienteListActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
      }
    });
  }
}