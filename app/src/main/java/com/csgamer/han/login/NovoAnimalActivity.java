package com.csgamer.han.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.csgamer.han.login.dao.AnimalDao;
import com.blue.giovani.login.model.Animal;

public class NovoAnimalActivity extends AppCompatActivity {
    private EditText nome;
    private EditText raca;
    private EditText cor;
    private EditText idade;
    private EditText nomeDono;
    private EditText cpfDono;
    private EditText telefoneDono;
    private Animal animal;
    private AnimalDao animalDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_animal);
        setTitle("Cadastro de Animais");

        nome = findViewById(R.id.txtNomeAnimal);
        raca = findViewById(R.id.txtRaca);
        cor = findViewById(R.id.txtCor);
        idade = findViewById(R.id.txtIdade);
        nomeDono = findViewById(R.id.txtDono);
        cpfDono = findViewById(R.id.txtCpf);
        telefoneDono = findViewById(R.id.txtTelefone);
        animalDao = new AnimalDao(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle extras = getIntent().getExtras();
        long id = 0;
        if(extras != null){
            id = extras.getLong("id");
        }
        if(id > 0){
            try{
                animal = animalDao.get(new Animal(id));
            }catch(Exception e){
                Log.e("teste", e.getMessage());
            }
            nome.setText(animal.getNome());
            raca.setText(animal.getRaca());
            cor.setText(animal.getCor());
            idade.setText(String.valueOf(animal.getIdade()));
            nomeDono.setText(animal.getDono());
            telefoneDono.setText(String.valueOf(animal.getTelefone()));
            cpfDono.setText(String.valueOf(animal.getCpf()));
        }
    }

    public void salvar(View v){
        animal = new Animal();
        animal.setNome(nome.getText().toString());
        animal.setRaca(raca.getText().toString());
        animal.setCor(cor.getText().toString());
        animal.setIdade(Integer.parseInt(idade.getText().toString()));
        animal.setDono(nomeDono.getText().toString());
        animal.setCpf(Long.parseLong(cpfDono.getText().toString()));
        animal.setTelefone(Long.parseLong(telefoneDono.getText().toString()));

        if(validaCampos()){
            Toast.makeText(this, "Animal salvo com sucesso!", Toast.LENGTH_SHORT).show();
            animalDao.insert(animal);
            finish();
        }else{
            Toast.makeText(this, "Erro ao salvar animal", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validaCampos() {
        return true;
    }
}
