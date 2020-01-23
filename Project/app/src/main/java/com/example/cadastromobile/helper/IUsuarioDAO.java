package com.example.cadastromobile.helper;

import com.example.cadastromobile.model.Usuario;

import java.util.List;

public interface IUsuarioDAO {

    public boolean salvar(Usuario usuario);

    public boolean atualizar(Usuario usuario);

    public boolean deletar(Usuario usuario);

    public List<Usuario> listar();
}
