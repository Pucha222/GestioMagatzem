package com.example.gestiomagatzem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AfegirActivity extends AppCompatActivity {

    private TextView codiET;
    private EditText descripcioET;
    private EditText estocET;
    private Spinner familiaSpinner;
    private EditText pvpET;
    private Button afegirBtn;
    private boolean nou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afegir);
        codiET = findViewById(R.id.codiET);
        descripcioET = findViewById(R.id.descripcioET);
        estocET = findViewById(R.id.estocET);
        pvpET = findViewById(R.id.pvpET);
        familiaSpinner = findViewById(R.id.familiaSpinner);
        afegirBtn = findViewById(R.id.btn_afegir);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ArticlesDataSource ds = new ArticlesDataSource(this);

        String intentCodi = getIntent().getStringExtra("codi");
        String intentDescipcio = getIntent().getStringExtra("descripcio");
        String intentEstoc = String.valueOf(getIntent().getFloatExtra("estoc", 0));
        String intentPvp = String.valueOf(getIntent().getFloatExtra("pvp", 0));
        String intentFamilia = getIntent().getStringExtra("familia");

        if (intentCodi != null) {
                codiET.setText(intentCodi);
        }
        if (intentDescipcio != null) {
            descripcioET.setText(intentDescipcio);
        }
        estocET.setText(intentEstoc);
        pvpET.setText(intentPvp);

        int seleccioSpinner;

        if (intentFamilia != null) {
            switch (intentFamilia) {
                case "--------":
                    seleccioSpinner = 0;
                    break;
                case "SOFTWARE":
                    seleccioSpinner = 1;
                    break;
                case "HARDWARE":
                    seleccioSpinner = 2;
                    break;
                case "ALTRES":
                    seleccioSpinner = 3;
                    break;
                default:
                    seleccioSpinner = 0;
            }
            familiaSpinner.setSelection(seleccioSpinner);
        }


        if (codiET.getText().toString().equals("")) {
            afegirBtn.setText("Afegir");
            setTitle("Afegir article");
            nou = true;
        } else {
            afegirBtn.setText("Modificar");
            setTitle("Modificar article");
            codiET.setFocusable(false);
            nou = false;
        }

        //desplegable
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("--------");
        spinnerArray.add("SOFTWARE");
        spinnerArray.add("HARDWARE");
        spinnerArray.add("ALTRES");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        familiaSpinner.setAdapter(adapter);

        //afegir item editar item
        afegirBtn.setOnClickListener(v -> {
            if (descripcioET.getText().toString().equals("")) {
                Toast.makeText(this, "La descripció no pot estar buida", Toast.LENGTH_SHORT).show();
            } else {
                if (Float.parseFloat(estocET.getText().toString()) < 0) {
                    Toast.makeText(this, "El estoc no pot ser inferior a 0", Toast.LENGTH_SHORT).show();
                } else {
                    if (nou) {
                        if (ds.checkCodi(codiET.getText().toString())) {
                            Toast.makeText(this, "El codi esta duplicat", Toast.LENGTH_SHORT).show();
                        } else {
                            ds.inserirArticle(codiET.getText().toString(), descripcioET.getText().toString(), familiaSpinner.getSelectedItem().toString(), Float.parseFloat(estocET.getText().toString()), Float.parseFloat(pvpET.getText().toString()));
                            goBack();
                        }
                    } else {
                        ds.modificarArticle(codiET.getText().toString(), descripcioET.getText().toString(), familiaSpinner.getSelectedItem().toString(), Float.parseFloat(estocET.getText().toString()), Float.parseFloat(pvpET.getText().toString()));
                        goBack();
                    }
                }
            }

        });

    }

    //perque la fletxa enrere funcioni
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //perque al afegir item sactualitzi la llista esborrant les pantalles anteriors amagades
    private void goBack() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}