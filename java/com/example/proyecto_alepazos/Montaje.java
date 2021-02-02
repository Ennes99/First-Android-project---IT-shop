package com.example.proyecto_alepazos;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class Montaje extends AppCompatActivity {
    private TabHost hospedador;
    private RecyclerView lista;
    private RecyclerView lista2;
    private RecyclerView lista3;
    private RecyclerView lista4;
    private RecyclerView lista5;
    private Button atras;
    private Button comprar;
    private double sumacompra = 0;
    private ArrayList<Componente> comp_seleccionados;  //para pasar la info de los Componentes seleccionados
    private ToggleButton noquierografica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_montaje);

        comp_seleccionados = new ArrayList<Componente>();

        hospedador = (TabHost) findViewById(android.R.id.tabhost);
        lista = (RecyclerView) findViewById(R.id.lista1);
        lista2 = (RecyclerView) findViewById(R.id.lista2);
        lista3 = (RecyclerView) findViewById(R.id.lista3);
        lista4 = (RecyclerView) findViewById(R.id.lista4);
        lista5 = (RecyclerView) findViewById(R.id.lista5);
        atras = (Button) findViewById(R.id.volver);
        comprar = (Button) findViewById(R.id.comprar_list);
        noquierografica = (ToggleButton) findViewById(R.id.noquierografica);

        final Componente[] placas = {new Componente(" PL. ASROCK 234", 68.40, "Compatible con procesadores Intel, HDD sí", R.drawable.placasrock),
                new Componente("PL. GiGABYTE OT2", 106.20, "Compatible con procesadores AMD Ryzen (3, 5, 7) 3º gen, HDD y SSD sí", R.drawable.placgigabyte),
                new Componente("PL. MSI 1202", 218.24, "Compatible con Intel i-9, HDD y SSD, velocidades de reloj hasta 4800", R.drawable.placmsi),
                new Componente("PL. GIGABYTE 1e^29", 768.99, "Compatible con todo, todas las velocidades, todo", R.drawable.placgigabyte2),
                new Componente("PL. BIOSTAR 2-DF", 38.40, "Chapucilla", R.drawable.placbiostar)};
        final Componente[] procesadores = {new Componente("PR. Intel i9 10900K", 535.32, "10 núcleos, 20 hilos, 3.7 GHz, overclock 4.6 GHz", R.drawable.inteli9),
                new Componente("PR. Intel i5 9600K", 268.10, "6 núcleos, 6 hilos, 3.7 GHz, overclock 4.6 GHz", R.drawable.inteli5),
                new Componente("PR. Intel i7 10700", 303.10, "8 núcleos, 16 hilos, 2.9 GHz, overclock 4.8 GHz", R.drawable.intel7),
                new Componente("PR. Ryzen 5 3600X", 68.40, "6 núcleos, 12 hilos, 3.8 GHz, overclock 4.7 Ghz", R.drawable.ryzen5),
                new Componente("PR. Ryzen Threadripper", 1800.21, "32 núcleos, 64 hilos, 3.7 GHz, overclock 4.7 Ghz", R.drawable.ryzenthreaddripper)};
        final Componente[] memorias = {new Componente("ME. CORSAIR 142", 38.40, "DDR4", R.drawable.memoram16gb),
                new Componente("ME. KINGSTON 12 8Gb", 28.30, "DDR3", R.drawable.memoram),
                new Componente("ME. CORSAIR 14S 16Gb", 18.40, "DDR4", R.drawable.memoram16gb),
                new Componente("ME. KINGSTON ERD 8Gb", 58.40, "DDR4", R.drawable.memoram),
                new Componente("ME. CORSAIR 1S 16Gb", 70.10, "DDR3", R.drawable.memoram16gb),
                new Componente("ME. KINGSTON AA 8Gb", 38.40, "UAYZ-B", R.drawable.memoram),
                new Componente("ME. FUJITSU 23 16Gb", 28.40, "DDR4", R.drawable.memoram16gb),
                new Componente("ME. KINGSTON 444 8Gb", 38.70, "A0UAYZ-B", R.drawable.memoram),
                new Componente("ME. KINGSTON 78 16 Gb", 48.40, "DDR3", R.drawable.memoram16gb),
                new Componente("ME. CORSAIR 11ab 8Gb", 58.60, "0UDDR4YZ-B", R.drawable.memoram),};
        final Componente[] discosduros = {new Componente("DI. SDD uno", 535.32, "10 núcleos, 20 hilos, 3.7 GHz, overclock 4.6 GHz", R.drawable.discohyperx240),
                new Componente("DI. SSD dos", 268.10, "6 núcleos, 6 hilos, 3.7 GHz, overclock 4.6 GHz", R.drawable.discohyperx480),
                new Componente("DI. HDD tres", 303.10, "8 núcleos, 16 hilos, 2.9 GHz, overclock 4.8 GHz", R.drawable.discoacer1),
                new Componente("DI. HDD cuatro", 68.40, "6 núcleos, 12 hilos, 3.8 GHz, overclock 4.7 Ghz", R.drawable.discoacer2),
                new Componente("DI. HDD cinco", 1800.21, "32 núcleos, 64 hilos, 3.7 GHz, overclock 4.7 Ghz", R.drawable.discoacer1),};
        final Componente[] graficas = {new Componente("GR. NVIDIA RTX 2070", 258.69, "ENVIDIA", R.drawable.nvidiartx2070),
                new Componente("GR. NVIDIA RTX 2070", 399.99, "INTEL DUAL CORE GEM.LZ-B", R.drawable.nvidiartx3080),
                new Componente("GR. SAPHIRE 23", 88.40, "A0UAYZ-B", R.drawable.graficasaphire1),
                new Componente("GR. ASUS 444", 98.40, "INYZ-B", R.drawable.graficaasus1),
                new Componente("GR. RADEON HD 6960", 149.55, "INTEL DUAL", R.drawable.radeonhd6360),
                new Componente("GR.MSI TOYCANSAO", 119.30, "INB", R.drawable.graficamsi),
                new Componente("GR. RANDOM DE", 58.40, "INTZ-B", R.drawable.graficarandom1),
                new Componente("GR. RANDOM PONER", 78.40, "INZ-B", R.drawable.graficarandom2),
                new Componente("GR.RANDOM NOMBRES", 76.40, "INTEL DUAL C", R.drawable.graficarandom3),
                new Componente("GR. RAND ALASCOSAS", 79.99, "AAAAAA", R.drawable.graficarandom1)};

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

        lista.setHasFixedSize(true);
        lista2.setHasFixedSize(true);
        lista3.setHasFixedSize(true);
        lista4.setHasFixedSize(true);
        lista5.setHasFixedSize(true);

        RecyclerAdapterArticulo adapter = new RecyclerAdapterArticulo(placas);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                componenteEvent(v, "PL.", placas, R.id.cuantos_list1);
            }
        });
        lista.setAdapter(adapter);

        RecyclerAdapterArticulo adapter2 = new RecyclerAdapterArticulo(procesadores);
        adapter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                componenteEvent(v, "PR.", procesadores, R.id.cuantos_list2);
            }
        });
        lista2.setAdapter(adapter2);

        RecyclerAdapterArticulo adapter3 = new RecyclerAdapterArticulo(memorias);
        adapter3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                componenteEvent(v, "ME.", memorias, R.id.cuantos_list3);
            }
        });
        lista3.setAdapter(adapter3);

        RecyclerAdapterArticulo adapter4 = new RecyclerAdapterArticulo(discosduros);
        adapter4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                componenteEvent(v, "DI.", discosduros, R.id.cuantos_list4);
            }
        });
        lista4.setAdapter(adapter4);

        RecyclerAdapterArticulo adapter5 = new RecyclerAdapterArticulo(graficas);
        adapter5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                componenteEvent(v, "GR.", graficas, R.id.cuantos_list5);
            }
        });
        lista5.setAdapter(adapter5);

        lista.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        lista2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        lista3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        lista4.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        lista5.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


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
                }
                else{
                    noquierografica.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    noquierografica.setTextOff("NO");
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




    public void componenteEvent(View v, String que_lista, Componente[] que_comps, int que_cuantos){
        TextView dinerocompra = (TextView) findViewById(R.id.dinerocompra_list);
        TextView cuantos = (TextView) findViewById(que_cuantos);
        Componente c = que_comps[lista.getChildAdapterPosition(v)];

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
        for(Componente each: comp_seleccionados){
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
}
