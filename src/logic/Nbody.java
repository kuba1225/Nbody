/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import static gui.DataPanel.bodyNumber;
import static java.lang.Math.sqrt;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Double.parseDouble;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Kuba
 */
public class Nbody implements Observed {

    int n = 5;//bodyNumber;
    private double[][] coordinates;
    private ArrayList<Observer> observators;
    public static final int DAY = 24 * 60 * 60;
    public static final double G = 6.67408e-11;

    public Nbody() {
        observators = new ArrayList<Observer>();
        coordinates = new double[n][3];
    }

    @Override
    public void addObserver(Observer o) {
        observators.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        int index = observators.indexOf(o);
        observators.remove(index);
    }

    @Override
    public void tellObserver() {
        for (Observer o : observators) {
            o.update(coordinates);
        }
    }

    private double norm(double x, double y, double z) {
        return sqrt(x * x + y * y + z * z);
    }

    public void findPosition(String fileIn, String fileOut, int dt, int pt) {
        int i, j, u;
        int l = 0;

        double tmp = 0;
        double tmp2 = 0;
        double tmp3 = 0;
        double r = 0;
        double rr = 0;
        double nrm = 0;

        PlanetList pl = new PlanetList();

        Scanner file = null;
        try {
            file = new Scanner(new BufferedReader(new FileReader("data.txt")));
            for (int it = 0; it < n; it++) {
                Planet planet = new Planet(file.next(), parseDouble(file.next()), parseDouble(file.next()), parseDouble(file.next()), parseDouble(file.next()),
                        parseDouble(file.next()), parseDouble(file.next()), parseDouble(file.next()));
                pl.addNewPlanet(planet);

            }

        } catch (IOException e) {
            System.err.println("Nieudana próba otwarcia pliku!");
        } finally {
            if (file != null) {
                file.close();
            }
        }

        double[][] c = new double[n][3];
        double[][] a = new double[n][3];
        double[][] mr = new double[n][3];

        int t = 0;
        while (t < pt) {
            for (i = 0; i < n; i++) {
                for (u = 0; u < 3; u++) {

                    a[i][u] = 0;

                }
                for (j = 0; j < n; j++) {
                    if (i != j) {
                        for (u = 0; u < 3; u++) {
                            rr = pl.getPlList().get(i).getCoordinates(u) - pl.getPlList().get(j).getCoordinates(u);
                            mr[i][u] = rr;
                        }
                        nrm = norm(mr[i][0], mr[i][1], mr[i][2]);
                        for (u = 0; u < 3; u++) {
                            r = pl.getPlList().get(i).getCoordinates(u) - pl.getPlList().get(j).getCoordinates(u);
                            tmp = ((-G * pl.getPlList().get(j).getMass()) / (nrm * nrm)) * (r / nrm);
                            a[i][u] = tmp;
                        }
                    }
                }
                for (u = 0; u < 3; u++) {
                    tmp2 = pl.getPlList().get(i).getVelocity(u) + a[i][u] * dt;

                    pl.getPlList().get(i).setVelocity(u, tmp2);

                    tmp3 = pl.getPlList().get(i).getCoordinates(u) + pl.getPlList().get(i).getVelocity(u) * dt;

                    pl.getPlList().get(i).setVelocity(u, tmp3);
                }

            }

            for (u = 0; u < 3; u++) {
                c[0][u] = pl.getPlList().get(0).getCoordinates(u);      //c - wspolrzedne sledzonego ciala	
            }

            for (i = 0; i < n; i++, l++) {
                coordinates[i][0] = pl.getPlList().get(i).getCoordinates(0) - c[0][0];
                coordinates[i][1] = pl.getPlList().get(i).getCoordinates(1) - c[0][1];
                coordinates[i][2] = pl.getPlList().get(i).getCoordinates(2) - c[0][2];
                //System.out.println(coordinates[i][0]);
                //System.out.println(coordinates[i][1]);
                //System.out.println(coordinates[i][2]);
            }
            tellObserver();
            t += dt;

        }
        //write_to_file(wrc, out);
    }

    public double[][] getResults() {
        return coordinates;
    }

}
