/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.lang.ArrayIndexOutOfBoundsException;

/**
 *
 * @author Kuba
 */
public class Planet {

    private String name;
    private double mass;
    private double[] velocity = new double[3];
    private double[] coordinates = new double[3];

    public double getVelocity(int i) {
        return velocity[i];
    }

    public double getCoordinates(int i) {
        return coordinates[i];
    }

    public String getName() {
        return name;
    }

    public double getMass() {
        return mass;
    }

    public Planet(String name, double mass, double x, double y, double z, double vx, double vy, double vz) {
        this.name = name;
        this.mass = mass;
        this.coordinates[0]=x;
        this.coordinates[1]=y;
        this.coordinates[2]=z;
        this.velocity[0]=vx;
        this.velocity[1]=vy;
        this.velocity[2]=vz;
    }
    public Planet(){
        
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public void setVelocity(double[] velocity) {
        this.velocity = velocity;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }
    
    
    public void setVelocity(int i,double val) {
        this.velocity[i] = val;
    }

    public void setCoordinates(int i,double val) {
        this.coordinates[i] = val;
    }
    
}
