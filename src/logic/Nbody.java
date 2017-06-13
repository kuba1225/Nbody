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
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Double.parseDouble;
import java.util.ArrayList;
import java.util.Scanner;
import static gui.DataPanel.isSaved;
import static java.lang.Math.abs;

/**
 *
 * @author Kuba
 */
public class Nbody implements Observed {

    private int n = bodyNumber;

    private double[][] coordinates;
    private ArrayList<Observer> observators;
    public static final int DAY = 24 * 60 * 60;
    public static final double G = 6.67408e-11;
    public static final int HOUR = 60 * 60;
    public static PlanetList pl;

    public static PlanetList getPl() {
        return pl;
    }

    public Nbody() {
        if (!isSaved) {
            n = 4;
        }
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
        pl = new PlanetList();
        if (!isSaved) {
            n = 4;

        }

        int i, j, u;
        int l = 0;

        pt *= DAY;
        dt *= HOUR;

        double tmp = 0;
        double tmp2 = 0;
        double tmp3 = 0;
        double r = 0;
        double rr = 0;
        double nrm = 0;

        PrintWriter fileWrite = null;
        try {
            fileWrite = new PrintWriter(new FileWriter(fileOut));

        } catch (IOException e) {

        }

        PrintWriter fileWriteEnergy = null;
        try {
            fileWriteEnergy = new PrintWriter(new FileWriter("energyresults.txt"));

        } catch (IOException e) {

        }

        Scanner file = null;
        try {
            file = new Scanner(new BufferedReader(new FileReader(fileIn)));
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
        double[][] v = new double[n][3];
        double[][] w = new double[n][3];
        double[] m = new double[n];

        for (i = 0; i < n; i++) {

            m[i] = pl.getPlList().get(i).getMass();
            w[i][0] = pl.getPlList().get(i).getCoordinates(0);
            w[i][1] = pl.getPlList().get(i).getCoordinates(1);
            w[i][2] = pl.getPlList().get(i).getCoordinates(2);
            v[i][0] = pl.getPlList().get(i).getVelocity(0);
            v[i][1] = pl.getPlList().get(i).getVelocity(1);
            v[i][2] = pl.getPlList().get(i).getVelocity(2);
            // System.out.println("m="+m[i]+" x="+w[i][0]+" y="+w[i][1]+" "+ "z="+w[i][2]+" vx="+v[i][0]+" vy="+v[i][1]+" vz="+v[i][2]);
        }

        int t = 0;
        while (t < pt) {
            for (i = 0; i < n; i++) {
                for (u = 0; u < 3; u++) {

                    a[i][u] = 0;

                }
                for (j = 0; j < n; j++) {
                    if (i != j) {
                        for (u = 0; u < 3; u++) {
                            rr = w[i][u] - w[j][u];
                            mr[i][u] = rr;
                        }
                        nrm = norm(mr[i][0], mr[i][1], mr[i][2]);
                        for (u = 0; u < 3; u++) {

                            tmp = (((G) * m[j]) / (nrm * nrm)) * (mr[i][u] / nrm);
                            a[i][u] = a[i][u] - tmp;
                        }
                    }
                }
                for (u = 0; u < 3; u++) {
                    tmp2 = v[i][u] + a[i][u] * dt;
                    //System.out.println("Przed: "+pl.getPlList().get(i).getVelocity(u));
                    v[i][u] = tmp2;
                    //System.out.println("Po: "+pl.getPlList().get(i).getVelocity(u)+"  "+tmp2);
                    tmp3 = w[i][u] + v[i][u] * dt;

                    w[i][u] = tmp3;
                }

            }

            for (u = 0; u < 3; u++) {
                c[0][u] = w[0][u];      //c - wspolrzedne sledzonego ciala	
            }

            for (i = 0; i < n; i++, l++) {

                coordinates[i][0] = w[i][0] - c[0][0];
                coordinates[i][1] = w[i][1] - c[0][1];
                coordinates[i][2] = w[i][2] - c[0][2];
                //System.out.print(coordinates[i][0]+" ");
                //System.out.print(coordinates[i][1]+" ");
                //System.out.print(coordinates[i][2]+" ");
            }
            //System.out.println();
            for (i = 0; i < n; i++, l++) {
                fileWrite.println(coordinates[i][0] + " " + coordinates[i][1]);
                //fileWrite.println();
            }

            tellObserver();

            for (i = 0; i < n; i++, l++) {
                coordinates[i][0] = 0;
                coordinates[i][1] = 0;
                coordinates[i][2] = 0;
            }

            ////////////////////////////////////////////////////////////////////
            double[][] Ep = new double[n][3];
            double[] Epc = new double[n];
            double[][] Ek = new double[n][3];
            double[] Ekc = new double[n];
            double[] Ec = new double[n];

            /////////////////////////////ENERGIA_POTENCJALNA////////////////////
            for (int g = 0; g < n; g++) {
                for (int s = 0; s < n; s++) {
                    for (int f = s + 1; f < n; f++) {
                        Ep[g][0] += ((-1) * G) * ((m[s] * m[f]) / (abs(w[s][0] - w[f][0])));
                        Ep[g][1] += ((-1) * G) * ((m[s] * m[f]) / (abs(w[s][1] - w[f][1])));
                        Ep[g][2] += ((-1) * G) * ((m[s] * m[f]) / (abs(w[s][2] - w[f][2])));
                    }
                }
                Epc[g] = norm(Ep[g][0], Ep[g][1], Ep[g][2]);
            }

            /////////////////////////////ENERGIA_KINETYCZNA/////////////////////
            for (int it = 0; it < n; it++) {
                Ek[it][0] = 0.5 * m[it] * (v[it][0] * v[it][0]);
                Ek[it][1] = 0.5 * m[it] * (v[it][0] * v[it][1]);
                Ek[it][2] = 0.5 * m[it] * (v[it][0] * v[it][2]);
                Ekc[it] = norm(Ek[it][0], Ek[it][1], Ek[it][2]);
               // System.out.println(v[it][0]+" "+v[it][1]+" "+v[it][2]);
            }

            ////////////////////////////////////////////////////////////////////
            for (int it = 0; it < n; it++) {
                Ec[it] = (Ekc[it] + Epc[it]);
                //System.out.println("Ekc = "+Ekc[it]+" Epc = "+Epc[it]+" Ec = "+Ec[it]);
            }

            for (int iit = 0; iit < n; iit++) {
                fileWriteEnergy.println(Epc[iit] + "  " + Ekc[iit] + "  " + Ec[iit]);
            }

            t += dt;

        }
        fileWrite.close();
        fileWriteEnergy.close();
    }

    public double[][] getResults() {
        return coordinates;
    }

}
