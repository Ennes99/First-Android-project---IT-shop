package com.example.proyecto_alepazos;

import androidx.annotation.Nullable;

import java.io.Serializable;

public abstract class Articulo implements Serializable { //es abstract porque se utilizarán sus hijos, el Ordenador y el Componente. Este último tiene una diferencia: el número máximo en stock (Ordenadores, solo uno por tipo)
    private String nombre;
    private double precio;
    private String caracteristicas;
    private String imagen;


    public Articulo(String n, double p, String c, String i){
        nombre = n;
        precio = p;
        caracteristicas = c;
        imagen = i;
    }

    public String getNombre(){
        return nombre;
    }

    public String getCaracteristicas(){
        return caracteristicas;
    }

    public String getImagen(){
        return imagen;
    }

    public double getPrecio(){
        return precio;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Articulo art = null;
        if(obj instanceof Articulo){
            art = (Articulo) obj;
        }

        if(nombre.equals(art.getNombre()) && caracteristicas.equals(art.getCaracteristicas()) && precio == art.getPrecio() && imagen.equals(art.getImagen())) return true;
        return false;
    }

    public String toString() {
        return nombre.toUpperCase() + "\n" + caracteristicas + "\n\n" + precio;
    }
}

