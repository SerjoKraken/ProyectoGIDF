/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion;

import java.util.ArrayList;

/**
 *
 * @author Serjo
 */
public class Sistema {
    
    private ArrayList<Entrada> entradas;
    private ArrayList<Proceso> procesos;
    private ArrayList<Flujo> flujos;
    private Inicio inicio;
    private Inicio fin;
    private ArrayList<Documento> documentos;

    public Sistema() {
        this.entradas = new ArrayList<>();
        this.procesos = new ArrayList<>();
        this.flujos = new ArrayList<>();
        this.inicio = null;
        this.fin = null;
        this.documentos = new ArrayList<>();
    }

    public ArrayList<Entrada> getEntradas() {
        return entradas;
    }

    public void setEntradas(ArrayList<Entrada> entradas) {
        this.entradas = entradas;
    }

    public ArrayList<Proceso> getProcesos() {
        return procesos;
    }

    public void setProcesos(ArrayList<Proceso> procesos) {
        this.procesos = procesos;
    }

    public ArrayList<Flujo> getFlujos() {
        return flujos;
    }

    public void setFlujos(ArrayList<Flujo> flujos) {
        this.flujos = flujos;
    }

    public Inicio getInicio() {
        return inicio;
    }

    public void setInicio(Inicio inicio) {
        this.inicio = inicio;
    }

    public Inicio getFin() {
        return fin;
    }

    public void setFin(Inicio fin) {
        this.fin = fin;
    }

    public ArrayList<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(ArrayList<Documento> documentos) {
        this.documentos = documentos;
    }
    
    
    
}
