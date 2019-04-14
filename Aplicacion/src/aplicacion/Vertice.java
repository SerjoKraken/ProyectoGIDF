/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion;

/**
 *
 * @author Serjo
 */
public class Vertice {
    
    private double x;
    private double y;

    public Vertice(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    
    public Vertice puntoMedio(Vertice vertice){
        return new Vertice((this.x + vertice.x)/2,(this.y + vertice.y)/2);
    }
    public Vertice puntoMedio(double x, double y){
        return new Vertice((this.x + x)/2, (this.y + y)/2);
    }
    
    
    
}
