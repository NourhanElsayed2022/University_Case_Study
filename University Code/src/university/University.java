/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Software
 */
public class University extends Application{

    /**
     * @param args the command line arguments
     */

    @Override
    public void start(Stage stage) throws Exception {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/university/home/home.fxml"));
            Scene scene = new Scene(root);
            
            

            stage.resizableProperty().setValue(false);
            stage.setScene(scene);
            stage.show(); 
        }catch(Exception ex){
            ex.printStackTrace();
        }
    
    }
    
    public static void main(String[] args) {
        launch(args);
        
    }
    
}
