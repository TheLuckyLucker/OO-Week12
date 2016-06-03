/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mandelbrotfx;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Sjaak
 */
public class MandelbrotFX extends Application {

    public static final int CANVAS_SIZE = 800;
    private Canvas canvas;
    private AreaFiller areaFiller;
    private TextField iterInput, centerXInput, centerYInput, scalingInput;
    private GridPane gridpane;
    private Rectangle selectedArea;
    private double mouseX, mouseY;
    private Group itemsOnCanvas;
    private boolean dragged;

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Mandelbrot");
        primaryStage.setScene(makeScene());
        primaryStage.show();
    }

    private Scene makeScene() {
        canvas = new Canvas(CANVAS_SIZE, CANVAS_SIZE);

        canvas.setFocusTraversable(true);

        areaFiller = new AreaFiller(new Area(5, 5, -2.5, -2.5));

        areaFiller.fill(canvas);
        
        gridpane = new GridPane();
        iterInput = new TextField("Iterations");   
        centerXInput = new TextField("Center X");
        centerYInput = new TextField("Center Y");
        scalingInput = new TextField("ScalingFactor");
        Button updateIter = new Button("Update Iterations");
        Button updateCenter = new Button("Update Center");
        itemsOnCanvas = new Group(canvas);
        
        /**
         * Updates the number of iterations and refills the canvas 
         */
        updateIter.setOnAction(event -> {
            areaFiller.setIterations(Integer.parseInt(iterInput.getText()));
            areaFiller.fill(canvas);
            itemsOnCanvas.requestFocus();
        });
        
        /**
         * Set the new center with a new scaling factor
         */
        updateCenter.setOnAction(event -> {
            double width = CANVAS_SIZE / Double.parseDouble(scalingInput.getText());
            double newCenterX = Double.parseDouble(centerXInput.getText());
            double newCenterY = Double.parseDouble(centerYInput.getText());
            
            areaFiller.setArea(new Area(width, width, (newCenterX - width/2 ), (newCenterY - width/2)));
            areaFiller.fill(canvas);
            itemsOnCanvas.requestFocus();
        });
        
        

        gridpane.setAlignment(Pos.CENTER);
        gridpane.setHgap(1);
        gridpane.setVgap(1);
        gridpane.add(itemsOnCanvas, 0, 0);
        gridpane.add(iterInput, 0, 1);
        gridpane.add(updateIter, 0, 2);
        gridpane.add(centerXInput, 0, 3);
        gridpane.add(centerYInput, 0, 4);
        gridpane.add(scalingInput, 0, 5);
        gridpane.add(updateCenter, 0, 6);

        selectedArea = new Rectangle();
        selectedArea.setStroke(Color.GREEN);
        selectedArea.setFill(Color.TRANSPARENT);

        dragged = false;

        /**
         * Draws a rectangle when dragging on the canvas
         */
        itemsOnCanvas.setOnMouseDragged(event -> {
            dragged = true;
            if (!itemsOnCanvas.getChildren().contains(selectedArea)) {
                itemsOnCanvas.getChildren().add(selectedArea);
            }
            selectedArea.setX(Math.min(mouseX, event.getX()));
            selectedArea.setY(Math.min(mouseY, event.getY()));
            selectedArea.setWidth(Math.abs(mouseX - event.getX()));
            selectedArea.setHeight(Math.abs(mouseY - event.getY()));

        });

        /**
         * Saves the initial mouse coordinates when clicked
         */
        itemsOnCanvas.setOnMousePressed(event -> {
            mouseX = event.getX();
            mouseY = event.getY();
        });

        /**
         * handles button input
         * ESCAPE -> close the app
         * R -> refresh to initial state
         */
        itemsOnCanvas.setOnKeyPressed(event -> {
            boolean pressedR = false;
            switch (event.getCode()) {
                case ESCAPE:
                    System.exit(0);
                case R:
                    pressedR = true;
            }
            if (pressedR) {
                areaFiller.setIterations(30);
                areaFiller.setArea(new Area(5, 5, -2.5, -2.5));
                areaFiller.fill(canvas);
            }
        });

        /**
         * If mouse is released:
         * if it was a dragging action -> zooms in the new rectangle
         * if it was just a click -> zoom in with click position as new center
         */
        itemsOnCanvas.setOnMouseReleased(event -> {
            if (dragged) {
                itemsOnCanvas.getChildren().remove(selectedArea);
                areaFiller.zoom( 
                    Math.min(mouseX, event.getX()),
                    Math.min(mouseY, event.getY()),
                    Math.abs(mouseX - event.getX()),
                    Math.abs(mouseY - event.getY())
            );
                dragged = false;
                scalingInput.setText(String.valueOf(areaFiller.getScaling()));
                centerXInput.setText(String.valueOf(areaFiller.getArea().getX() + areaFiller.getArea().getWidth()));
                centerYInput.setText(String.valueOf(areaFiller.getArea().getY() + areaFiller.getArea().getHeight()));
            } else {
                if (event.isShiftDown()) {
                    areaFiller.zoom(event.getX(), event.getY(), 0.5);
                } 
                else {
                    areaFiller.zoom(event.getX(), event.getY(), 2);
                }
            }
            areaFiller.fill(canvas);
            itemsOnCanvas.requestFocus();
        });

        Scene scene = new Scene(gridpane, 800, 960);
        return scene;
    }
    
 
    
    public static void main(String[] args) {
        launch(args);
    }

}
