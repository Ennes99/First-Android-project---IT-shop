package com.example.proyecto_alepazos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
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

public class NavigationMenu extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    private static String nombreusuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_menu);


        TextView saludo = (TextView) findViewById(R.id.saludo_nav_main);
        Toolbar appbar = (Toolbar) findViewById(R.id.appbar_home);
        setSupportActionBar(appbar);

        NavigationView navigationView = findViewById(R.id.nav_view_home);

        View headerView = navigationView.getHeaderView(0);

        if(this.getIntent().getExtras() != null){
            nombreusuario = this.getIntent().getExtras().getString("usuario");
            TextView nombremenu = (TextView) headerView.findViewById(R.id.nombre_nav_menu);
            nombremenu.setText(this.getIntent().getExtras().getString("usuario"));
        }
        saludo.setText("¡Bienvenido," + nombreusuario + "!");



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

                switch (item.getItemId()) {
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
                        Intent salir2 = new Intent(NavigationMenu.this, MainActivity.class);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}