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
public class Punt {
    private final double x0,y0;
    private final int mandelgetal;
    
    public Punt(double x, double y){
        this.x0 = x;
        this.y0 = y;

        if(squareSom(x,y) > 2)
            this.mandelgetal = 0;
        else
            this.mandelgetal = calculateMandel();
    }
    
    /**
     * Calculate the Mandelgetal of the punt
     * @return the mandelgetal
     */
    public int calculateMandel(){
        int counter;
        double xNew = this.x0, yNew = this.y0;
        double temp;
        for(counter = 1; counter < 101; counter++){
            temp = xNew;
            xNew = Math.pow(xNew,2) - Math.pow(yNew,2) + x0;
            yNew = 2 * temp * yNew + y0;
            if(squareSom(xNew,yNew) > 2 || counter == 100){
                return counter;
            }
        }
        
       return counter;
    }
    
    /**
     * calcultes sqrt(x^2+y^2)
     * @param x
     * @param y
     * @return returns sqrt(x^2+y^2)
     */
    private double squareSom(double x, double y){
        return Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
    }
    
    /**
     * getter for the mandelgetal
     * @return 
     */
    public int getMandelGetal(){
        return this.mandelgetal;
    }
}
