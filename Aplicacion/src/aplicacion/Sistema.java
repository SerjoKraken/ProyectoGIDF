
package aplicacion;




import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;




public class Sistema implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private Button correr;
    @FXML
    private Button codigo;
    @FXML
    private Button b1;
    
    @FXML
    private Button b5;
    
    @FXML
    private Button b2;
    
    @FXML
    private Button b3;
    
    @FXML
    private TextArea area;
    
    @FXML
    private Button b4;
    
    @FXML
    private Button b6;
    
    @FXML
    private Button b7;
    
    @FXML
    private Button b8;
    
    @FXML
    private Button b9;
    
    @FXML
    private Button b10;
    
    @FXML
    private Button b11;
    
    @FXML
    private Button b12;
    
    @FXML
    private Button jpg;
    @FXML
    private Button png;
    @FXML
    private Button pdf;
    @FXML
    private Button edit;
    @FXML
    private Canvas canvas;
    
    @FXML
    private AnchorPane ap;
    
    private ArrayList<Figura> figuras = new ArrayList<>();
    
    private GraphicsContext gc;
    
    private Figura aux = null;
    
    private Figura f;
    public double powerUp=0;
    boolean b = false;
    boolean run=false;
    ArrayList<Variable> variables = new ArrayList<>();
    String texto="";
    @FXML
    public void mover()  {
        
        canvas.setOnMousePressed(e1->{
            aux = buscarConexion(e1.getX(), e1.getY());
            canvas.setOnMouseDragged(e2->{ 
                if(aux!=null && moviendo && run==false && corriendo==false){
                    //System.out.println("Manteniendo");
                    redimensionCanvas(e2.getX(), e2.getY());
                   
                    aux.dibujar(gc, e2.getX(), e2.getY());


                    actualizar();
                }else if(!moviendo){
                    moviendo=true;
                }
            });

            canvas.setOnMouseReleased(e3->{

                //System.out.println("Soltado");
                moviendo = false;
                
                aux=null;



            });
        });
               
        
    }
    
    @FXML
    public void editar()  {
        b=true;
        canvas.setOnMouseClicked((MouseEvent event1) -> {
            double x1 = event1.getX();
            double y1 = event1.getY();
            
            if(buscarConexion(x1,y1)!=null && buscarConexion(x1,y1).tipo!=TipoF.INICIO && 
                    buscarConexion(x1,y1).tipo!=TipoF.FIN && buscarConexion(x1,y1).tipo!=TipoF.DESICION 
                    && buscarConexion(x1,y1).tipo!=TipoF.ITERACION && buscarConexion(x1,y1).tipo!=TipoF.FLUJO 
                    && buscarConexion(x1,y1).tipo!=TipoF.FINDESICION){
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Ventana de seleccion");
           
                alert.setContentText("Escoge una opcion.");

                ButtonType buttonTypeOne = new ButtonType("Color");
                ButtonType buttonTypeTwo = new ButtonType("Modificar contenido");
                ButtonType buttonTypeThree = new ButtonType("Modificar tipo");
                ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree, buttonTypeCancel);
                Optional<ButtonType> result = alert.showAndWait();               
                if (result.get() == buttonTypeOne){
                    
                    for (int i = 0; i < figuras.size(); i++) {
                        if(buscarConexion(x1,y1)==figuras.get(i)){
                            ((Proceso)figuras.get(i)).setColor(Color.BLUE);
                            actualizar();
                        }
                    }
                     
                } else if (result.get() == buttonTypeTwo) {
                    if(buscarConexion(x1,y1).tipo==TipoF.PROCESO){
                        TextInputDialog dialog = new TextInputDialog(buscarConexion(x1,y1).texto);
                        dialog.setTitle("Modificar texto");
                        dialog.setContentText("Introduzca el texto:");
                        Optional<String> resultmodtext = dialog.showAndWait();
                        if(resultmodtext.isPresent() && !result.get().equals(" ") && !resultmodtext.get().equalsIgnoreCase(" ")){
                            if (resultmodtext.get().matches("[A-Za-z1-9]+=.+") && evaluarAritmetica(resultmodtext.get().split("=")[1],variables)){
                                for (int i = 0; i < figuras.size(); i++) {
                                    if(buscarConexion(x1,y1)==figuras.get(i)){
                                        figuras.get(i).setTexto(resultmodtext.get());
                                        actualizar();
                                    }
                                }
                            }
                        }else{
                            Alert alert1 = new Alert(AlertType.WARNING);
                            alert1.setTitle("Error");
                            alert1.setHeaderText("cuidado");
                            alert1.setContentText("El formato del texto ingresado es incorrecto");

                            alert.showAndWait();
                        }
                    }else if(buscarConexion(x1,y1).tipo==TipoF.DOCUMENTACION){
                        TextInputDialog dialog = new TextInputDialog(buscarConexion(x1,y1).texto);
                        dialog.setTitle("Modificar texto");
                        dialog.setContentText("Introduzca el texto:");
                        Optional<String> resultmodtext = dialog.showAndWait();
                        if(resultmodtext.isPresent() && !resultmodtext.get().equals(" ") && !resultmodtext.get().equalsIgnoreCase(" ")){
                            if (resultmodtext.get().matches("[A-Za-z]+") && evaluarAritmetica(resultmodtext.get(),variables)){
                                for (int i = 0; i < figuras.size(); i++) {
                                    if(buscarConexion(x1,y1)==figuras.get(i)){
                                        figuras.get(i).setTexto(resultmodtext.get());
                                        actualizar();
                                    }
                                }
                            }
                        }else{
                            Alert alert1 = new Alert(AlertType.WARNING);
                            alert1.setTitle("Error");
                            alert1.setHeaderText("cuidado");
                            alert1.setContentText("El formato del texto ingresado es incorrecto");

                            alert.showAndWait();
                        }
                    }else if(buscarConexion(x1,y1).tipo==TipoF.ENTRADA || buscarConexion(x1,y1).tipo==TipoF.SALIDA){
                        if(buscarConexion(x1,y1).tipo==TipoF.ENTRADA){
                            TextInputDialog dialog = new TextInputDialog(buscarConexion(x1,y1).texto);
                            dialog.setTitle("Modificar texto");
                            dialog.setContentText("Introduzca el texto:");
                            Optional<String> resultmodtext = dialog.showAndWait();
                            if(resultmodtext.isPresent() && !resultmodtext.get().equals(" ") && !resultmodtext.get().equalsIgnoreCase(" ")){
                                if ((resultmodtext.get().matches("[A-Za-z]+=.+") && 
                                        evaluarAritmetica(resultmodtext.get().split("=")[1],variables)) || 
                                        ((resultmodtext.get().matches("[A-Za-z]+") && evaluarAritmetica(resultmodtext.get(),variables)))){
                                    for (int i = 0; i < figuras.size(); i++) {
                                        if(buscarConexion(x1,y1)==figuras.get(i)){
                                            figuras.get(i).setTexto(resultmodtext.get());
                                            actualizar();
                                        }
                                    }
                                }
                            }else{
                                Alert alert1 = new Alert(AlertType.WARNING);
                                alert1.setTitle("Error");
                                alert1.setHeaderText("cuidado");
                                alert1.setContentText("El formato del texto ingresado es incorrecto");

                                alert.showAndWait();

                            }
                        }
                        
                    } else if (buscarConexion(x1,y1).tipo==TipoF.PROCESO){
                        TextInputDialog dialog = new TextInputDialog(buscarConexion(x1,y1).texto);
                        dialog.setTitle("Modificar texto");
                        dialog.setContentText("Introduzca el texto:");
                        Optional<String> resultmodtext = dialog.showAndWait();
                        if(resultmodtext.isPresent() && !result.get().equals(" ") && !resultmodtext.get().equalsIgnoreCase(" ")){
                            if (resultmodtext.get().matches("[A-Za-z1-9]+=.+") && evaluarAritmetica(resultmodtext.get().split("=")[1],variables)){
                                for (int i = 0; i < figuras.size(); i++) {
                                    if(buscarConexion(x1,y1)==figuras.get(i)){
                                        figuras.get(i).setTexto(resultmodtext.get());
                                        actualizar();
                                    }
                                }
                            }
                        }else{
                            Alert alert1 = new Alert(AlertType.WARNING);
                            alert1.setTitle("Error");
                            alert1.setHeaderText("cuidado");
                            alert1.setContentText("El formato del texto ingresado es incorrecto");

                            alert.showAndWait();
                        }
                    }else{
                        
                    }
                    
                } else if (result.get() == buttonTypeThree) {
                    if(buscarConexion(x1,y1).tipo==TipoF.PROCESO){
                        List<String> choices = new ArrayList<>();
                        choices.add("documento");
                        choices.add("entrada");

                        ChoiceDialog<String> dialog = new ChoiceDialog<>("documento", choices);
                        alert.setTitle("Ventana de seleccion");          
                        alert.setContentText("Escoge una opcion.");

                        // Traditional way to get the response value.
                        Optional<String> result1 = dialog.showAndWait();
                        if (result1.isPresent() && result1.get()=="documento"){
                            System.out.println("aaaaaaaaaaaaaa");                           
                            TextInputDialog dialogF = new TextInputDialog(buscarConexion(x1,y1).texto);
                            dialogF.setTitle("Crear documento");
                            dialogF.setContentText("Introduzca el texto:");
                            Optional<String> resultMODDO = dialogF.showAndWait();
                            if(resultMODDO.isPresent() && !resultMODDO.get().equals(" ") && !resultMODDO.get().equalsIgnoreCase(" ")){
                                if (resultMODDO.get().matches("[A-Za-z]+") && evaluarAritmetica(resultMODDO.get(),variables)){
                                    for (int i = 0; i < figuras.size(); i++) {
                                        if(buscarConexion(x1,y1)==figuras.get(i)){
                                            figuras.get(i).setEstado(false);
                                            actualizar();
                                        }
                                    }                      
                                    double x1N = x1 - 50;
                                    double y1N = y1 - 25;
                                    double x2 = x1 + 50;
                                    double y2 = y1 - 25;
                                    double x3 = x1 + 50;
                                    double y3 = y1 + 25;
                                    double x4 = x1 - 50;
                                    double y4 = y1 + 25;

                                    /***
                                     * por si la figura a crear se crea muy arriba o muy a la izquierda 
                                     * o la combinacion de las dos anteriores
                                     */
                                    if(x1N<0){
                                        x1=50;
                                        x2=x2-x1N;
                                        x3=x3-x1N;
                                        x4=0;
                                        x1N=0;
                                    }
                                    if(y1N<0){
                                        y1=25;
                                        y3=y3-y1N;
                                        y4=y4-y1N;        
                                        y2=0;
                                        y1N=0;
                                    }

                                    Documento documento = new Documento(TipoF.DOCUMENTACION);
                                    documento.setVerticeCentro(new Vertice(x1,y1));
                                    documento.getVertices().add(new Vertice(x1N,y1N));
                                    documento.getVertices().add(new Vertice(x2,y2));
                                    documento.getVertices().add(new Vertice(x3,y3));
                                    documento.getVertices().add(new Vertice(x4,y4));
                                    documento.texto = resultMODDO.get();
                                    documento.calcularConexiones();
                                    documento.setEstado(true);
                                    figuras.add(documento);
                                    documento.dibujar(gc);

                                    actualizar();



                                    b=false;
                                    canvas.setOnMouseClicked(null);





                                }else{
                                    Alert alertMODDO = new Alert(AlertType.WARNING);
                                    alertMODDO.setTitle("Error");
                                    alertMODDO.setHeaderText("cuidado");
                                    alertMODDO.setContentText("El formato del texto ingresado es incorrecto");

                                    alertMODDO.showAndWait();      
                                }
                            }else {
                                Alert alertMODDO = new Alert(AlertType.WARNING);
                                alertMODDO.setTitle("Error");
                                alertMODDO.setHeaderText("cuidado");
                                alertMODDO.setContentText("Debes ingresar un texto");

                                alertMODDO.showAndWait();
                            }
                            
                        }else if(result.isPresent() && result1.get()=="entrada"){
                            
                            
                            TextInputDialog dialogF = new TextInputDialog(buscarConexion(x1,y1).texto);
                            dialogF.setTitle("Crear Entrada");
                            dialogF.setContentText("Introduzca el texto:");


                            Optional<String> resultMOEN = dialogF.showAndWait();


                            if(resultMOEN.isPresent() && !resultMOEN.get().equals(" ") && !resultMOEN.get().equalsIgnoreCase(" ")){

                                if ((resultMOEN.get().matches("[A-Za-z]+=.+") && evaluarAritmetica(resultMOEN.get().split("=")[1],variables)) || 
                                        ((resultMOEN.get().matches("[A-Za-z]+") && evaluarAritmetica(resultMOEN.get(),variables)))){
                                        for (int i = 0; i < figuras.size(); i++) {
                                              if(buscarConexion(x1,y1)==figuras.get(i)){
                                                  figuras.get(i).setEstado(false);
                                                  actualizar();
                                              }
                                        }
               
                                        double x1N = x1 - 33;
                                        double y1N = y1 - 25;
                                        double x2 = x1 + 67;
                                        double y2 = y1 - 25;
                                        double x3 = x1 + 33;
                                        double y3 = y1 + 25;
                                        double x4 = x1 - 67;
                                        double y4 = y1 + 25;
                                        /***
                                         * por si la figura se sale por la izquierda y
                                         * por si la figura se sale por la parte superior
                                         */
                                        if(x4<0){
                                            x1=55;
                                            x1N=x1N-x4;
                                            x2=x2-x4;
                                            x3=x3-x4;
                                            x4=0;
                                        }
                                        if(y1<0){
                                            y1=25;
                                            y3=y3-y1N;
                                            y4=y3;
                                            y2=0;
                                            y1N=0;
                                        }

                                        Entrada entrada = new Entrada(TipoF.ENTRADA);
                                        entrada.setVerticeCentro(new Vertice(x1,y1));
                                        entrada.getVertices().add(new Vertice(x1N,y1N));
                                        entrada.getVertices().add(new Vertice(x2,y2));
                                        entrada.getVertices().add(new Vertice(x3,y3));
                                        entrada.getVertices().add(new Vertice(x4,y4));
                                        entrada.calcularConexiones();
                                        entrada.texto = resultMOEN.get();
                                        entrada.setEstado(true);
                                        figuras.add(entrada);
                                        entrada.dibujar(gc);

                                        actualizar();

                                        b=false;
                                        canvas.setOnMouseClicked(null);


                                    

                                    }else {
                                        Alert alertE = new Alert(AlertType.WARNING);
                                        alertE.setTitle("Error");
                                        alertE.setHeaderText("cuidado");
                                        alertE.setContentText("El texto ingresado es incorrecto debe ser del estilo 'algo' = 'algo' o solamente 'algo'");

                                        alertE.showAndWait();
                                    }
                                }else{
                                    Alert alertE = new Alert(AlertType.WARNING);
                                    alertE.setTitle("Error");
                                    alertE.setHeaderText("cuidado");
                                    alertE.setContentText("Debes ingresar un texto");

                                    alert.showAndWait();
                                }

                        }


                    }else if(buscarConexion(x1,y1).tipo==TipoF.ENTRADA){
                        List<String> choices = new ArrayList<>();
                        choices.add("documento");
                        choices.add("proceso");

                        ChoiceDialog<String> dialog = new ChoiceDialog<>("b", choices);
                        alert.setTitle("Ventana de seleccion");          
                        alert.setContentText("Escoge una opcion.");

                        // Traditional way to get the response value.
                        Optional<String> result1 = dialog.showAndWait();
                        if (result1.isPresent() && result1.get()=="documento"){
                            System.out.println("aaaaaaaaaaaaaa");
                            
                            TextInputDialog dialogF = new TextInputDialog(buscarConexion(x1,y1).texto);
                            dialogF.setTitle("Crear documento");
                            dialogF.setContentText("Introduzca el texto:");


                            Optional<String> resultMODDO = dialogF.showAndWait();
                            if(resultMODDO.isPresent() && !resultMODDO.get().equals(" ") && !resultMODDO.get().equalsIgnoreCase(" ")){
                                if (resultMODDO.get().matches("[A-Za-z]+") && evaluarAritmetica(resultMODDO.get(),variables)){
                                    for (int i = 0; i < figuras.size(); i++) {
                                    if(buscarConexion(x1,y1)==figuras.get(i)){
                                        figuras.get(i).setEstado(false);
                                        actualizar();
                                    }
                            }
                                        double x1N = x1 - 50;
                                        double y1N = y1 - 25;
                                        double x2 = x1 + 50;
                                        double y2 = y1 - 25;
                                        double x3 = x1 + 50;
                                        double y3 = y1 + 25;
                                        double x4 = x1 - 50;
                                        double y4 = y1 + 25;

                                        /***
                                         * por si la figura a crear se crea muy arriba o muy a la izquierda 
                                         * o la combinacion de las dos anteriores
                                         */
                                        if(x1N<0){
                                            x1=50;
                                            x2=x2-x1N;
                                            x3=x3-x1N;
                                            x4=0;
                                            x1N=0;
                                        }
                                        if(y1N<0){
                                            y1=25;
                                            y3=y3-y1N;
                                            y4=y4-y1N;        
                                            y2=0;
                                            y1N=0;
                                        }

                                        Documento documento = new Documento(TipoF.DOCUMENTACION);
                                        documento.setVerticeCentro(new Vertice(x1,y1));
                                        documento.getVertices().add(new Vertice(x1N,y1N));
                                        documento.getVertices().add(new Vertice(x2,y2));
                                        documento.getVertices().add(new Vertice(x3,y3));
                                        documento.getVertices().add(new Vertice(x4,y4));
                                        documento.texto = resultMODDO.get();
                                        documento.calcularConexiones();
                                        documento.setEstado(true);
                                        figuras.add(documento);
                                        documento.dibujar(gc);

                                        actualizar();



                                        b=false;
                                        canvas.setOnMouseClicked(null);



                                      

                                }else{
                                    Alert alertMODDO = new Alert(AlertType.WARNING);
                                    alertMODDO.setTitle("Error");
                                    alertMODDO.setHeaderText("cuidado");
                                    alertMODDO.setContentText("El formato del texto ingresado es incorrecto");

                                    alertMODDO.showAndWait();      
                                }
                            }else {
                                Alert alertMODDO = new Alert(AlertType.WARNING);
                                alertMODDO.setTitle("Error");
                                alertMODDO.setHeaderText("cuidado");
                                alertMODDO.setContentText("Debes ingresar un texto");

                                alertMODDO.showAndWait();
                            }
                        }else if (result1.isPresent() && result1.get()=="proceso"){
                            TextInputDialog dialogf = new TextInputDialog(buscarConexion(x1,y1).texto);
                            dialogf.setTitle("Crear proceso");
                            dialogf.setContentText("Introduzca el texto:");


                            Optional<String> resultPRO = dialogf.showAndWait();
                            if(resultPRO.isPresent() && !resultPRO.get().equals(" ") && !resultPRO.get().equalsIgnoreCase(" ")){
                                if (resultPRO.get().matches("[A-Za-z1-9]+=.+") && evaluarAritmetica(resultPRO.get().split("=")[1],variables)){
                                    for (int i = 0; i < figuras.size(); i++) {
                                              if(buscarConexion(x1,y1)==figuras.get(i)){
                                                  figuras.get(i).setEstado(false);
                                                  actualizar();
                                              }
                                        }

                                        double x1N = x1 - 50;
                                        double y1N = y1 - 25;
                                        double x2 = x1 + 50+powerUp;
                                        double y2 = y1 - 25;
                                        double x3 = x1 + 50+powerUp;
                                        double y3 = y1 + 25;
                                        double x4 = x1 - 50;
                                        double y4 = y1 +25;

                                        if(x1N<0){
                                            x1=50;
                                            x2=x2-x1N;
                                            x3=x3-x1N;
                                            x1N=0;
                                            x4=0;
                                        }

                                        if(y1N<0){
                                            y1=25;
                                            y3=y3-y1N;
                                            y4=y4-y1N;
                                            y1N=0;
                                            y2=0;
                                        }


                                        Proceso proceso = new Proceso( TipoF.PROCESO);
                                        proceso.setVerticeCentro(new Vertice(x1,y1));
                                        proceso.getVertices().add(new Vertice(x1N, y1N));
                                        proceso.getVertices().add(new Vertice(x2, y2));
                                        proceso.getVertices().add(new Vertice(x3, y3));
                                        proceso.getVertices().add(new Vertice(x4, y4));
                                        proceso.calcularConexiones();
                                        proceso.texto = resultPRO.get();
                                        proceso.setEstado(true);
                                        figuras.add(proceso);
                                        proceso.dibujar(gc);


                                        actualizar();
      
                                }else{
                                    Alert alertE = new Alert(AlertType.WARNING);
                                    alertE.setTitle("Error");
                                    alertE.setHeaderText("cuidado");
                                    alertE.setContentText("El formato del texto ingresado es incorrecto");

                                    alert.showAndWait();
                                }    
                            } else {
                                Alert alertE = new Alert(AlertType.WARNING);
                                alertE.setTitle("Error");
                                alertE.setHeaderText("cuidado");
                                alertE.setContentText("Debes ingresar un texto");

                                alertE.showAndWait();
                            }
                        
                        }

                    
                    } else if (buscarConexion(x1,y1).tipo==TipoF.DOCUMENTACION){
                        List<String> choices = new ArrayList<>();
                        choices.add("proceso");
                        choices.add("entrada");

                        ChoiceDialog<String> dialog = new ChoiceDialog<>("proceso", choices);
                        alert.setTitle("Ventana de seleccion");           
                        alert.setContentText("Escoge una opcion.");

                        // Traditional way to get the response value.
                        Optional<String> result1 = dialog.showAndWait();
                        if (result1.isPresent() && result1.get()=="proceso"){
                            TextInputDialog dialogf = new TextInputDialog(buscarConexion(x1,y1).texto);
                            dialogf.setTitle("Crear proceso");
                            dialogf.setContentText("Introduzca el texto:");


                            Optional<String> resultPRO = dialogf.showAndWait();
                            if(resultPRO.isPresent() && !resultPRO.get().equals(" ") && !resultPRO.get().equalsIgnoreCase(" ")){
                                if (resultPRO.get().matches("[A-Za-z1-9]+=.+") && evaluarAritmetica(resultPRO.get().split("=")[1],variables)){
                                        for (int i = 0; i < figuras.size(); i++) {
                                              if(buscarConexion(x1,y1)==figuras.get(i)){
                                                  figuras.get(i).setEstado(false);
                                                  actualizar();
                                              }
                                        }

                                        double x1N = x1 - 50;
                                        double y1N = y1 - 25;
                                        double x2 = x1 + 50+powerUp;
                                        double y2 = y1 - 25;
                                        double x3 = x1 + 50+powerUp;
                                        double y3 = y1 + 25;
                                        double x4 = x1 - 50;
                                        double y4 = y1 +25;

                                        if(x1N<0){
                                            x1=50;
                                            x2=x2-x1N;
                                            x3=x3-x1N;
                                            x1N=0;
                                            x4=0;
                                        }

                                        if(y1N<0){
                                            y1=25;
                                            y3=y3-y1N;
                                            y4=y4-y1N;
                                            y1N=0;
                                            y2=0;
                                        }


                                        Proceso proceso = new Proceso( TipoF.PROCESO);
                                        proceso.setVerticeCentro(new Vertice(x1,y1));
                                        proceso.getVertices().add(new Vertice(x1N, y1N));
                                        proceso.getVertices().add(new Vertice(x2, y2));
                                        proceso.getVertices().add(new Vertice(x3, y3));
                                        proceso.getVertices().add(new Vertice(x4, y4));
                                        proceso.calcularConexiones();
                                        proceso.texto = resultPRO.get();
                                        proceso.setEstado(true);
                                        figuras.add(proceso);
                                        proceso.dibujar(gc);


                                        actualizar();
      
                                }else{
                                    Alert alertE = new Alert(AlertType.WARNING);
                                    alertE.setTitle("Error");
                                    alertE.setHeaderText("cuidado");
                                    alertE.setContentText("El formato del texto ingresado es incorrecto");

                                    alert.showAndWait();
                                }    
                            } else {
                                Alert alertE = new Alert(AlertType.WARNING);
                                alertE.setTitle("Error");
                                alertE.setHeaderText("cuidado");
                                alertE.setContentText("Debes ingresar un texto");

                                alertE.showAndWait();
                            }
                            System.out.println("Your choice: " + result1.get());
                        }else if(result1.isPresent() && result1.get()=="entrada"){
                            TextInputDialog dialogF = new TextInputDialog(buscarConexion(x1,y1).texto);
                            dialogF.setTitle("Crear Entrada");
                            dialogF.setContentText("Introduzca el texto:");


                            Optional<String> resultMOEN = dialogF.showAndWait();


                            if(resultMOEN.isPresent() && !resultMOEN.get().equals(" ") && !resultMOEN.get().equalsIgnoreCase(" ")){

                                if ((resultMOEN.get().matches("[A-Za-z]+=.+") && evaluarAritmetica(resultMOEN.get().split("=")[1],variables)) || 
                                        ((resultMOEN.get().matches("[A-Za-z]+") && evaluarAritmetica(resultMOEN.get(),variables)))){
                                        for (int i = 0; i < figuras.size(); i++) {
                                              if(buscarConexion(x1,y1)==figuras.get(i)){
                                                  figuras.get(i).setEstado(false);
                                                  actualizar();
                                              }
                                        }
               
                                        double x1N = x1 - 33;
                                        double y1N = y1 - 25;
                                        double x2 = x1 + 67;
                                        double y2 = y1 - 25;
                                        double x3 = x1 + 33;
                                        double y3 = y1 + 25;
                                        double x4 = x1 - 67;
                                        double y4 = y1 + 25;
                                        /***
                                         * por si la figura se sale por la izquierda y
                                         * por si la figura se sale por la parte superior
                                         */
                                        if(x4<0){
                                            x1=55;
                                            x1N=x1N-x4;
                                            x2=x2-x4;
                                            x3=x3-x4;
                                            x4=0;
                                        }
                                        if(y1<0){
                                            y1=25;
                                            y3=y3-y1N;
                                            y4=y3;
                                            y2=0;
                                            y1N=0;
                                        }

                                        Entrada entrada = new Entrada(TipoF.ENTRADA);
                                        entrada.setVerticeCentro(new Vertice(x1,y1));
                                        entrada.getVertices().add(new Vertice(x1N,y1N));
                                        entrada.getVertices().add(new Vertice(x2,y2));
                                        entrada.getVertices().add(new Vertice(x3,y3));
                                        entrada.getVertices().add(new Vertice(x4,y4));
                                        entrada.calcularConexiones();
                                        entrada.texto = resultMOEN.get();
                                        entrada.setEstado(true);
                                        figuras.add(entrada);
                                        entrada.dibujar(gc);

                                        actualizar();

                                        b=false;
                                        canvas.setOnMouseClicked(null);


                                    

                                    }else {
                                        Alert alertE = new Alert(AlertType.WARNING);
                                        alertE.setTitle("Error");
                                        alertE.setHeaderText("cuidado");
                                        alertE.setContentText("El texto ingresado es incorrecto debe ser del estilo 'algo' = 'algo' o solamente 'algo'");

                                        alertE.showAndWait();
                                    }
                                }else{
                                    Alert alertE = new Alert(AlertType.WARNING);
                                    alertE.setTitle("Error");
                                    alertE.setHeaderText("cuidado");
                                    alertE.setContentText("Debes ingresar un texto");

                                    alert.showAndWait();
                                }
                        }


                    }                 
                }
                canvas.setOnMouseClicked(null);
                
            }else if(buscarConexion(x1,y1)!=null && buscarConexion(x1,y1).tipo==TipoF.FIN || buscarConexion(x1,y1).tipo==TipoF.INICIO){
                if(buscarConexion(x1,y1).tipo==TipoF.FIN){
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Ventana de seleccion");
                    alert.setContentText("Escoge una opcion.");

                    ButtonType buttonTypeOne = new ButtonType("Color");

                    ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

                    alert.getButtonTypes().setAll(buttonTypeOne,buttonTypeCancel);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == buttonTypeOne){
                        // ... user chose "One"
                    } 
                }else if (buscarConexion(x1,y1).tipo==TipoF.INICIO){
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Ventana de seleccion");           
                    alert.setContentText("Escoge una opcion.");

                    ButtonType buttonTypeOne = new ButtonType("Color");
                    ButtonType buttonTypeTwo = new ButtonType("Modificar contenido");
                    ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

                    alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == buttonTypeOne){
                        // ... user chose "One"
                    } else if (result.get() == buttonTypeTwo) {
                        TextInputDialog dialog = new TextInputDialog(buscarConexion(x1,y1).texto);
                        dialog.setTitle("Modificar texto");
                        dialog.setContentText("Introduzca el texto:");
                        Optional<String> resultmodtext = dialog.showAndWait();
                        if(resultmodtext.isPresent() && !resultmodtext.get().equals(" ") && !resultmodtext.get().equalsIgnoreCase(" ")){
                            if (resultmodtext.get().matches("[A-Za-z]+") && evaluarAritmetica(resultmodtext.get(),variables)){
                                for (int i = 0; i < figuras.size(); i++) {
                                    if(buscarConexion(x1,y1)==figuras.get(i)){
                                        figuras.get(i).setTexto(resultmodtext.get());
                                        actualizar();
                                    }
                                }
                            }
                        }else{
                            Alert alert1 = new Alert(AlertType.WARNING);
                            alert1.setTitle("Error");
                            alert1.setHeaderText("cuidado");
                            alert1.setContentText("El formato del texto ingresado es incorrecto");

                            alert.showAndWait();
                        }
                    } 
                }
                
                canvas.setOnMouseClicked(null);
                
                
                

            }else if(buscarConexion(x1,y1)!=null && buscarConexion(x1,y1).tipo==TipoF.ITERACION || buscarConexion(x1,y1).tipo==TipoF.DESICION){
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Ventana de seleccion");           
                alert.setContentText("Escoge una opcion.");

                ButtonType buttonTypeOne = new ButtonType("Color");
                ButtonType buttonTypeTwo = new ButtonType("Modificar contenido");

                ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo,buttonTypeCancel);

                Optional<ButtonType> result = alert.showAndWait();
                
                if (result.get() == buttonTypeOne){
                    
                } else if (result.get() == buttonTypeTwo) {
                    if(buscarConexion(x1,y1).tipo==TipoF.DESICION){
                        TextInputDialog dialog = new TextInputDialog(buscarConexion(x1,y1).texto);
                        dialog.setTitle("Modificar texto");
                        dialog.setContentText("Introduzca el texto:");
                        Optional<String> resultmodtext = dialog.showAndWait();
                        if(resultmodtext.isPresent() && !result.get().equals(" ") && !resultmodtext.get().equalsIgnoreCase(" ")){
                            if (resultmodtext.get().matches("[A-Za-z1-9]+=.+") && evaluarAritmetica(resultmodtext.get().split("=")[1],variables)){
                                for (int i = 0; i < figuras.size(); i++) {
                                    if(buscarConexion(x1,y1)==figuras.get(i)){
                                        figuras.get(i).setTexto(resultmodtext.get());
                                        actualizar();
                                    }
                                }
                            }
                        }else{
                            Alert alert1 = new Alert(AlertType.WARNING);
                            alert1.setTitle("Error");
                            alert1.setHeaderText("cuidado");
                            alert1.setContentText("El formato del texto ingresado es incorrecto");

                            alert.showAndWait();
                        }
                    }else if(buscarConexion(x1,y1).tipo==TipoF.ITERACION){
                        TextInputDialog dialog = new TextInputDialog(buscarConexion(x1,y1).texto);
                        dialog.setTitle("Modificar texto");
                        dialog.setContentText("Introduzca el texto:");
                        Optional<String> resultmodtext = dialog.showAndWait();
                        if(resultmodtext.isPresent() && !result.get().equals(" ") && !resultmodtext.get().equalsIgnoreCase(" ")){
                            if (resultmodtext.get().matches("[A-Za-z1-9]+=.+") && evaluarAritmetica(resultmodtext.get().split("=")[1],variables)){
                                for (int i = 0; i < figuras.size(); i++) {
                                    if(buscarConexion(x1,y1)==figuras.get(i)){
                                        figuras.get(i).setTexto(resultmodtext.get());
                                        actualizar();
                                    }
                                }
                            }
                        }else{
                            Alert alert1 = new Alert(AlertType.WARNING);
                            alert1.setTitle("Error");
                            alert1.setHeaderText("cuidado");
                            alert1.setContentText("El formato del texto ingresado es incorrecto");

                            alert.showAndWait();
                        }
                    }
                } 
                
                canvas.setOnMouseClicked(null);

            }else{
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("cuidado");
                alert.setContentText("El formato del texto ingresado es incorrecto");
            }
            
            canvas.setOnMouseClicked(null);
        });
           
               
        
    }
    
    @FXML
    private void dibujarProceso(ActionEvent event) throws IOException {
        
        if(run==false && corriendo==false){
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Crear proceso");
            dialog.setContentText("Introduzca el texto:");


            Optional<String> result = dialog.showAndWait();
            if(result.isPresent() && !result.get().equals(" ") && !result.get().equalsIgnoreCase(" ")){
                if (result.get().matches("[A-Za-z1-9]+=.+") && evaluarAritmetica(result.get().split("=")[1],variables)){


                    if(contar(result.get())>12){
                        powerUp = 80;
                        boolean a = true;
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
                        double x2 = x + 50+powerUp;
                        double y2 = y - 25;
                        double x3 = x + 50+powerUp;
                        double y3 = y + 25;
                        double x4 = x - 50;
                        double y4 = y +25;

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


                        Proceso proceso = new Proceso( TipoF.PROCESO);
                        proceso.setVerticeCentro(new Vertice(x,y));
                        proceso.getVertices().add(new Vertice(x1, y1));
                        proceso.getVertices().add(new Vertice(x2, y2));
                        proceso.getVertices().add(new Vertice(x3, y3));
                        proceso.getVertices().add(new Vertice(x4, y4));
                        proceso.calcularConexiones();
                        proceso.texto = result.get();
                        proceso.setEstado(true);
                        figuras.add(proceso);
                        proceso.dibujar(gc);


                        actualizar();
                        b=false;

                        powerUp=0;
                        canvas.setOnMouseClicked(null);
                    }  
                });
                }else{
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setHeaderText("cuidado");
                    alert.setContentText("El formato del texto ingresado es incorrecto");

                    alert.showAndWait();
                }    
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("cuidado");
                alert.setContentText("Debes ingresar un texto");

                alert.showAndWait();
            }
        }
        
        

            
    }
    
    public boolean comprobarPadres(Flujo flujo){
        for (Figura figura : figuras) {
            if(figura instanceof Flujo){
                if(flujo.padre.getVerticeCentro().distancia(((Flujo) figura).padre.getVerticeCentro())==0
                        || flujo.hijo.getVerticeCentro().distancia(((Flujo) figura).hijo.getVerticeCentro())==0){
                    return false;
                }
            }
            
        }
        return true;
    }
    
    
    private boolean buscarInicio(){ 
        for (Figura figura : figuras) {           
            if(figura.getTipo().equals(TipoF.INICIO)){
                return true;
            }
        }   
        return false;
    }
    
    private boolean buscarFin(){ 
        for (Figura figura : figuras) {           
            if(figura.getTipo().equals(TipoF.FIN)){
                return true;
            }
        }    
        return false;
    }
        
    
    @FXML
    private void dibujarFinDecision(ActionEvent event){
        gc.setStroke(Color.BLACK);
  
        b=true;
        canvas.setOnMouseClicked((MouseEvent event1) -> {
            double x1 = event1.getX();
            double y1 = event1.getY();
            Vertice vertice1;
            
            if(buscarConexion(x1,y1)!=null){
                vertice1=buscarConexion(x1,y1).getVerticeCentro();
                canvas.setOnMouseClicked(null);
                canvas.setOnMouseClicked((MouseEvent event2) -> {
                    if(b && run==false && corriendo==false){
                        double puntox1=vertice1.getX();
                        double puntoy1=vertice1.getY();
                        double x2 = event2.getX();
                        double y2 = event2.getY();
                        Vertice vertice2;
                      
                        if(buscarConexion(x2,y2)!=null){
                            vertice2 = buscarConexion(x2,y2).getVerticeCentro();
                            double puntox2 = vertice2.getX();
                            double puntoy2 = vertice2.getY();
                            
                            FinDecision fin = new FinDecision(TipoF.FINDESICION);
                            fin.getVertices().add(new Vertice(puntox1,puntoy1));
                            fin.getVertices().add(new Vertice(puntox2,puntoy2));
                            fin.setEstado(true);
                            fin.setVerticeCentro(new Vertice((puntox1+puntox2)/2,(puntoy1+puntoy2)/2));
                            

                            figuras.add(fin);

                            agregarFigurasParaDFin(fin);
                            
                         
                            //if(flujoFinValido(fin)){
                                
                                figuras.add(fin);
                                fin.dibujar(gc);
                            //}
                            
                            actualizar();
                            b=false;
                            canvas.setOnMouseClicked(null);
                        }else{
                            
                            
                        }    
                    }
                });
            }
        });    
    }
    
    
    
    @FXML
    private void correr(ActionEvent event) throws InterruptedException {
        texto="";
        Flujo flujo = null;
        Figura fig = null;
        ArrayList<Figura> corredors = new ArrayList<>(); 
        b=true;
        if(b && run==false && corriendo==false){
            if (!figuras.isEmpty() ){
                if(buscarInicio() && buscarFin()){
                    
                        //buscar inicio
                        for (Figura figura : figuras) {
                            if(figura.getTipo() == TipoF.INICIO){
                                fig = figura;
                            }
                        }
                        // buscarFlujo que tenga el inicio
                        for (Figura figura : figuras) {
                            if(figura instanceof Flujo){
                                if(((Flujo) figura).padre.equals(fig)){
                                    flujo = (Flujo) figura;
                                }
                            }
                        }
                        if(!flujo.equals(null)){
                            System.out.println(fig.texto);
                            corredors.add(fig);


                            //actualizar();
                            boolean terminar = true;
                            while(terminar && fig != null && fig.getTipo() != TipoF.FIN ){
                                fig = figuras.get(flujo.indexHijo);
                                /*
                                for (Figura figura : figuras) {
                                    if(figura instanceof Flujo){
                                        if(((Flujo) figura).padre.equals(fig)){
                                            flujo = (Flujo) figura;
                                        }
                                    }
                                }*/
                                for (int i = 0; i < figuras.size(); i++) {
                                    if (figuras.get(i) instanceof Flujo) {
                                        if (((Flujo)figuras.get(i)).padre.equals(fig)) {
                                            flujo = (Flujo)figuras.get(i);
                                            break;
                                        }
                                        if (i>=figuras.size()-1) {
                                        terminar = false;
                                        }
                                    }
                                }
                                if(fig.tipo != TipoF.FIN){
                                   System.out.println(fig.texto);
                                   corredors.add(fig);
                                }
                            }
                            if(fig == null){
                                System.out.println("Mala construccion");
                            }else if(fig.getTipo() == TipoF.FIN){
                                System.out.println(fig.texto);
                                corredors.add(fig);
                            }
                            for(Figura corredor:corredors){
                                int condicion=0;
                                int ciclo=0;
                                if (corredor.tipo!=TipoF.FIN && corredor.tipo!=TipoF.INICIO){
                                    agregarVariable(corredor);
                                                
                                } 
                                if(corredor.tipo==TipoF.INICIO){
                                    texto=("Inicio codigo\n");
                                }else if(corredor.tipo==TipoF.FIN){
                                    texto=texto+("Fin codigo");                                  
                                }else if(corredor.tipo==TipoF.DOCUMENTACION){
                                    texto=texto+" print"+corredor.texto+(";\n");
                                }else if(corredor.tipo==TipoF.ENTRADA){
                                    texto=texto+" "+corredor.texto+(";\n");
                                }else if(corredor.tipo==TipoF.PROCESO){
                                    texto=texto+" "+corredor.texto+(";\n");
                                }else if(corredor.tipo==TipoF.SALIDA){
                                    texto=texto+" "+corredor.texto+(";\n");
                                }else if(corredor.tipo==TipoF.CONDICION){
                                    texto=texto+" if "+corredor.texto+(";\n");
                                }else if(corredor.tipo==TipoF.SALIDA){
                                    texto=texto+" while "+corredor.texto+(";\n");
                                }
                            }
                            gc.setFill(Color.RED);
                            Thread hilo = new Thread(new Runnable() {
                                @Override
                                
                                public void run() {


                                        for (Figura corredor : corredors) {
                                            
                                            run=true;
                                            if (corredor.tipo!=TipoF.FIN && corredor.tipo!=TipoF.INICIO){
                                                agregarVariable(corredor);
                                                
                                            } 
                                            gc.fillOval(corredor.verticeCentro.getX()-80, corredor.verticeCentro.getY(), 20, 20);
                                            
                                            try {
                                                Thread.sleep(3000);
                                                actualizar();
                                            } catch (InterruptedException ex) {
                                                Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                        b=false;
                                        corredors.clear();
                                        variables.clear();
                                        area.clear();
                                        run=false;
                                    }
                            });
                            hilo.start();
                            run=false;
                            
                            //hilo.stop();

                        }

                }else{
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setHeaderText("Cuidado");
                    alert.setContentText("No se ha encontrado el inicio o el fin");

                    alert.showAndWait();
                    b=false;
                }
            }else{
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("Cuidado");
                alert.setContentText("No se ha encontrado ninguna figuras");

                alert.showAndWait();
                b=false;
            }
        }
        b=false;
        run=false;
    }
    
    private boolean existeVariable(String nombre){
        for (Variable variable : variables) {
            if(variable.nombre.equals(nombre)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Arreglar manera de actualizacion de consola
     * 
     * @param figura 
     */
    private void agregarVariable(Figura figura){
        
        
        
        if(figura.tipo == TipoF.ENTRADA){
            if(figura.texto.matches("[A-Za-z0-9]+=.+")){
                if(!existeVariable(figura.texto.split("=")[0])){
                    area.appendText(figura.texto.split("=")[0]+ "⟵  "+ String.valueOf(operarExpresion(figura.texto.split("=")[1], variables))+  "\n");
                    variables.add(new Variable(figura.texto.split("=")[0] , String.valueOf(operarExpresion(figura.texto.split("=")[1], variables))));
                }else{
                    for (int i = 0; i < variables.size(); i++) {
                        if(variables.get(i).nombre.equals(figura.texto.split("=")[0])){
                            variables.get(i).setValor(String.valueOf(operarExpresion(figura.texto.split("=")[1], variables)));
                        }
                    }

                }
            }else if(figura.texto.matches("[A-Za-z0-9]+")){
                //validar en el caso de que la variable no exista
                area.appendText("\"el valor de la variable "+figura.texto+" es: "+ String.valueOf(operarExpresion(figura.texto, variables)) +"\" \n");
            }else{
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("Cuidado");
                alert.setContentText("Esta variable no existe por tanto no tiene un valor almacenado");

                alert.showAndWait();
            }
        }else if(figura.tipo == TipoF.PROCESO){
            for (int i = 0; i < variables.size(); i++) {
                if(variables.get(i).nombre.equals(figura.texto.split("=")[0])){
                    area.appendText(figura.texto.split("=")[0]+"⟵"+ figura.texto.split("=")[1] + "\n");
                    variables.get(i).setValor(String.valueOf(operarExpresion(figura.texto.split("=")[1], variables)));
                }
            }
        }
    }
    
    @FXML
    private void dibujarFlujo(ActionEvent event) {
        
        gc.setStroke(Color.BLACK);
  
        b=true;
        canvas.setOnMouseClicked((MouseEvent event1) -> {
            double x1 = event1.getX();
            double y1 = event1.getY();
            Vertice vertice1;
            
            if(buscarConexion(x1,y1)!=null){
                vertice1=buscarConexion(x1,y1).getVerticeCentro();
                canvas.setOnMouseClicked(null);
                canvas.setOnMouseClicked((MouseEvent event2) -> {
                    if(b && run==false && corriendo==false){
                        double puntox1=vertice1.getX();
                        double puntoy1=vertice1.getY();
                        double x2 = event2.getX();
                        double y2 = event2.getY();
                        Vertice vertice2;
                      
                        if(buscarConexion(x2,y2)!=null){
                            vertice2 = buscarConexion(x2,y2).getVerticeCentro();
                            double puntox2 = vertice2.getX();
                            double puntoy2 = vertice2.getY();
                            Flujo flujo = new Flujo(TipoF.FLUJO);
                            flujo.getVertices().add(new Vertice(puntox1,puntoy1));
                            flujo.getVertices().add(new Vertice(puntox2,puntoy2));
                            flujo.setEstado(true);
                            
                            if(buscarConexion(x1,y1) instanceof Desicion){
                                flujo.desicionPadre = (Desicion) buscarConexion(x1,y1);
                                if (existeTrue(flujo.desicionPadre) == false) {
                                    flujo.esVerdadero = true;
                                    flujo.texto = "true";
                                }else if(existeFalse(flujo.desicionPadre) == false){
                                    flujo.esVerdadero = false;
                                    flujo.texto = "false";
                                }
                                
                            }
                                
                            
                            
                            
                            agregarFiguras(flujo);
                            try {
                                if (flujo.padre instanceof Desicion) {
                                    flujo.hijo.desicionPadre = flujo.desicionPadre;
                                }else if (!(flujo.padre instanceof Desicion)) {
                                    if (flujo.padre.desicionPadre!=null) {
                                        flujo.desicionPadre = flujo.padre.desicionPadre;
                                        flujo.hijo.desicionPadre = flujo.desicionPadre;
                                    }
                            }
                                
                            } catch (NullPointerException e) {
                                System.out.println(e.getMessage());
                            }
                            
                                
                            try {
                                flujo.calcularVertices(figuras);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                            

                            if(flujoValido(flujo)){
                                
                                figuras.add(flujo);
                                flujo.dibujar(gc);
                            }
                            
                            actualizar();
                            b=false;
                            canvas.setOnMouseClicked(null);
                        }else{
                            
                            
                        }
                        
                    }


                });
            
            }
            
        });
           
    }
    
    public boolean existeTrue(Desicion desicion){
        for (Figura figura : figuras) {
            if (figura instanceof Flujo) {
                if (desicion.equals(((Flujo) figura).desicionPadre) && ((Flujo) figura).esVerdadero && ((Flujo) figura).padre instanceof Desicion) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean existeFalse(Desicion desicion){
        for (Figura figura : figuras) {
            if (figura instanceof Flujo) {
                if (desicion.equals(((Flujo) figura).desicionPadre) && !((Flujo) figura).esVerdadero && ((Flujo) figura).padre instanceof Desicion) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void agregarFiguras(Flujo flujo){
        
        for (int i = 0; i < figuras.size(); i++) {
            if(!(figuras.get(i) instanceof Flujo)){
                if(flujo.getVertices().get(0).distancia(figuras.get(i).getVerticeCentro())==0){
                    flujo.padre = figuras.get(i);
                    flujo.indexPadre = i;
                    break;
                }
            }
        }
        for (int i = 0; i < figuras.size(); i++) {
            if(!(figuras.get(i) instanceof Flujo)){
                if(flujo.getVertices().get(1).distancia(figuras.get(i).getVerticeCentro())==0){
                    flujo.hijo = figuras.get(i);
                    flujo.indexHijo = i;
                    break;
                }
            }
        }
   
    }
    
    public void agregarFigurasParaDFin(FinDecision flujo){
        
        for (int i = 0; i < figuras.size(); i++) {
            
            if(flujo.getVertices().get(0).distancia(figuras.get(i).getVerticeCentro())==0){
                flujo.padre = figuras.get(i);
                flujo.indexPadre = i;
                break;
            }
            
        }
        for (int i = 0; i < figuras.size(); i++) {
            
            if(flujo.getVertices().get(1).distancia(figuras.get(i).getVerticeCentro())==0){
                flujo.hijo = figuras.get(i);
                flujo.indexHijo = i;
                break;
            }
            
        }
    }
    
    public boolean estaConectado(double x, double y){
        if(!figuras.isEmpty()) {
            for (Figura figura : figuras) {
                if(!(figura instanceof Flujo)){
                    if(x<=figura.getVertices().get(2).getX()){
                        if(x>=figura.getVertices().get(0).getX()){
                           if(y<=figura.getVertices().get(2).getY()){
                                if(y>=figura.getVertices().get(0).getY()){
                                    return true;
                                }
                            } 
                        }
                    }
                }
            }
        }
        
        return false;
    }
    
    
    public Figura buscarConexion(double x, double y){
        if(!figuras.isEmpty()) {
            for (Figura figura : figuras) {
                if(!(figura instanceof Flujo) && !(figura instanceof FinDecision) && !(figura instanceof Desicion) && !(figura instanceof Ciclo)){
                    if(x<=figura.getVertices().get(2).getX()){
                        if(x>=figura.getVertices().get(0).getX()){
                           if(y<=figura.getVertices().get(2).getY()){
                                if(y>=figura.getVertices().get(0).getY()){
                                    return figura;
                                }
                            } 
                        }
                    }
                }else if(figura instanceof Desicion){
                    if(x<=figura.getVertices().get(1).getX()){
                        if(x>=figura.getVertices().get(3).getX()){
                           if(y<=figura.getVertices().get(2).getY()){
                                if(y>=figura.getVertices().get(0).getY()){
                                    return figura;
                                }
                            } 
                        }
                    }
                }else if(figura instanceof Ciclo){
                    if(x<=figura.getVertices().get(1).getX()){
                        if(x>=figura.getVertices().get(3).getX()){
                            if(y<=figura.getVertices().get(2).getY()){
                                if(y>=figura.getVertices().get(0).getY()){
                                    return figura;
                                }
                            }
                        }
                    }
                }else if(figura instanceof Flujo){
                    if(x<=figura.getVertices().get(3).getX()){
                        if(x>=figura.getVertices().get(2).getX()){
                           if(y<=figura.getVertices().get(3).getY()){
                                if(y>=figura.getVertices().get(2).getY()){
                                    return figura;
                                }
                            } 
                        }
                    }
                }else if (figura instanceof FinDecision){
                    if(x<=figura.getVertices().get(3).getX()){
                        if(x>=figura.getVertices().get(2).getX()){
                           if(y<=figura.getVertices().get(3).getY()){
                                if(y>=figura.getVertices().get(2).getY()){
                                    return figura;
                                }
                            } 
                        }
                    }
                    
                }
            }
        }
        
        return null;
    }
    public boolean comprobarPosicion(double x1,double y1,double x2,double y2,double x3,double y3,double x4,double y4 ){
        if (!figuras.isEmpty()){
            for (int i = 0; i < figuras.size(); i++) {
                if(figuras.get(i).tipo!= TipoF.FLUJO && figuras.get(i).tipo==TipoF.PROCESO){
                    if(figuras.get(i).getVerticeCentro().getX()-50<=x1 && figuras.get(i).getVerticeCentro().getX()+50>=x1 && figuras.get(i).getVerticeCentro().getY()-25<=y1 && figuras.get(i).getVerticeCentro().getY()+25>=y1){
                        return false;
                    }else{
                        if(figuras.get(i).getVerticeCentro().getX()-50<=x2 && figuras.get(i).getVerticeCentro().getX()+50>=x2 && figuras.get(i).getVerticeCentro().getY()-25<=y2 && figuras.get(i).getVerticeCentro().getY()+25>=y2){
                            return false;
                        }else{              
                            if(figuras.get(i).getVerticeCentro().getX()-50<=x3 && figuras.get(i).getVerticeCentro().getX()+50>=x3 && figuras.get(i).getVerticeCentro().getY()-25<=y3 && figuras.get(i).getVerticeCentro().getY()+25>=y3){
                                return false;
                            }else{
                                if(figuras.get(i).getVerticeCentro().getX()-50<=x4 && figuras.get(i).getVerticeCentro().getX()+50>=x4 && figuras.get(i).getVerticeCentro().getY()-25<=y4 && figuras.get(i).getVerticeCentro().getY()+25>=y4){
                                    return false;
                                }
                            }
                        }
                    }
                
                }
                if(figuras.get(i).tipo!= TipoF.FLUJO && figuras.get(i).tipo==TipoF.INICIO ||figuras.get(i).tipo!= TipoF.FLUJO && figuras.get(i).tipo==TipoF.FIN ){
                    if(figuras.get(i).getVerticeCentro().getX()-90<=x1 && figuras.get(i).getVerticeCentro().getX()+90>=x1 && figuras.get(i).getVerticeCentro().getY()-25<=y1 && figuras.get(i).getVerticeCentro().getY()+25>=y1){
                        return false;
                    }else{
                        if(figuras.get(i).getVerticeCentro().getX()-90<=x2 && figuras.get(i).getVerticeCentro().getX()+90>=x2 && figuras.get(i).getVerticeCentro().getY()-25<=y2 && figuras.get(i).getVerticeCentro().getY()+25>=y2){
                            return false;
                        }else{              
                            if(figuras.get(i).getVerticeCentro().getX()-90<=x3 && figuras.get(i).getVerticeCentro().getX()+90>=x3 && figuras.get(i).getVerticeCentro().getY()-25<=y3 && figuras.get(i).getVerticeCentro().getY()+25>=y3){
                                return false;
                            }else{
                                if(figuras.get(i).getVerticeCentro().getX()-90<=x4 && figuras.get(i).getVerticeCentro().getX()+90>=x4 && figuras.get(i).getVerticeCentro().getY()-25<=y4 && figuras.get(i).getVerticeCentro().getY()+25>=y4){
                                    return false;
                                }
                            }
                        }
                    }
                }
                if(figuras.get(i).tipo!=TipoF.FLUJO && figuras.get(i).tipo==TipoF.ENTRADA){
                    if(figuras.get(i).getVerticeCentro().getX()-67<=x1 && figuras.get(i).getVerticeCentro().getX()+67>=x1 && figuras.get(i).getVerticeCentro().getY()-25<=y1 && figuras.get(i).getVerticeCentro().getY()+25>=y1){
                        return false;
                    }else{
                        if(figuras.get(i).getVerticeCentro().getX()-67<=x2 && figuras.get(i).getVerticeCentro().getX()+67>=x2 && figuras.get(i).getVerticeCentro().getY()-25<=y2 && figuras.get(i).getVerticeCentro().getY()+25>=y2){
                            return false;
                        }else{              
                            if(figuras.get(i).getVerticeCentro().getX()-67<=x3 && figuras.get(i).getVerticeCentro().getX()+67>=x3 && figuras.get(i).getVerticeCentro().getY()-25<=y3 && figuras.get(i).getVerticeCentro().getY()+25>=y3){
                                return false;
                            }else{
                                if(figuras.get(i).getVerticeCentro().getX()-67<=x4 && figuras.get(i).getVerticeCentro().getX()+67>=x4 && figuras.get(i).getVerticeCentro().getY()-25<=y4 && figuras.get(i).getVerticeCentro().getY()+25>=y4){
                                    return false;
                                }
                            }
                        }
                    }
                }
                if(figuras.get(i).tipo!=TipoF.FLUJO && figuras.get(i).tipo==TipoF.DOCUMENTACION){
                    if(figuras.get(i).getVerticeCentro().getX()-50<=x1 && figuras.get(i).getVerticeCentro().getX()+50>=x1 && figuras.get(i).getVerticeCentro().getY()-25<=y1 && figuras.get(i).getVerticeCentro().getY()+75>=y1){
                        return false;
                    }else{
                        if(figuras.get(i).getVerticeCentro().getX()-50<=x2 && figuras.get(i).getVerticeCentro().getX()+50>=x2 && figuras.get(i).getVerticeCentro().getY()-25<=y2 && figuras.get(i).getVerticeCentro().getY()+75>=y2){
                            return false;
                        }else{              
                            if(figuras.get(i).getVerticeCentro().getX()-50<=x3 && figuras.get(i).getVerticeCentro().getX()+50>=x3 && figuras.get(i).getVerticeCentro().getY()-25<=y3 && figuras.get(i).getVerticeCentro().getY()+75>=y3){
                                return false;
                            }else{
                                if(figuras.get(i).getVerticeCentro().getX()-50<=x4 && figuras.get(i).getVerticeCentro().getX()+50>=x4 && figuras.get(i).getVerticeCentro().getY()-25<=y4 && figuras.get(i).getVerticeCentro().getY()+75>=y4){
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }
        return true;
    }
    
        @FXML
    private void dibujarDesicion(ActionEvent event) throws IOException {
        if(run==false && corriendo==false){
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Crear proceso");
            dialog.setContentText("Introduzca el texto:");


            Optional<String> result = dialog.showAndWait();
            if (result.isPresent() && !result.get().equals(" ") && !result.get().equalsIgnoreCase(" ")){
               if(result.get().matches("[A-Za-z0-9]+[\\>|\\<|\\>=|\\<=|\\==|\\!=]{1}[A-Za-z0-9]+")){

                if(contar(result.get())>12){
                    powerUp = 80;
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
                    double x1 = x ;
                    double y1 = y - 50;
                    double x2 = x+50;
                    double y2 = y ;
                    double x3 = x ;
                    double y3 = y+50;
                    double x4 = x - 50;
                    double y4 = y;


                    Desicion desicion = new Desicion(TipoF.DESICION);
                    desicion.setVerticeCentro(new Vertice(x,y));
                    desicion.getVertices().add(new Vertice(x1, y1));
                    desicion.getVertices().add(new Vertice(x2, y2));
                    desicion.getVertices().add(new Vertice(x3, y3));
                    desicion.getVertices().add(new Vertice(x4, y4));
                    desicion.calcularConexiones();
                    desicion.texto = result.get();
                    figuras.add(desicion);
                    desicion.estado=true;
                    desicion.dibujar(gc);
                    actualizar();
                    b=false;
                    powerUp=0;
                    canvas.setOnMouseClicked(null);
                }  
            });
               }else{
                   Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setHeaderText("cuidado");
                    alert.setContentText("El texto ingresado es incorrecto, intente otra vez");

                    alert.showAndWait();

               }
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("cuidado");
                alert.setContentText("Debes ingresar un texto");

                alert.showAndWait();
            }
        }
    }
    
    
    @FXML
    private void dibujarEntrada(ActionEvent event) throws IOException {
        
        if(run==false && corriendo==false){
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Crear Entrada");
            dialog.setContentText("Introduzca el texto:");


            Optional<String> result = dialog.showAndWait();


            if(result.isPresent() && !result.get().equals(" ") && !result.get().equalsIgnoreCase(" ")){

                if ((result.get().matches("[A-Za-z]+=.+") && evaluarAritmetica(result.get().split("=")[1],variables)) || ((result.get().matches("[A-Za-z]+") && evaluarAritmetica(result.get(),variables)))){
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

                            Entrada entrada = new Entrada(TipoF.ENTRADA);
                            entrada.setVerticeCentro(new Vertice(x,y));
                            entrada.getVertices().add(new Vertice(x1,y1));
                            entrada.getVertices().add(new Vertice(x2,y2));
                            entrada.getVertices().add(new Vertice(x3,y3));
                            entrada.getVertices().add(new Vertice(x4,y4));
                            entrada.calcularConexiones();
                            entrada.texto = result.get();
                            entrada.setEstado(true);
                            figuras.add(entrada);
                            entrada.dibujar(gc);

                            actualizar();

                            b=false;
                            canvas.setOnMouseClicked(null);

                        }       
                    });

                }else {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setHeaderText("cuidado");
                    alert.setContentText("El texto ingresado es incorrecto debe ser del estilo 'algo' = 'algo' o solamente 'algo'");

                    alert.showAndWait();
                }
            }else{
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("cuidado");
                alert.setContentText("Debes ingresar un texto");

                alert.showAndWait();
            }

        }     
    }
    private ArrayList<Figura> auxi = new ArrayList<>();
    
    @FXML
    private void limpiar(ActionEvent event) {
        if(  run==false && corriendo==false){
        b=true;
        canvas.setOnMouseClicked((MouseEvent event2)->{
            double px=event2.getX();
            double py=event2.getY();
            
            if(!figuras.isEmpty()){
                if(b){
                    for (int i = 0; i < figuras.size(); i++) {
                        if(figuras.get(i).getVertices().get(0).getX()<= px &&
                            figuras.get(i).getVertices().get(1).getX()>=px &&
                            figuras.get(i).getVertices().get(0).getY()<= py &&
                            figuras.get(i).getVertices().get(2).getY()>=py &&
                            figuras.get(i).getTipo()==TipoF.PROCESO ){
                            figuras.get(i).setEstado(false);
                        }else{
                            if(figuras.get(i).getVertices().get(0).getX()-20<= px &&
                            figuras.get(i).getVertices().get(1).getX()+20>=px &&
                            figuras.get(i).getVertices().get(0).getY()<= py &&
                            figuras.get(i).getVertices().get(2).getY()>=py &&
                            figuras.get(i).getTipo()==TipoF.INICIO ){
                               figuras.get(i).setEstado(false);
                            }else if(figuras.get(i).getVertices().get(0).getX()-20<= px &&
                            figuras.get(i).getVertices().get(1).getX()+20>=px &&
                            figuras.get(i).getVertices().get(0).getY()<= py &&
                            figuras.get(i).getVertices().get(2).getY()>=py &&
                            figuras.get(i).getTipo()==TipoF.FIN ){
                                figuras.get(i).setEstado(false);
                                
                            }else{
                                
                                if(figuras.get(i).getVertices().get(0).getX()<= px &&
                                figuras.get(i).getVertices().get(1).getX()>=px &&
                                figuras.get(i).getVertices().get(0).getY()<= py &&
                                figuras.get(i).getVertices().get(2).getY()+20>=py &&
                                figuras.get(i).getTipo()==TipoF.ENTRADA){
                                    figuras.get(i).setEstado(false);
                                }else{
                                    if(figuras.get(i).getVertices().get(0).getX()<= px &&
                                    figuras.get(i).getVertices().get(1).getX()>=px &&
                                    figuras.get(i).getVertices().get(0).getY()<= py &&
                                    figuras.get(i).getVertices().get(2).getY()>=py &&
                                    figuras.get(i).getTipo()==TipoF.DOCUMENTACION){
                                        figuras.get(i).setEstado(false);
                                    }else{
                                    
                                    
                                        if(figuras.get(i) instanceof Flujo && 
                                        figuras.get(i).getVerticeCentro().getX()-5 <= px &&
                                        figuras.get(i).getVerticeCentro().getX()+5 >= px &&
                                        figuras.get(i).getVerticeCentro().getY()-5 <= py &&
                                        figuras.get(i).getVerticeCentro().getY()+5 >= py &&
                                        figuras.get(i).getTipo()==TipoF.FLUJO){
                                            figuras.get(i).setEstado(false);
                                            
                                        }else{
                                            if(figuras.get(i).getVertices().get(3).getX()<= px &&
                                            figuras.get(i).getVertices().get(1).getX()>=px &&
                                            figuras.get(i).getVertices().get(0).getY()<= py &&
                                            figuras.get(i).getVertices().get(2).getY()>=py &&
                                            figuras.get(i).getTipo()==TipoF.DESICION ){
                                                figuras.get(i).setEstado(false);
                                            }else{
                                                if(figuras.get(i).getVerticeCentro().getX()-5 <= px &&
                                               figuras.get(i).getVerticeCentro().getX()+5 >= px &&
                                               figuras.get(i).getVerticeCentro().getY()-5 <= py &&
                                               figuras.get(i).getVerticeCentro().getY()+5 >= py &&
                                               figuras.get(i).getTipo()==TipoF.FINDESICION ){
                                                       figuras.get(i).setEstado(false);
                                               }else if(figuras.get(i).getVerticeCentro().getX()-5<= px &&
                                                       figuras.get(i).getVerticeCentro().getX()+5>= px &&
                                                       figuras.get(i).getVerticeCentro().getY()-5<=py &&
                                                       figuras.get(i).getVerticeCentro().getY()+5>=py &&
                                                       figuras.get(i).getTipo()==TipoF.CICLO){
                                                       figuras.get(i).setEstado(false);
                                               }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    for (Figura figura : figuras) {
                        if(figura instanceof Flujo && figura.getEstado()!=false){
                            ((Flujo)figura).calcularVertices(figuras);
                        }
                    }
                    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    
                    actualizar();
                    
                    canvas.setOnMouseClicked(null);
                }
                b=false;
            }
            else{
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("cuidado");
                alert.setContentText("No has dibujo una figura, intenta crear una antes");

                alert.showAndWait();
            }
        
            
        
        
          
            });
        }
    }
    private void actualizar(){
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        System.out.println("Actualizando");

        
        for (Figura figura : figuras) {
            //if(figura instanceof Flujo && figura.getEstado()!=false){
            if(figura instanceof Flujo && figura.getEstado()!=false){
                
                ((Flujo) figura).calcularVertices(figuras);
                figura.dibujar(gc);
            }
            else if(figura instanceof FinDecision && figura.getEstado()!=false){
                ((FinDecision) figura).calcularVertices(figuras);
                figura.dibujar(gc);
            }
            
        }
        for (Figura figura : figuras) {
            if(!(figura instanceof Flujo) && !(figura instanceof FinDecision) && figura.getEstado()!=false){
            //if(!(figura instanceof Flujo )&& figura.getEstado()!=false){
                
                figura.dibujar(gc);
            }
        }
        
        for (int i = 0; i < figuras.size(); i++) {
            if(figuras.get(i).getEstado()!=false){
                auxi.add(figuras.get(i));
            }
        }

        figuras.clear();
        for (int i = 0; i < auxi.size(); i++) {
            figuras.add(auxi.get(i));
        }
        auxi.clear();
        
    }

    @FXML
    private void dibujarInicio(ActionEvent event) throws IOException {
        b= true;
        
        canvas.setOnMouseClicked((MouseEvent event2) -> {
            double p1 = event2.getX();
            double p2 = event2.getY();
            if(existeInicio()==false || existeFin()==false){
                redimensionCanvas(p1,p2);    
                if(b && run==false && corriendo==false){
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


                    if(existeInicio()==false){
                        Inicio inicio = new Inicio(TipoF.INICIO);
                        inicio.setVerticeCentro(new Vertice(x,y));
                        inicio.getVertices().add(new Vertice(x1,y1));
                        inicio.getVertices().add(new Vertice(x2,y2));
                        inicio.getVertices().add(new Vertice(x3,y3));
                        inicio.getVertices().add(new Vertice(x4,y4));
                        inicio.calcularConexiones();
                        //se debe validar la diferencia entre inicio y fin
                        inicio.texto = "INICIO";
                        inicio.setEstado(true);
                        figuras.add(inicio);
                        inicio.dibujar(gc);
                        

                    }else if(existeFin() == false){
                        Inicio inicio = new Inicio(TipoF.FIN);
                        inicio.setVerticeCentro(new Vertice(x,y));
                        inicio.getVertices().add(new Vertice(x1,y1));
                        inicio.getVertices().add(new Vertice(x2,y2));
                        inicio.getVertices().add(new Vertice(x3,y3));
                        inicio.getVertices().add(new Vertice(x4,y4));
                        inicio.calcularConexiones();
                        //se debe validar la diferencia entre inicio y fin
                        inicio.texto = "FIN";
                        inicio.setEstado(true);
                        figuras.add(inicio);
                        inicio.dibujar(gc);
                    }
                    
                    actualizar();
                    b=false;
                    canvas.setOnMouseClicked(null);

                }
            }
        });
    
       
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
        if(run==false && corriendo==false){
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Crear documento");
            dialog.setContentText("Introduzca el texto:");


            Optional<String> result = dialog.showAndWait();
            if(result.isPresent() && !result.get().equals(" ") && !result.get().equalsIgnoreCase(" ")){
                if (result.get().matches("[A-Za-z]+") && evaluarAritmetica(result.get(),variables)){

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

                        Documento documento = new Documento(TipoF.DOCUMENTACION);
                        documento.setVerticeCentro(new Vertice(x,y));
                        documento.getVertices().add(new Vertice(x1,y1));
                        documento.getVertices().add(new Vertice(x2,y2));
                        documento.getVertices().add(new Vertice(x3,y3));
                        documento.getVertices().add(new Vertice(x4,y4));
                        documento.texto = result.get();
                        documento.calcularConexiones();
                        documento.setEstado(true);
                        figuras.add(documento);
                        documento.dibujar(gc);

                        actualizar();



                        b=false;
                        canvas.setOnMouseClicked(null);



                    }      

                });
                }else{
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setHeaderText("cuidado");
                    alert.setContentText("El formato del texto ingresado es incorrecto");

                    alert.showAndWait();      
                }
            }else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("cuidado");
                alert.setContentText("Debes ingresar un texto");

                alert.showAndWait();
            }
        }
    }
    
    boolean moviendo = false;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mover();
        gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        ap.setBackground(Background.EMPTY);
    }    
    
    
    public void redimensionCanvas(double p1, double p2){
        if(p1+60>canvas.getWidth()){
            canvas.setWidth(canvas.getWidth()+50);
            if(p2+60>canvas.getHeight()){canvas.setHeight(canvas.getHeight()+50);
                }
            }else if (p2+51>canvas.getHeight()) {
                canvas.setHeight(canvas.getHeight()+50);
                
        }
    }
    
    public boolean flujoValido(Flujo flujo){
        
        if(!(flujo.padre.tipo==TipoF.DESICION)){
            if(flujo.padre.getVerticeCentro().distancia(flujo.hijo.getVerticeCentro())==0){
                return false;
            }

            if(flujo.padre.getTipo() == TipoF.FIN || flujo.hijo.getTipo() == TipoF.INICIO){
                return false;
            }
            for (Figura figura : figuras) {
                if(figura instanceof Flujo){
                    if((flujo.vertices.get(0).distancia(figura.getVertices().get(0))==0 && flujo.vertices.get(1).distancia(figura.getVertices().get(1))==0) || 
                        (flujo.vertices.get(0).distancia(figura.getVertices().get(1))==0 && flujo.vertices.get(1).distancia(figura.getVertices().get(0))==0)){
                    return false;
                    }
                    if(flujo.padre.getVerticeCentro().distancia(((Flujo) figura).padre.getVerticeCentro())==0
                        || flujo.hijo.getVerticeCentro().distancia(((Flujo) figura).hijo.getVerticeCentro())==0 ){
                        return false;
                    }
                
                    if (flujo.padre.equals(((Flujo) figura).hijo) && flujo.hijo.equals(((Flujo) figura).padre)) {
                        return false;
                    }
                
                }
            }
            
        }else{
            
            if(flujo.padre.getVerticeCentro().distancia(flujo.hijo.getVerticeCentro())==0){
                return false;
            }

            if(flujo.padre.getTipo() == TipoF.FIN || flujo.hijo.getTipo() == TipoF.INICIO){
                return false;
            }
            
            int count = 0;
            for (Figura figura : figuras) {
                if (figura instanceof Flujo) {
                    if(flujo.padre.equals(((Flujo) figura).padre)){
                        count++;
                    }
                    if (count>=2) {
                        return false;
                    }
                }
            }
            
            for (Figura figura : figuras) {
                if (figura instanceof Flujo) {
                    if (flujo.padre.equals(((Flujo) figura).padre) && flujo.hijo.equals(((Flujo) figura).desicionPadre)) {
                        return false;
                    }
                }
            }
            
            for (Figura figura : figuras) {
                if(figura instanceof Flujo){
                    /*
                    if((flujo.vertices.get(0).distancia(figura.getVertices().get(0))==0 && flujo.vertices.get(1).distancia(figura.getVertices().get(1))==0) || 
                        (flujo.vertices.get(0).distancia(figura.getVertices().get(1))==0 && flujo.vertices.get(1).distancia(figura.getVertices().get(0))==0)){
                        return false;
                    }
                    
                    if(flujo.padre.getVerticeCentro().distancia(((Flujo) figura).padre.getVerticeCentro())==0
                        || flujo.hijo.getVerticeCentro().distancia(((Flujo) figura).hijo.getVerticeCentro())==0 ){
                        return false;
                    }
                    */
                }
            }
            if(flujo.padre.tipo==TipoF.DESICION){          
                if (existeTrue((Desicion) flujo.padre) == false) {
                    return true;
                }else if (existeFalse((Desicion) flujo.padre)==false) {
                    return true;
                }
                      
            }
            
            
        }
        
        return true;
    
    }
    
    public boolean flujoFinValido(FinDecision flujo){
        
            if(flujo.padre.getVerticeCentro().distancia(flujo.hijo.getVerticeCentro())==0){
                return false;
            }

            if(flujo.padre.getTipo() == TipoF.FIN || flujo.hijo.getTipo() == TipoF.INICIO){
                return false;
            }
            for (Figura figura : figuras) {
                if(figura instanceof FinDecision){
                    if((flujo.vertices.get(0).distancia(figura.getVertices().get(0))==0 && flujo.vertices.get(1).distancia(figura.getVertices().get(1))==0) || 
                        (flujo.vertices.get(0).distancia(figura.getVertices().get(1))==0 && flujo.vertices.get(1).distancia(figura.getVertices().get(0))==0)){
                    return false;
                    }
                    if(flujo.padre.getVerticeCentro().distancia(((FinDecision) figura).padre.getVerticeCentro())==0
                        || flujo.hijo.getVerticeCentro().distancia(((FinDecision) figura).hijo.getVerticeCentro())==0 ){
                        return false;
                    }
                
                    if (flujo.padre.equals(((FinDecision ) figura).hijo) && flujo.hijo.equals(((FinDecision) figura).padre)) {
                        return false;
                    }
                
                }
            }
            
        
        return true;
    }
    
    
    public int contar(String cadena){
        char[] arrayChar = cadena.toCharArray();
        int cant=0;
        for(int i=0; i<arrayChar.length; i++){
            cant++;
        }

        return cant;
    }

    public ArrayList<Figura> getFiguras() {
        return figuras;
    }

    public void setFiguras(ArrayList<Figura> figuras) {
        this.figuras = figuras;
    }

    public boolean evaluarAritmetica(String expresion, ArrayList<Variable> variables){
        if(expresion.matches("[0-9]+")){
            return true;
            
        }else if(expresion.matches("[A-Za-z]+")){
            //if(valorAritmetica()expresion, variables){
            return true;
        }else if(expresion.matches("\\(.+\\)")){
           expresion = expresion.replaceFirst("[(]", "");
           int index = expresion.lastIndexOf(")");
           expresion = expresion.substring(0, index)+expresion.substring(index+1);
           return evaluarAritmetica(expresion, variables);
           
        }else if(expresion.matches("-((-[0-9]+)|([0-9]+))")){
            return true;
            
        }else if(expresion.matches("\\d+\\*.+")){
            int index = expresion.indexOf("*");
            return true && evaluarAritmetica(expresion.substring(index+1),variables);
        
        }else if(expresion.matches("\\d+\\/.+")){
            int index = expresion.indexOf("/");
            return true && evaluarAritmetica(expresion.substring(index+1),variables);
        
        }else if(expresion.matches("\\d+\\+.+")){
            int index = expresion.indexOf("+");
            return true && evaluarAritmetica(expresion.substring(index+1),variables);
        
        }else if(expresion.matches("\\d+\\-.+")){
            int index = expresion.indexOf("-");
            return true && evaluarAritmetica(expresion.substring(index+1),variables);
            
        }else if(expresion.matches("[A-Za-z0-9]+\\*.+")){
            int index = expresion.indexOf("*");
            return true && evaluarAritmetica(expresion.substring(index+1),variables);
        
        
        }else if(expresion.matches("[A-Za-z0-9]+\\/.+")){
            int index = expresion.indexOf("/");
            return true && evaluarAritmetica(expresion.substring(index+1),variables);
        
        
        }else if(expresion.matches("[A-Za-z0-9]+\\+.+")){
            int index = expresion.indexOf("+");
            return true && evaluarAritmetica(expresion.substring(index+1),variables);
        
        
        }else if(expresion.matches("[A-Za-z0-9]+\\-.+")){
            int index = expresion.indexOf("-");
            return true && evaluarAritmetica(expresion.substring(index+1),variables);
        
        
        }else if(expresion.matches("\\(.+\\)\\*.+")){
            int index = expresion.indexOf("*");
            return true && evaluarAritmetica(expresion.substring(index+1),variables);
            
        }else if(expresion.matches("\\(.+\\)\\/\\(.+\\)")){
            int index = expresion.indexOf("/");
            return evaluarAritmetica(expresion.substring(index-1),variables) && evaluarAritmetica(expresion.substring(index+1),variables);
            
        }else if(expresion.matches("\\(.+\\)\\+\\(.+\\)")){
            int index = expresion.indexOf("+");
            return evaluarAritmetica(expresion.substring(index-1),variables) && evaluarAritmetica(expresion.substring(index+1),variables);
            
        }
        else if(expresion.matches("\\(.+\\)-\\(.+\\)")){
            int index = expresion.indexOf("-");
            return evaluarAritmetica(expresion.substring(index-1),variables) && evaluarAritmetica(expresion.substring(index+1),variables);
            
        }else{
            return false;
        }
    }
        
    
    /**
     * Metodo encargado de devolver el valor de la variable ingresada como expresion,
     * luego es buscada en el arreglo de variables donde se buscara su valor
     * @param expresion
     * @param variables
     * @return 
     */
    public String valorAritmetico(String expresion, ArrayList<Variable> variables){
        for (Variable variable : variables) {
            if (variable.nombre.equals(expresion)) {
                return variable.getValor();
            }
        }
        // se deja esto asi por mientras
        return expresion;
    }
    
   /**
    * Metodod encargado de operar expresiones matematicas de numeros o cadenas
    * dependiendo de la expresion ingresada, si la expresion encuentra una cadena 
    * y esta no es una variable o su variables retorna un valor de cadena (llamaremos
    * cadena a una linea de caracteres) donde se operaran concatenaciones o multiplicaciones
    * de cadena por un entero, si la expresion no contiene ningun valor de cadena
    * por tanto solo numeros se operara segun las operaciones ingresadas siendo estas
    * las mas basicas
    * @param expresion
    * @param variables
    * @return 
    */
    public String operarExpresion(String expresion, ArrayList<Variable> variables){
        
        String[] aux1 = expresion.split("(\\+|\\-|\\*|\\/|\\(|\\))+");
        String[] aux2 = expresion.split("[A-Za-z0-9]+");
        
        
        
        for (int i = 0; i < aux2.length; i++) {
            System.out.println(aux2[i]);
        }
        
        for (int i = 0; i < aux1.length; i++) {
            System.out.println(aux1[i]);
        }
        for (int i = 0; i < aux1.length; i++) {
            if(!aux1[i].matches("[0-9]+")){
                System.out.println(""+aux1[i]);
                aux1[i] = valorAritmetico(aux1[i], variables);
            }
        }
        
        
        int i = 0;
        int j = 0;
        expresion = "";
        while(i < aux1.length || j < aux2.length){
            if(i < aux1.length){
                while(aux1[i].matches("")){    
                    i++;
                }
                expresion = expresion.concat(aux1[i]);
                if(i < aux1.length){
                    i++;
                }
            }
            if (j < aux2.length) {
                while(aux2[j].matches("")){    
                    j++;
                }
                expresion = expresion.concat(aux2[j]);
                if(j< aux2.length){
                    j++;
                }
            }
        }
        if(expresion.matches(".*[A-Za-z]+.*")){
            System.out.println("Tenemos un String en la cadena");
            if (expresion.matches(".*[\\/||\\-]+.*")) {
                if(expresion.matches(".*[\\/]+.*")){
                    System.out.println("No es posible dividir su cadena");
                }
                if(expresion.matches(".*[\\-]+.*")){
                    System.out.println("No es posible restar su cadena");
                }
            }else{
                //primero buscar si hay una multiplicacion por un entero a cadena
                if (expresion.matches(".*[\\*].*")) {
                    System.out.println("Tenemos una multiplicacion");
                
                    if(expresion.matches(".*[\\+].*")){
                        System.out.println("Operacion mixta");
                        System.out.println(""+expresion);
                        String[] auxMix= expresion.split("[\\+]+");
                        expresion="";
                        int k=0;
                        while( k < auxMix.length) {

                            if(auxMix[k].matches(".*[\\*].*")){
                                System.out.println("Encontrada la multiplicacion");

                                String[] auxP= auxMix[k].split("[\\*]");
                                System.out.println("********");
                                System.out.println(""+auxP[0]);
                                System.out.println(""+auxP[1]);
                                System.out.println("*******");
                                if(auxP[1].matches("[0-9]+")){
                                    System.out.println("Es un numero");
                                }else{
                                    System.out.println("No es numero, multiplicacion no valida");
                                }
                                int num=Integer.parseInt(auxP[1]);
                                String aux="";
                                for (int l = 0; l < num; l++) {
                                    aux = aux.concat(auxP[0]);
                                    
                                }
                                k++;
                                System.out.println(""+aux);
                                System.out.println("*********");
                                expresion=expresion.concat(aux);
                            }
                            
                            expresion = expresion.concat(auxMix[k]);
                            k++;
                        }
                        
                        System.out.println(""+expresion);
                    }else{
                        System.out.println("Encontrada la multiplicacion");
                        String[] cen=expresion.split("[\\*]");
                        expresion="";
                        System.out.println(""+cen[0]);
                        System.out.println(""+cen[1]);
                        i=0;
                        int num = Integer.parseInt(cen[1]);
                        while(i<num){
                            expresion=expresion.concat(cen[0]);
                            i++;
                        
                        }
                        
                    System.out.println(""+expresion);
                    }
                
                return expresion;
                }else{
                    System.out.println("Solo suma de cadenas");
                    String[] aux3 = expresion.split("[\\+]+");
                    for (int k = 0; k < aux3.length; k++) {
                        System.out.println(""+aux3[k]);
                    }
                    
                    expresion="";
                    i=0;
                    while(i<aux3.length){
                        while(aux3[i].matches("")){    
                            i++;
                        }
                        expresion = expresion.concat(aux3[i]);
                        if(i < aux3.length){
                            i++;
                        }
                    }
                    System.out.println(""+expresion);
                        
                    
                }
            }
                
        }
            
        
        else{
            System.out.println(expresion);
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("js");
            try {
                System.out.println(""+expresion);
                Object operation = engine.eval(expresion);
                String resultado = String.valueOf(operation);
                System.out.println("Evaluado operacion 1: " + operation);
                return String.valueOf(resultado); 
            } catch (ScriptException e) {
                e.printStackTrace();
            }
        }
        return expresion;
    }
    /**
     * Metodo encargado de evaluar la decision ingresada mediante una expresion
     * esta expresion debe ser previamente comprobada para que funcione bien
     * retorna un valor boolean true si la expresion es evaluada y es incorrecta
     * retorna un valor boolean false si la expresion es evaluada y es falsa
     * 
     * @param expresion 
     * La expresion sera aquella ingresada para evaluar
     * @param variables
     * Arreglo que contiene las variables y sus valores
     * @return 
     */
    public boolean decidirDesicion(String expresion, ArrayList<Variable> variables){
        String[] aux1=expresion.split("[\\>|\\<|\\>=|\\<=|\\==|\\!=]+");
        String[] aux2=expresion.split("[A-Za-z0-9]+");
        
        for (int i = 0; i < aux1.length; i++) {
            if(!aux1[i].matches("[0-9]+")){
                aux1[i] = valorAritmetico(aux1[i], variables);
            }
        }

        if ("<".equals(aux2[1])) {

            if(Integer.parseInt(aux1[0]) < Integer.parseInt(aux1[1])){
                return true;
            }else{
                return false;
            }
        }else{
            if(">".equals(aux2[1])){

                if(Integer.parseInt(aux1[0]) > Integer.parseInt(aux1[1])){
                    return true;
                }else{
                    return false;
                }
            }else{
                if(">=".equals(aux2[1])){

                    if(Integer.parseInt(aux1[0]) >= Integer.parseInt(aux1[1])){
                        return true;
                    }else{
                        return false;
                    }
                }else{
                    if("<=".equals(aux2[1])){

                        if(Integer.parseInt(aux1[0]) <= Integer.parseInt(aux1[1])){
                            return true;
                        }else{
                            return true;
                        }
                    }else{
                        if("==".equals(aux2[1])){

                            if(Integer.parseInt(aux1[0]) == Integer.parseInt(aux1[1])){
                                return true;
                            }else{
                                return false;
                            }
                        }else{
                            if("!=".equals(aux2[1])){

                                if(Integer.parseInt(aux1[0]) != Integer.parseInt(aux1[1])){
                                    return true;
                                }else{
                                    return false;
                                }
                            }
                        }                       
                    }
                }
            }
        }
        System.out.println("No entra");
        return false;
    }
    /**
     Recordatorio para mi mismo
     * esta parte esta siendo construida
     * deberia poder comenzar como un correr comun y corriente solo
     * con la diferencia que dentro de uno se llamararia a la otra para 
     * pausar el hilo, (dentro del ciclo llamar y pausar) esta pausaria 
     * hasta que se pulse otra vez en el boton de avanzar, hice un  boton de mas
     * y una funcion de mas por si acaso
     */
    private int cuentaPasos=0;
    public boolean corriendo=false;
    @FXML
    public void correrP(){
        b=true;
        corriendo=true;
        ArrayList<Figura>corredores = crearCorredores();
        if(b && corriendo==true && run==false){
            
                /*Crear lista en otro metodo para no interferir cada vez en este otro metodo*/
                /*Tenemos la lista de los "Corredores creada"*/
                /*Entero cuentaPasos que va a servir de indice*/
                actualizar();
                gc.fillOval(corredores.get(cuentaPasos).verticeCentro.getX()-80, corredores.get(cuentaPasos).verticeCentro.getY(), 20, 20);
                if (corredores.get(cuentaPasos).tipo!=TipoF.FIN && corredores.get(cuentaPasos).tipo!=TipoF.INICIO){
                    agregarVariable(corredores.get(cuentaPasos));
                } 
                cuentaPasos+=1;
                /*Aviso de que el correr pausado termino, se deja tambien presente 
                que si esta corriendo el pausado no se pueden hacer cambios o activar
                otros botones*/
                if(cuentaPasos==corredores.size()){
                    /*Se completo se deja un aviso de que se pueden utilizar los botones
                    y que esto ya termino*/
                    cuentaPasos=0;
                    
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Termino de Correr");
                    alert.setHeaderText("Hemos terminado");
                    alert.setContentText("Ya puede modificar el diagrama");
                    alert.showAndWait();
                    corriendo=false;
                    actualizar();
                    area.clear();
                    variables.clear();
                    
                }
            }
        }
    
    public ArrayList crearCorredores(){
        Flujo flujo = null;
        Figura fig = null;
        System.out.println("hola");
        ArrayList<Figura> corredors = new ArrayList<>();
        if (!figuras.isEmpty() ){
                if(buscarInicio() && buscarFin()){
                    
                        //buscar inicio
                        for (Figura figura : figuras) {
                            if(figura.getTipo() == TipoF.INICIO){
                                fig = figura;
                            }
                        }
                        // buscarFlujo que tenga el inicio
                        for (Figura figura : figuras) {
                            if(figura instanceof Flujo){
                                if(((Flujo) figura).padre.equals(fig)){
                                    flujo = (Flujo) figura;
                                }
                            }
                        }
                        if(!flujo.equals(null)){
                            System.out.println(fig.texto);
                            corredors.add(fig);


                            //actualizar();
                            boolean terminar = true;
                            while(terminar && fig != null && fig.getTipo() != TipoF.FIN ){
                                fig = figuras.get(flujo.indexHijo);
                                /*
                                for (Figura figura : figuras) {
                                    if(figura instanceof Flujo){
                                        if(((Flujo) figura).padre.equals(fig)){
                                            flujo = (Flujo) figura;
                                        }
                                    }
                                }*/
                                for (int i = 0; i < figuras.size(); i++) {
                                    if (figuras.get(i) instanceof Flujo) {
                                        if (((Flujo)figuras.get(i)).padre.equals(fig)) {
                                            flujo = (Flujo)figuras.get(i);
                                            break;
                                        }
                                        if (i>=figuras.size()-1) {
                                        terminar = false;
                                        }
                                    }
                                }
                                if(fig.tipo != TipoF.FIN){
                                   System.out.println(fig.texto);
                                   corredors.add(fig);
                                }
                            }
                            if(fig == null){
                                System.out.println("Mala construccion");
                            }else if(fig.getTipo() == TipoF.FIN){
                                System.out.println(fig.texto);
                                corredors.add(fig);
                            }
                        }
                }
        }
        return corredors;
    }
    
    @FXML
    public void exportImage() throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("png files (.png)", ".png");
        fileChooser.getExtensionFilters().add(extFilter);
        final Stage stage = new Stage();
        File file = fileChooser.showSaveDialog(stage);
        WritableImage writableImage = canvas.snapshot(new SnapshotParameters(), null);
        canvas.snapshot(null, writableImage);
        RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
        try{
            ImageIO.write(renderedImage, "png", file);
        }catch( IllegalArgumentException r){
            
        } 
        }
    
    @FXML
    public void exportImageJpg() throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("jpg files (.jpg)", ".jpg");
        fileChooser.getExtensionFilters().add(extFilter);
        final Stage stage = new Stage();
        File file = fileChooser.showSaveDialog(stage);
        WritableImage writableImage = canvas.snapshot(new SnapshotParameters(), null);
        canvas.snapshot(null, writableImage);
        RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
        try{
            ImageIO.write(renderedImage, "jpg", file);
        }catch( IllegalArgumentException r){
            
        }  
        BufferedImage bufferedImage;
		
	try {
			
	  //read image file
	  bufferedImage = ImageIO.read(new File("c:\\javanullpointer.png"));

	  // create a blank, RGB, same width and height, and a white background
	  BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
			bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
	  newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, null);

	  // write to jpeg file
	  ImageIO.write(newBufferedImage, "jpg", new File("c:\\javanullpointer.jpg"));

	  System.out.println("Done");
			
	} catch (IOException e) {

	  e.printStackTrace();

	}

        }
    
    @FXML
    private void exportPDF() throws IOException{
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("pdf files (.pdf)", ".pdf");
        fileChooser.getExtensionFilters().add(extFilter);
        final Stage stage = new Stage();
        File file = fileChooser.showSaveDialog(stage);
        OutputStream archivo = null;
        try {
            archivo = new FileOutputStream(file);
        }catch(Exception e){
        }

        WritableImage writableImage = canvas.snapshot(new SnapshotParameters(), null);
        canvas.snapshot(null, writableImage);
        File file2 = new File("chart.png");
        RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
        try {
           ImageIO.write(renderedImage, "png", file2); 
        }catch( IllegalArgumentException r){

        }
        
        try{
            
            com.itextpdf.text.Rectangle rectangle = new com.itextpdf.text.Rectangle( 1920 , 1080);
            Document doc = new Document(rectangle);
            PdfWriter.getInstance(doc, archivo);
            Image img = Image.getInstance("chart.png");
            img.setBorderColor(BaseColor.BLACK);           
            doc.setMargins(0, 1920, 0, 1080);
            doc.open();
            doc.add(img);
            doc.close();
            file2.delete();
        }catch(Exception e){

        }
        
        

    }
    @FXML
    private void dibujarCiclo(ActionEvent event) throws IOException {
        if(run==false && corriendo==false){
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Crear Ciclo");
            dialog.setContentText("Introduzca el texto:");


            Optional<String> result = dialog.showAndWait();
            if (result.isPresent() && !result.get().equals(" ") && !result.get().equalsIgnoreCase(" ")){
               if(result.get().matches("[A-Za-z0-9]+[\\>|\\<|\\>=|\\<=|\\==|\\!=]{1}[A-Za-z0-9]+")){

                if(contar(result.get())>12){
                    powerUp = 80;
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
                    double x1 = x ;
                    double y1 = y - 50;
                    double x2 = x+50;
                    double y2 = y ;
                    double x3 = x ;
                    double y3 = y+50;
                    double x4 = x - 50;
                    double y4 = y;


                    Ciclo ciclo = new Ciclo(TipoF.CICLO);
                    ciclo.setVerticeCentro(new Vertice(x,y));
                    ciclo.getVertices().add(new Vertice(x1, y1));
                    ciclo.getVertices().add(new Vertice(x2, y2));
                    ciclo.getVertices().add(new Vertice(x3, y3));
                    ciclo.getVertices().add(new Vertice(x4, y4));
                    ciclo.calcularConexiones();
                    ciclo.texto = result.get();
                    figuras.add(ciclo);
                    ciclo.estado=true;
                    ciclo.dibujar(gc);
                    actualizar();
                    b=false;
                    powerUp=0;
                    canvas.setOnMouseClicked(null);
                }  
            });
               }else{
                   Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setHeaderText("cuidado");
                    alert.setContentText("El texto ingresado es incorrecto, intente otra vez");

                    alert.showAndWait();

               }
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("cuidado");
                alert.setContentText("Debes ingresar un texto");

                alert.showAndWait();
            }
        }
    }
    @FXML
    private void generarpseudocodigo(ActionEvent event) {
        try {
            String ruta = "filename.txt";
            File file = new File(ruta);
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(texto);
            
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
    
    
}
