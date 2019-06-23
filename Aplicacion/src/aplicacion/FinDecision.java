
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
        
        if(vertices.size()>2){
            double px1 =vertices.get(2).getX();
            double py1 =vertices.get(2).getY();
            double px2 =vertices.get(3).getX();
            double py2 =vertices.get(3).getY();
            
            double centroX = verticeCentro.getX();
            double centroY = verticeCentro.getY();

            gc.setStroke(Color.BLACK);

            gc.strokeLine(x1,y1,centroX,centroY);
            gc.strokeLine(centroX,centroY, x2, y2);

            for (double i = 0; i < 10; i+=0.5) {
                gc.strokeLine(px1, py1+i, px2, py1+i);
            }
        }
        
        
 
    }
    
    public void calcularVertices(ArrayList<Figura> figuras){

        for (int i = 0; i < figuras.size(); i++) {
            if(figuras.get(i).getVerticeCentro().distancia(padre.verticeCentro)==0){
                indexPadre=i;
            }
            if(figuras.get(i).getVerticeCentro().distancia(hijo.verticeCentro)==0){
                indexHijo=i;
            }
        }
        padre = figuras.get(indexPadre);
        hijo = figuras.get(indexHijo);

        Vertice v ;
        Vertice v2;

        if(figuras.get(indexHijo).getEstado()==true && figuras.get(indexPadre).getEstado()==true){

            v = padre.verticeCentro;
            v2 = hijo.verticeCentro;


            vertices.set(0, v);
            vertices.set(1, v2);

            

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
        double xActual = verticeCentro.getX();
        double yActual = verticeCentro.getY();
        
        verticeCentro.setX(xActual+(x-xActual));
        verticeCentro.setY(yActual+(y-yActual));
        
        padre.setVerticeCentro(new Vertice(padre.getVerticeCentro().getX()+(x-xActual), padre.getVerticeCentro().getY()+(y-yActual)));
        padre.dibujar(gc, padre.getVerticeCentro().getX(), padre.getVerticeCentro().getY());
        padre.calcularConexiones();
        
        hijo.setVerticeCentro(new Vertice(hijo.getVerticeCentro().getX()+(x-xActual), hijo.getVerticeCentro().getY()+(y-yActual)));
        hijo.dibujar(gc, hijo.getVerticeCentro().getX(), hijo.getVerticeCentro().getY());
        hijo.calcularConexiones();
    }


    @Override
    public void calcularConexiones() {
        
    }
    
}
