/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mandelbrotfx;

/**
 *
 * @author Conny
 */
public class Area {
    
    private int[][] mandelGetallen;
    private final double width, height;
    private final double startX, startY;
    
    public Area(double w, double h, double xBeginning, double yBeginning){
        this.width = w;
        this.height = h;
        this.startX = xBeginning;
        this.startY = yBeginning;
    }
    
    public double getX ()       { return startX; }
    public double getY ()       { return startY; }
    public double getWidth ()   { return width; }
    public double getHeight ()  { return height; }
    
        
    public Area zoom (int xul, int yul, int zw, int zh, int tw, int th) {
        double zoom_fact = ((double) zw) / tw;
        return new Area (startX + (width  * xul) / tw,
                         startY - (height * yul) / th,
                         width * zoom_fact,
                         height * zoom_fact);
    }
    

}
