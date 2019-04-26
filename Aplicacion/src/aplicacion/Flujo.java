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
        
    }

    @Override
    public void dibujar(GraphicsContext gc) {
        double x1=vertices.get(0).getX();
        double y1=vertices.get(0).getY();
        double x2=vertices.get(1).getX();
        double y2=vertices.get(1).getY();
        
        double tamanno = 15;
        double angulo = Math.atan2(y2-y1, x2-x1);
        double x3 = x2 - tamanno*Math.cos(angulo - Math.PI/6);
        double y3 = y2 - tamanno*Math.sin(angulo - Math.PI/6);
        double x4 = x2 - tamanno*Math.cos(angulo + Math.PI/6);
        double y4 = y2 - tamanno*Math.sin(angulo + Math.PI/6);
        
        gc.strokeLine(x1,y1,x2,y2);
        gc.strokeLine(x2, y2, x3, y3);
        gc.strokeLine(x2, y2, x4, y4);
        
    }

    @Override
    public void calcularConexiones() {
        Vertice aux1 = vertices.get(0);
        Vertice aux2 = vertices.get(1);
        
        
        verticeCentro = new Vertice((aux1.getX()+aux2.getX())/2, (aux1.getY()+aux2.getY())/2);
        
    }
    
}
