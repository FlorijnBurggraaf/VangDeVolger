//Deze klasse definieert de SpelElementen die door de speler geduwd kunnen worden

package vangdevolger;

import java.awt.Graphics;
import javax.swing.ImageIcon;

public class Box extends SpelElement {
    
    @Override
    public void teken (Graphics g, int x, int y){
        g.drawImage(new ImageIcon(getClass().getResource("/images/box.png")).getImage(), x, y, null);
    }

}
