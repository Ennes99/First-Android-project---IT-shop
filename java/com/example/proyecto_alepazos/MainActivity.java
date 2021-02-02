package com.example.proyecto_alepazos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private CheckBox ocultar;
    private EditText usuario;
    private EditText contrasena;
    private Button iniciasesion;
    private TextInputLayout cajacontrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fabbuttonencuesta);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent encuestasatisfaccion = new Intent(MainActivity.this, EncuestaSatisfaccion.class);
                startActivity(encuestasatisfaccion);
            }
        });

        ocultar = (CheckBox) findViewById(R.id.ocultar);
        usuario = (EditText) findViewById(R.id.nombreusuario);
        contrasena = (EditText) findViewById(R.id.contrasena);
        iniciasesion = (Button) findViewById(R.id.iniciasesion);
        cajacontrasena = (TextInputLayout) findViewById(R.id.cajacontrasena);


        ocultar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(ocultar.isChecked()){
                    contrasena.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else contrasena.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });


        iniciasesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle mochila = new Bundle();
                mochila.putString("usuario", usuario.getText().toString());

                if(checkearContrasena()){
                    Intent enviar = new Intent(MainActivity.this, NavigationMenu.class);
                    enviar.putExtras(mochila);
                    startActivity(enviar);
                }
            }
        });
    }


    private boolean checkearContrasena(){
        if(!hayMayuscula() || !hayNumero()){
            contrasena.setText("");
            contrasena.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            cajacontrasena.setErrorEnabled(true);
            contrasena.setError("Mínimo una mayúscula y un número");

            return false;
        }
        else return true;
    }


    private boolean hayMayuscula(){
        char[] cadena = contrasena.getText().toString().toCharArray();

        for(int i=0;i<cadena.length;i++){
            String aux = Character.toString(cadena[i]);
            if(aux == aux.toUpperCase()){
                return true;
            }
        }
        return false;
    }


    private boolean hayNumero(){
        char[] cadena = contrasena.getText().toString().toCharArray();

        for(int i=0;i<cadena.length;i++){
            char aux = cadena[i];
            if(Character.isDigit(aux)){
                return true;
            }
        }
        return false;
    }
}