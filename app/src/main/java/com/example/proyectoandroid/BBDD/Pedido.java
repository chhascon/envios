package com.example.proyectoandroid.BBDD;

public class Pedido {
    int ID, numCajaSmall, numCajaMid, numCajaBig;

    public Pedido(int ID, int numCajaSmall, int numCajaMid, int numCajaBig){
        this.ID = ID;
        this.numCajaSmall = numCajaSmall;
        this.numCajaMid = numCajaMid;
        this.numCajaBig = numCajaBig;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getNumCajaSmall() {
        return numCajaSmall;
    }

    public void setNumCajaSmall(int numCajaSmall) {
        this.numCajaSmall = numCajaSmall;
    }

    public int getNumCajaMid() {
        return numCajaMid;
    }

    public void setNumCajaMid(int numCajaMid) {
        this.numCajaMid = numCajaMid;
    }

    public int getNumCajaBig() {
        return numCajaBig;
    }

    public void setNumCajaBig(int numCajaBig) {
        this.numCajaBig = numCajaBig;
    }
}
