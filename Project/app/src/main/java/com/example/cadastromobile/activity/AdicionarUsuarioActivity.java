package com.example.cadastromobile.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cadastromobile.R;
import com.example.cadastromobile.helper.UsuarioDAO;
import com.example.cadastromobile.model.Usuario;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.InputMismatchException;

public class AdicionarUsuarioActivity extends AppCompatActivity {

    private EditText editNome;
    private EditText editEndereco;
    private EditText editTelefone;
    private EditText editCPF;
    private Usuario usuarioAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_usuario);

        editNome = findViewById(R.id.edtNome);
        editEndereco = findViewById(R.id.edtEndereco);
        editTelefone = findViewById(R.id.edtTelefone);
        editCPF = findViewById(R.id.edtCPF);

        usuarioAtual = (Usuario) getIntent().getSerializableExtra("usuarioSelecionado");

        if (usuarioAtual != null) {
            editNome.setText(usuarioAtual.getNomeUsuario());
            editEndereco.setText(usuarioAtual.getEnderecoUsuario());
            editTelefone.setText(usuarioAtual.getTelefoneUsuario());
            editCPF.setText(usuarioAtual.getCpfUsuario());
        }

        implementarMascaras();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_usuario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemSalvar:

                UsuarioDAO usuarioDAO = new UsuarioDAO(getApplicationContext());
                Usuario usuario = new Usuario();


                usuario.setNomeUsuario(editNome.getText().toString());
                usuario.setEnderecoUsuario(editEndereco.getText().toString());
                usuario.setTelefoneUsuario(editTelefone.getText().toString());
                usuario.setCpfUsuario(editCPF.getText().toString());

                if (usuario.getNomeUsuario().isEmpty() || !usuario.getNomeUsuario().matches("[A-Za-záàãâäÁÀÃÂÄéèêëÉÈÊËíìîïÍÌÎÏóòõôöÓÒÕÔÖúùûüÚÙÛÜ ]*")) {
                    editNome.setError("O campo Nome não pode estar vazio! ou conter caracteres Especiais e Números");
                    editNome.requestFocus();
                } else if (usuario.getEnderecoUsuario().isEmpty()) {
                    editEndereco.setError(" O campo Endereço Não pode estar vazio!");
                    editEndereco.requestFocus();
                } else if (usuario.getTelefoneUsuario().isEmpty() || editTelefone.getText().toString().length() < 15 ||
                        editTelefone.getText().toString().charAt(5) != '9') {
                    editTelefone.setError(" O Número de telefone não é valido!");
                    editTelefone.requestFocus();
                } else if (usuario.getCpfUsuario().isEmpty() || isCPF(usuario.getCpfUsuario()) == false) {
                    editCPF.setError("CPF invalido!");
                    editCPF.requestFocus();
                } else {

                    if (usuarioAtual != null) {
                        usuario.setId(usuarioAtual.getId());
                        if (usuarioDAO.atualizar(usuario)) {
                            finish();
                            Toast.makeText(getApplicationContext(), "Sucesso ao atualizar usuário!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Erro ao atualizar usuário!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (usuarioDAO.salvar(usuario)) {
                            finish();
                            Toast.makeText(getApplicationContext(), "Sucesso ao salvar usuário!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Erro ao salvar usuário!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static boolean isCPF(String CPF) {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais

        CPF = CPF.replace(".", "");
        CPF = CPF.replace("-", "");

        if (CPF.equals("00000000000") ||
                CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11))
            return (false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {

                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char) (r + 48);

            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }
            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char) (r + 48);

            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return (true);
            else return (false);
        } catch (InputMismatchException erro) {
            return (false);
        }
    }

    public void implementarMascaras() {

        SimpleMaskFormatter smfCpf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtwCpf = new MaskTextWatcher(editCPF, smfCpf);
        editCPF.addTextChangedListener(mtwCpf);

        SimpleMaskFormatter smfCelular = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher mtwCelular = new MaskTextWatcher(editTelefone, smfCelular);
        editTelefone.addTextChangedListener(mtwCelular);
    }
}
