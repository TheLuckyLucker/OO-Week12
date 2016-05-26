/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mandelbrotfx;

import static java.lang.Math.sqrt;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * A skeleton class illustrating the use of a pixelWriter
 * 
 * @author Sjaak Smetsers
 * @version 06-05-2016
 */
public class AreaFiller {

    public static final int MAX_ITERATIONS = 20;
    static private int zoomfactor;
    
    private static final ColorMap colorMap = new ColorMap( MAX_ITERATIONS );

    private Area area;
    
    
    public AreaFiller(Area a    ){
        this.area = a;
    }
    
    
    /**
     * fills the canvas with some arbitrarily chosen pattern
     */
    public void fill( Canvas canvas, int zoom ) {
        this.zoomfactor = zoom;
        int imageWith   = (int) canvas.getWidth();
        int imageHeight = (int) canvas.getHeight();
        final PixelWriter pixelWriter = canvas.getGraphicsContext2D().getPixelWriter();
        for (int i = 0; i < imageWith; i++) {
            for (int j = 0; j < imageHeight; j++) {
                Punt temp = new Punt((double)(i-(imageWith/2))/this.zoomfactor, (double)(j-(imageHeight/2))/this.zoomfactor);
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
    
    public int getZoom(){
        return this.zoomfactor;
    }
    


}
