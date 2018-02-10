//Deze functie vangt de invoer van de pijltjestoetsen af en verplaatst 
//en tekent de speler.

package vangdevolger;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Speler extends SpelElement implements KeyListener {
    
   private Vakje locatieSpeler;//Het vakje waar de speler zich bevindt
  
    public void spelerLocatie(Vakje vakje){
        locatieSpeler = vakje;
    }//spelerLocatie
    
    public Vakje geefLocatie(){
        return locatieSpeler;
    }//geefLocatie
    
    //Verplaatst de speler naar een ander vakje
    private void beweeg(int a){
        Vakje buur = locatieSpeler.geefBuren(a);
        if (buur.geefInhoud() instanceof Box)
            schuifDozen(a, buur);
        else if (!(buur.geefInhoud() instanceof Blok) && !(buur.geefInhoud() instanceof Vijand)){
            if (! (buur instanceof Grens)) {
                SpelElement speler = locatieSpeler.geefInhoud();
                locatieSpeler.wijzigInhoud(new SpelElement());
                locatieSpeler = locatieSpeler.geefBuren(a);
                locatieSpeler.wijzigInhoud(speler);
            }//if
        }//if
    }//beweeg
    
    //Deze functie zorgt voor het verschuiven van de dozen
    private void schuifDozen(int a, Vakje buur){
        while (buur.geefInhoud() instanceof Box)
                buur = buur.geefBuren(a);
        if (! (buur.geefInhoud() instanceof Vijand || buur.geefInhoud() instanceof Blok || buur instanceof Grens)){
            SpelElement speler = locatieSpeler.geefInhoud();
            locatieSpeler.wijzigInhoud(new SpelElement());
            locatieSpeler = locatieSpeler.geefBuren(a);
            locatieSpeler.wijzigInhoud(speler);
            buur.wijzigInhoud(new Box());
        }//if
    }//schuifDozen
 
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                beweeg(1);
                break;
            case KeyEvent.VK_LEFT:
                beweeg(3);
                break;
            case KeyEvent.VK_UP:
                beweeg(0);
                break;
            case KeyEvent.VK_DOWN:
                beweeg(2);
                break;
            default:
                break;
        }  //switch
    }//KeyPressed

    @Override
    public void keyReleased(KeyEvent e) {
    }    
    
    @Override
    public void teken (Graphics g, int x, int y){
        g.drawImage(new ImageIcon(getClass().getResource("/images/speelfiguur.png")).getImage(), x, y, null);
    }//teken
}//Klasse Speler
