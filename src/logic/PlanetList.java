/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kuba
 */
public class PlanetList {
    ArrayList<Planet> plList = new ArrayList<Planet>();

    public void addNewPlanet(Planet p){
        plList.add(p);
        
    }
    
    public void removePlanet(Planet p){
        plList.remove(p);
    }
    
    public void removePlanetNumber(int i){
        plList.remove(i);
        
    }

    public void showPlanetList(){
        for(Planet pl : plList){///////Zmien 5 na n !!!!!!!!!!!!!!!!
            System.out.println(pl.getName());
        }
        
    }

    public ArrayList<Planet> getPlList() {
        return plList;
    }

    public void setPlList(ArrayList<Planet> plList) {
        this.plList = plList;
    }
    
    
}
