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
    
        
    public void printArea(){
        System.out.println( "Area: x = " + startX 
                            + '\n' + "y = " + startY 
                            + '\n' + "width = " + width
                            + '\n' + "height = " + height
                );
        
    }
    
    public void printCenter(){
        System.out.println("center x = " + (startX + width) / 2);
        System.out.println("center y = " + (startY + height) / 2);
    }
    


}
