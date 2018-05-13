package com.csgamer.han.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.csgamer.han.login.dao.DaoAdapter;
import com.csgamer.han.login.dao.UsuarioDao;
import com.csgamer.han.login.Usuario;

public class NovoRegistroActivity extends AppCompatActivity {

    private EditText usuario;
    private EditText nome;
    private EditText sobrenome;
    private EditText email;
    private EditText senha;
    private EditText confirma;
    private EditText sexo;

    private Usuario usuarioModel;
    private UsuarioDao usuarioDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_registro);
        setTitle("Novo Registro");

        usuario = findViewById(R.id.txtUsuario);
        nome = findViewById(R.id.txtNome);
        sobrenome = findViewById(R.id.txtSobrenome);
        email = findViewById(R.id.txtEmail);
        senha = findViewById(R.id.txtSenha);
        confirma = findViewById(R.id.txtConfirma);
        sexo = findViewById(R.id.txtSexo);
    }

    @Override
    protected void onResume() {
        super.onResume();

        usuarioDao = new UsuarioDao(this);
        usuarioModel = new Usuario();
    }

    public void salvar(View v){
        usuarioModel.setUsuario(usuario.getText().toString());
        usuarioModel.setNome(nome.getText().toString());
        usuarioModel.setSobrenome(sobrenome.getText().toString());
        usuarioModel.setEmail(email.getText().toString());
        usuarioModel.setSenha(senha.getText().toString());
        usuarioModel.setSexo(sexo.getText().toString());

        if(validaCampos()){
            boolean success = usuarioDao.insert(usuarioModel);
            if(success) {
                Toast.makeText(this, "Registro incluido com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this, "Falha ao incluir novo registro!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validaCampos(){
        if(usuario.getText().toString().length() < 3){
            usuario.setError("Usuario deve conter no minimo 3 caracteres");
            return false;
        }
        if(senha.getText().toString().length() < 8){
            usuario.setError("Senha deve conter no mínimo 8 caracteres");
            return false;
        }
        if(!senha.getText().toString().equals(confirma.getText().toString())){
            senha.setError("As senhas não conferem");
            return false;
        }

        Usuario userDb = usuarioDao.getByUsername(usuarioModel);
        if(userDb.getId() != 0){
            usuario.setError("Usuario já existe");
            return false;
        }

        return true;
    }
}
