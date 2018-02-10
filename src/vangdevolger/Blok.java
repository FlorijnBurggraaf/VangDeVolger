//Deze klasse definieert de SpelElementen die niet kunnen bewegen

package vangdevolger;

import java.awt.Graphics;
import javax.swing.ImageIcon;

public class Blok extends SpelElement {
    
    @Override
    public void teken (Graphics g, int x, int y){
        g.drawImage(new ImageIcon(getClass().getResource("/images/rock.jpg")).getImage(), x, y, null);
    }
}
