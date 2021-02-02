package com.example.gestiomagatzem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArticlesAdapter adapter;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArticlesDataSource dataSource = new ArticlesDataSource(this);

        list = findViewById(R.id.llistaProductes);
        Cursor c = dataSource.getArticles();

        adapter = new ArticlesAdapter(this, c);
        list.setAdapter(adapter);

        setTitle("Llista d'articles");

        list.setOnItemClickListener((parent, view, position, id) -> {

            Cursor articleActual = dataSource.getArticleById(id);
            articleActual.moveToFirst();

            String codi = articleActual.getString(articleActual.getColumnIndexOrThrow(DbHelper.COL_CODI));
            String descripcio = articleActual.getString(articleActual.getColumnIndexOrThrow(DbHelper.COL_DESCRIPCIO));
            String familia = articleActual.getString(articleActual.getColumnIndexOrThrow(DbHelper.COL_FAMILIA));
            float estoc = articleActual.getFloat(articleActual.getColumnIndexOrThrow(DbHelper.COL_ESTOC));
            float pvp = articleActual.getFloat(articleActual.getColumnIndexOrThrow(DbHelper.COL_PREU));

            Intent intent = new Intent(this, AfegirActivity.class);

            intent.putExtra("codi", codi);
            intent.putExtra("descripcio", descripcio);
            intent.putExtra("familia", familia);
            intent.putExtra("estoc", estoc);
            intent.putExtra("pvp", pvp);

            startActivity(intent);
        });

    }

    public void eliminar(final String codi) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ArticlesDataSource dataSource = new ArticlesDataSource(this);

        builder.setMessage("Segur que vols eliminar l'article?");
        builder.setPositiveButton("Si", (dialog, which) -> {
            dataSource.esborrarArticle(codi);
            adapter.changeCursor(dataSource.getArticles());
            adapter.notifyDataSetChanged();
            list.setAdapter(adapter);
        });

        builder.setNegativeButton("No", null);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        SearchView searchView = (SearchView) menu.findItem(R.id.buscar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

                                              @Override
                                              public boolean onQueryTextSubmit(String query) {
                                                  return false;
                                              }

                                              @Override
                                              public boolean onQueryTextChange(String newText) {

                                                  if(newText != null){

                                                      ArticlesDataSource dataSource = new ArticlesDataSource(getApplicationContext());

                                                      Cursor queryCursor = dataSource.buscarArticles(newText);
                                                      adapter=new ArticlesAdapter(getApplicationContext(),queryCursor);
                                                      list.setAdapter(adapter);
                                                      adapter.notifyDataSetChanged();

                                                  }
                                                  return true;
                                              }
                                          });

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_add:
                Intent intent = new Intent(this, AfegirActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}