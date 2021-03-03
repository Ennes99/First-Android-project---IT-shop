package com.example.proyecto_alepazos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class OlvideContrasena extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText mandarcorreo_edit;
    private Button mandarcorreo_button;
    //private String antiguacontrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvide_contrasena);

        mandarcorreo_edit = findViewById(R.id.mandarcorreo_edit);
        mandarcorreo_button = findViewById(R.id.mandarcorreo_button);

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        //final DatabaseReference db_user = FirebaseDatabase.getInstance().getReference().child(user.getUid());

        mandarcorreo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Random azar = new Random();
                String aux_nuevacontrasena = antiguacontrasena + "_" + azar.nextInt(9999);

                user.updatePassword(aux_nuevacontrasena);*/

                mAuth.sendPasswordResetEmail(user.getEmail())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            private static final String TAG = "Mensaje enviado";

                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Email sent.");
                                }
                            }
                        });

            }
        });

        /*db_user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                antiguacontrasena = snapshot.child("password").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }
}