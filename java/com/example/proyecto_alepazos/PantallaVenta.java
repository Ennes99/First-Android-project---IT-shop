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

import com.google.android.material.navigation.NavigationView;

public class PantallaVenta extends AppCompatActivity{
    private AppBarConfiguration mAppBarConfiguration;

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

        NavigationView navigationView = findViewById(R.id.nav_view_venta);

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
                        Intent salir2 = new Intent(PantallaVenta.this, MainActivity.class);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
