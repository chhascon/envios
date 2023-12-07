package com.example.proyectoandroid.BBDD;

import java.util.List;

public class DataManager {
    //interfaces para usuarios
    public interface ReadUserStatus {
        void onUsersLoaded(List<Usuario> listaUsuarios);
        void onUsersLoadFailed(String errorMessage);
    }

    public interface WriteUserStatus {
        void onUsersWriteSuccess();
        void onUsersWriteFailure(String errorMessage);
    }

    //interfaces par pedidos
    public interface WriteOrderStatus{
        void onOrderWriteSuccess();
        void onOrderWriteFailure(String errorMessage);
    }

}
