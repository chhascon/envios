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

public class MainActivity extends AppCompatActivity {

    private Button btnEnviar, btnCerrarSesion;
    private EditText idPedido;
    private TextView idPedido_txt, contenidoPedido_txt, tipoPaquete_txt;
    private RadioButton cpeque,cmediana,cgrande;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEnviar = (Button) findViewById(R.id.buttonEnviar);
        btnCerrarSesion = (Button) findViewById(R.id.buttonCerrarSesion);
        idPedido = (EditText) findViewById(R.id.idPedido_input);

        tipoPaquete_txt = (TextView) findViewById(R.id.idPedido_txt);
        contenidoPedido_txt = (TextView) findViewById(R.id.contenido_pedido_txt);
        tipoPaquete_txt = (TextView) findViewById(R.id.tipodepaquete_txt);

        cpeque = (RadioButton) findViewById(R.id.cpeque_rb);
        cmediana = (RadioButton) findViewById(R.id.cmediana_rb);
        cgrande = (RadioButton) findViewById(R.id.cgrande_rb);

        //onclick btnenviar
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Pedido actualizado correctamente ", Toast.LENGTH_SHORT).show();
                //obtener el valor de id pedido y mostrarlo
                idPedido_txt.setText(idPedido.getText().toString());

                //mostrar el tipo de paquete
                if(cpeque.isChecked()){
                    tipoPaquete_txt.setText(cpeque.getText().toString());
                } else if (cmediana.isChecked()) {
                    tipoPaquete_txt.setText(cmediana.getText().toString());
                } else if (cgrande.isChecked()) {
                    tipoPaquete_txt.setText(cgrande.getText().toString());
                }
            }
        });

        //onclick cerrarsesion
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
}