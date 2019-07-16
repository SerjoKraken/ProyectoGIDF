
package aplicacion;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Aplicacion extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        
        
        stage.setScene(scene);
        stage.setMinWidth(1400);
        stage.setMinHeight(850);
        stage.setResizable(true);
        
        stage.show();
    }

    
    public static void main(String[] args) {
        launch(args);
    }
    
}
