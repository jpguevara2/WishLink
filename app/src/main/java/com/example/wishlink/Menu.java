package com.example.wishlink;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Menu extends AppCompatActivity {

    EditText txtNombre;

    ImageButton btnAgregar, btnCompartir, btnSalir;

    RecyclerView recView;

    ArrayList<GiftItem> listaRegalos;

    GiftAdapter adapter;

    public ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_menu);



        // Insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main),
                (v, insets) -> {

                    Insets systemBars =
                            insets.getInsets(WindowInsetsCompat.Type.systemBars());

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                });



        // Vincular componentes
        txtNombre = findViewById(R.id.txtNombre);

        btnAgregar = findViewById(R.id.btnFormulario);

        btnCompartir = findViewById(R.id.btnCompartir);

        btnSalir = findViewById(R.id.btnSalir);

        recView = findViewById(R.id.recyclerRegalos);



        // RecyclerView
        cargarLista();

        adapter = new GiftAdapter(listaRegalos);

        recView.setLayoutManager(
                new LinearLayoutManager(this));

        recView.setAdapter(adapter);



        // Launcher para recibir datos desde Agregar.java
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),

                result -> {

                    if(result.getResultCode() == RESULT_OK &&
                            result.getData() != null){

                        String nombre =
                                result.getData().getStringExtra("nombre");

                        String link1 =
                                result.getData().getStringExtra("link1");

                        String link2 =
                                result.getData().getStringExtra("link2");



                        GiftItem item =
                                new GiftItem(nombre, link1, link2);

                        int posicion =
                                result.getData().getIntExtra(
                                        "posicion",
                                        -1);

                        if(posicion != -1){

                            listaRegalos.set(posicion, item);
                            guardarLista();

                        }else{

                            listaRegalos.add(item);
                            guardarLista();
                        }



                        adapter.notifyDataSetChanged();
                    }
                }
        );



        // Cargar nombre guardado
        cargarNombre();



        // Guardar nombre automáticamente
        txtNombre.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s,
                                          int start,
                                          int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(CharSequence s,
                                      int start,
                                      int before,
                                      int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                guardarNombre(s.toString());
            }
        });



        // Abrir pantalla agregar
        btnAgregar.setOnClickListener(v -> {

            Intent i =
                    new Intent(Menu.this,
                            Agregar.class);

            launcher.launch(i);
        });



// Compartir wishlist
        btnCompartir.setOnClickListener(v -> {

            StringBuilder mensaje = new StringBuilder();

            mensaje.append("Wishlist de ")
                    .append(txtNombre.getText().toString())
                    .append("\n\n");



            for(GiftItem item : listaRegalos){

                String link1 =
                        item.getLink1()
                                .split("\\?")[0]
                                .replace("https://", "https ://");

                String link2 =
                        item.getLink2()
                                .split("\\?")[0]
                                .replace("https://", "https ://");



                mensaje.append("🎁 ")
                        .append(item.getNombre())
                        .append("\n\n");



                mensaje.append(link1)
                        .append("\n\n");


                mensaje.append(link2)
                        .append("\n\n");
            }

            Intent intent = new Intent(Intent.ACTION_SEND);

            intent.setType("text/plain");

            intent.putExtra(
                    Intent.EXTRA_TEXT,
                    mensaje.toString());

            startActivity(Intent.createChooser(
                    intent,
                    "Compartir wishlist"));
        });
        


        // Salir app
        btnSalir.setOnClickListener(v -> {

            Intent intent =
                    new Intent(Intent.ACTION_MAIN);

            intent.addCategory(
                    Intent.CATEGORY_HOME);

            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
        });
    }



    // Guardar nombre
    private void guardarNombre(String nombre) {

        SharedPreferences prefs =
                getSharedPreferences(
                        "wishlist",
                        MODE_PRIVATE
                );

        prefs.edit()
                .putString("nombre", nombre)
                .apply();
    }

    public void guardarLista() {

        SharedPreferences prefs =
                getSharedPreferences(
                        "wishlist",
                        MODE_PRIVATE);

        SharedPreferences.Editor editor =
                prefs.edit();

        Gson gson = new Gson();

        String json =
                gson.toJson(listaRegalos);

        editor.putString(
                "lista_regalos",
                json);

        editor.apply();
    }

    private void cargarLista() {

        SharedPreferences prefs =
                getSharedPreferences(
                        "wishlist",
                        MODE_PRIVATE);

        Gson gson = new Gson();

        String json =
                prefs.getString(
                        "lista_regalos",
                        null);

        Type type =
                new TypeToken<ArrayList<GiftItem>>() {}.getType();

        listaRegalos =
                gson.fromJson(json, type);

        if(listaRegalos == null){

            listaRegalos = new ArrayList<>();
        }
    }



    // Cargar nombre
    private void cargarNombre() {

        SharedPreferences prefs =
                getSharedPreferences(
                        "wishlist",
                        MODE_PRIVATE
                );

        String nombre =
                prefs.getString("nombre", "");

        txtNombre.setText(nombre);
    }
}