package com.csgamer.han.login.dao;

import android.content.Context;
import com.blue.giovani.login.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {
    private static final String INSERT = "INSERT INTO usuarios (usuario, nome, sobrenome, senha, email, sexo) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL = "SELECT id, usuarios, nome, sobrenome, senha, email, sexo FROM usuarios ORDER BY nome ASC";
    private static final String SELECT_BY_ID = "SELECT id, usuarios, nome, sobrenome, senha, email, sexo FROM usuarios WHERE id = ?";
    private static final String SELECT_BY_USERNAME = "SELECT id, usuario, nome, sobrenome, senha, email, sexo FROM usuarios WHERE usuario = ?";
    private static final String UPDATE= "UPDATE usuarios SET usuario = ?, nome = ?, sobrenome = ?, senha = ?, email = ?, sexo = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM usuarios WHERE id = ?";
    private DaoAdapter daoAdapter;

    public UsuarioDao(Context context){
        daoAdapter = new DaoAdapter(context);
    }

    public boolean insert(Usuario usuario){
        Object[] args = {
                usuario.getUsuario(),
                usuario.getNome(),
                usuario.getSobrenome(),
                usuario.getSenha(),
                usuario.getEmail(),
                usuario.getSexo()
        };

        return daoAdapter.queryExecute(INSERT, args);
    }

    public boolean update(Usuario usuario){
        Object[] args = {
                usuario.getId(),
                usuario.getUsuario(),
                usuario.getNome(),
                usuario.getSobrenome(),
                usuario.getSenha(),
                usuario.getEmail(),
                usuario.getSexo()
        };

        return daoAdapter.queryExecute(UPDATE, args);
    }

    public boolean delete(Usuario usuario) {
        Object[] args = {
                usuario.getId()
        };

        return daoAdapter.queryExecute(DELETE, args);
    }

    public List<Usuario> getTodos(){
        ObjetoBanco ob = daoAdapter.queryConsulta(SELECT_ALL, null);
        List<Usuario> lista = new ArrayList<>();
        for (int i = 0; i < ob.size(); i++) {
            Usuario usuario = new Usuario();
            usuario.setId(ob.getLong(i, "id"));
            usuario.setNome(ob.getString(i, "nome"));
            usuario.setSobrenome(ob.getString(i, "sobrenome"));
            usuario.setSenha(ob.getString(i, "senha"));
            usuario.setEmail(ob.getString(i, "email"));
            usuario.setSexo(ob.getString(i, "sexo"));
            lista.add(usuario);
        }

        return lista;
    }

    public Usuario get(Usuario usuario){
        String[] args = {String.valueOf(usuario.getId())};

        ObjetoBanco ob = daoAdapter.queryConsulta(SELECT_BY_ID, args);
        Usuario retorno = setRetorno(ob);

        return retorno;
    }

    public Usuario getByUsername(Usuario usuario){
        String[] args = {String.valueOf(usuario.getUsuario())};

        ObjetoBanco ob = daoAdapter.queryConsulta(SELECT_BY_USERNAME, args);
        Usuario retorno = setRetorno(ob);

        return retorno;
    }

    private Usuario setRetorno(ObjetoBanco ob){
        Usuario retorno = new Usuario();
        if(ob.size() > 0){
            retorno.setId(ob.getLong(0, "id"));
            retorno.setNome(ob.getString(0, "nome"));
            retorno.setSobrenome(ob.getString(0, "sobrenome"));
            retorno.setSenha(ob.getString(0, "senha"));
            retorno.setEmail(ob.getString(0, "email"));
            retorno.setSexo(ob.getString(0, "sexo"));
        }
        return retorno;
    }
}
