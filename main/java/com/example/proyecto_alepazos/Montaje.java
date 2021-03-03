package com.example.proyecto_alepazos;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Iterator;

public class Montaje extends AppCompatActivity {
    private TabHost hospedador;
    private RecyclerView lista1; //placas
    private RecyclerView lista2; //procesadores
    private RecyclerView lista3; //memorias
    private RecyclerView lista4; //discos
    private RecyclerView lista5; //gráficas
    private Button atras;
    private Button comprar;
    private double sumacompra = 0;
    private ArrayList<Componente> comp_seleccionados;  //para pasar la info de los Componentes seleccionados
    private ToggleButton noquierografica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_montaje);

        comp_seleccionados = new ArrayList<>();

        hospedador = (TabHost) findViewById(android.R.id.tabhost);
        lista1 = (RecyclerView) findViewById(R.id.lista1);
        lista2 = (RecyclerView) findViewById(R.id.lista2);
        lista3 = (RecyclerView) findViewById(R.id.lista3);
        lista4 = (RecyclerView) findViewById(R.id.lista4);
        lista5 = (RecyclerView) findViewById(R.id.lista5);
        atras = (Button) findViewById(R.id.volver);
        comprar = (Button) findViewById(R.id.comprar_list);
        noquierografica = (ToggleButton) findViewById(R.id.noquierografica);

        final Componente[] placas = new Componente[5];
        final Componente[] procesadores = new Componente[5];
        final Componente[] memorias = new Componente[10];
        final Componente[] discosduros = new Componente[5];
        final Componente[] graficas = new Componente[9];


        DatabaseReference ref_placas = FirebaseDatabase.getInstance().getReference().child("componentes").child("placas");
        DatabaseReference ref_memos = FirebaseDatabase.getInstance().getReference().child("componentes").child("memorias");
        DatabaseReference ref_proc = FirebaseDatabase.getInstance().getReference().child("componentes").child("procesadores");
        DatabaseReference ref_discos = FirebaseDatabase.getInstance().getReference().child("componentes").child("discosduros");
        DatabaseReference ref_graficas = FirebaseDatabase.getInstance().getReference().child("componentes").child("graficas");

        //----------------------------------------------------------------------------------------------------------------------------------------------------------

        //--------------------------------------------------------- HACER ARRAY PARA RECYCLER DE PLACAS ------------------------------------------------------------

        ref_placas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator it = snapshot.getChildren().iterator();
                int i = 0;

                while(it.hasNext()){
                    DataSnapshot each = (DataSnapshot) it.next();
                    System.out.println(each.getKey());
                    placas[i] = new Componente(each.child("nombre").getValue().toString(), Double.valueOf(each.child("precio").getValue().toString()),
                            each.child("caracteristicas").getValue().toString(), each.child("imagen").getValue().toString());
                    i++;
                }

                crearRecycler(lista1, "PL", placas, R.id.cuantos_list1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //----------------------------------------------------------------------------------------------------------------------------------------------------------

        //--------------------------------------------------------- HACER ARRAY PARA RECYCLER DE MEMORIAS ------------------------------------------------------------

        ref_memos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator it = snapshot.getChildren().iterator();
                int i = 0;

                while(it.hasNext()){
                    DataSnapshot each = (DataSnapshot) it.next();
                    memorias[i] = new Componente(each.child("nombre").getValue().toString(), Double.valueOf(each.child("precio").getValue().toString()),
                            each.child("caracteristicas").getValue().toString(), each.child("imagen").getValue().toString());
                    i++;
                }

                crearRecycler(lista3, "ME", memorias, R.id.cuantos_list3);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //----------------------------------------------------------------------------------------------------------------------------------------------------------

        //--------------------------------------------------------- HACER ARRAY PARA RECYCLER DE PROCESADORES ------------------------------------------------------------

        ref_proc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator it = snapshot.getChildren().iterator();
                int i = 0;

                while(it.hasNext()){
                    DataSnapshot each = (DataSnapshot) it.next();
                    procesadores[i] = new Componente(each.child("nombre").getValue().toString(), Double.valueOf(each.child("precio").getValue().toString()),
                            each.child("caracteristicas").getValue().toString(), each.child("imagen").getValue().toString());
                    i++;
                }

                crearRecycler(lista2, "PR", procesadores, R.id.cuantos_list2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //----------------------------------------------------------------------------------------------------------------------------------------------------------

        //--------------------------------------------------------- HACER ARRAY PARA RECYCLER DE GRAFICAS ------------------------------------------------------------


        ref_graficas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator it = snapshot.getChildren().iterator();
                int i = 0;

                while(it.hasNext()){
                    DataSnapshot each = (DataSnapshot) it.next();
                    graficas[i] = new Componente(each.child("nombre").getValue().toString(), Double.valueOf(each.child("precio").getValue().toString()),
                            each.child("caracteristicas").getValue().toString(), each.child("imagen").getValue().toString());
                    i++;
                }

                crearRecycler(lista5, "GR", graficas, R.id.cuantos_list5);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //----------------------------------------------------------------------------------------------------------------------------------------------------------

        //--------------------------------------------------------- HACER ARRAY PARA RECYCLER DE DISCOS ------------------------------------------------------------

        ref_discos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator it = snapshot.getChildren().iterator();
                int i = 0;

                while(it.hasNext()){
                    DataSnapshot each = (DataSnapshot) it.next();
                    discosduros[i] = new Componente(each.child("nombre").getValue().toString(), Double.valueOf(each.child("precio").getValue().toString()),
                            each.child("caracteristicas").getValue().toString(), each.child("imagen").getValue().toString());
                    i++;
                }

                crearRecycler(lista4, "DI", discosduros, R.id.cuantos_list4);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //---------------------------------------------------------------------------------------------------------------------------------------------------------


        hospedador.setup();


        TabHost.TabSpec spec=hospedador.newTabSpec("placas");
        spec.setContent(R.id.placa);
        spec.setIndicator("PLACA", getResources().getDrawable(android.R.drawable.ic_btn_speak_now,null));
        hospedador.addTab(spec);

        TabHost.TabSpec spec2=hospedador.newTabSpec("procesadores");
        spec2.setContent(R.id.Proc);
        spec2.setIndicator("PROC", getResources().getDrawable(android.R.drawable.ic_btn_speak_now,null));
        hospedador.addTab(spec2);

        TabHost.TabSpec spec3=hospedador.newTabSpec("memorias");
        spec3.setContent(R.id.Memo);
        spec3.setIndicator("MEMO", getResources().getDrawable(android.R.drawable.ic_btn_speak_now,null));
        hospedador.addTab(spec3);

        TabHost.TabSpec spec4=hospedador.newTabSpec("discosduros");
        spec4.setContent(R.id.Discos);
        spec4.setIndicator("DISCS", getResources().getDrawable(android.R.drawable.ic_btn_speak_now,null));
        hospedador.addTab(spec4);

        TabHost.TabSpec spec5=hospedador.newTabSpec("gráficas");
        spec5.setContent(R.id.grafic);
        spec5.setIndicator("GRAFIC", getResources().getDrawable(android.R.drawable.ic_btn_speak_now,null));
        hospedador.addTab(spec5);

        hospedador.setCurrentTab(0);





        comprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean esPorMontaje = true; //boolean, similar al de OrdenadoresYaMontados.java,  que le servirá a CompraFinal.java para saber si recibe los datos de una o de otra activity

                Bundle mochila = new Bundle();
                mochila.putBoolean("esPorMontaje", esPorMontaje);
                mochila.putDouble("sumafinal", sumacompra);
                mochila.putString("barrio_o_direccion", Montaje.this.getIntent().getExtras().getString("barrio_o_direccion"));
                mochila.putString("ciudad", Montaje.this.getIntent().getExtras().getString("ciudad"));
                mochila.putString("ccaa", Montaje.this.getIntent().getExtras().getString("ccaa"));

                Intent envio = new Intent(Montaje.this, CompraFinal.class);
                envio.putExtra("seleccionados", comp_seleccionados);
                envio.putExtras(mochila);
                startActivity(envio);
            }
        });


        noquierografica.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    noquierografica.setBackgroundColor(getResources().getColor(R.color.toggleOn));
                    noquierografica.setTextOn("SÍ");
                    lista5.setVisibility(View.VISIBLE);
                }
                else{
                    noquierografica.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    noquierografica.setTextOff("NO");
                    lista5.setVisibility(View.INVISIBLE);
                }
            }
        });

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }





    public void crearRecycler(final RecyclerView querecycler, final String que_lista, final Componente[] componentes, final int cuantos_list) {
        querecycler.setHasFixedSize(true);


        RecyclerAdapterArticulo adapter = new RecyclerAdapterArticulo(componentes);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                componenteEvent(v, querecycler, que_lista, componentes, cuantos_list);
            }
        });

        querecycler.setAdapter(adapter);
        querecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }




    public void componenteEvent(View v, RecyclerView querecycler, String que_lista, Componente[] que_comps, int que_cuantos){
        TextView dinerocompra = (TextView) findViewById(R.id.dinerocompra_list);
        TextView cuantos = (TextView) findViewById(que_cuantos);
        Componente c = que_comps[querecycler.getChildAdapterPosition(v)];

        if (!listaOcupada(que_lista)){
            v.setBackgroundColor(getResources().getColor(R.color.coloresInterfazPrincipal));
            sumacompra+=c.getPrecio();
            comp_seleccionados.add(c);
            dinerocompra.setText(sumacompra + " €");
        }
        else if(mismoQueEnLista(c)){
            v.setBackgroundColor(getResources().getColor(R.color.buttonColor));
            sumacompra-=c.getPrecio();
            comp_seleccionados.remove(c);
            dinerocompra.setText(sumacompra + " €");
            cuantos.setText("");
        }
        else if (listaOcupada(que_lista) && !mismoQueEnLista(c)){
            cuantos.setText("Ya hay uno seleccionado");
        }
    }


    public boolean listaOcupada(String que_lista){ //hago dos métodos separados para darse el caso de, o bien está en la lista y es el mismo (se reinicia) o bien no lo es (avisa de que ya hay uno)
        for(Componente each: comp_seleccionados){   //listaOcupada se refiere, por ejemplo, a si ya hay otra gráfica seleccionada en la lista de gráficas, que no tiene por qué ser la misma
            if(each.getNombre().contains(que_lista)){
                return true;
            }
        }
        return false;
    }

    public boolean mismoQueEnLista(Componente c){
        for(Componente each: comp_seleccionados){
            if(each.equals(c)){
                return true;
            }
        }
        return false;
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
            startActivity(new Intent(Montaje.this, MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
