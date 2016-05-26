/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mandelbrotfx;

import java.awt.TextField;
import java.awt.event.MouseEvent;
import java.beans.EventHandler;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

/**
 *
 * @author Sjaak
 */
public class MandelbrotFX extends Application {

    public static final int GRID_WIDTH = 800, GRID_HEIGHT = 800;    
    private Canvas canvas;
    Stage stage;
    
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        primaryStage.setTitle("Mandelbrot");
        primaryStage.setScene( makeScene() );
        primaryStage.show();
    }

    private Scene makeScene() {
        canvas = new Canvas(GRID_WIDTH, GRID_HEIGHT);
 
        canvas.setFocusTraversable(true);
        
        canvas.setOnKeyPressed(event -> {  
            switch(event.getCode()){
                case ESCAPE : System.exit(0);
            }
        });
        

        
        Area area = new Area(-2.5, 2.5, 5, 5);
        AreaFiller areaFiller = new AreaFiller(area  );

        canvas.setOnMouseClicked(event -> {
            if(event.isShiftDown()){
                areaFiller.fill(canvas, areaFiller.getZoom()/2);
            }
            else{
                areaFiller.fill(canvas,areaFiller.getZoom()*2);
                
            }
        }); 
        
        areaFiller.fill( canvas, 100);
        Group root = new Group( canvas );
        Scene scene = new Scene(root);
        return scene;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    

}
