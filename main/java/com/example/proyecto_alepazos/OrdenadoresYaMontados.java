package com.example.proyecto_alepazos;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Iterator;

public class OrdenadoresYaMontados extends AppCompatActivity {
    private RecyclerView tabla;
    private Button volver;
    private Button comprar;
    private TextView dinerocompra;
    private Ordenador ordenadoracomprar = null; //donde se guardará el ordenador que vayamos a comprar, para mandarlo por el botón "comprar_yamontados"
    private double sumacompra = 0;          //aquí entra en juego el maxstock: el usuario sólo podrá comprar un tipo de ordenador (indicárselo en el evento si intenta seleccionar otro...añadir TexView en rojo o algo)
    //y, de ese ordenador, podrá comprar tantos como quiera hasta un máximo, los que hay en stock en tienda

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenadores_ya_montados);

        tabla = (RecyclerView) findViewById(R.id.RecView);
        dinerocompra = (TextView) findViewById(R.id.dinerocompra);
        volver = (Button) findViewById(R.id.volver);
        comprar = (Button) findViewById(R.id.comprar_yamontados);

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("ordenadores");

        final Ordenador[] ordenadores = new Ordenador[29];



        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator it = snapshot.getChildren().iterator();
                int i = 0;

                while(it.hasNext()){
                    DataSnapshot each = (DataSnapshot) it.next();
                    ordenadores[i] = new Ordenador(each.child("nombre").getValue().toString(), Double.valueOf(each.child("precio").getValue().toString()),
                            each.child("caracteristicas").getValue().toString(), each.child("imagen").getValue().toString(), Integer.valueOf(each.child("maxstock").getValue().toString()));
                    i++;
                }

                crearRecycler(ordenadores);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        final String barriodireccion = this.getIntent().getExtras().getString("barrio_o_direccion");
        final String ciudad = this.getIntent().getExtras().getString("ciudad");
        final String ccaa = this.getIntent().getExtras().getString("ccaa");

        comprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean esPorMontaje = false;
                Bundle mochila = new Bundle();
                mochila.putString("barrio_o_direccion", barriodireccion);
                mochila.putString("barrio_o_direccion", ciudad);
                mochila.putString("barrio_o_direccion", ccaa);
                //mochila.putDouble("");   -- NO HACE FALTA PASARLE DINEROCOMPRA PORQUE EL DINERO SERÁ EL PRECIO DE UN ORDENADOR * Nº ORDS., YA QUE SOLO PUEDES ELEGIR DE UN TIPO
                mochila.putBoolean("esPorMontaje", esPorMontaje);
                Intent envio = new Intent(OrdenadoresYaMontados.this, CompraFinal.class);
                envio.putExtras(mochila);
                envio.putExtra("ordenadoracomprar", ordenadoracomprar);
                startActivity(envio);
            }
        });

    }






    public void crearRecycler(final Ordenador[] ordenadores){
        tabla.setHasFixedSize(true); //optimizar el recycler. El tamaño no va a variar, así que es conveniente
        RecyclerAdapterArticulo adapter = new RecyclerAdapterArticulo(ordenadores); //ArticuloAdapter, admite Artículos, es decir, Ordenadores y Componentes


        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView cuantos = (TextView) findViewById(R.id.cuantos);           //al ser un Recycler, destruye y crea los items conforme vamos haciendo scroll, por tanto
                Ordenador o = ordenadores[tabla.getChildAdapterPosition(v)];        //no se mantiene el view que tengo pulsado en la lista, se va cambiando (aunque los datos que pasa a CompraFinal sí son los mismos)

                if(ordenadoracomprar != null && ordenadoracomprar.equals(o)){
                    o.sumaContador();

                    if(o.getContador() >= 0 && o.getContador() < o.getMaxstock()){
                        v.setBackgroundColor(getResources().getColor(R.color.coloresInterfazPrincipal));
                        cuantos.setText("x " + o.getContador() + "/ " + o.getMaxstock());
                        sumacompra+=o.getPrecio();
                        dinerocompra.setText(sumacompra + " €");
                    }
                    else if(o.getContador() == o.getMaxstock()){
                        v.setBackgroundColor(getResources().getColor(R.color.tooManyPCs));
                        cuantos.setText("x " + o.getContador() + " - ya no quedan más");
                        sumacompra+=o.getPrecio();
                        dinerocompra.setText(sumacompra + " €");
                    }
                    else if(o.getContador() > o.getMaxstock()){
                        v.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                        sumacompra = 0.0;
                        dinerocompra.setText(sumacompra + " €");
                        o.resetContador();
                        cuantos.setText("");
                        ordenadoracomprar = null;

                    }
                }
                else if (ordenadoracomprar != null && !ordenadoracomprar.equals(o)){
                    cuantos.setText("Ya hay uno seleccionado");
                }
                else if (ordenadoracomprar == null){
                    ordenadoracomprar = o;
                    o.sumaContador();

                    if(o.getContador() >= 0 && o.getContador() < o.getMaxstock()){
                        v.setBackgroundColor(getResources().getColor(R.color.coloresInterfazPrincipal));
                        cuantos.setText("x " + o.getContador() + "/ " + o.getMaxstock());
                        sumacompra+=o.getPrecio();
                        dinerocompra.setText(sumacompra + " €");
                    }
                    else if(o.getContador() == (o.getMaxstock())){
                        v.setBackgroundColor(getResources().getColor(R.color.tooManyPCs));
                        cuantos.setText("x " + o.getContador() + " - ya no quedan más");
                        sumacompra+=o.getPrecio();
                        dinerocompra.setText(sumacompra + " €");
                    }
                    else if (o.getContador() > (o.getMaxstock())) {
                        v.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                        sumacompra = 0.0;
                        dinerocompra.setText(sumacompra + " €");
                        o.resetContador();
                        cuantos.setText("");
                        ordenadoracomprar = null;

                    }
                }
            }
        });



        tabla.setAdapter(adapter);
        tabla.setLayoutManager(new GridLayoutManager(this, 2));

    }



    //MENÚ PEQUEÑO DE LA DERECHA DEL TOOLBAR
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(OrdenadoresYaMontados.this, MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
