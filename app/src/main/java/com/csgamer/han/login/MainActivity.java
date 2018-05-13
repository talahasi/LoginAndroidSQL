package com.csgamer.han.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.csgamer.han.login.dao.DaoAdapter;
import com.csgamer.han.login.dao.UsuarioDao;
import com.blue.giovani.login.model.Usuario;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText senha;
    private Button logar;
    private Button novoRegistro;
    private Usuario usuario;
    private UsuarioDao usuarioDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.txtUsuario);
        senha = findViewById(R.id.txtSenha);

        usuario = new Usuario();

        DaoAdapter daoAdapter =  new DaoAdapter(this);
//        daoAdapter.onUpgrade(daoAdapter.getWritableDatabase(), 1, 2);
        daoAdapter.onCreate(daoAdapter.getWritableDatabase());
    }

    public void logar(View v){
        usuario.setUsuario(username.getText().toString());
        usuario.setSenha(senha.getText().toString());

        if(validarLogin()){
            Toast.makeText(this, "Logado com successo!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, com.csgamer.han.login.AnimaisActivity.class);
            startActivity(i);
        }
    }

    public void novoRegistro(View v){
        Intent i = new Intent(this, com.csgamer.han.login.NovoRegistroActivity.class);
        startActivity(i);
    }

    private boolean validarLogin() {
        usuarioDao = new UsuarioDao(this);
        Usuario usuarioDb = usuarioDao.getByUsername(usuario);
        if(usuarioDb.getId() != 0) {
            if (!usuarioDb.getSenha().equals(usuario.getSenha())) {
                senha.setError("Senha invalida");
                return false;
            }
        }else{
            username.setError("Usuario não cadastrado!");
            return false;
        }

        return true;
    }
}
