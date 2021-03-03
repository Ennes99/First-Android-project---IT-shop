package com.example.proyecto_alepazos;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CompraFinal extends AppCompatActivity {
    private static int id_compra = 0;
    private TextView sumatotal;
    private boolean esPorMontaje;
    private TextView nombrefinal;
    private TextView caracteristicasfinal;
    private ImageView fotofinal;
    private EditText nombresetup;
    private ToggleButton toggle;
    private TextView infoenvio;
    private Button confirmacompra;
    private FirebaseAuth mAuth;
    private ArrayList<Componente> montaje;
    private Ordenador ordenadoracomprar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra_final);

        nombrefinal = (TextView) findViewById(R.id.nombrefinal);
        caracteristicasfinal = (TextView) findViewById(R.id.caracteristicasfinal);
        fotofinal = (ImageView) findViewById(R.id.fotofinal);
        nombresetup = findViewById(R.id.nombre_setup);
        sumatotal = (TextView) findViewById(R.id.sumacomprafinal);
        infoenvio = (TextView) findViewById(R.id.infoenvio);
        montaje = (ArrayList<Componente>) this.getIntent().getSerializableExtra("seleccionados");
        ordenadoracomprar= (Ordenador) this.getIntent().getSerializableExtra("ordenadoracomprar");

        toggle = (ToggleButton) findViewById(R.id.togglebutton);
        confirmacompra = (Button) findViewById(R.id.confirmacompra);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final DatabaseReference db_user = FirebaseDatabase.getInstance().getReference().child("usuarios").child(user.getUid());

        Bundle recogermochila = this.getIntent().getExtras();
        esPorMontaje = recogermochila.getBoolean("esPorMontaje");



        mostrarDatosCompra();



        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    confirmacompra.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    confirmacompra.setText("ENVIAR");
                }
                else{
                    confirmacompra.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                    confirmacompra.setText("CANCELAR");
                }
            }
        });



        confirmacompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (confirmacompra.getText().toString()){
                    case "ENVIAR" : {
                        id_compra++;
                            if(esPorMontaje){
                                db_user.child("lista_montajes").child("id_compra_setup"+ id_compra).setValue(montaje);
                                db_user.child("lista_montajes").child("id_compra_setup" + id_compra).child("nombre_setup").setValue(nombresetup.getText().toString());
                                db_user.child("lista_montajes").child("id_compra_setup" + id_compra).child("precio_final").setValue(sumatotal.getText().toString());
                            }
                            else db_user.child("lista_ordenadores").child("id_compra_"+ id_compra).setValue(ordenadoracomprar);
                        confirmacompra.setText("¡PETICIÓN DE\nCOMPRA ENVIADA!");

                    }
                    case "CANCELAR" : {
                        Intent atras = new Intent(CompraFinal.this, NavigationMenu.class);
                        startActivity(atras);
                    }
                    default :
                }
            }
        });
    }








    private void mostrarDatosCompra(){
        if(esPorMontaje){
            nombresetup.setVisibility(View.VISIBLE);
            String auxnombres = "";
            //String auxcaracteristicas = "";

            for(int i = 0; i<montaje.size(); i++){
                auxnombres += montaje.get(i).getNombre() + "+\n";
                //auxcaracteristicas += seleccionados.get(i).getNombre() + "+\n";
            }
            nombrefinal.setText(auxnombres);
            //caracteristicasfinal.setText(auxcaracteristicas);

            sumatotal.setText(String.valueOf(this.getIntent().getExtras().getDouble("sumafinal")) + " €");
            infoenvio.setText("Dirección: " + this.getIntent().getExtras().getString("barrio_o_direccion") + "\nCiudad "
                    +this.getIntent().getExtras().getString("ciudad") + "\nCCAA: " + this.getIntent().getExtras().getString("ccaa")
                    + "\nPodrás ver lo que has comprado en el menú home o al gestionar tus ventas.");

        }
        else{
            fotofinal.setVisibility(View.VISIBLE);

            nombrefinal.setText(ordenadoracomprar.getNombre());
            caracteristicasfinal.setText(ordenadoracomprar.getCaracteristicas());
            //fotofinal.setImageResource(ordenadoracomprar.getImagen());    --------------------------------------------------  CAMBIAR A PICASSO
            sumatotal.setText(String.valueOf(ordenadoracomprar.getPrecio() * ordenadoracomprar.getContador()) + " €");

            infoenvio.setText("Te hemos asignado la tienda más cercana a tu barrio, " + this.getIntent().getExtras().getString("barrio_o_direccion") +
                    ", para recoger el pedido:\nPRIMERA INFORMÁTICA CC LAS ARENAS -- LAS PALMAS DE GRAN CANARIA" + "\nPodrás ver lo que has comprado en el menú home o al gestionar tus ventas.");
        }
    }
}
