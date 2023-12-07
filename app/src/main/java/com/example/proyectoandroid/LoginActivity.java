package com.example.proyectoandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectoandroid.BBDD.DataManager;
import com.example.proyectoandroid.BBDD.FirebaseHelper;
import com.example.proyectoandroid.BBDD.Usuario;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    Button btnIniciarSesion,btnCreateAccount;
    EditText et_Email, et_Password;
    FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_Email = findViewById(R.id.editTextTextEmailAddress);
        et_Password = findViewById(R.id.editTextTextPassword);

        //instanciar base de datos.
        firebaseHelper = new FirebaseHelper();

        btnIniciarSesion = (Button) findViewById(R.id.buttonIniciarSesion);
        //Onclick Btn iniciar sesion
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifUsuario();
            }
        });
        btnCreateAccount = findViewById(R.id.createAccount_Button);
        //On click boton crear cuenta.
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterUsuarioActivity.class);
                startActivity(i);
            }
        });
    }
    //funcion para verificar usuario
    private void verifUsuario(){
        //Recuperar la informacion en los edit text y pasarlos a String
        String email = et_Email.getText().toString();
        String password = et_Password.getText().toString();

        firebaseHelper.readUsers(new DataManager.ReadUserStatus() {
            @Override
            public void onUsersLoaded(List<Usuario> listaUsuarios) {
                for (Usuario usuario : listaUsuarios) {

                    if (usuario.getEmail() != null && usuario.getPassword() != null &&
                            usuario.getEmail().equals(email) && usuario.getPassword().equals(password)) {
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                        return;  // Importante: Sale del método después de iniciar la actividad
                    }
                }

                AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                alert.setMessage("Usuario o Contraseña Incorrecto");
                alert.setCancelable(false);
                alert.setNegativeButton("OK", (DialogInterface.OnClickListener) (dialog, which) -> {
                    et_Email.setText("");
                    et_Password.setText("");
                    dialog.cancel();
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }

            @Override
            public void onUsersLoadFailed(String errorMessage) {
                Toast.makeText(LoginActivity.this, "Error al recuperar la lista de clientes: "+ errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}