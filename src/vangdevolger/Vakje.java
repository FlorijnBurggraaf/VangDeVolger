//Deze klasse definieert de vakjes waaruit het speelveld is opgebouwd

package vangdevolger;

import javax.swing.JLabel;
import java.awt.Graphics;
import java.util.EnumMap;

public class Vakje extends JLabel {
    
    private SpelElement inhoud; 
    public boolean gebruikt = false;//Geeft aan of er al een SpelElement in dit vakje staat
    private EnumMap<buren,Vakje> rits;
    public int afstand = 100;//Afstand tot de locatie van de speler
    
    public Vakje() {
        this.rits = new EnumMap<>(buren.class);
        inhoud  = new SpelElement();
    }
    
    private enum buren{
        NOORD, OOST, ZUID, WEST
    }
    
    //Ritst de buren aan een vakje
    public void zetBuren (Vakje a, Vakje b, Vakje c, Vakje d){
        rits.put (buren.NOORD, a);
        rits.put (buren.OOST, b);
        rits.put (buren.ZUID, c);
        rits.put (buren.WEST, d);
    }//zetBuren
    
    //Geeft een specifiek vakje buren
    public Vakje geefBuren (int a){
        if (a == 0)
            return rits.get(buren.NOORD);
        if (a == 1)
            return rits.get(buren.OOST);
        if (a == 2)
            return rits.get(buren.ZUID);
        else
            return rits.get(buren.WEST);
    }//geefburen
    
    public void wijzigInhoud (SpelElement nieuw){
        inhoud = nieuw;
    }
    
    public SpelElement geefInhoud (){
        return inhoud;
    }
    
    public void tekenInhoud(Graphics g, int x, int y){
        inhoud.teken(g, x, y);
    }
}
