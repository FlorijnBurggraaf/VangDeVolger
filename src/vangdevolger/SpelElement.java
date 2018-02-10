//De ouderklasse van de SpelElementen

package vangdevolger;

import java.awt.Graphics;
import javax.swing.ImageIcon;

public class SpelElement {
    
    //Lege vakjes worden getekend als gras
    public void teken (Graphics g, int x, int y){
        g.drawImage(new ImageIcon(getClass().getResource("/images/grass.jpg")).getImage(), x, y, null);  
    }
}
