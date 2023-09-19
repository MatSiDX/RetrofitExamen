package com.genuinecoder.springclient.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.genuinecoder.springclient.R;

public class ClienteHolder extends RecyclerView.ViewHolder {

    TextView Nombre, Apellido, Telefono;
    ImageView editarIcon, eliminarIcon;
    public ClienteHolder(@NonNull View itemView){
        super(itemView);
        Nombre = itemView.findViewById(R.id.idnombre);
        Apellido = itemView.findViewById(R.id.idapellido);
        Telefono = itemView.findViewById(R.id.idtelefono);
        editarIcon = itemView.findViewById(R.id.icon_editar);
        eliminarIcon = itemView.findViewById(R.id.icon_eliminar);

    }

}
