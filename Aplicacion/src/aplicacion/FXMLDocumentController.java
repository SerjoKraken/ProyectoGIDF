
package aplicacion;




import java.net.URL;
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
  
    
    boolean b = true;
    @FXML
    private void dibujar1(ActionEvent event) {
        gc.setStroke(Color.RED);
        b=true;
        canvas.setOnMouseClicked((MouseEvent event2) -> {
            if(b){
                double x1 = event2.getX() - 50;
                double y1 = event2.getY() -25;
                double x2 = event2.getX() + 50;
                double y2 = event2.getY() +25;

                for (int i = 0; i < 50; i++) {
                    gc.strokeLine(x1, y1+i, x1+100, y1+i);
                    gc.strokeLine(x1+100, y1, x2, y2);
                    gc.strokeLine(x1, y1+50, x2, y2);
                    gc.strokeLine(x1, y1, x1, y1+50);
                    
                }
                b=false;
            }  
        });
       
        System.out.println("Dibujar 1");   
    }
    @FXML
    private void dibujar2(ActionEvent event) {
        gc.setStroke(Color.PINK);
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
        System.out.println("Dibujar 2");   
    }

    @FXML
    private void dibujar3(ActionEvent event) {
        gc.setStroke(Color.YELLOW);
        b= true;
        canvas.setOnMouseClicked((MouseEvent event2) -> {
            if(b){
                double x1 = event2.getX() - 33;
                double y1 = event2.getY() -25;
                double x2 = event2.getX() + 33;
                double y2 = event2.getY() +25;
                for (int i = 0; i < 50; i++) {
                    gc.strokeLine(x1+i, y1+i, x1+100, y1);
                    gc.strokeLine(x1+100-i, y1, x2-i, y2);
                    gc.strokeLine(x1+i, y1, x1-33+i, y2);
                    gc.strokeLine(x1-33, y2, x2, y2-i);
                    
                }
                b=false;
            }       
        });
        
        System.out.println("Dibujar 3");   
    }
    @FXML
    private void dibujar4(ActionEvent event) {
     
        gc.fillRect(0, 0, 642, 515);
        System.out.println("Dibujar 4");   
    }
    
    
    @FXML
    private void dibujar5(ActionEvent event) {
        b= true;
        gc.setStroke(Color.AQUAMARINE);
        canvas.setOnMouseClicked((MouseEvent event2) -> {
            if(b){
                double x1 = event2.getX() - 50;
                double y1 = event2.getY() -25;
                double x2 = event2.getX() + 50;
                double y2 = event2.getY() +25;
                for (double i = 0; i < 50; i+=0.5) {
                    gc.strokeLine(x1, y1+i, x1+100, y1+i);
                    gc.strokeArc(x1-19+i, y1, 37, 50, 90, 180, ArcType.OPEN);
                    gc.strokeLine(x1, y1+50, x2, y2);
                    gc.strokeArc(x1+82-i, y1, 37, 50, -90, 180, ArcType.OPEN);
                   
                }
                b=false;
                
            }      
        });
        
        System.out.println("Dibujar 3");   
    }
        @FXML
    private void dibujar6(ActionEvent event) {
        b= true;
        gc.setStroke(Color.CHARTREUSE);
        canvas.setOnMouseClicked((MouseEvent event2) -> {
            if(b){
                double x1 = event2.getX()-50;
                double y1 = event2.getY()-25;

                for (double i = 0; i < 50; i+=0.5) {
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
        
                b=false;
            }      
        });
        
        System.out.println("Dibujar 3");   
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 500, 500);

        
        ap.setBackground(Background.EMPTY);
        
    }    
    
}
