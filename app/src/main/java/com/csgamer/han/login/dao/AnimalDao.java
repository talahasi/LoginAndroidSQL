package com.csgamer.han.login.dao;

import android.content.Context;
import com.blue.giovani.login.model.Animal;

import java.util.ArrayList;
import java.util.List;

public class AnimalDao {
    private static final String INSERT = "INSERT INTO animais (nome, raca, idade, cor, dono, cpf, telefone) VALUES (?,?,?,?,?,?,?)";
    private static final String SELECT_ALL = "SELECT id, nome, raca, idade, cor, dono, cpf, telefone FROM animais";
    private static final String SELECT_BY_ID = "SELECT id, nome, raca, idade, cor, dono, cpf, telefone FROM animais WHERE id = ?";
    private static final String SELECT_BY_NOME = "SELECT id, nome, raca, idade, cor, dono, cpf, telefone FROM animais WHERE nome = ?";
    private static final String UPDATE = "UPDATE animais SET nome = ?, raca = ?, idade = ?, cor = ?, dono = ?, cpf = ?, telefone = ? FROM animais";
    private static final String DELETE = "DELETE FROM animais WHERE id = ?";
    private com.csgamer.han.login.dao.DaoAdapter daoAdapter;

    public AnimalDao(Context context){
        daoAdapter = new com.csgamer.han.login.dao.DaoAdapter(context);
    }

    public boolean insert(Animal animal){
        Object[] args = {
                animal.getNome(),
                animal.getRaca(),
                animal.getIdade(),
                animal.getCor(),
                animal.getDono(),
                animal.getCpf(),
                animal.getTelefone()
        };
        return daoAdapter.queryExecute(INSERT, args);
    }

    public boolean update(Animal animal){
        Object[] args = {
                animal.getId(),
                animal.getNome(),
                animal.getRaca(),
                animal.getIdade(),
                animal.getCor(),
                animal.getDono(),
                animal.getCpf(),
                animal.getTelefone()
        };
        return daoAdapter.queryExecute(UPDATE, args);
    }

    public boolean delete(Animal animal){
        Object[] args = {
                animal.getId()
        };
        return daoAdapter.queryExecute(DELETE, args);
    }

    public List<Animal> getTodos(){
        ObjetoBanco ob = daoAdapter.queryConsulta(SELECT_ALL, null);
        List<Animal> lista = new ArrayList<>();
        for (int i = 0; i < ob.size(); i++) {
            Animal animal = setRetorno(ob, i);
            lista.add(animal);
        }
        return lista;
    }

    public Animal get(Animal animal){
        String[] args = {String.valueOf(animal.getId())};

        ObjetoBanco ob = daoAdapter.queryConsulta(SELECT_BY_ID, args);
        return setRetorno(ob, null);
    }

    public Animal getByNome(Animal animal){
        String[] args = {String.valueOf(animal.getNome())};

        ObjetoBanco ob = daoAdapter.queryConsulta(SELECT_BY_NOME, args);
        return setRetorno(ob, null);
    }

    private Animal setRetorno(ObjetoBanco ob, Integer index){
        Animal animal = new Animal();
        int i = index != null ? index : 0;
        if(ob.size() > 0) {
            animal.setId(ob.getLong(i, "id"));
            animal.setNome(ob.getString(i, "nome"));
            animal.setRaca(ob.getString(i, "raca"));
            animal.setIdade(ob.getInt(i, "idade"));
            animal.setCor(ob.getString(i, "cor"));
            animal.setDono(ob.getString(i, "dono"));
            animal.setCpf(ob.getLong(i, "cpf"));
            animal.setTelefone(ob.getLong(i, "telefone"));
        }
        return animal;
    }
}
