/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author jralb
 */
public class Desicion extends Figura{
        
    private ArrayList<Figura> verdadero= new ArrayList<>();
    private ArrayList<Figura> falso= new ArrayList<>();
    

    public Desicion(TipoF tipo) {
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
        gc.setStroke(Sistema.color[4]);
        for (double i = 0; i < 50; i+=0.1) {
            gc.strokeLine(x2-i, y2-i, x3-i, y3-i);
        }
        gc.setStroke(Color.BLACK);
        gc.strokeLine(x1, y1, x2, y2);
        gc.strokeLine(x2, y2, x3, y3);
        gc.strokeLine(x3, y3, x4, y4);
        gc.strokeLine(x4, y4, x1, y1);
        gc.setFill(Color.BLACK);
        gc.fillText(texto, x1-25,y1+50);
    }

    @Override
    public void calcularConexiones() {
        Vertice aux1 = vertices.get(0);
        
        
        if(conexiones.size()>0){
            conexiones.set(0,aux1);
            
        }else{
            conexiones.add(aux1);
            
        }
        
    }

    @Override
    public void dibujar(GraphicsContext gc, double x, double y) {
        double x1 = x ;
        double y1 = y - 50;
        double x2 = x + 50;
        double y2 = y ;
        double x3 = x;
        double y3 = y + 50;
        double x4 = x - 50;
        double y4 = y ;
        
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
        
        
        gc.setStroke(Sistema.color[4]);
        for (double i = 0; i > 50; i+=0.1) {
            gc.strokeLine(x2-i, y2-i, x3-i, y3-i);
        }
        gc.setStroke(Color.BLACK);
        gc.strokeLine(x1, y1, x2, y2);
        gc.strokeLine(x2, y2, x3, y3);
        gc.strokeLine(x3, y3, x4, y4);
        gc.strokeLine(x4, y4, x1, y1);
        gc.setFill(Color.BLACK);
        gc.fillText(texto, x1-25,y1+50);
    }

    

    

}
