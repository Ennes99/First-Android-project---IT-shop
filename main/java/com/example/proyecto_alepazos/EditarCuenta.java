package com.example.proyecto_alepazos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditarCuenta extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText nuevocorreo;
    private EditText nuevacontrasena;
    private Button enviar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cuenta);

        nuevocorreo = findViewById(R.id.nuevocorreo);
        nuevacontrasena = findViewById(R.id.nuevacontrasena);
        enviar = findViewById(R.id.enviaredicioncuenta);

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        final DatabaseReference db_user = FirebaseDatabase.getInstance().getReference().child(user.getUid());


        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aux_correo = nuevocorreo.getText().toString();
                String aux_contrasena = nuevacontrasena.getText().toString();

                if(!aux_correo.equals("")){
                    user.updateEmail(aux_correo)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                private static final String TAG = "";

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "Email actualizado!");
                                    }
                                }
                            });
                    db_user.child("email").setValue(aux_correo);
                }

                if(!aux_contrasena.equals("")){
                    user.updatePassword(aux_contrasena)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                private static final String TAG = "";

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "Contraseña actualizada!");
                                    }
                                }
                            });
                    db_user.child("password").setValue(aux_contrasena);
                }

            }
        });
    }
}