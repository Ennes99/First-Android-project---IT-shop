package com.example.proyecto_alepazos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        final Ordenador[] ordenadores = {new Ordenador("APPLE I6 TC I", 807.42, "BLACK MAC SPACE MINI 5 -- i5 9400, RAM 8 GB, SSD 512 GB", R.drawable.apple807,1),
                new Ordenador("ULTIMA CORE", 724.67, "AMD Ryzen 5 3600, RAM 8 GB, HDD 1 TB + SSD 240 GB, gráfica GTX 1650 SUPER 4GB", R.drawable.core724, 2),
                new Ordenador("ULTIMA EXTREME ", 3199.33, "AMD Ryzen 7 5800X, RAM 32 GB, HDD 2 TB + 512 SSD, gráfica RTX3090 24GB", R.drawable.extreme3199, 3),
                new Ordenador("ULTIMA ADVANCE", 905.45, "i5-10400F, RAM 16 GB, HDD 1TB, gráfica GTX 1660 SUPER 6GB", R.drawable.advance905, 4),
                new Ordenador("APPLE MAC GREY", 807.78, "SPACE MINI I3 QC I3 -- i7 10700, RAM 8 GB, SSD 256 GB", R.drawable.apple807, 2),
                new Ordenador("ULTIMA ATOM", 679.99, "i5 10400F, RAM 8 GB, HDD 1 TB + SSD 240 TB, gráfica GTX 1650 SUPER 4GB", R.drawable.atom679, 3),
                new Ordenador("ULTIMA CREATOR", 1283.11, "AMD Ryzen 7 5800X, RAM 16 GB, HDD 1 TB, gráfica GTX 1650 SUPER 4GB", R.drawable.creator1283, 1),
                new Ordenador("ULTIMA CREATOR BLANCO", 1283.61, "AMD Ryzen 7 5800X, RAM 16 GB, HDD 1 TB, gráfica GTX 1650 SUPER 4GB", R.drawable.creator1283blanco, 4),
                new Ordenador("DELL OPTIPLEX", 632.79, "7070 MT CJ89Y NEGRO -- i5-9500, RAM 8 GB, SSD 256 GB", R.drawable.delloptiplex632, 1),
                new Ordenador("DELL VOSTRO\n 7DF0K", 588.60, "3681 SFF NEGRO -- i5-10400, RAM 8 GB, SSD 512 GB", R.drawable.dellvostro588, 1),
                new Ordenador("DELL VOSTRO\n 025NC", 656.12, "3681 SFF NEGRO -- i5-10400, RAM 8 GB, SSD 512 GB", R.drawable.dellvostro656, 1),
                new Ordenador("DIFFERO DFi398-01", 227.31, "NEGRO MIDI TORRE PC -- i3-9100F, RAM 4 GB, SSD 512 GB", R.drawable.differo277y357, 2),
                new Ordenador("ELITEALGO ", 1703.21, "244TR -- AMD Ryzen 5 3600X, RAM 16 GB, HDD 1 TB", R.drawable.elite1703, 3),
                new Ordenador("DIFFERO DFi598-01", 337.47, "NEGRO MIDI -- i3-9100F, RAM 8 GB, SSD 512 GB", R.drawable.differo277y357, 4),
                new Ordenador("LENOVO 720", 471.23, "IDEACENTRE -- AMD Ryzen 5 2400G, RAM 8 GB, HDD 1 TB", R.drawable.lenovo471, 5),
                new Ordenador("LENOVO 620S", 539.66, "IDEACENTRE -- i5-7400T, RAM 8 GB, HDD 2 TB", R.drawable.lenovo539, 1),
                new Ordenador("MSI CODEX 5", 918.37, "i5-10400F, RAM 8 GB, HDD 3 TB", R.drawable.mag918, 2),
                new Ordenador("MSI INFINITE", 963.66, "i5-10400F, RAM 16 GB, HDD 2 TB + SSD 512 GB", R.drawable.maginfinite963, 4),
                new Ordenador("ULTIMA MASTERRACE", 2319.12, "i9-10900KF, RAM 16 GB, HDD 2 TB + SSD 500 GB, gráfica RTX3080 10GB", R.drawable.masterrace2319, 4),
                new Ordenador("ULTIMA MEGA", 1132.44, "i5 10600KF, RAM 16 GB, HDD 1 TB + SSD 240 GB, gráfica RTX2060 6GB", R.drawable.mega1132, 3),
                new Ordenador("ULTIMA NZXT", 1259.53, "i5-10400F, RAM 16GB, HDD 1 TB + SSD 512 GB, gráfica RTX2060 6GB", R.drawable.nzxt1259, 3),
                new Ordenador("ULTIMA PRESTIGE", 1649.88, "AMD Ryzen 5 3600, RAM 16 GB, HDD 1 TB + SSD 240 GB, gráfica GTX 1660 SUPER 6GB", R.drawable.prestige1649, 3),
                new Ordenador("ULTIMA PRO", 1331.11, "i7-10700KF, RAM 16 GB, HDD 2 TB + SSD 500 GB, gráfica RTX 2060 6GB", R.drawable.prointel1331, 2),
                new Ordenador("ULTIMA SUPRA", 1032.55, "i7-10700F, RAM 16 GB, HDD 1 TB + SSD 240 GB, gráfica GTX 1660 SUPER 6GB", R.drawable.supra1032, 2),
                new Ordenador("MSI TRIDENT 3 10SI-022EU", 1101.32, "NEGRO -- i5-10400F, RAM 8 GB, HDD 1 TB + SSD 512 GB", R.drawable.trident1101, 2),
                new Ordenador("MSI TRIDENT 3 10SI-016EU", 1283.54, "NEGRO -- i7-10700, RAM 16 GB, SSD 512 GB", R.drawable.trident1283, 4),
                new Ordenador("ULTIMA ULTRA", 1177.98, "AMD Ryzen 7 3700X, RAM 16 GB, HDD 1 TB + SSD 240 GB, gráfica RTX 2060 6GB", R.drawable.ultra1177, 1),
                new Ordenador("MSI VORTEX 224ES", 1497.62, "i7-9700, RAM 16 GB, SSD 512 GB", R.drawable.vortex1497, 1),
                new Ordenador("MSI VORTEX 223ES", 2041.59, "i7-9700, RAM 32 GB, HDD 1 TB + SSD 512 GB", R.drawable.vortex2041, 4),
                new Ordenador("MSI VORTEX 222ES", 2675.43, "i7-10700, RAM 32 GB, HDD 2 TB + SSD 512 GB", R.drawable.vortex2675, 2),
                new Ordenador("ULTIMA ZERO", 543.43, "i3-10100, RAM 8 GB, HDD 1 TB, gráfica GTX1650 4GB", R.drawable.zerointel543, 5)};


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

}
