package com.example.proyectoandroid.BBDD;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseHelper {
    private DatabaseReference mDatabase;

    public FirebaseHelper() {
        // Referencia de la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
    public void readUsers(final DataManager.ReadUserStatus readDataStatus) {
        // Realiza una consulta a la base de datos
        mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Usuario> listaUsuarios = new ArrayList<>();
                // Itera a través de los hijos de "usuarios"
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Obtiene el email y la contraseña de cada usuario
                    String email = snapshot.child("email").getValue(String.class);
                    String password = snapshot.child("password").getValue(String.class);

                    // Crea un objeto Usuario y agrega a la lista
                    Usuario usuario = new Usuario(email, password);
                    listaUsuarios.add(usuario);
                }

                // Llama al método de interfaz para informar que los datos se han cargado
                readDataStatus.onUsersLoaded(listaUsuarios);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                readDataStatus.onUsersLoadFailed(databaseError.getMessage());
            }
        });
    }

    public void registerNewUser(String email, String password, final DataManager.WriteUserStatus writeUserStatus) {
        // Crea un nuevo usuario
        Usuario newUser = new Usuario(email, password);

        // Referencia a la ubicación donde deseas almacenar el usuario en la base de datos
        DatabaseReference usersRef = mDatabase.child("Users");

        // Comprobar q el usuario no este duplicado
        usersRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // El correo electrónico ya está en uso
                    writeUserStatus.onUsersWriteFailure("El correo electrónico ya está registrado");
                } else {
                    // El correo electrónico no está en uso, proceder con el registro
                    String userId = usersRef.push().getKey();

                    usersRef.child(userId).setValue(newUser)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    writeUserStatus.onUsersWriteSuccess();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    writeUserStatus.onUsersWriteFailure("Error durante el registro de usuarios: " + e.getMessage());
                                }
                            });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                writeUserStatus.onUsersWriteFailure(databaseError.getMessage());
            }
        });
    }
    public void registerNewOrder(final Pedido order, final DataManager.WriteOrderStatus writeOrderStatus) {
        // Referencia a la ubicación
        DatabaseReference ordersRef = mDatabase.child("Orders");

        ordersRef.orderByChild("id").equalTo(order.getID()).addListenerForSingleValueEvent(new ValueEventListener() {
            boolean productExists = false;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // El ID ya está en uso
                    // Recuperar el pedido existente
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // Obtiene la referencia al nodo del producto específico
                        DatabaseReference productRef = ordersRef.child(snapshot.getKey());

                        // Actualiza las propiedades del producto
                        productRef.child("numCajaSmall").setValue(order.getNumCajaSmall());
                        productRef.child("numCajaMid").setValue(order.getNumCajaMid());
                        productRef.child("numCajaBig").setValue(order.getNumCajaBig());

                        productExists = true;
                        // No rompemos el bucle aquí, para permitir la ejecución del código después del bucle
                    }
                }

                if (!productExists) {
                    // El ID no existe o no se actualizó, proceder con el registro
                    String userId = ordersRef.push().getKey();

                    ordersRef.child(userId).setValue(order)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    writeOrderStatus.onOrderWriteSuccess();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    writeOrderStatus.onOrderWriteFailure("Error durante el registro: " + e.getMessage());
                                }
                            });
                } else {
                    // Se actualizó un pedido existente, notificar el éxito
                    writeOrderStatus.onOrderWriteSuccess();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                writeOrderStatus.onOrderWriteFailure(databaseError.getMessage());
            }
        });
    }



}
