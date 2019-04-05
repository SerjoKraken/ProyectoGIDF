
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



public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private Button b1;
    
    @FXML
    private Button b2;
    
    @FXML
    private Button b3;

    @FXML
    private Button b4;
    
    @FXML
    private Canvas canvas;
    
    @FXML
    private AnchorPane ap;
    
    
    private GraphicsContext gc;
    
    boolean b = true;
    @FXML
    private void dibujar1(ActionEvent event) {
        b=true;
        canvas.setOnMouseClicked((MouseEvent event2) -> {
            if(b){
                double x1 = event2.getX() - 50;
                double y1 = event2.getY() -25;
                double x2 = event2.getX() + 50;
                double y2 = event2.getY() +25;

                gc.strokeLine(x1, y1, x1+100, y1);
                gc.strokeLine(x1+100, y1, x2, y2);
                gc.strokeLine(x1, y1+50, x2, y2);
                gc.strokeLine(x1, y1, x1, y1+50);
                b=false;
            }  
        });
       
        System.out.println("Dibujar 1");   
    }
    @FXML
    private void dibujar2(ActionEvent event) {
        System.out.println("datitox bonitox");   
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
        b= true;
        canvas.setOnMouseClicked((MouseEvent event2) -> {
            if(b){
                double x1 = event2.getX() - 33;
                double y1 = event2.getY() -25;
                double x2 = event2.getX() + 33;
                double y2 = event2.getY() +25;
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
    private void dibujar4(ActionEvent event) {
        gc.fillRect(0, 0, 500, 500);
        System.out.println("Dibujar 4");   
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        gc = canvas.getGraphicsContext2D();
        
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 500, 500);
        
        ap.setBackground(Background.EMPTY);
        
    }    
    
}
