package com.example.proyecto_alepazos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputLayout;

public class EncuestaSatisfaccion extends AppCompatActivity {
    private CheckBox otro;
    private CheckBox cb;
    private EditText queotro;
    private TextInputLayout campotexto;
    private Button enviar;
    private Button volverencuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta_satisfaccion);

        otro = (CheckBox) findViewById(R.id.otro);
        queotro = (EditText) findViewById(R.id.queotros);
        campotexto = (TextInputLayout) findViewById(R.id.campotexto);
        enviar = (Button) findViewById(R.id.enviarencuesta);
        //volverencuesta = (Button) findViewById(R.id.volverencuesta);

        cb = new CheckBox(getBaseContext());


        otro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (otro.isChecked()) {
                    campotexto.setVisibility(View.VISIBLE);
                    queotro.setVisibility(View.VISIBLE);
                    queotro.setText("");
                } else {
                    campotexto.setVisibility(View.GONE);
                    queotro.setVisibility(View.GONE);
                }
            }
        });

        otro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout layout = (LinearLayout) findViewById(R.id.encuesta_sat_layout);
                cb.setChecked(true);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.0F);
                if(cb.getParent() != null){
                    ((ViewGroup)cb.getParent()).removeView(cb);
                }
                else{
                    layout.addView(cb, 6, lp);
                }

            }
        });
        queotro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                cb.setText(queotro.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviar.setText("ENVIADO");
                finish();
            }
        });

       /* volverencuesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent atras = new Intent(EncuestaSatisfaccion.this, MainActivity.class);
                startActivity(atras);
            }
        });*/

    }

    /*public AlertDialog abrirAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage("¡Te podrán hackear el móvil! ¿Estás\n seguro de querer mandar la encuesta?");
        builder.setTitle("ADVERTENCIA");

        builder.setPositiveButton("SÍ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enviar.setText("ENVIADO");
                finish();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }*/
}
