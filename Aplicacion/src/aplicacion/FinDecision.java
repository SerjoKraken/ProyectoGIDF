
package aplicacion;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class FinDecision extends Figura{

    public FinDecision(TipoF tipo) {
        super(tipo);
    }

    @Override
    public void dibujar(GraphicsContext gc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void dibujar(GraphicsContext gc, double x, double y) {
        double x1=vertices.get(0).getX();
        double y1=vertices.get(0).getY();
        double x2=vertices.get(1).getX();
        double y2=vertices.get(1).getY();
        
        double px1 =vertices.get(2).getX();
        double py1 =vertices.get(2).getY();
        double px2 =vertices.get(3).getX();
        double py2 =vertices.get(3).getY();
        
        if(esVerdadero && desicionPadre != null){
            gc.setStroke(Color.DEEPSKYBLUE);
            gc.setFill(Color.DEEPSKYBLUE);
            gc.fillText(texto, px2, py2);
        }else if(!esVerdadero && desicionPadre != null){
           
            
            gc.setStroke(Color.FUCHSIA);
            gc.setFill(Color.FUCHSIA);
            gc.fillText(texto, px2, py2);
        }else{
            gc.setStroke(Color.BLACK);
        }
        
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
