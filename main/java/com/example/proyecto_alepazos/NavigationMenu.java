package com.example.proyecto_alepazos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.okhttp.internal.DiskLruCache;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.w3c.dom.Text;

import java.net.SocketOption;
import java.util.Iterator;
import java.util.Random;

public class NavigationMenu extends AppCompatActivity{

    private TextView saludo;
    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth mAuth;
    private TextView idultimacompra;
    private TextView nombreultimacompra;
    private TextView precioultimocomprado;
    private ImageView imagenultimacompra;
    private TextView idultimacompra_setups;
    private TextView nombreultimacompra_setups;
    private TextView precioultimocomprado_setups;
    private ImageView imagenultimacompra_setups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_menu);


        saludo = (TextView) findViewById(R.id.saludo_nav_main);
        idultimacompra = findViewById(R.id.idultimacompra);
        nombreultimacompra = findViewById(R.id.contenidoultimacompra);
        imagenultimacompra = (ImageView) findViewById(R.id.imagenultimocomprado);
        precioultimocomprado = findViewById(R.id.precioultimocomprado);
        idultimacompra_setups = findViewById(R.id.idultimacompra_setups);
        nombreultimacompra_setups = findViewById(R.id.contenidoultimacompra_setups);
        imagenultimacompra_setups = findViewById(R.id.imagenultimocomprado_setups);
        precioultimocomprado_setups = findViewById(R.id.precioultimocomprado_setups);

        Toolbar appbar = (Toolbar) findViewById(R.id.appbar_home);
        setSupportActionBar(appbar);

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        NavigationView navigationView = findViewById(R.id.nav_view_home);

        final View headerView = navigationView.getHeaderView(0);

        //------------------------------------------------   PEDIR VALORES A LA BBDD -----------------------------------------------------------

        final DatabaseReference db_user = FirebaseDatabase.getInstance().getReference().child("usuarios").child(user.getUid());
        final Query last_ordenadorquery = db_user.child("lista_ordenadores").orderByKey().limitToLast(1);
        final Query last_setupquery = db_user.child("lista_montajes").orderByKey().limitToLast(1);



        db_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(user != null){
                    TextView nombremenu = (TextView) headerView.findViewById(R.id.nombre_nav_menu);
                    nombremenu.setText(snapshot.child("email").getValue().toString());
                }
                saludo.setText("¡Bienvenido, " + snapshot.child("nombre").getValue().toString() + "!");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Historial compras

        last_ordenadorquery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(user != null){
                    idultimacompra.setText("Identificador: " + snapshot.getKey());
                    nombreultimacompra.setText("Nombre: " + snapshot.child("nombre").getValue().toString());
                    precioultimocomprado.setText(snapshot.child("precio").getValue().toString() + " €");
                    Picasso.get().load(snapshot.child("imagen").getValue().toString()).into(imagenultimacompra);    //descarga la imagen desde la url dada
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(user != null){
                    idultimacompra.setText("Identificador: " + snapshot.getKey());
                    nombreultimacompra.setText("Nombre: " + snapshot.child("nombre").getValue().toString());
                    precioultimocomprado.setText(snapshot.child("precio").getValue().toString() + " €");
                    Picasso.get().load(snapshot.child("imagen").getValue().toString()).into(imagenultimacompra);    //descarga la imagen desde la url dada
                }
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) { }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });


        //Historial compras

        last_setupquery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Random componente_azar = new Random();  //la imagen asignada al montaje/setup será una aleatoria de cualquier componente de dicho montaje

                if(user != null){
                    idultimacompra_setups.setText("Identificador: " + snapshot.getKey());
                    nombreultimacompra_setups.setText("Nombre: " + snapshot.child("nombre_setup").getValue().toString());
                    precioultimocomprado_setups.setText(snapshot.child("precio_final").getValue().toString());
                    Picasso.get().load(snapshot.child(String.valueOf(componente_azar.nextInt(4))).child("imagen").getValue().toString()).into(imagenultimacompra);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Random componente_azar = new Random();  //la imagen asignada al montaje/setup será una aleatoria de cualquier componente de dicho montaje

                if(user != null){
                    idultimacompra_setups.setText("Identificador: " + snapshot.getKey());
                    nombreultimacompra_setups.setText("Nombre: " + snapshot.child("nombre_setup").getValue().toString());
                    precioultimocomprado_setups.setText(snapshot.child("precio_final").getValue().toString());
                    Picasso.get().load(snapshot.child(String.valueOf(componente_azar.nextInt(4))).child("imagen").getValue().toString()).into(imagenultimacompra);
                }
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) { }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        //----------------------------------------------------------------------------------------------------------------------------


        //------------------------------------------------  NAVIGATOR CONFIG  -----------------------------------------------------------

        DrawerLayout drawer = findViewById(R.id.drawer_layout_home);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, appbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_comprar, R.id.nav_vender)
                .setDrawerLayout(drawer)
                .build();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressWarnings("StatementWithEmptyBody")
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                // Handle navigation view item clicks here.
                int id = item.getItemId();

                switch (id) {
                    case R.id.nav_comprar:
                        Intent comprar = new Intent(NavigationMenu.this, PantallaCompra.class);
                        startActivity(comprar);
                        break;
                    case R.id.nav_vender:
                        Intent vender = new Intent(NavigationMenu.this, PantallaVenta.class);
                        startActivity(vender);
                        break;
                    case R.id.nav_home:
                        break;
                    case R.id.submenu_salir_1:
                        finish();
                        break;
                    case R.id.submenu_salir_2: {
                        Intent salir2 = new Intent(NavigationMenu.this, EditarCuenta.class);
                        startActivity(salir2); }
                    break;
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_home);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        navigationView.setItemIconTintList(null);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(NavigationMenu.this, MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}