
package aplicacion;

import java.util.ArrayList;


public class Figura {
    
    protected ArrayList<Vertice> vertices;
    protected ArrayList<Vertice> conexiones;
    protected Vertice verticeCentro;
    protected TipoF tipo;

    public Figura( Vertice verticeCentro, TipoF tipo) {
        this.vertices = new ArrayList<>();
        this.conexiones = new ArrayList<>();
        this.verticeCentro = verticeCentro;
        this.tipo = tipo;
    }

    public Vertice getVerticeCentro() {
        return verticeCentro;
    }

    public void setVerticeCentro(Vertice verticeCentro) {
        this.verticeCentro = verticeCentro;
    }

    public TipoF getTipo() {
        return tipo;
    }

    public void setTipo(TipoF tipo) {
        this.tipo = tipo;
    }

    

    
    
    public ArrayList<Vertice> getConexiones() {
        return conexiones;
    }

    public void setConexiones(ArrayList<Vertice> conexiones) {
        this.conexiones = conexiones;
    }

    public Vertice getVertice() {
        return verticeCentro;
    }

    public void setVertice(Vertice vertice) {
        this.verticeCentro = vertice;
    }
    
    
    
    public ArrayList<Vertice> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Vertice> vertices) {
        this.vertices = vertices;
    }    
}
