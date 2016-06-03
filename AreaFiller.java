/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mandelbrotfx;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;

/**
 * A skeleton class illustrating the use of a pixelWriter
 * 
 * @author Sjaak Smetsers
 * @version 06-05-2016
 */
public class AreaFiller {

    public static final int MAX_ITERATIONS = 100;
    
    private static final ColorMap colorMap = new ColorMap( MAX_ITERATIONS );

    private Area area;
    private double scalingFactor;
    
    private int iterations;
    public AreaFiller(Area a    ){
        this.area = a;
        this.iterations = 30;
        this.scalingFactor = MandelbrotFX.CANVAS_SIZE / 5;
    }
    
    
    /**
     * fills the canvas with some arbitrarily chosen pattern
     * @param canvas
     * @param zoom
     */
    public void fill( Canvas canvas) {
        int imageWith   = (int) canvas.getWidth();
        int imageHeight = (int) canvas.getHeight();
        final PixelWriter pixelWriter = canvas.getGraphicsContext2D().getPixelWriter();
        for (int i = 0; i < imageWith; i++) {
            for (int j = 0; j < imageHeight; j++) {
                Punt temp = new Punt(
                        ((double)(area.getX() + i * (area.getWidth() / imageWith))),
                        ((double)(area.getY() + j * (area.getHeight() / imageHeight))),
                        this.iterations
                );
                       
                int color_index;
                if (temp.getMandelGetal() % 2 == 0) {
                    color_index = temp.getMandelGetal() * (int)Math.sqrt(temp.getMandelGetal());
                } else {
                    color_index = temp.getMandelGetal() * (int)Math.pow(temp.getMandelGetal(), 2);
                }
                pixelWriter.setColor(i, j, colorMap.getColor(color_index));
            }               
        }

    }  
    
    public void zoom(double posX, double posY, double zoom){
        double width = area.getWidth() / zoom;
        double height = area.getHeight() / zoom;
        area = new Area(
                width,
                height,
                area.getX() + (posX / MandelbrotFX.CANVAS_SIZE) * area.getWidth() - width / 2,
                area.getY() + (posY / MandelbrotFX.CANVAS_SIZE) * area.getHeight() - height / 2     
        );
    }
    
    public void zoom(double x, double y, double w, double h){
        area = new Area(
                (w / MandelbrotFX.CANVAS_SIZE) * area.getWidth(),
                (h / MandelbrotFX.CANVAS_SIZE) * area.getHeight(),
                area.getX() + (x / MandelbrotFX.CANVAS_SIZE) * area.getWidth(),
                area.getY() + (y / MandelbrotFX.CANVAS_SIZE) * area.getHeight()
        );
        this.scalingFactor = MandelbrotFX.CANVAS_SIZE / area.getWidth() ;
    }
    
    public void setArea(Area a){
        this.area = a;
    }
    
    
    public Area getArea(){
        return this.area;
    }
    
    public void setIterations(int iter){
        this.iterations = iter;
    }
    
    public double getScaling(){
        return this.scalingFactor;
    }

}
