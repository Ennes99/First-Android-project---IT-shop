package com.example.proyecto_alepazos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class PantallaCompra extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    private Button atras;
    private Button enviar;
    private RadioButton opcionmontar;
    private RadioGroup grupo;
    private EditText direccion;
    private EditText ciudad;
    private EditText ccaa;
    private TextView informativo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_menu_compra);
        DrawerLayout drawer = findViewById(R.id.drawer_layout_compra);

        Toolbar appbar = (Toolbar) findViewById(R.id.appbar_compra);
        setSupportActionBar(appbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, appbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view_compra);

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

                switch (item.getItemId()) {
                    case R.id.nav_comprar_compra:
                        break;
                    case R.id.nav_vender_compra:
                        Intent vender = new Intent(PantallaCompra.this, PantallaVenta.class);
                        startActivity(vender);
                        break;
                    case R.id.nav_home_compra:
                        Intent home = new Intent(PantallaCompra.this, NavigationMenu.class);
                        startActivity(home);
                        break;
                    case R.id.submenu_salir_1_compra:
                        finish();
                        break;
                    case R.id.submenu_salir_2_compra: {
                        Intent salir2 = new Intent(PantallaCompra.this, MainActivity.class);
                        startActivity(salir2); }
                    break;
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_compra);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        navigationView.setItemIconTintList(null);


        //PROGRAMACIÓN DE LA PANTALLA EN SÍ:

        atras = (Button) findViewById(R.id.volver);
        grupo = (RadioGroup) findViewById(R.id.opcionescompra);
        opcionmontar = (RadioButton) findViewById(R.id.montar);
        direccion = (EditText) findViewById(R.id.direccion);
        ciudad = (EditText) findViewById(R.id.ciudad);
        ccaa = (EditText) findViewById(R.id.comunidad);
        enviar = (Button) findViewById(R.id.enviar);
        informativo = (TextView) findViewById(R.id.textoinformativo);


        grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(opcionmontar.getId() == checkedId){
                    informativo.setVisibility(View.VISIBLE);
                    direccion.setVisibility(View.VISIBLE);
                    ciudad.setVisibility(View.VISIBLE);
                    ccaa.setVisibility(View.VISIBLE);
                    enviar.setVisibility(View.VISIBLE);

                    informativo.setText("Te llevamos tus piezas a casa:");
                    direccion.setHint("Dirección");
                }

                else{
                    informativo.setVisibility(View.VISIBLE);
                    direccion.setVisibility(View.VISIBLE);
                    ciudad.setVisibility(View.VISIBLE);
                    ccaa.setVisibility(View.VISIBLE);
                    enviar.setVisibility(View.VISIBLE);

                    informativo.setText("Buscamos tu tienda más cercana:");
                    direccion.setHint("Barrio");
                    direccion.setWidth(150);
                }
            }
        });


        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle mochila = new Bundle();
                mochila.putString("barrio_o_direccion", direccion.getText().toString());
                mochila.putString("ciudad", ciudad.getText().toString());
                mochila.putString("ccaa", ccaa.getText().toString());

                Intent envio;
                if(opcionmontar.isChecked()){
                    envio = new Intent(PantallaCompra.this, Montaje.class);
                }
                else{
                    envio = new Intent(PantallaCompra.this, OrdenadoresYaMontados.class);
                }

                envio.putExtras(mochila);
                startActivity(envio);
            }
        });
    }
}