package com.example.cadastromobile.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cadastromobile.R;
import com.example.cadastromobile.model.Usuario;

import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.MyViewHolder> {

    public List<Usuario> listaUsuarios;

    public UsuarioAdapter(List<Usuario> list) {
        this.listaUsuarios = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemLista = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.lista_usuario_adapter, viewGroup, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        Usuario usuario = listaUsuarios.get(position);
        myViewHolder.usuario.setText(usuario.getNomeUsuario());
        myViewHolder.cpfUsuario.setText(usuario.getCpfUsuario());
    }

    @Override
    public int getItemCount() {
        return this.listaUsuarios.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView usuario;
        TextView cpfUsuario;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            usuario = itemView.findViewById(R.id.textUsuario);
            cpfUsuario = itemView.findViewById(R.id.textUsuarioCpf);
        }
    }
}
