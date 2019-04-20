
package aplicacion;




import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javax.swing.JOptionPane;



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
    
    private ArrayList<Figura> figuras = new ArrayList<>();
    
    private GraphicsContext gc;
    
    private Sistema sistema;
    
    private Figura f;
    
    boolean b = true;
    @FXML
    /**
     * Proceso
     */
    private void dibujarProceso(ActionEvent event) throws IOException {
        
        
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Crear proceso");
        dialog.setContentText("Introduzca el texto:");


        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().equals("")){
            System.out.println("Your name: " + result.get());
            double powerUp = 0;
            if(wea(result.get())>12){
                powerUp+=20;
                boolean a= true;
            }
            
            b=true;
            canvas.setOnMouseClicked((MouseEvent event2) -> {
            double p1 = event2.getX();
            double p2 = event2.getY();
            
            redimensionCanvas(p1,p2);
            
            
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
                /***
                 * caso en que el punto se crea muy arriba o si el punto se crea
                 * muy a la izquierda
                 */

                if(x1<0){
                    x=50;
                    x2=x2-x1;
                    x3=x3-x1;
                    x1=0;
                    x4=0;
                }
                
                if(y1<0){
                    y=25;
                    y3=y3-y1;
                    y4=y4-y1;
                    y1=0;
                    y2=0;
                }

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
                gc.setFill(Color.BLACK);
                gc.fillText(result.get(), x1+5,y1+30);

                Proceso proceso = new Proceso( TipoF.PROCESO);
                proceso.setVerticeCentro(new Vertice(x,y));
                proceso.getVertices().add(new Vertice(x1, y1));
                proceso.getVertices().add(new Vertice(x2, y2));
                proceso.getVertices().add(new Vertice(x3, y3));
                proceso.getVertices().add(new Vertice(x4, y4));
                
                
                proceso.texto = result.get();
                
                figuras.add(proceso);
                   
                b=false;
            }  
        });
            
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("cuidado");
            alert.setContentText("Debes ingresar un texto");

            alert.showAndWait();
        }

        // The Java 8 way to get the response value (with lambda expression).
        result.ifPresent(name -> System.out.println("Your name: " + name));
        
        

        
    }
    @FXML
    private void dibujarFlujo(ActionEvent event) {
        
        gc.setStroke(Color.BLACK);
        System.out.println("datitox");   
        b=true;
        canvas.setOnMouseClicked((MouseEvent event1) -> {
            double x1 = event1.getX();
            double y1 = event1.getY();
            Vertice vertice1;
            if((vertice1=buscarConexion(x1,y1))!=null){
            
                canvas.setOnMouseClicked((MouseEvent event2) -> {
                    if(b){
                        double x2 = event2.getX();
                        double y2 = event2.getY();
                        Vertice vertice2;
                      
                        if((vertice2 = buscarConexion(x2,y2))!=null){
                            gc.strokeLine(vertice1.getX(), vertice1.getY(), vertice2.getX(), vertice2.getY());


                            Flujo flujo = new Flujo(TipoF.FLUJO);
                            flujo.getVertices().add(new Vertice(x1,y1));
                            flujo.getVertices().add(new Vertice(x2,y2));
                            figuras.add(flujo);
                            b=false;
                        }
                    }


                });
            
            }
            
        });
           
    }

    public Vertice buscarConexion(double x, double y){
        if(!figuras.isEmpty()) {
            for (Figura figura : figuras) {
                if(!(figura instanceof Flujo)){
                    if(x<=figura.getVertices().get(2).getX()){
                        if(x>=figura.getVertices().get(0).getX()){
                           if(y<=figura.getVertices().get(2).getY()){
                                if(y>=figura.getVertices().get(0).getY()){
                                    return figura.verticeCentro;
                                }
                            } 
                        }
                    }
                }
            }
        }
        
        return null;
    }

    
    @FXML
    private void dibujarEntrada(ActionEvent event) throws IOException {
        
        
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Crear Entrada");
        dialog.setContentText("Introduzca el texto:");

        
        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().equalsIgnoreCase("")){
            System.out.println("Your name: " + result.get());
            b= true;
            canvas.setOnMouseClicked((MouseEvent event2) -> {
            double p1 = event2.getX();
            double p2 = event2.getY();
            redimensionCanvas(p1,p2);
            if(b){  
                double x = event2.getX();
                double y = event2.getY();
                double x1 = x - 33;
                double y1 = y - 25;
                double x2 = x + 67;
                double y2 = y - 25;
                double x3 = x + 33;
                double y3 = y + 25;
                double x4 = x - 67;
                double y4 = y + 25;
                /***
                 * por si la figura se sale por la izquierda y
                 * por si la figura se sale por la parte superior
                 */
                if(x4<0){
                    x=55;
                    x1=x1-x4;
                    x2=x2-x4;
                    x3=x3-x4;
                    x4=0;
                }
                if(y1<0){
                    y=25;
                    y3=y3-y1;
                    y4=y3;
                    y2=0;
                    y1=0;
                }
                gc.setStroke(Color.YELLOW);
                for (double i = 0; i < 100; i+=0.5) {
                    gc.strokeLine(x2-i, y2, x3-i, y3);
                    
                }
                gc.setStroke(Color.BLACK);
                gc.strokeLine(x1, y1, x2, y2);
                gc.strokeLine(x2, y2, x3, y3);
                gc.strokeLine(x3, y3, x4, y4);
                gc.strokeLine(x4, y4, x1, y1);
                gc.setFill(Color.BLACK);
                gc.fillText(result.get(), x1+5,y1+30);
                
                Entrada entrada = new Entrada(TipoF.ENTRADA);
                entrada.setVerticeCentro(new Vertice(x,y));
                entrada.getVertices().add(new Vertice(x1,y1));
                entrada.getVertices().add(new Vertice(x2,y2));
                entrada.getVertices().add(new Vertice(x3,y3));
                entrada.getVertices().add(new Vertice(x4,y4));
                entrada.texto = result.get();
                figuras.add(entrada);
                b=false;
            }       
        });
        }else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("cuidado");
            alert.setContentText("Debes ingresar un texto");

            alert.showAndWait();
        }

        // The Java 8 way to get the response value (with lambda expression).
        result.ifPresent(name -> System.out.println("Your name: " + name));
        
        
        
        System.out.println("Dibujar 3");   
    }
    
    @FXML
    private void limpiar(ActionEvent event) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
        sistema.getFiguras().clear();
        
        System.out.println("Limpieza");   
    }
    
    
    @FXML
    private void dibujarInicio(ActionEvent event) throws IOException {
        b= true;
        
            canvas.setOnMouseClicked((MouseEvent event2) -> {
                double p1 = event2.getX();
                double p2 = event2.getY();
                if(existeInicio()==false || existeFin()==false){
                redimensionCanvas(p1,p2);    
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
                    double y4 = y + 25;

                    if(x1-20<0){
                        x=70;
                        x2=x2-x1+20;
                        x3=x3-x1+20;
                        x4=x4-x1+20;
                        x1=20;
                    }
                    if(y1<0){
                        y=25;
                        y3=y3-y1;
                        y4=y4-y1;
                        y1=0;
                        y2=0;
                    }


                    gc.setStroke(Color.AQUAMARINE);
                    for (double i = 0; i < 50; i+=0.5) {

                        gc.strokeLine(x1, y1+i, x2, y2+i);
                        gc.strokeArc(x1-19+i, y1, 37, 50, 90, 180, ArcType.OPEN);
                        //gc.strokeLine(x3, y3, x4, y4);
                        gc.strokeArc(x1+82-i, y1, 37, 50, -90, 180, ArcType.OPEN);

                    }
                    b=false;
                    gc.setStroke(Color.BLACK);
                    gc.strokeLine(x1, y1, x2, y2);
                    gc.strokeArc(x1-19, y1, 37, 50, 90, 180, ArcType.OPEN);
                    gc.strokeLine(x3, y3, x4, y4);
                    gc.strokeArc(x1+82, y1, 37, 50, -90, 180, ArcType.OPEN);


                    if(existeInicio()==false){
                        Inicio inicio = new Inicio(TipoF.INICIO);
                        inicio.setVerticeCentro(new Vertice(x,y));
                        inicio.getVertices().add(new Vertice(x1,y1));
                        inicio.getVertices().add(new Vertice(x2,y2));
                        inicio.getVertices().add(new Vertice(x3,y3));
                        inicio.getVertices().add(new Vertice(x4,y4));
                        //se debe validar la diferencia entre inicio y fin
                        inicio.texto = "INICIO";
                        gc.fillText(inicio.texto, x1+5,y1+30);
                        figuras.add(inicio);
                        
                    }else if(existeFin() == false){
                        Inicio inicio = new Inicio(TipoF.FIN);
                        inicio.setVerticeCentro(new Vertice(x,y));
                        inicio.getVertices().add(new Vertice(x1,y1));
                        inicio.getVertices().add(new Vertice(x2,y2));
                        inicio.getVertices().add(new Vertice(x3,y3));
                        inicio.getVertices().add(new Vertice(x4,y4));
                        //se debe validar la diferencia entre inicio y fin
                        inicio.texto = "FIN";
                        gc.fillText(inicio.texto, x1+5,y1+30);
                        figuras.add(inicio);
                    }
                    
                }
                }
            
             
        });
    
        System.out.println("Dibujar Inicio");   
    }
    
    public boolean existeInicio(){
        if(!figuras.isEmpty()){
            for (Figura figura : figuras) {
                if(figura.getTipo()==TipoF.INICIO){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean existeFin(){
        if(!figuras.isEmpty()){
            for (Figura figura : figuras) {
                if(figura.getTipo()==TipoF.FIN){
                    return true;
                }
            }
        }
        return false;
    }
    
    @FXML
    private void dibujarDocumento(ActionEvent event) throws IOException {
        /***
         * Cambiar esto de abajo que salen palabras que no deberian ir en un 
         * codigo formal
         * primer aviso
         */
        
        
        //String pene = JOptionPane.showInputDialog("ingresar algo scw");
        //System.out.println(pene);
        
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Crear documento");
        dialog.setContentText("Introduzca el texto:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().equalsIgnoreCase("")){
            System.out.println("Your name: " + result.get());
            b= true;
        
            canvas.setOnMouseClicked((MouseEvent event2) -> {
            double p1 = event2.getX();
            double p2 = event2.getY();
            redimensionCanvas(p1,p2);   
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
                double y4 = y + 25;
                
                /***
                 * por si la figura a crear se crea muy arriba o muy a la izquierda 
                 * o la combinacion de las dos anteriores
                 */
                if(x1<0){
                    x=50;
                    x2=x2-x1;
                    x3=x3-x1;
                    x4=0;
                    x1=0;
                }
                if(y1<0){
                    y=25;
                    y3=y3-y1;
                    y4=y4-y1;        
                    y2=0;
                    y1=0;
                }
                gc.setStroke(Color.CHARTREUSE);
                
                for (double i = 0; i < 50; i+=0.5) {
                    
                    gc.strokeLine(x1+i, y1+i*0.9, x2, y2);
                    gc.strokeLine(x1+i, y1, x1, y1+50);
                    if(i>=25){
                        gc.strokeLine(x2, y2, x2-i, y1+50-i);
                    }else{
                        gc.strokeLine(x2, y2, x2-i, y1+50-i*1.9); 
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
                gc.strokeLine(x1, y1, x2, y2);
                gc.strokeLine(x1, y1, x4, y4);
                gc.strokeLine(x2, y2, x3, y3);
                gc.strokeArc(x1, y1+36, 50, 30, 180, 180, ArcType.OPEN);
                gc.strokeArc(x1+50, y1+36, 50, 30, 360, 180, ArcType.OPEN);
                gc.setFill(Color.BLACK);
                gc.fillText(result.get(), x1+5,y1+30);
                
                Documento documento = new Documento(TipoF.DOCUMENTACION);
                documento.setVerticeCentro(new Vertice(x,y));
                documento.getVertices().add(new Vertice(x1,y1));
                documento.getVertices().add(new Vertice(x2,y2));
                documento.getVertices().add(new Vertice(x3,y3));
                documento.getVertices().add(new Vertice(x4,y4));
                documento.texto = result.get();
                figuras.add(documento);
                
                b=false;
                
                

            }      
          
        });
        }else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("cuidado");
            alert.setContentText("Debes ingresar un texto");

            alert.showAndWait();
        }

        // The Java 8 way to get the response value (with lambda expression).
        result.ifPresent(name -> System.out.println("Your name: " + name));
        
        
        
        System.out.println("Dibujar Documento");   
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sistema = new Sistema();
        gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        
        ap.setBackground(Background.EMPTY);
        
    }    
    
    
    public void validarAreaDocumento(int x,int y){
        
        
    }
    
    public void validarAreaInicioFin(int x,int y){
        
        
    }
    
    /*
    public void validarAreaEntrada(double x,double y){
        if(!sistema.getEntradas().isEmpty()){
            Entrada d = sistema.getEntradas().get(0);
        
        if(x>d.getVertices().get(0).getX() && y>d.getVertices().get(0).getY() 
                && x<d.getVertices().get(1).getX() && y>d.getVertices().get(1).getY() 
                && x>d.getVertices().get(2).getX() && y<d.getVertices().get(2).getY() 
                && x<d.getVertices().get(3).getX() && y<d.getVertices().get(3).getY()){
            System.out.println("muerete perkin qlo");
            
        }else{
            System.out.println("todo de apanaaaaaaaaaaaaaaaaaaaaa");
        }
        
        }else{
            
        }
        
        
    }
   */
    
    /*
    public void validarAreaProceso(double x,double y){
        if(!sistema.getProcesos().isEmpty()){
            Proceso d = sistema.getProcesos().get(0);
        
            if(x>d.getVertices().get(0).getX() && y>d.getVertices().get(0).getY() 
                    && x<d.getVertices().get(1).getX() && y>d.getVertices().get(1).getY() 
                    && x>d.getVertices().get(2).getX() && y<d.getVertices().get(2).getY() 
                    && x<d.getVertices().get(3).getX() && y<d.getVertices().get(3).getY()){
                System.out.println("muerete perkin qlo");

            }else{
                System.out.println("todo de apanaaaaaaaaaaaaaaaaaaaaa");
            }
        
        }else{
            
        }
        
    }
    */
    
    
    public void redimensionCanvas(double p1, double p2){
        if(p1+51>canvas.getWidth()){
                canvas.setWidth(canvas.getWidth()+50);
                if(p2+51>canvas.getHeight()){
                    canvas.setHeight(canvas.getHeight()+50);
                }
            } else if (p2+51>canvas.getHeight()) {
                canvas.setHeight(canvas.getHeight()+50);
                
            }
    }
    
    
    public int wea(String cadena){
        char[] arrayChar = cadena.toCharArray();
        int cant=0;
        for(int i=0; i<arrayChar.length; i++){
            cant++;
        }
        System.out.println(cant);

        return cant;
    }

    public ArrayList<Figura> getFiguras() {
        return figuras;
    }

    public void setFiguras(ArrayList<Figura> figuras) {
        this.figuras = figuras;
    }

    
    
    
}
