package com.example.proyecto_alepazos;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapterArticulo extends RecyclerView.Adapter<RecyclerAdapterArticulo.ViewHolder> implements  View.OnClickListener{
    private Articulo[] articulos;
    private View.OnClickListener listener;


    public RecyclerAdapterArticulo(Articulo[] articulos){
        super();
        this.articulos = articulos;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = 0;

        if(articulos[0] instanceof Ordenador){  //a ver si en lugar de todo el array sólo puedo comparar un objeto
            layout = R.layout.recycleritems_layout;
        }
        else{
            layout = R.layout.recycleitems_list_layout;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, null, false);
        view.setOnClickListener(this);

        ViewHolder holder = new ViewHolder(view, articulos);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindArticulo(articulos[position]);
        //holder.setIsRecyclable(false); //es la manera para que guarde los items seleccionados de forma correcta. Aunque es contraproducente, ya que supone la pérdida
    }                                   //de la propiedad que hace interesante al RecyclerView

    @Override
    public int getItemCount() {
        return articulos.length;
    }

    public void setOnClickListener(View.OnClickListener listener){  //aquí se escucha como tal al evento. La variable que creamos para guardar los listener que lleguemos la usamos aquí
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener != null){   //es decir, si, arriba, efectivamente me llega un listener
            listener.onClick(v);
        }
    }







    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nombre;
        //private TextView caracteristicas;    --> no va a aparecer en lo que son las listas
        private ImageView imagen;
        private TextView precio;
        //private TextView maxstock;          --> ídem


        public ViewHolder(View v, Articulo[] articulos){
            super(v);

            if(articulos[0] instanceof Ordenador){
                nombre = itemView.findViewById(R.id.nombre);
                imagen = itemView.findViewById(R.id.foto);
                precio = itemView.findViewById(R.id.precio);
            }
            else{
                nombre = itemView.findViewById(R.id.nombre_list);
                imagen = itemView.findViewById(R.id.foto_list);
                precio = itemView.findViewById(R.id.precio_list);
            }

        }

        public void bindArticulo(Articulo a){ //método para asignar los datos de cada articulo a los atributos "retenidos" en el holder
            nombre.setText(a.getNombre());
            //caracteristicas.setText(a.getCaracteristicas());
            imagen.setImageResource(a.getImagen());
            precio.setText(Double.toString(a.getPrecio()) + " €");
        }
    }
}
