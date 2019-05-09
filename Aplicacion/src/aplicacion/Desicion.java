/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author jralb
 */
public class Desicion extends Figura{
        

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
        gc.setStroke(Color.ORANGE);
        for (double i = 0; i < 50; i+=0.5) {
            gc.strokeLine(x2+i, y2+i, x1+i, y1+i);
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
        
        
    }

    

}
