package vangdevolger;

import java.awt.Graphics; 
import java.util.Random;

import javax.swing.ImageIcon;

public class Vijand extends SpelElement{

private Vakje locatieVijand;
private boolean verloren = false;//Geeft aan of de vijand het spel gewonnen heeft
    
    public void vijandLocatie(Vakje vakje){
        locatieVijand = vakje;
    }//vijandLocatie
    
    public Vakje geefLocatie(){
        return locatieVijand;
    }//geefLocatie
    
    
    public boolean spelVerloren(){
        return verloren;
    }//SpelVerloren
    
    public void nieuwSpel(){
        verloren = false;
    }//nieuwSpel
    
    //Verplaatst de vijand in richting, tenzij er geen pad naar de speler is.
    //In dat geval doet de vijand random stappen.
    public void beweegVijand(int richting, int a){     
               Random randomGetal = new Random();
               if (a == 100){
               richting = randomGetal.nextInt(4);
            }//if
            Vakje buur = locatieVijand.geefBuren(richting);
                if (buur.geefInhoud() instanceof Speler)
                    verloren = true;
                else if (!(buur.geefInhoud() instanceof Blok || buur instanceof Grens || buur.geefInhoud() instanceof Box)) {
                        SpelElement vijand = locatieVijand.geefInhoud();
                        locatieVijand.wijzigInhoud(new SpelElement());
                        locatieVijand = locatieVijand.geefBuren(richting);
                        locatieVijand.wijzigInhoud(vijand);
                }//else if 
                else{
                    richting = randomGetal.nextInt(4);
                    beweegVijand(richting, 100);
                }//else
    }//beweegVijand
    
    @Override
    public void teken (Graphics g, int x, int y){
        g.drawImage(new ImageIcon(getClass().getResource("/images/vader.jpg")).getImage(), x, y, null);
    }//teken
    
}//klasse vijand
