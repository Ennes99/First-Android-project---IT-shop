package com.example.proyecto_alepazos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.os.Bundle;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainAct";
    private CheckBox ocultar;
    private EditText usuario;
    private EditText contrasena;
    private Button iniciasesion;
    private TextView crearcuenta;
    private TextView olvidecontrasena;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        crearcuenta = (TextView) findViewById(R.id.crearcuenta);
        olvidecontrasena = (TextView) findViewById(R.id.olvidecontrasena);
        iniciasesion = (Button) findViewById(R.id.iniciasesion);
        mAuth = FirebaseAuth.getInstance();

        SpannableString subrayado = new SpannableString("Regístrate aquí");
        subrayado.setSpan(new UnderlineSpan(), 0, subrayado.length(), 0);
        crearcuenta.setText(subrayado);

        SpannableString subrayado_olvidecont = new SpannableString("Olvidé mi contraseña");
        subrayado_olvidecont.setSpan(new UnderlineSpan(), 0, subrayado_olvidecont.length(), 0);
        olvidecontrasena.setText(subrayado_olvidecont);


        crearcuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearcuenta.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                Intent envio = new Intent(MainActivity.this, CrearUsuario.class);
                startActivity(envio);
            }
        });

        olvidecontrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OlvideContrasena.class));
            }
        });

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
                autentificacion();

            }
        });

    }






    @Override
    protected void onStart() {
        super.onStart();
    }



    private void autentificacion(){
        final TextInputLayout cajausuario = findViewById(R.id.cajausuario);
        final TextInputLayout cajacontrasena = findViewById(R.id.cajacontrasena);

        mAuth.signInWithEmailAndPassword(usuario.getText().toString(), contrasena.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            mAuth.updateCurrentUser(user);

                            Intent enviar = new Intent(MainActivity.this, NavigationMenu.class);
                            startActivity(enviar);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Nombre o contraseña incorrectos",
                                    Toast.LENGTH_SHORT).show();


                            contrasena.setText("");
                            contrasena.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                            cajacontrasena.setErrorEnabled(true);

                            usuario.setText("");
                            usuario.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                            cajausuario.setErrorEnabled(true);
                        }

                    }
                });
    }


}