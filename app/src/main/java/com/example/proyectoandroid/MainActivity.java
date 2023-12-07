package com.example.proyectoandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectoandroid.BBDD.DataManager;
import com.example.proyectoandroid.BBDD.FirebaseHelper;
import com.example.proyectoandroid.BBDD.Pedido;

public class MainActivity extends AppCompatActivity {

    private Button btnEnviar, btnCerrarSesion;
    private EditText idPedido, numCajaSmall, numCajaMid, numCajaBig;
    private TextView idPedido_txt, tvCajaSmall, tvCajamid, tvCajaBig;
    private FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa FirebaseHelper
        firebaseHelper = new FirebaseHelper();

        btnEnviar = findViewById(R.id.buttonEnviar);
        btnCerrarSesion = findViewById(R.id.buttonCerrarSesion);
        idPedido = findViewById(R.id.idPedido_input);
        numCajaSmall = findViewById(R.id.cantpequeña);
        numCajaMid = findViewById(R.id.cantmediana);
        numCajaBig = findViewById(R.id.cantgrande);

        idPedido_txt = findViewById(R.id.idPedido_txt);
        tvCajaSmall = findViewById(R.id.cantpequeña_txt);
        tvCajamid = findViewById(R.id.cantmediana_txt);
        tvCajaBig = findViewById(R.id.cantgrande_txt);

        // onclick btnenviar
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener datos de los EditText
                int idPedidoValue = Integer.parseInt(idPedido.getText().toString());
                int numCajaSmallValue = Integer.parseInt(numCajaSmall.getText().toString());
                int numCajaMidValue = Integer.parseInt(numCajaMid.getText().toString());
                int numCajaBigValue = Integer.parseInt(numCajaBig.getText().toString());

                // Crear un nuevo Pedido
                Pedido nuevoPedido = new Pedido(idPedidoValue, numCajaSmallValue, numCajaMidValue, numCajaBigValue);

                // Registrar el nuevo pedido
                firebaseHelper.registerNewOrder(nuevoPedido, new DataManager.WriteOrderStatus() {
                    @Override
                    public void onOrderWriteSuccess() {
                        // Actualizar los TextView con los datos del pedido registrado
                        idPedido_txt.setText(String.valueOf(nuevoPedido.getID()));
                        tvCajaSmall.setText(String.valueOf(nuevoPedido.getNumCajaSmall()));
                        tvCajamid.setText(String.valueOf(nuevoPedido.getNumCajaMid()));
                        tvCajaBig.setText(String.valueOf(nuevoPedido.getNumCajaBig()));
                        // Actualizar los EditText con los nuevos valores
                        idPedido.setText(String.valueOf(nuevoPedido.getID()));
                        numCajaSmall.setText(String.valueOf(nuevoPedido.getNumCajaSmall()));
                        numCajaMid.setText(String.valueOf(nuevoPedido.getNumCajaMid()));
                        numCajaBig.setText(String.valueOf(nuevoPedido.getNumCajaBig()));

                        // Mostrar un mensaje al usuario
                        Toast.makeText(MainActivity.this, "Pedido registrado correctamente", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onOrderWriteFailure(String errorMessage) {
                        // Manejar el error al registrar el pedido
                        Toast.makeText(MainActivity.this, "Error al registrar el pedido: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // onclick cerrarsesion
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
}