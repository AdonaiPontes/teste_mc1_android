package com.example.cadastromobile.helper;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.cadastromobile.activity.AdicionarUsuarioActivity;

public class DbHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NOME_DB = "DB_USUARIOS";
    public static String TABELA_USUARIOS = "tb_usuarios";

    public DbHelper(Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS " + TABELA_USUARIOS + "   (id INTEGER PRIMARY KEY AUTOINCREMENT,  " + "   nome VARCHAR(50) NOT NULL, "
                + "   endereco TEXT NOT NULL, " + "   telefone VARCHAR(15) NOT NULL, " + "   cpf VARCHAR(14) NOT NULL);   ";

        try {
            db.execSQL(sql);
            Log.i("INFO DB", "SUCESSO AO CRIAR TABELA!");
        } catch (Exception ex) {
            Log.i("INFO DB", "ERRO AO CRIAR TABELA! " + ex.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
