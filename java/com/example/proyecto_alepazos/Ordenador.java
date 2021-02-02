package com.example.proyecto_alepazos;

import android.widget.ArrayAdapter;

public class Ordenador extends Articulo{
    private final int maxstock;
    private int contador; //cuenta los toques que se le dan con el dedo al seleccionarlo en la compra

    public Ordenador(String n, double p, String c, int i, int m){
        super(n,p,c,i);
        maxstock = m;
        contador = 0;
    }

    public int getMaxstock(){
        return maxstock;
    }

    public int getContador(){
        return contador;
    }

    public void sumaContador(){
        contador++;
    }

    public void resetContador(){ contador = 0; }

}
