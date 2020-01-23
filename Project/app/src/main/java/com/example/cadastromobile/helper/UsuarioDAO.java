package com.example.cadastromobile.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.cadastromobile.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements IUsuarioDAO {

    private SQLiteDatabase escrever;
    private SQLiteDatabase ler;
    ContentValues cv = new ContentValues();

    public UsuarioDAO(Context context) {
        DbHelper db = new DbHelper(context);
        escrever = db.getWritableDatabase();
        ler = db.getReadableDatabase();

    }

    @Override
    public boolean salvar(Usuario usuario) {
        cv.put("nome", usuario.getNomeUsuario());
        cv.put("endereco", usuario.getEnderecoUsuario());
        cv.put("telefone", usuario.getTelefoneUsuario());
        cv.put("cpf", usuario.getCpfUsuario());

        try {
            escrever.insert(DbHelper.TABELA_USUARIOS, null, cv);
            Log.i("INFO DB", "SUCESSO AO SALVAR USUARIO");
        } catch (Exception ex) {
            Log.i("INFO DB", "ERRO AO SALVAR USUARIO");
            return false;
        }
        return true;
    }

    @Override
    public boolean atualizar(Usuario usuario) {
        cv.put("nome", usuario.getNomeUsuario());
        cv.put("endereco", usuario.getEnderecoUsuario());
        cv.put("telefone", usuario.getTelefoneUsuario());
        cv.put("cpf", usuario.getCpfUsuario());

        try {
            String[] args = {usuario.getId().toString()};
            escrever.update(DbHelper.TABELA_USUARIOS, cv, "id=?", args);
            Log.i("INFO DB", "SUCESSO AO ATUALIZAR USUARIO");
        } catch (Exception ex) {
            Log.i("INFO DB", "ERRO AO ATUALIZAR USUARIO");
            return false;
        }
        return true;
    }

    @Override
    public boolean deletar(Usuario usuario) {
        try {
            String[] args = {usuario.getId().toString()};
            escrever.delete(DbHelper.TABELA_USUARIOS, "id=?", args);
            Log.i("INFO DB", "SUCESSO AO EXCLUIR USUARIO");
        } catch (Exception ex) {
            Log.i("INFO DB", "ERRO AO EXCLUIR USUARIO");
            return false;
        }
        return true;
    }

    @Override
    public List<Usuario> listar() {
        List<Usuario> listaDeUsuarios = new ArrayList<>();
        String sql = "SELECT * FROM " + DbHelper.TABELA_USUARIOS + "    ;";
        Cursor c = ler.rawQuery(sql, null);

        while (c.moveToNext()) {
            Usuario usuario = new Usuario();

            Long id = c.getLong(c.getColumnIndex("id"));
            String nomeUsuario = c.getString(c.getColumnIndex("nome"));
            String enderecoUsuario = c.getString(c.getColumnIndex("endereco"));
            String telefoneUsuario = c.getString(c.getColumnIndex("telefone"));
            String cpfUsuario = c.getString(c.getColumnIndex("cpf"));

            usuario.setId(id);
            usuario.setNomeUsuario(nomeUsuario);
            usuario.setEnderecoUsuario(enderecoUsuario);
            usuario.setTelefoneUsuario(telefoneUsuario);
            usuario.setCpfUsuario(cpfUsuario);
            listaDeUsuarios.add(usuario);
        }
        return listaDeUsuarios;
    }
}
