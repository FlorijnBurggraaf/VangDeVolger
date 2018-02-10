//Deze klasse representeert het speelveld

package vangdevolger;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;//Voor aanpassing van het spelniveau
import javax.swing.JPanel;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Wereld extends JPanel implements KeyListener {   
    private final int hoogte = 10;
    private final int breedte= 10;
    Vakje[][] wereld = new Vakje[breedte][hoogte]; 
    
    public final Speler speler = new Speler(); 
    private final Vijand vijand = new Vijand();
    private Vakje locatieSpeler = new Vakje();
    private Vakje locatieVijand = new Vakje();
    Random rand = new Random();//gebruikt voor het plaatsen van dozen en blokken
    private int aantalDozen = 20;
    private int aantalBlokken = 5;
    private Timer vijandTimer;     
    private int time = 1000;
    private final int ONE100_SEC = 10; 
    private int spelerAfstand = 0;

    public int wat = 0;//menukeuze

    //In de constructor wordt het array gevuld met vakjes, die vervolgens worden geritst
    public Wereld() {
        initComponents();
        new Timer(ONE100_SEC, opnieuwAfdrukken).start();  
        vijandTimer = new Timer(time, loopVijand);
        for (int i=0; i < hoogte; i++)
            for (int j = 0; j < breedte ; j++)
                wereld[i][j] = new Vakje();

        wereld[0][0].zetBuren (new Grens(), wereld[0][1],wereld[1][0], new Grens());
        wereld[0][breedte-1].zetBuren (new Grens(), new Grens(),wereld[1][breedte-1], wereld[0][breedte-2]);
        wereld[hoogte-1][0].zetBuren (wereld[hoogte-2][0], wereld[hoogte-1][1], new Grens(), new Grens());
        wereld[hoogte-1][breedte-1].zetBuren (wereld[hoogte-2][breedte-1], new Grens(), new Grens(), wereld[hoogte-1][breedte-2]);
        for (int i = 1; i < hoogte-1; i++){
            wereld [i][0].zetBuren (wereld[i-1][0], wereld[i][1],wereld[i+1][0], new Grens());
            wereld [i][breedte-1].zetBuren (wereld[i-1][breedte-1], new Grens(),wereld[i+1][breedte-1], wereld[i][breedte-2]);
            for (int j = 1; j < breedte-1; j++){
               wereld[0][j].zetBuren (new Grens(), wereld[0][j+1],wereld[1][j],wereld[0][j-1]);
               wereld[hoogte-1][j].zetBuren (wereld[hoogte-2][j], wereld[hoogte-1][j+1],new Grens(),wereld[hoogte-1][j-1]);
               wereld[i][j].zetBuren (wereld[i-1][j], wereld[i][j+1],wereld[i+1][j],wereld[i][j-1]);
            }//for
        }//for
    }//constructor
    
    //vindLocatie vult met BFS een 2D-array met getallen (<100) om zo te bepalen 
    //wat het snelste pad is van vijand naar speler.
    private void snelstePad(){
       for(int i = 0 ; i < hoogte ; i++)
           for(int j = 0; j < breedte; j++)
               wereld[i][j].afstand = 100;
       locatieSpeler = speler.geefLocatie();
       locatieSpeler.afstand = 0;
       for (int i = 0; i < 30; i++)
           vindLocatie(spelerAfstand++);
   }//snelstePad
    
    //Vult stapsgewijs het array voor vindLocatie
    private void vindLocatie(int spelerAfstand){
        locatieSpeler.afstand = 0;
        for(int i = 0 ; i < hoogte ; i++)
            for(int j = 0; j < breedte; j++)                                                   
                if (wereld[i][j].afstand == spelerAfstand-1)
                    for (int k =  0;  k < 4 ; k++){
                        Vakje buurman = wereld[i][j].geefBuren(k);
                        if (!(buurman.geefInhoud() instanceof Blok))
                            if (! (buurman instanceof Grens) && ! (buurman.geefInhoud() instanceof Box))
                                if (buurman.afstand > spelerAfstand)
                                    buurman.afstand = spelerAfstand;
                    }//for
    }//vindLocatie
    //roept de beweegfunctie van klasse Vijand aan met de juiste parameters
    ActionListener loopVijand = new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent evt){ 
            locatieVijand = vijand.geefLocatie();
            if (! (gewonnen() || vijand.spelVerloren())){
                boolean stap = false; 
                while(!stap){  
                    snelstePad(); 
                    int a = 100; 
                    int richting = 0; 
                    for (int j = 0; j < 4; j++){
                        Vakje buur = locatieVijand.geefBuren(j);
                        if (buur.afstand < 100){
                            int b = buur.afstand;
                            if (b <  a){
                                a = buur.afstand;
                                richting = j; 
                            }//if
                        }//for
                    }//for
                    vijand.beweegVijand(richting, a);
                    stap = true;
                    spelerAfstand = 0;
                }//while    
                repaint();
            }//if 
        }//actionPerformed
    };//loopVijand
    
    //Bepaalt of de spelsituatie zodanig is dat de speler gewonnen heeft
    public boolean gewonnen(){
        Vakje locatie = vijand.geefLocatie();
        int geenZet = 0;
        for (int i = 0; i < 4; i++){
            SpelElement content = locatie.geefBuren(i).geefInhoud();
            if ((content instanceof Box || content instanceof Blok || locatie.geefBuren(i) instanceof Grens))
                   geenZet++;
        }//for
        if (geenZet == 4){
            vijandTimer.stop();
            return true;
        }//if
        return false;
    }//gewonnen
    

    
    //Gebruikt bij het steeds opnieuw afdrukken van het veld
    ActionListener opnieuwAfdrukken = new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent evt){
        repaint();
        }
    };//opnieuwAfdrukken
    
    //Laat speler en vijand lopen
    public void start(){
        this.addKeyListener(speler);
        this.setFocusable(true);
        this.requestFocus();
        vijandTimer.start();
    }//start
    
    //Laat speler en vijand stoppen met lopen
    public void stop(){
        this.removeKeyListener(speler);
        vijandTimer.stop();
    }//stop
    
    //Vult de wereld met SpelElementen
    public void wereldVullen(){
        stop();
        vijandTimer = new Timer(time, loopVijand);
        start();
        vijand.nieuwSpel();
        speler.spelerLocatie(wereld[0][0]);
        vijand.vijandLocatie(wereld[9][9]);
        int teller = 0;
        for (int a = 0; a < hoogte; a++){
            for (int b = 0; b < breedte; b++){
               wereld[a][b].gebruikt = false;
               wereld[a][b].wijzigInhoud(new SpelElement());
            }//for
        }//for
        wereld[0][0].wijzigInhoud(speler);
        wereld[hoogte-1][breedte-1].wijzigInhoud(vijand);
        wereld[0][0].gebruikt = true;
        wereld[hoogte-1][breedte-1].gebruikt = true;
        while (teller < aantalDozen){
            int i = rand.nextInt(hoogte);
            int j = rand.nextInt(breedte);
            if (wereld[i][j].gebruikt == false){
                wereld[i][j].wijzigInhoud(new Box());
                wereld[i][j].gebruikt = true;
                teller++;
            }//if
        }//while
        teller = 0;
        while (teller < aantalBlokken){
            int i = rand.nextInt(hoogte);
            int j = rand.nextInt(breedte);
            if (wereld[i][j].gebruikt == false){
                wereld[i][j].wijzigInhoud(new Blok());
                wereld[i][j].gebruikt = true;
                teller++;
            }//if
        }//while
    }//WereldVullen
    
   
    @Override
    protected void paintComponent (Graphics g){ 
        super.paintComponent(g);  
        switch (wat){
            case 0: break;
            case 1: tekenWereld(g);
                    break;
            case 2: tekenWereld(g);
            default:
                    break;
        }
    }//paintComponent
    
    //Tekent het spel, het niveau-menu of de afbeeldingen voor winst en verlies
    private void tekenWereld(Graphics g){
        if (wat == 2)
                g.drawImage(new ImageIcon(getClass().getResource("/images/niveaumenu.png")).getImage(), 0, 0, null);  
        else{
            for (int i =  0; i < hoogte ; i++)
                for (int j = 0; j < breedte ; j++)
                    wereld[i][j].tekenInhoud(g, j*40, i*40);
            if (gewonnen())
                    g.drawImage(new ImageIcon(getClass().getResource("/images/yoda.jpg")).getImage(), 0, 0, null);
            if (vijand.spelVerloren())
                 g.drawImage(new ImageIcon(getClass().getResource("/images/emperor.jpg")).getImage(), 0, 0, null);
        }//else
    }//tekenWereld
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    //Hiermee wordt het spelniveau aangepast
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_1:
                aantalDozen = 30;
                aantalBlokken = 10;
                wat = 1;
                time = 1500;
                wereldVullen();
                break;
            case KeyEvent.VK_2:
                aantalDozen = 20;
                aantalBlokken = 5;
                wat = 1;
                time = 1000;
                wereldVullen();
                break;
            case KeyEvent.VK_3:
                aantalDozen = 15;
                aantalBlokken = 5;
                wat = 1;
                time = 700;
                wereldVullen();
                break;
            case KeyEvent.VK_4:
                aantalDozen = 25;
                aantalBlokken = 5;
                wat = 1;
                time = 250;
                wereldVullen();
                break;
            default:
                break;
        }//switch
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}//Klasse Wereld
