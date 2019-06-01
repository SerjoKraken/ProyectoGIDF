
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
    
    
    
    public boolean comprobarPosicion(Figura figura, double puntoX,double puntoY){
        /** 
         * dejo esto por el momento, planeo dejar solo que quede con el tema del flujo, porque documentacion
         * y inicio/fin las declarare como un rectangulo para comprobar mejor las areas de las figuras,
         * De momento recibe una figura (que serian las de un "array") y el punto central que seria 
         * de la figura a crear
         * 
         * retorna true si la figura no tiene un contacto con la figura a comprobar
         * y false si la figura impacta en algun lado
         */
        
        if (figura.getTipo() != TipoF.DOCUMENTACION || figura.getTipo() != TipoF.FLUJO){
            double x1=figura.getVerticeCentro().getX()-50;
            double x2=figura.getVerticeCentro().getX()+50;
            double y1=figura.getVerticeCentro().getY()-25;
            double y2=figura.getVerticeCentro().getY()+25;

            double a1=puntoX-50;
            double a2=puntoX+50;
            double b1=puntoY-25;
            double b2=puntoY+25;

            if (x1<a1 && x2>a1){
                return false;
            }else{
                if(y1<b1 && y2>b1){
                    return false;
                }else{
                    if (x1<a2 && x2>a2){
                        return false;
                    }
                    else{
                        if (y1<b2 && y2>b2){
                            return false;
                        }
                    }
                }
                }
            return true;
        }
        System.out.println("Es documentacion");
        return false;
        
    }
    public abstract void dibujar(GraphicsContext gc);
    public abstract void dibujar(GraphicsContext gc, double x, double y);
    public abstract void calcularConexiones();
        
    
    }
    

