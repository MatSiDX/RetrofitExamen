package com.genuinecoder.springclient.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.genuinecoder.springclient.ClienteForm;
import com.genuinecoder.springclient.R;
import com.genuinecoder.springclient.model.Cliente;

import java.util.List;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteHolder> {
    private List<Cliente> clienteList;
    private OnItemClickListener listener;



    public interface OnItemClickListener {
        void onItemClick(Cliente cliente);
        void onDeleteClick(Cliente cliente);
    }

    public ClienteAdapter(List<Cliente> clienteList, OnItemClickListener listener){
        this.clienteList = clienteList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ClienteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_cliente_item, parent, false);
        return new ClienteHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ClienteHolder holder, int position) {
        final Cliente cliente = clienteList.get(position);
        holder.Nombre.setText(cliente.getNombre());
        holder.Apellido.setText(cliente.getApellido());
        holder.Telefono.setText(cliente.getTelefono());

        final View.OnClickListener editarClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(cliente);
                }
            }
        };

        holder.eliminarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onDeleteClick(cliente);
                }
            }
        });

        // Configura el clic en el ícono de edición
        holder.editarIcon.setOnClickListener(editarClickListener);
    }

    @Override
    public int getItemCount() {
        return clienteList.size();
    }
}
