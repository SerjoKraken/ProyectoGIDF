/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

/**
 *
 * @author Serjo
 */
public class Inicio extends Figura{
    
    public Inicio(TipoF tipo) {
        super(tipo);
    }

    @Override
    public void dibujar(GraphicsContext gc) {
        double x1=vertices.get(0).getX();
        double y1=vertices.get(0).getY();
        double x2=vertices.get(1).getX();
        double y2=vertices.get(1).getY();
        double x3=vertices.get(2).getX();
        double y3=vertices.get(2).getY();
        double x4=vertices.get(3).getX();
        double y4=vertices.get(3).getY();
        gc.setStroke(Color.AQUAMARINE);
        for (double i = 0; i < 50; i+=0.5) {
            gc.strokeLine(x1, y1+i, x2, y2+i);
            gc.strokeArc(x1-19+i, y1, 37, 50, 90, 180, ArcType.OPEN);
            //gc.strokeLine(x3, y3, x4, y4);
            gc.strokeArc(x1+82-i, y1, 37, 50, -90, 180, ArcType.OPEN);
        }
        gc.setStroke(Color.BLACK);
        gc.strokeLine(x1, y1, x2, y2);
        gc.strokeArc(x1-19, y1, 37, 50, 90, 180, ArcType.OPEN);
        gc.strokeLine(x3, y3, x4, y4);
        gc.strokeArc(x1+82, y1, 37, 50, -90, 180, ArcType.OPEN);
        gc.fillText(texto, x1+5,y1+30);

    }

    @Override
    public void calcularConexiones() {
        Vertice aux1 = vertices.get(0);
        Vertice aux2 = vertices.get(1);
        Vertice aux3 = vertices.get(2);
        Vertice aux4 = vertices.get(3);
        
        conexiones.add(new Vertice((aux1.getX()+aux2.getX())/2, (aux1.getY()+aux2.getY())/2));
        conexiones.add(new Vertice(((aux2.getX()+aux3.getX())/2)+20, (aux2.getY()+aux3.getY())/2));
        conexiones.add(new Vertice((aux3.getX()+aux4.getX())/2, (aux3.getY()+aux4.getY())/2));
        conexiones.add(new Vertice(((aux4.getX()+aux1.getX())/2)-20, (aux4.getY()+aux1.getY())/2));
    }
    
}
