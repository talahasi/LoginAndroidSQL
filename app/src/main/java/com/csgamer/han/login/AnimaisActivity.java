package com.csgamer.han.login;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.csgamer.han.login.dao.AnimalDao;
import com.csgamer.han.login.dao.DaoAdapter;
import com.blue.giovani.login.model.Animal;

import java.util.ArrayList;
import java.util.List;

public class AnimaisActivity extends AppCompatActivity {
    private static final int EDITAR = 1;
    private static final int EXCLUIR = 2;
    private ListView lista;
    private List<Animal> animais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animais);
        setTitle("Lista de Animais");

        lista = findViewById(R.id.listaAnimais);

        DaoAdapter daoAdapter = new DaoAdapter(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AnimaisActivity.this, com.csgamer.han.login.NovoAnimalActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerForContextMenu(lista);

        final AnimalDao animalDao = new AnimalDao(this);
        final List<String> values = new ArrayList<>();
        animais = animalDao.getTodos();

        for(Animal animal : animais){
            values.add(animal.getNome());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
//        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                final Animal clicked = animais.get(i);
//                Intent intent = new Intent(AnimaisActivity.this, NovoAnimalActivity.class);
//                intent.putExtra("id", clicked.getId());
//                startActivity(intent);
//            }
//        });

//        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                return true;
//            }
//        });

        lista.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
//        MenuInflater inflater = getMenuInflater();
        menu.add(0, EDITAR, 0, "Editar");
        menu.add(0, EXCLUIR, 0, "Excluir");
//        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case EDITAR:
                Intent intent = new Intent(AnimaisActivity.this, com.csgamer.han.login.NovoAnimalActivity.class);
                intent.putExtra("id", animais.get(info.position).getId());
                startActivity(intent);
                return true;

            case EXCLUIR:
                AnimalDao dao = new AnimalDao(AnimaisActivity.this);
                if(dao.delete(new Animal(info.id))){
                    Toast.makeText(AnimaisActivity.this, "Excluido com sucesso!", Toast.LENGTH_SHORT).show();
                    onResume();
                }else{
                    Toast.makeText(AnimaisActivity.this, "Erro ao excluir animal", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
