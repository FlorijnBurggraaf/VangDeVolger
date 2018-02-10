//Deze klasse vormt de main-class, en reageert op het indrukken van de buttons

package vangdevolger;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Spel extends javax.swing.JFrame{
    private int pauzeTeller;//Voor pauze/herstart
   
    public Spel() {
        initComponents();
        pauzeTeller = 0;
        geluidAfspelen();
    }//Constructor
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Start = new javax.swing.JButton();
        Niveau = new javax.swing.JButton();
        Pauze = new javax.swing.JButton();
        wereld = new vangdevolger.Wereld();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(550, 460));

        Start.setText("Start");
        Start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StartActionPerformed(evt);
            }
        });

        Niveau.setText("Niveau");
        Niveau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NiveauActionPerformed(evt);
            }
        });

        Pauze.setText("Pauze/Herstart");
        Pauze.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PauzeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout wereldLayout = new javax.swing.GroupLayout(wereld);
        wereld.setLayout(wereldLayout);
        wereldLayout.setHorizontalGroup(
            wereldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        wereldLayout.setVerticalGroup(
            wereldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 401, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Start)
                    .addComponent(Pauze)
                    .addComponent(Niveau))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(wereld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Start)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Pauze)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Niveau))
                    .addComponent(wereld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NiveauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NiveauActionPerformed
        wereld.removeKeyListener(wereld.speler);
        wereld.addKeyListener(wereld);
        wereld.requestFocus();
        wereld.wat = 2;
    }//GEN-LAST:event_NiveauActionPerformed
    
    private void StartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StartActionPerformed
        wereld.wat = 1;
        wereld.removeKeyListener(wereld.speler);
        do{
            wereld.wereldVullen();
        }while (wereld.gewonnen());
        wereld.setVisible(true);
        wereld.setFocusable(true);
        wereld.requestFocus(); 
    }//GEN-LAST:event_StartActionPerformed

    private void PauzeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PauzeActionPerformed
        pauzeTeller++;
        if (pauzeTeller % 2 == 0){
            wereld.stop();
            wereld.start();
        }
        else
            wereld.stop();
    }//GEN-LAST:event_PauzeActionPerformed
    
    public void geluidAfspelen() {
        try {
            AudioInputStream i = AudioSystem.getAudioInputStream(new File("starwars.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip(null);
            clip.open(i);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch(Exception ex) {
            System.out.println("Kan het geluid niet afspelen.");
            ex.printStackTrace();
        }
    }//geluidAfspelen
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Spel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Spel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Spel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Spel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //Wereld wereld;
       // wereld.tekenWereld(g);
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Spel().setVisible(true);
            }
        });
    }//main

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Niveau;
    private javax.swing.JButton Pauze;
    private javax.swing.JButton Start;
    private vangdevolger.Wereld wereld;
    // End of variables declaration//GEN-END:variables
}
