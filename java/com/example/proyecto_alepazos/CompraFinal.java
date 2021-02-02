package com.example.proyecto_alepazos;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CompraFinal extends AppCompatActivity {
    private int imagen; //solo para cuando la CompraFinal venga desde OrdenadoresYaMontados
    private TextView sumatotal;
    private boolean esPorMontaje;
    private TextView nombrefinal;
    private TextView caracteristicasfinal;
    private ImageView fotofinal;
    private ToggleButton toggle;
    private TextView infoenvio;
    private Button confirmacompra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra_final);

        nombrefinal = (TextView) findViewById(R.id.nombrefinal);
        caracteristicasfinal = (TextView) findViewById(R.id.caracteristicasfinal);
        fotofinal = (ImageView) findViewById(R.id.fotofinal);
        sumatotal = (TextView) findViewById(R.id.sumacomprafinal);
        infoenvio = (TextView) findViewById(R.id.infoenvio);

        toggle = (ToggleButton) findViewById(R.id.togglebutton);
        confirmacompra = (Button) findViewById(R.id.confirmacompra);

        Bundle recogermochila = this.getIntent().getExtras();
        esPorMontaje = recogermochila.getBoolean("esPorMontaje");

        if(esPorMontaje){
            ArrayList<Componente> seleccionados= (ArrayList<Componente>) this.getIntent().getSerializableExtra("seleccionados");
            String auxnombres = "";
            //String auxcaracteristicas = "";

            for(int i = 0; i<seleccionados.size(); i++){
                auxnombres += seleccionados.get(i).getNombre() + "+\n";
                //auxcaracteristicas += seleccionados.get(i).getNombre() + "+\n";
            }
            nombrefinal.setText(auxnombres);
            //caracteristicasfinal.setText(auxcaracteristicas);

            sumatotal.setText(String.valueOf(recogermochila.getDouble("sumafinal")) + " €");
            infoenvio.setText("Dirección: " + this.getIntent().getExtras().getString("barrio_o_direccion") + "\nCiudad "
                    +this.getIntent().getExtras().getString("ciudad") + "\nCCAA: " + this.getIntent().getExtras().getString("ccaa"));

        }
        else{
            Ordenador ordenadoracomprar= (Ordenador) this.getIntent().getSerializableExtra("ordenadoracomprar");
            nombrefinal.setText(ordenadoracomprar.getNombre());
            caracteristicasfinal.setText(ordenadoracomprar.getCaracteristicas());
            fotofinal.setImageResource(ordenadoracomprar.getImagen());
            sumatotal.setText(String.valueOf(ordenadoracomprar.getPrecio() * ordenadoracomprar.getContador()) + " €");

            infoenvio.setText("Te hemos asignado la tienda más cercana a tu barrio" + this.getIntent().getExtras().getString("barrio_o_direccion") +
                    "para recoger el pedido:\nPRIMERA INFORMÁTICA CC LAS ARENAS -- LAS PALMAS DE GRAN CANARIA");
        }


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
                    case "ENVIAR" : confirmacompra.setText("¡PETICIÓN DE\nCOMPRA ENVIADA!");
                    case "CANCELAR" : {
                        Intent atras = new Intent(CompraFinal.this, NavigationMenu.class);
                        startActivity(atras);
                    }
                    default :
                }
            }
        });
    }
}
