
package aplicacion;




import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;



public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private Button b1;
    
    @FXML
    private Button b5;
    
    @FXML
    private Button b2;
    
    @FXML
    private Button b3;

    @FXML
    private Button b4;
    
    @FXML
    private Button b6;
    
    @FXML
    private Canvas canvas;
    
    @FXML
    private AnchorPane ap;
    
    
    private GraphicsContext gc;
    
    private Sistema sistema;
    
    boolean b = true;
    @FXML
    /**
     * Proceso
     */
    private void dibujarProceso(ActionEvent event) {
        
        b=true;
        canvas.setOnMouseClicked((MouseEvent event2) -> {
            
            if(b){
                double x = event2.getX();
                double y = event2.getY();
                double x1 = x - 50;
                double y1 = y - 25;
                double x2 = x + 50;
                double y2 = y - 25;
                double x3 = x + 50;
                double y3 = y + 25;
                double x4 = x - 50;
                double y4 = y +25;
               

                for (int i = 0; i < 50; i++) {
                    gc.setStroke(Color.RED);
                    gc.strokeLine(x1, y1+i, x2, y2+i);
                    gc.strokeLine(x2, y2, x3, y3);
                    gc.strokeLine(x3, y3, x4, y4);
                    gc.strokeLine(x4, y4, x1, y1);
                    
                }
                gc.setStroke(Color.BLACK);
                gc.strokeLine(x1, y1, x2, y2);
                gc.strokeLine(x2, y2, x3, y3);
                gc.strokeLine(x3, y3, x4, y4);
                gc.strokeLine(x4, y4, x1, y1);

                Proceso proceso = new Proceso(new Vertice(x,y), TipoF.PROCESO);
                proceso.getVertices().add(new Vertice(x1, y1));
                proceso.getVertices().add(new Vertice(x2, y2));
                proceso.getVertices().add(new Vertice(x3, y3));
                proceso.getVertices().add(new Vertice(x4, y4));
                
                sistema.getProcesos().add(proceso);
                
                for (int i = 0; i < sistema.getProcesos().size(); i++) {
                    System.out.println("Vertice: "+ (i+1));
                    for(int j = 0; j < sistema.getProcesos().get(i).getVertices().size(); j++){
                        
                        System.out.println("Punto "+j+1+" "+sistema.getProcesos().get(i).getVertices().get(j).getX()+", "+sistema.getProcesos().get(i).getVertices().get(j).getY());    
                    }
                    System.out.println("");
                }
                
                System.out.println("Finish");    
                b=false;
            }  
        });

        
    }
    @FXML
    private void dibujarFlujo(ActionEvent event) {
        gc.setStroke(Color.BLACK);
        System.out.println("datitox");   
        b=true;
        canvas.setOnMouseClicked((MouseEvent event1) -> {
            double x1 = event1.getX();
            double y1 = event1.getY();
            canvas.setOnMouseClicked((MouseEvent event2) -> {
                if(b){
                    gc.strokeLine(x1, y1, event2.getX(), event2.getY());
                    b=false;
                }
                
                
            });
            
        });
        System.out.println("Dibujar Flujo");   
    }

    @FXML
    private void dibujarEntrada(ActionEvent event) {
        
        b= true;
        canvas.setOnMouseClicked((MouseEvent event2) -> {
            if(b){
                double x1 = event2.getX() - 33;
                double y1 = event2.getY() -25;
                double x2 = event2.getX() + 33;
                double y2 = event2.getY() +25;
                for (int i = 0; i < 50; i++) {
                    gc.setStroke(Color.YELLOW);
                    gc.strokeLine(x1+i, y1+i, x1+100, y1);
                    gc.strokeLine(x1+100-i, y1, x2-i, y2);
                    gc.strokeLine(x1+i, y1, x1-33+i, y2);
                    gc.strokeLine(x1-33, y2, x2, y2-i);
                    
                }
                gc.setStroke(Color.BLACK);
                gc.strokeLine(x1, y1, x1+100, y1);
                gc.strokeLine(x1+100, y1, x2, y2);
                gc.strokeLine(x1, y1, x1-33, y2);
                gc.strokeLine(x1-33, y2, x2, y2);
                b=false;
            }       
        });
        
        System.out.println("Dibujar 3");   
    }
    @FXML
    private void limpiar(ActionEvent event) {
     
        gc.fillRect(0, 0, 642, 515);
        System.out.println("Limpieza");   
    }
    
    
    @FXML
    private void dibujarInicio(ActionEvent event) {
        b= true;
        
        canvas.setOnMouseClicked((MouseEvent event2) -> {
            if(b){
                double x1 = event2.getX() - 50;
                double y1 = event2.getY() -25;
                double x2 = event2.getX() + 50;
                double y2 = event2.getY() +25;
                for (double i = 0; i < 50; i+=0.5) {
                    gc.setStroke(Color.AQUAMARINE);
                    gc.strokeLine(x1, y1+i, x1+100, y1+i);
                    gc.strokeArc(x1-19+i, y1, 37, 50, 90, 180, ArcType.OPEN);
                    gc.strokeLine(x1, y1+50, x2, y2);
                    gc.strokeArc(x1+82-i, y1, 37, 50, -90, 180, ArcType.OPEN);
                   
                }
                b=false;
                gc.setStroke(Color.BLACK);
                gc.strokeLine(x1, y1, x1+100, y1);
                gc.strokeArc(x1-19, y1, 37, 50, 90, 180, ArcType.OPEN);
                gc.strokeLine(x1, y1+50, x2, y2);
                gc.strokeArc(x1+82, y1, 37, 50, -90, 180, ArcType.OPEN);
            }      
        });
        
        System.out.println("Dibujar Inicio");   
    }
        @FXML
    private void dibujarDocumento(ActionEvent event) {
        b= true;
        
        canvas.setOnMouseClicked((MouseEvent event2) -> {
            if(b){
                double x1 = event2.getX()-50;
                double y1 = event2.getY()-25;

                for (double i = 0; i < 50; i+=0.5) {
                    gc.setStroke(Color.CHARTREUSE);
                    gc.strokeLine(x1+i, y1+i*0.9, x1+100, y1);
                    gc.strokeLine(x1+i, y1, x1, y1+50);
                    if(i>=25){
                        gc.strokeLine(x1+100, y1, x1+100-i, y1+50-i);
                    }else{
                        gc.strokeLine(x1+100, y1, x1+100-i, y1+50-i*1.9); 
                    }
                    
                    /**
                     * Güatita hacia abajo
                     */
                    gc.strokeArc(x1, y1+36-i, 50, 30, 180, 180, ArcType.OPEN);

                    /**
                     * Güatita hacia arriba
                     */
                    gc.strokeArc(x1+50, y1+36-i, 50, 30-i, 360, 180, ArcType.OPEN);

                }
                gc.setStroke(Color.BLACK);
                gc.strokeLine(x1, y1, x1+100, y1);
                gc.strokeLine(x1, y1, x1, y1+50);
                gc.strokeLine(x1+100, y1, x1+100, y1+50);
                gc.strokeArc(x1, y1+36, 50, 30, 180, 180, ArcType.OPEN);
                gc.strokeArc(x1+50, y1+36, 50, 30, 360, 180, ArcType.OPEN);
                b=false;

            }      
        });
        
        System.out.println("Dibujar Documento");   
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sistema = new Sistema();
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 500, 500);

        
        ap.setBackground(Background.EMPTY);
        
    }    
    
}
