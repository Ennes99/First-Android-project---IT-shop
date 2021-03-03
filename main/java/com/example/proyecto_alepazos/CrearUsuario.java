package com.example.proyecto_alepazos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CrearUsuario extends AppCompatActivity {
    private static final String TAG = "CrearUsuario";
    private Button botonregistro;
    private TextInputEditText registroemail;
    private TextInputEditText registronombre;
    private TextInputEditText registrocontrasena;
    private TextInputLayout cajaregistrocontrasena;
    private CheckBox ocultarcontrasena;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);

        botonregistro = findViewById(R.id.botonregistro);
        registroemail = findViewById(R.id.cajaregistroemail);
        registronombre = findViewById(R.id.cajaregistronombre);
        registrocontrasena = findViewById(R.id.registocontrasena);
        cajaregistrocontrasena = findViewById(R.id.cajaregistocontrasena);
        ocultarcontrasena = findViewById(R.id.registroocultarcontrasena);


        botonregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();

                String nombre = registronombre.getText().toString();
                String email = registroemail.getText().toString();
                String password = registrocontrasena.getText().toString();

                createUser(email, password, nombre);

            }
    });

        ocultarcontrasena.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(ocultarcontrasena.isChecked()){
                    registrocontrasena.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else registrocontrasena.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        //FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void createUser(final String email, final String password, final String nombre){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "Usuario creado correctamente");
                            FirebaseUser user = mAuth.getCurrentUser();
                            mAuth.updateCurrentUser(user);

                            DatabaseReference db_users_id = FirebaseDatabase.getInstance().getReference().child("usuarios");
                            db_users_id.child(user.getUid()).setValue("");
                            db_users_id.child(user.getUid()).child("nombre").setValue(nombre);
                            db_users_id.child(user.getUid()).child("email").setValue(email);
                            db_users_id.child(user.getUid()).child("password").setValue(password);


                            Intent envio = new Intent(CrearUsuario.this, MainActivity.class);
                            startActivity(envio);
                        } else {

                            registrocontrasena.setText("");
                            registrocontrasena.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                            cajaregistrocontrasena.setErrorEnabled(true);
                            registrocontrasena.setError("El dominio debe incluir @ y la dirección \".es\" o \".com\"");

                            registrocontrasena.setText("");
                            registrocontrasena.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                            cajaregistrocontrasena.setErrorEnabled(true);
                            registrocontrasena.setError("Mínimo un número");

                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "¡Ups! Algo ha fallado...", task.getException());
                            Toast.makeText(CrearUsuario.this, "Problemas al registrar el usuario",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    /*  MÉTODOS DE CHECKEAR MAIL Y PASSWORD ANTES DE FIREBASE

    private boolean checkearMail(){
        String textoemail = registroemail.getText().toString();

        if(!textoemail.contains("@") || textoemail.contains(".com") || textoemail.contains(".es")){
            registrocontrasena.setText("");
            registrocontrasena.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            cajaregistrocontrasena.setErrorEnabled(true);
            registrocontrasena.setError("El dominio debe incluir @ y la dirección \".es\" o \".com\"");

            return false;
        }
        else return true;
    }


    private boolean checkearContrasena(){
        if(!hayMayuscula() || !hayNumero()){
            registrocontrasena.setText("");
            registrocontrasena.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            cajaregistrocontrasena.setErrorEnabled(true);
            registrocontrasena.setError("Mínimo una mayúscula y un número");

            return false;
        }
        else return true;
    }


    private boolean hayMayuscula(){
        char[] cadena = registrocontrasena.getText().toString().toCharArray();

        for(int i=0;i<cadena.length;i++){
            String aux = Character.toString(cadena[i]);
            if(aux == aux.toUpperCase()){
                return true;
            }
        }
        return false;
    }


    private boolean hayNumero(){
        char[] cadena = registrocontrasena.getText().toString().toCharArray();

        for(int i=0;i<cadena.length;i++){
            char aux = cadena[i];
            if(Character.isDigit(aux)){
                return true;
            }
        }
        return false;
    }*/
}