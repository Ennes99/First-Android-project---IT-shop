package com.example.proyecto_alepazos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class PantallaVenta extends AppCompatActivity{
    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth mAuth;
    private ArrayList<Articulo> tabla_articulos = new ArrayList<>();
    private ArrayList<Articulo> tabla_reparar = new ArrayList<>();
    private RecyclerView tabla;
    private Button buttonreparar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_menu_venta);

        DrawerLayout drawer = findViewById(R.id.drawer_layout_venta);

        Toolbar appbar = (Toolbar) findViewById(R.id.appbar_venta);
        setSupportActionBar(appbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, appbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        tabla = findViewById(R.id.listareparar);
        buttonreparar = findViewById(R.id.buttonreparar);

        NavigationView navigationView = findViewById(R.id.nav_view_venta);
        final View headerView = navigationView.getHeaderView(0);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_comprar, R.id.nav_vender)
                .setDrawerLayout(drawer)
                .build();


        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();


        //------------------------------------------------   PEDIR VALORES A LA BBDD -----------------------------------------------------------

        DatabaseReference db_user = FirebaseDatabase.getInstance().getReference().child("usuarios").child(user.getUid());

        db_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(user != null){
                    TextView nombremenu = (TextView) headerView.findViewById(R.id.nombre_nav_menu);
                    nombremenu.setText(snapshot.child("email").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        DatabaseReference ref_ordenadores_user = FirebaseDatabase.getInstance().getReference().child("usuarios").child(user.getUid()).child("lista_ordenadores");
        final DatabaseReference ref_componentes_user = FirebaseDatabase.getInstance().getReference().child("usuarios").child(user.getUid()).child("lista_montajes"); //recorrer todos los componentes, del 0 al 5



        ref_ordenadores_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final Iterator it = snapshot.getChildren().iterator();

                while(it.hasNext()){
                    DataSnapshot each = (DataSnapshot) it.next();
                    System.out.println(each.getKey()); //o directamente "características" o id_compra1

                    tabla_articulos.add(new Ordenador(each.child("nombre").getValue().toString(), Double.valueOf(each.child("precio").getValue().toString()),
                                each.child("caracteristicas").getValue().toString(), each.child("imagen").getValue().toString(), Integer.valueOf(each.child("maxstock").getValue().toString())));
                }

                tabla_reparar = createRecycler();  //aquí se pondrán todos los Ordenador en la tabla_reparar


                /*buttonreparar.setOnClickListener(new View.OnClickListener() {   //meto aquí el setOnClickListener para comparar el ArrayList eliminar con los values de la DatabaseRef, que son accesibles al suscribirse a la referencia
                    @Override
                    public void onClick(View v) {
                        for(Articulo cada_reparar: tabla_reparar){
                            while(it.hasNext()){
                                DataSnapshot each = (DataSnapshot) it.next();

                                if(each.child("nombre").getValue().toString().equals(cada_reparar.getNombre())){
                                    each.getRef().removeValue();
                                }
                            }
                        }


                        buttonreparar.setText("¡EN REPARACIÓN!");
                        startActivity(new Intent(PantallaVenta.this, NavigationMenu.class));
                    }
                });*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        ref_componentes_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final Iterator it = snapshot.getChildren().iterator();

                while(it.hasNext()){
                    DataSnapshot each = (DataSnapshot) it.next();

                    Iterator it_numcomponente = each.getChildren().iterator();

                    while(it_numcomponente.hasNext()){
                        DataSnapshot each_numcomponente = (DataSnapshot) it_numcomponente.next();

                        if(each_numcomponente.getKey().equals("0") || each_numcomponente.getKey().equals("1") || each_numcomponente.getKey().equals("2") || each_numcomponente.getKey().equals("3") ||each_numcomponente.getKey().equals("4")){
                            tabla_articulos.add(new Componente(each_numcomponente.child("nombre").getValue().toString(), Double.valueOf(each_numcomponente.child("precio").getValue().toString()),
                                    each_numcomponente.child("caracteristicas").getValue().toString(), each_numcomponente.child("imagen").getValue().toString()));
                        }
                    }
                }

                tabla_reparar = createRecycler();  //aquí se "actualizará" la tabla reparar, ya que el recycler contendrá ahora también los Componentes


                /*buttonreparar.setOnClickListener(new View.OnClickListener() {   //meto aquí el setOnClickListener para comparar el ArrayList eliminar con los values de la DatabaseRef, que son accesibles al suscribirse a la referencia
                    @Override
                    public void onClick(View v) {
                        for(Articulo cada_reparar: tabla_reparar){
                            while(it.hasNext()){
                                DataSnapshot each = (DataSnapshot) it.next();

                                Iterator it_numcomponente = each.getChildren().iterator();

                                while(it_numcomponente.hasNext()){
                                    DataSnapshot each_numcomponente = (DataSnapshot) it_numcomponente.next();
                                    if(each_numcomponente.child("nombre").getValue().toString().equals(cada_reparar.getNombre())){
                                        each.getRef().removeValue();
                                    }
                                }
                            }
                        }

                        buttonreparar.setText("¡EN REPARACIÓN!");
                        startActivity(new Intent(PantallaVenta.this, NavigationMenu.class));
                    }
                });*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //----------------------------------------------------------------------------------------------------------------------------------

        //------------------------------------------------  NAVIGATOR CONFIG  -----------------------------------------------------------

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressWarnings("StatementWithEmptyBody")
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_comprar_venta:
                        Intent vender = new Intent(PantallaVenta.this, PantallaCompra.class);
                        startActivity(vender);
                        break;
                    case R.id.nav_vender_venta:

                        break;
                    case R.id.nav_home_venta:
                        Intent home = new Intent(PantallaVenta.this, NavigationMenu.class);
                        startActivity(home);
                        break;
                    case R.id.submenu_salir_1_venta:
                        finish();
                        break;
                    case R.id.submenu_salir_2_venta: {
                        Intent salir2 = new Intent(PantallaVenta.this, EditarCuenta.class);
                        startActivity(salir2); }
                    break;
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_venta);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        navigationView.setItemIconTintList(null);

    }






    public ArrayList<Articulo> createRecycler() {
        final ArrayList aux_aeliminar = new ArrayList();
        tabla.setHasFixedSize(true);
        tabla.setLayoutManager(new GridLayoutManager(this, 2));
        FirebaseRecAdapterArticulo adapter =new FirebaseRecAdapterArticulo(tabla_articulos);
        tabla.setAdapter(adapter);


        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(getResources().getColor(R.color.coloresInterfazPrincipal));

                Articulo a = tabla_articulos.get(tabla.getChildAdapterPosition(v));
                aux_aeliminar.add(a);
            }
        });

        return aux_aeliminar;
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
            startActivity(new Intent(PantallaVenta.this, MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
