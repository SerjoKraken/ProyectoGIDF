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

public class Documento extends Figura{
    Color color = Sistema.color[2];
    public Documento(TipoF tipo) {
        super(tipo);
    }

    public void setColor(Color color) {
        this.color = color;
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
        
        gc.setStroke(color);
        for (double i = 0; i < 50; i+=0.5) {
            gc.strokeLine(x1+i, y1+i*0.9, x2, y2);
            gc.strokeLine(x1+i, y1, x1, y1+50);
            if(i>=25){
                gc.strokeLine(x2, y2, x2-i, y1+50-i);
            }else{
                gc.strokeLine(x2, y2, x2-i, y1+50-i*1.9); 
            }
            gc.strokeArc(x1, y1+36-i, 50, 30, 180, 180, ArcType.OPEN);
            gc.strokeArc(x1+50, y1+36-i, 50, 30-i, 360, 180, ArcType.OPEN);
        }
        gc.setStroke(Color.BLACK);
        gc.strokeLine(x1, y1, x2, y2);
        gc.strokeLine(x1, y1, x4, y4);
        gc.strokeLine(x2, y2, x3, y3);
        gc.strokeArc(x1, y1+36, 50, 30, 180, 180, ArcType.OPEN);
        gc.strokeArc(x1+50, y1+36, 50, 30, 360, 180, ArcType.OPEN);
        gc.setFill(Color.BLACK);
        gc.fillText(texto, x1+5,y1+30);
    }

    @Override
    public void calcularConexiones() {
        Vertice aux1 = vertices.get(0);
        Vertice aux2 = vertices.get(1);
        Vertice aux3 = vertices.get(2);
        Vertice aux4 = vertices.get(3);
        
        if(conexiones.size()>0){
            conexiones.set(0,new Vertice((aux1.getX()+aux2.getX())/2, (aux1.getY()+aux2.getY())/2));
            conexiones.set(1,new Vertice((aux2.getX()+aux3.getX())/2, (aux2.getY()+aux3.getY())/2));
            conexiones.set(2,new Vertice((aux3.getX()+aux4.getX())/2, (aux3.getY()+aux4.getY())/2));
            conexiones.set(3,new Vertice((aux4.getX()+aux1.getX())/2, (aux4.getY()+aux1.getY())/2));
        }else{
            conexiones.add(new Vertice((aux1.getX()+aux2.getX())/2, (aux1.getY()+aux2.getY())/2));
            conexiones.add(new Vertice((aux2.getX()+aux3.getX())/2, (aux2.getY()+aux3.getY())/2));
            conexiones.add(new Vertice((aux3.getX()+aux4.getX())/2, (aux3.getY()+aux4.getY())/2));
            conexiones.add(new Vertice((aux4.getX()+aux1.getX())/2, (aux4.getY()+aux1.getY())/2));
            
        }
    }

    @Override
    public void dibujar(GraphicsContext gc, double x, double y) {
        
        double x1 = x - 50;
        double y1 = y - 25;
        double x2 = x + 50;
        double y2 = y - 25;
        double x3 = x + 50;
        double y3 = y + 25;
        double x4 = x - 50;
        double y4 = y + 25;
        
        verticeCentro.setX(x);
        verticeCentro.setY(y);
        vertices.get(0).setX(x1);
        vertices.get(0).setY(y1);
        vertices.get(1).setX(x2);
        vertices.get(1).setY(y2);
        vertices.get(2).setX(x3);
        vertices.get(2).setY(y3);
        vertices.get(3).setX(x4);
        vertices.get(3).setY(y4);
        calcularConexiones();
        
        gc.setStroke(color);
        for (double i = 0; i < 50; i+=0.5) {
            gc.strokeLine(x1+i, y1+i*0.9, x2, y2);
            gc.strokeLine(x1+i, y1, x1, y1+50);
            if(i>=25){
                gc.strokeLine(x2, y2, x2-i, y1+50-i);
            }else{
                gc.strokeLine(x2, y2, x2-i, y1+50-i*1.9); 
            }
            gc.strokeArc(x1, y1+36-i, 50, 30, 180, 180, ArcType.OPEN);
            gc.strokeArc(x1+50, y1+36-i, 50, 30-i, 360, 180, ArcType.OPEN);
        }
        gc.setStroke(Color.BLACK);
        gc.strokeLine(x1, y1, x2, y2);
        gc.strokeLine(x1, y1, x4, y4);
        gc.strokeLine(x2, y2, x3, y3);
        gc.strokeArc(x1, y1+36, 50, 30, 180, 180, ArcType.OPEN);
        gc.strokeArc(x1+50, y1+36, 50, 30, 360, 180, ArcType.OPEN);
        gc.setFill(Color.BLACK);
        gc.fillText(texto, x1+5,y1+30);
    }
    
    
}
