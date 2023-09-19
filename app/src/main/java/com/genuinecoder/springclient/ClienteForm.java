package com.genuinecoder.springclient;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.genuinecoder.springclient.model.Cliente;
import com.genuinecoder.springclient.retrofit.ClienteApi;
import com.genuinecoder.springclient.retrofit.RetrofitService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClienteForm extends AppCompatActivity {

    private Cliente clienteParaEditar;
    private boolean modoEdicion = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();

        // Recupera el cliente JSON del intent
        String clienteJson = getIntent().getStringExtra("cliente");
        boolean modoEdicion = getIntent().getBooleanExtra("modoEdicion", false);

        if (clienteJson != null) {
            // Convierte la cadena JSON de nuevo a un objeto Cliente
            Gson gson = new Gson();
            Cliente cliente = gson.fromJson(clienteJson, Cliente.class);

            if (modoEdicion) {
                // Estamos en modo de edición, así que llena los campos de edición con los datos del cliente
                fillFormFields(cliente);
            } else {
                // Estamos en modo de agregado, no necesitas llenar los campos de edición
            }
        }
    }
    private void initializeComponents() {
        TextInputEditText inputEditTextNombre = findViewById(R.id.textNombre);
        TextInputEditText inputEditTextApellido = findViewById(R.id.textApellido);
        TextInputEditText inputEditTextTelefono = findViewById(R.id.textTelefono);
        MaterialButton buttonSave = findViewById(R.id.form_buttonSave);

        RetrofitService retrofitService = new RetrofitService();
        ClienteApi clienteApi = retrofitService.getRetrofit().create(ClienteApi.class);

        buttonSave.setOnClickListener(view -> {
            String nombre = String.valueOf(inputEditTextNombre.getText());
            String apellido = String.valueOf(inputEditTextApellido.getText());
            String telefono = String.valueOf(inputEditTextTelefono.getText());

            Cliente cliente = new Cliente();
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setTelefono(telefono);
            boolean modoEdicion = getIntent().getBooleanExtra("modoEdicion", false);
            if (modoEdicion) {
                // Estamos en modo de edición, así que actualiza el cliente existente
                clienteApi.update(cliente.getIdCliente(), cliente)
                        .enqueue(new Callback<Cliente>() {
                            @Override
                            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                                Toast.makeText(ClienteForm.this, "Cliente Editado", Toast.LENGTH_SHORT).show();
                                // Finaliza la actividad de edición después de guardar
                                finish();
                            }

                            @Override
                            public void onFailure(Call<Cliente> call, Throwable t) {
                                Toast.makeText(ClienteForm.this, "Fallo al Editar", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                // Estamos en modo de agregado, así que agrega un nuevo cliente
                clienteApi.save(cliente)
                        .enqueue(new Callback<Cliente>() {
                            @Override
                            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                                Toast.makeText(ClienteForm.this, "Cliente Agregado", Toast.LENGTH_SHORT).show();
                                // Finaliza la actividad de edición después de guardar
                                finish();
                            }

                            @Override
                            public void onFailure(Call<Cliente> call, Throwable t) {
                                Toast.makeText(ClienteForm.this, "Fallo al Agregar", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void fillFormFields(Cliente cliente) {
        TextInputEditText inputEditTextNombre = findViewById(R.id.textNombre);
        TextInputEditText inputEditTextApellido = findViewById(R.id.textApellido);
        TextInputEditText inputEditTextTelefono = findViewById(R.id.textTelefono);

        inputEditTextNombre.setText(cliente.getNombre());
        inputEditTextApellido.setText(cliente.getApellido());
        inputEditTextTelefono.setText(cliente.getTelefono());
    }


}
