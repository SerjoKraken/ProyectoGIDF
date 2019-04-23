/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion;

import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author Serjo
 */
public class Flujo extends Figura{
    
    public Flujo( TipoF tipo) {
        super(tipo);
    }

    @Override
    public void dibujar(GraphicsContext gc) {
        double x1=vertices.get(0).getX();
        double y1=vertices.get(0).getY();
        double x2=vertices.get(1).getX();
        double y2=vertices.get(1).getY();
        
        gc.strokeLine(x1,y1,x2,y2);
    }
    
}
