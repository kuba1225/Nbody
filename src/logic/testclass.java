/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Double.parseDouble;
import java.util.Scanner;

/**
 *
 * @author Kuba
 */
public class testclass {

    public static void main(String[] args) {
        PlanetList pl = new PlanetList();
        //Planet planet = new Planet();
        int i = 0;

        Scanner file = null;
        try {
            file = new Scanner(new BufferedReader(new FileReader("data.txt")));
            while (i < 5) {
                Planet planet = new Planet(file.next(), parseDouble(file.next()), parseDouble(file.next()), parseDouble(file.next()), parseDouble(file.next()),
                        parseDouble(file.next()), parseDouble(file.next()), parseDouble(file.next()));
                pl.addNewPlanet(planet);
                i++;
            }

        } catch (IOException e) {
            System.err.println("Nieudana próba otwarcia pliku!");
        } finally {
            if (file != null) {
                file.close();
            }
        }
        pl.showPlanetList();
    }
}
