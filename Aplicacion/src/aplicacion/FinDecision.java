
package aplicacion;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class FinDecision extends Figura{

    int indexPadre;
    int indexHijo;
    
    Figura padre;
    Figura hijo;
    
    public FinDecision(TipoF tipo) {
        super(tipo);
    }

    @Override
    public void dibujar(GraphicsContext gc) {
        double x1=vertices.get(0).getX();
        double y1=vertices.get(0).getY();
        double x2=vertices.get(1).getX();
        double y2=vertices.get(1).getY();
        
        
        gc.setStroke(Color.BLACK);

        
        gc.strokeLine(x1,y1,x2,y2);
        gc.setStroke(Color.DEEPSKYBLUE);
        gc.setFill(Color.DEEPSKYBLUE);
        gc.fillText(texto, x2, y2);

            
    }
    
    public void calcularVertices(ArrayList<Figura> figuras){

        for (int i = 0; i < figuras.size(); i++) {
            if(!(figuras.get(i) instanceof FinDecision) && figuras.get(i).getVerticeCentro().distancia(padre.verticeCentro)==0){
                indexPadre=i;
            }
            if(!(figuras.get(i) instanceof FinDecision) && figuras.get(i).getVerticeCentro().distancia(hijo.verticeCentro)==0){
                indexHijo=i;
            }
        }
        padre = figuras.get(indexPadre);
        hijo = figuras.get(indexHijo);

        Vertice v ;
        Vertice v2;

        if(figuras.get(indexHijo).getEstado()==true && figuras.get(indexPadre).getEstado()==true && !(figuras.get(indexHijo) instanceof FinDecision) && !(figuras.get(indexPadre) instanceof FinDecision)){

            v = padre.verticeCentro;
            v2 = hijo.conexiones.get(0);


            for (Vertice conexion2 : hijo.getConexiones()) {
                if(v.distancia(conexion2) < v.distancia(v2)){

                    v2 = conexion2;
                }
            }


            vertices.set(0, v);
            vertices.set(1, v2);

            verticeCentro = new Vertice((v.getX()+v2.getX())/2, (v.getY()+v2.getY())/2);

            if(vertices.size()>2){
                vertices.set(2,new Vertice(verticeCentro.getX()-5, verticeCentro.getY()-5));
                vertices.set(3,new Vertice(verticeCentro.getX()+5, verticeCentro.getY()+5) );
            }else{
                vertices.add(new Vertice(verticeCentro.getX()-5, verticeCentro.getY()-5));
                vertices.add(new Vertice(verticeCentro.getX()+5, verticeCentro.getY()+5) );
            }
        }
        else{
            System.out.println("Omitiendo");
            this.setEstado(false);
        }
    }

    @Override
    public void dibujar(GraphicsContext gc, double x, double y) {
        double x1=vertices.get(0).getX();
        double y1=vertices.get(0).getY();
        double x2=vertices.get(1).getX();
        double y2=vertices.get(1).getY();
        
        

        gc.setStroke(Color.BLACK);
        

        gc.strokeLine(x1,y1,x2,y2);
        
    }



    @Override
    public void calcularConexiones() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
