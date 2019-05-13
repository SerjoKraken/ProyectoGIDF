/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author Serjo
 */
public class Flujo extends Figura{
    
    Figura padre;
    Figura hijo;
    
    public Flujo( TipoF tipo) {
        super(tipo);
        
    }
    
    public void calcularVertices(){
        Vertice v = padre.verticeCentro;
        Vertice v2 = hijo.verticeCentro;
        for (Vertice conexion1 : padre.getConexiones()) {
            for (Vertice conexion2 : hijo.getConexiones()) {
                if(conexion1.distancia(conexion2) < v.distancia(v2)){
                    v = conexion1;
                    v2 = conexion2;
                }
            }
        }
        vertices.set(0, v);
        vertices.set(1, v2);
        
        verticeCentro = new Vertice((vertices.get(0).getX()+vertices.get(1).getX())/2, (vertices.get(0).getY()+vertices.get(1).getY())/2);
        
        vertices.add(0,new Vertice(verticeCentro.getX()+5, verticeCentro.getY()+5));
        vertices.add(0,new Vertice(verticeCentro.getX()-5, verticeCentro.getY()-5) );
        
        
    }

    @Override
    public void dibujar(GraphicsContext gc) {
        double x1=vertices.get(2).getX();
        double y1=vertices.get(2).getY();
        double x2=vertices.get(3).getX();
        double y2=vertices.get(3).getY();
        
        double px1 =vertices.get(0).getX();
        double py1 =vertices.get(0).getY();
        double px2 =vertices.get(1).getX();
        double py2 =vertices.get(1).getY();
        
        double tamanno = 15;
        double angulo = Math.atan2(y2-y1, x2-x1);
        double x3 = x2 - tamanno*Math.cos(angulo - Math.PI/6);
        double y3 = y2 - tamanno*Math.sin(angulo - Math.PI/6);
        double x4 = x2 - tamanno*Math.cos(angulo + Math.PI/6);
        double y4 = y2 - tamanno*Math.sin(angulo + Math.PI/6);
        
        gc.strokeLine(x1,y1,x2,y2);
        gc.strokeLine(x2, y2, x3, y3);
        gc.strokeLine(x2, y2, x4, y4);
        
        for (double i = 0; i < 10; i+=0.5) {
            gc.strokeLine(px1, py1+i, px2, py1+i);
        }
        
    }

    @Override
    public void calcularConexiones() {
        
    }

    @Override
    public void dibujar(GraphicsContext gc, double x, double y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
