/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Double.parseDouble;
import static java.lang.Math.abs;
import java.util.Scanner;
import javax.swing.Timer;
import logic.Nbody;

/**
 *
 * @author Kuba
 */
public class VisualisationPanel3 extends javax.swing.JPanel implements ActionListener {

    int n = 5;
    private double[][] coordinates;
    private double[][] coordinates2D = new double[n][3];
    private Nbody nbody;
    boolean start = false;
    Timer timer = new Timer(40, this);
    double xw, yw;
    Scanner file = null;

    public VisualisationPanel3(Nbody nbody) {
        initComponents();
        coordinates = new double[n][3];
        this.nbody = nbody;
        try {
            file = new Scanner(new BufferedReader(new FileReader("rozw.txt")));
        } catch (IOException ex) {

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        OptionPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        OptionPanel.setBackground(new java.awt.Color(0, 0, 0));

        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Stop");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout OptionPanelLayout = new javax.swing.GroupLayout(OptionPanel);
        OptionPanel.setLayout(OptionPanelLayout);
        OptionPanelLayout.setHorizontalGroup(
            OptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OptionPanelLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(OptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        OptionPanelLayout.setVerticalGroup(
            OptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OptionPanelLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(399, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 770, Short.MAX_VALUE)
                .addComponent(OptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(OptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        nbody.findPosition("data.txt", "results.txt", 20, 10000);
        
        
        start = true;
        timer.start();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        start = false;
        timer.stop();
    }//GEN-LAST:event_jButton2ActionPerformed

    @Override
    public void actionPerformed(ActionEvent e) {

        for (int i = 0; i < n; i++) {
            coordinates[i][0] = parseDouble(file.next());
            coordinates[i][1] = parseDouble(file.next());

        }
        for (int i = 0; i < n; i++) {
            if (xw < coordinates[i][0]) {
                xw = coordinates[i][0];
            }
            if (yw < coordinates[i][1]) {
                yw = coordinates[i][1];
            }

            int xmax = this.getWidth()-OptionPanel.getWidth()-10;
            int ymax = this.getHeight()-10;

            coordinates2D[i][0] = (xmax / 2) + ((coordinates[i][0] / abs(xw)) * (xmax / 2));
            coordinates2D[i][1] = (ymax / 2) - ((coordinates[i][1] / abs(yw)) * (ymax / 2));
            //System.out.println("x = "+coordinates2D[i][0]+" y = "+coordinates2D[i][1]);
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g2d) {
        /*Graphics2D g22d = (Graphics2D) g2d;

            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
            g2d.setColor(Color.BLACK);
           
        }*/
        if (start) {
            super.paintComponent(g2d);
            for (int i = 0; i < n; i++) {
                Graphics2D g22d = (Graphics2D) g2d;
                g2d.setColor(Color.blue);
                Ellipse2D.Double circle = new Ellipse2D.Double(coordinates2D[i][0], coordinates2D[i][1], 30, 30);
                g22d.fill(circle);
            }
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel OptionPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    // End of variables declaration//GEN-END:variables
}
