
package aplicacion;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;


public abstract class Figura {
    
    protected ArrayList<Vertice> vertices;
    protected ArrayList<Vertice> conexiones;
    protected Vertice verticeCentro;
    protected TipoF tipo;
    String texto;
    boolean estado;
    boolean esVerdadero = false;
    protected int verfal=0;
    
    Desicion desicionPadre = null;

    public Figura(TipoF tipo) {
        this.vertices = new ArrayList<>();
        this.conexiones = new ArrayList<>();
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
    public void setEstado(boolean estado){
        this.estado=estado;
    }
    public boolean getEstado(){
        return estado;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
    

    
    
    public ArrayList<Vertice> getConexiones() {
        return conexiones;
    }

    public void setConexiones(ArrayList<Vertice> conexiones) {
        this.conexiones = conexiones;
    }

    
    public ArrayList<Vertice> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Vertice> vertices) {
        this.vertices = vertices;
    }
    public abstract void dibujar(GraphicsContext gc);
    public abstract void dibujar(GraphicsContext gc, double x, double y);
    public abstract void calcularConexiones();
        
    
    }
    

    

