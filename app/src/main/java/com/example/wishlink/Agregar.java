package com.example.wishlink;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Agregar extends AppCompatActivity {

    ImageButton btnVolver;

    EditText txtNomArticulo;
    EditText txtNombreLink;
    EditText txtNombreLink2;

    TextView txtTitulo;

    Button btnLimpiar;
    Button btnAgregarArt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_agregar);



        // Inicializar vistas
        btnVolver = findViewById(R.id.btnVolver);

        btnLimpiar = findViewById(R.id.btnLimpiar);

        btnAgregarArt = findViewById(R.id.btnAgregarArt);

        txtNomArticulo = findViewById(R.id.txtNomArticulo);

        txtNombreLink = findViewById(R.id.txtNombreLink);

        txtNombreLink2 = findViewById(R.id.txtNombreLink2);

        txtTitulo = findViewById(R.id.txtTitulo);



        // Revisar si viene en modo editar
        String modo =
                getIntent().getStringExtra("modo");

        if(modo != null && modo.equals("editar")){

            txtTitulo.setText("Editar artículo");


            txtNomArticulo.setText(
                    getIntent().getStringExtra("nombre"));

            txtNombreLink.setText(
                    getIntent().getStringExtra("link1"));

            txtNombreLink2.setText(
                    getIntent().getStringExtra("link2"));

            btnAgregarArt.setText("Modificar");

        }else{

            txtTitulo.setText("Agregar artículo");

            btnAgregarArt.setText("Agregar");
        }



        // Botón volver
        btnVolver.setOnClickListener(v -> {

            finish();
        });



        // Botón limpiar
        btnLimpiar.setOnClickListener(v -> {

            txtNomArticulo.setText("");

            txtNombreLink.setText("");

            txtNombreLink2.setText("");
        });



        // Botón agregar / guardar
        btnAgregarArt.setOnClickListener(v -> {

            String nombre =
                    txtNomArticulo.getText().toString();

            String link1 =
                    txtNombreLink.getText().toString();

            String link2 =
                    txtNombreLink2.getText().toString();



            Intent intent = new Intent();

            intent.putExtra("nombre", nombre);

            intent.putExtra("link1", link1);

            intent.putExtra("link2", link2);

            int posicion =
                    getIntent().getIntExtra(
                            "posicion",
                            -1);

            intent.putExtra(
                    "posicion",
                    posicion);

            setResult(RESULT_OK, intent);

            finish();
        });
    }
}