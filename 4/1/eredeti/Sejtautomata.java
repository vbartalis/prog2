/*
 * Sejtautomata.java
 *
 * DIGIT 2005, Javat tan�tok
 * B�tfai Norbert, nbatfai@inf.unideb.hu
 *
 */
/**
 * Sejtautomata oszt�ly.
 *
 * @author B�tfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 */
public class Sejtautomata extends java.awt.Frame implements Runnable {
    /** Egy sejt lehet �l� */
    public static final boolean �L� = true;
    /** vagy halott */
    public static final boolean HALOTT = false;
    /** K�t r�csot haszn�lunk majd, az egyik a sejtt�r �llapot�t
     * a t_n, a m�sik a t_n+1 id�pillanatban jellemzi. */
    protected boolean [][][] r�csok = new boolean [2][][];
    /** Valamelyik r�csra mutat, technikai jelleg�, hogy ne kelljen a
     * [2][][]-b�l az els� dimenzi�t haszn�lni, mert vagy az egyikre
     * �ll�tjuk, vagy a m�sikra. */
    protected boolean [][] r�cs;
    /** Megmutatja melyik r�cs az aktu�lis: [r�csIndex][][] */
    protected int r�csIndex = 0;
    /** Pixelben egy cella adatai. */
    protected int cellaSz�less�g = 20;
    protected int cellaMagass�g = 20;
    /** A sejtt�r nagys�ga, azaz h�nyszor h�ny cella van? */
    protected int sz�less�g = 20;
    protected int magass�g = 10;
    /** A sejtt�r k�t egym�st k�vet� t_n �s t_n+1 diszkr�t id�pillanata
     k�z�tti val�s id�. */  
    protected int v�rakoz�s = 1000;
    // Pillanatfelv�tel k�sz�t�s�hez
    private java.awt.Robot robot;
    /** K�sz�ts�nk pillanatfelv�telt? */
    private boolean pillanatfelv�tel = false;
    /** A pillanatfelv�telek sz�moz�s�hoz. */
    private static int pillanatfelv�telSz�ml�l� = 0;
    /**
     * L�trehoz egy <code>Sejtautomata</code> objektumot.
     *
     * @param      sz�less�g    a sejtt�r sz�less�ge.
     * @param      magass�g     a sejtt�r sz�less�ge.
     */
    public Sejtautomata(int sz�less�g, int magass�g) {
        this.sz�less�g = sz�less�g;
        this.magass�g = magass�g;
        // A k�t r�cs elk�sz�t�se
        r�csok[0] = new boolean[magass�g][sz�less�g];
        r�csok[1] = new boolean[magass�g][sz�less�g];
        r�csIndex = 0;
        r�cs = r�csok[r�csIndex];
        // A kiindul� r�cs minden cell�ja HALOTT
        for(int i=0; i<r�cs.length; ++i)
            for(int j=0; j<r�cs[0].length; ++j)
                r�cs[i][j] = HALOTT;
        // A kiindul� r�csra "�l�l�nyeket" helyez�nk
        //sikl�(r�cs, 2, 2);
        sikl�Kil�v�(r�cs, 5, 60);
        // Az ablak bez�r�sakor kil�p�nk a programb�l.
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                setVisible(false);
                System.exit(0);
            }
        });
        // A billenty�zetr�l �rkez� esem�nyek feldolgoz�sa
        addKeyListener(new java.awt.event.KeyAdapter() {
            // Az 'k', 'n', 'l', 'g' �s 's' gombok lenyom�s�t figyelj�k
            public void keyPressed(java.awt.event.KeyEvent e) {
                if(e.getKeyCode() == java.awt.event.KeyEvent.VK_K) {
                    // Felez�k a cella m�reteit:
                    cellaSz�less�g /= 2;
                    cellaMagass�g /= 2;
                    setSize(Sejtautomata.this.sz�less�g*cellaSz�less�g,
                            Sejtautomata.this.magass�g*cellaMagass�g);
                    validate();
                } else if(e.getKeyCode() == java.awt.event.KeyEvent.VK_N) {
                    // Dupl�zzuk a cella m�reteit:
                    cellaSz�less�g *= 2;
                    cellaMagass�g *= 2;
                    setSize(Sejtautomata.this.sz�less�g*cellaSz�less�g,
                            Sejtautomata.this.magass�g*cellaMagass�g);
                    validate();
                } else if(e.getKeyCode() == java.awt.event.KeyEvent.VK_S)
                    pillanatfelv�tel = !pillanatfelv�tel;
                else if(e.getKeyCode() == java.awt.event.KeyEvent.VK_G)
                    v�rakoz�s /= 2;
                else if(e.getKeyCode() == java.awt.event.KeyEvent.VK_L)
                    v�rakoz�s *= 2;
                repaint();
            }
        });
        // Eg�r kattint� esem�nyek feldolgoz�sa:
        addMouseListener(new java.awt.event.MouseAdapter() {
            // Eg�r kattint�ssal jel�lj�k ki a nagy�tand� ter�letet
            // bal fels� sark�t vagy ugyancsak eg�r kattint�ssal
            // vizsg�ljuk egy adott pont iter�ci�it:
            public void mousePressed(java.awt.event.MouseEvent m) {
                // Az eg�rmutat� poz�ci�ja
                int x = m.getX()/cellaSz�less�g;
                int y = m.getY()/cellaMagass�g;
                r�csok[r�csIndex][y][x] = !r�csok[r�csIndex][y][x];
                repaint();
            }
        });
        // Eg�r mozg�s esem�nyek feldolgoz�sa:
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            // Vonszol�ssal jel�lj�k ki a n�gyzetet:
            public void mouseDragged(java.awt.event.MouseEvent m) {
                int x = m.getX()/cellaSz�less�g;
                int y = m.getY()/cellaMagass�g;
                r�csok[r�csIndex][y][x] = �L�;
                repaint();
            }
        });
        // Cellam�retek kezdetben
        cellaSz�less�g = 10;
        cellaMagass�g = 10;
        // Pillanatfelv�tel k�sz�t�s�hez:
        try {
            robot = new java.awt.Robot(
                    java.awt.GraphicsEnvironment.
                    getLocalGraphicsEnvironment().
                    getDefaultScreenDevice());
        } catch(java.awt.AWTException e) {
            e.printStackTrace();
        }
        // A program ablak�nak adatai:
        setTitle("Sejtautomata");
        setResizable(false);
        setSize(sz�less�g*cellaSz�less�g,
                magass�g*cellaMagass�g);
        setVisible(true);
        // A sejtt�r �letrekelt�se:
        new Thread(this).start();
    }
    /** A sejtt�r kirajzol�sa. */
    public void paint(java.awt.Graphics g) {
        // Az aktu�lis
        boolean [][] r�cs = r�csok[r�csIndex];
        // r�csot rajzoljuk ki:
        for(int i=0; i<r�cs.length; ++i) { // v�gig l�pked a sorokon
            for(int j=0; j<r�cs[0].length; ++j) { // s az oszlopok
                // Sejt cella kirajzol�sa
                if(r�cs[i][j] == �L�)
                    g.setColor(java.awt.Color.BLACK);
                else
                    g.setColor(java.awt.Color.WHITE);
                g.fillRect(j*cellaSz�less�g, i*cellaMagass�g,
                        cellaSz�less�g, cellaMagass�g);
                // R�cs kirajzol�sa
                g.setColor(java.awt.Color.LIGHT_GRAY);
                g.drawRect(j*cellaSz�less�g, i*cellaMagass�g,
                        cellaSz�less�g, cellaMagass�g);
            }
        }
        // K�sz�t�nk pillanatfelv�telt?
        if(pillanatfelv�tel) {
            // a biztons�g kedv��rt egy k�p k�sz�t�se ut�n
            // kikapcsoljuk a pillanatfelv�telt, hogy a
            // programmal ismerked� Olvas� ne �rja tele a
            // f�jlrendszer�t a pillanatfelv�telekkel
            pillanatfelv�tel = false;
            pillanatfelv�tel(robot.createScreenCapture
                    (new java.awt.Rectangle
                    (getLocation().x, getLocation().y,
                    sz�less�g*cellaSz�less�g,
                    magass�g*cellaMagass�g)));
        }
    }
    /**
     * Az k�rdezett �llapotban l�v� nyolcszomsz�dok sz�ma.
     *
     * @param   r�cs    a sejtt�r r�cs
     * @param   sor     a r�cs vizsg�lt sora
     * @param   oszlop  a r�cs vizsg�lt oszlopa
     * @param   �llapor a nyolcszomsz�dok vizsg�lt �llapota
     * @return int a k�rdezett �llapotbeli nyolcszomsz�dok sz�ma.
     */
    public int szomsz�dokSz�ma(boolean [][] r�cs,
            int sor, int oszlop, boolean �llapot) {        
        int �llapot�Szomsz�d = 0;
        // A nyolcszomsz�dok v�gigzongor�z�sa:
        for(int i=-1; i<2; ++i)
            for(int j=-1; j<2; ++j)
                // A vizsg�lt sejtet mag�t kihagyva:
                if(!((i==0) && (j==0))) {
            // A sejtt�rb�l sz�l�nek szomsz�dai
            // a szembe oldalakon ("peri�dikus hat�rfelt�tel")
            int o = oszlop + j;
            if(o < 0)
                o = sz�less�g-1;
            else if(o >= sz�less�g)
                o = 0;
            
            int s = sor + i;
            if(s < 0)
                s = magass�g-1;
            else if(s >= magass�g)
                s = 0;
            
            if(r�cs[s][o] == �llapot)
                ++�llapot�Szomsz�d;
                }
        
        return �llapot�Szomsz�d;
    }
    /**
     * A sejtt�r id�beli fejl�d�se a John H. Conway f�le
     * �letj�t�k sejtautomata szab�lyai alapj�n t�rt�nik.
     * A szab�lyok r�szletes ismertet�s�t l�sd p�ld�ul a
     * [MATEK J�T�K] hivatkoz�sban (Cs�k�ny B�la: Diszkr�t
     * matematikai j�t�kok. Polygon, Szeged 1998. 171. oldal.)
     */
    public void id�Fejl�d�s() {
        
        boolean [][] r�csEl�tte = r�csok[r�csIndex];
        boolean [][] r�csUt�na = r�csok[(r�csIndex+1)%2];
        
        for(int i=0; i<r�csEl�tte.length; ++i) { // sorok
            for(int j=0; j<r�csEl�tte[0].length; ++j) { // oszlopok
                
                int �l�k = szomsz�dokSz�ma(r�csEl�tte, i, j, �L�);
                
                if(r�csEl�tte[i][j] == �L�) {
                /* �l� �l� marad, ha kett� vagy h�rom �l�
                 szomszedja van, k�l�nben halott lesz. */
                    if(�l�k==2 || �l�k==3)
                        r�csUt�na[i][j] = �L�;
                    else
                        r�csUt�na[i][j] = HALOTT;
                }  else {
                /* Halott halott marad, ha h�rom �l�
                 szomszedja van, k�l�nben �l� lesz. */
                    if(�l�k==3)
                        r�csUt�na[i][j] = �L�;
                    else
                        r�csUt�na[i][j] = HALOTT;
                }
            }
        }
        r�csIndex = (r�csIndex+1)%2;
    }
    /** A sejtt�r id�beli fejl�d�se. */
    public void run() {
        
        while(true) {
            try {
                Thread.sleep(v�rakoz�s);
            } catch (InterruptedException e) {}
            
            id�Fejl�d�s();
            repaint();
        }
    }
    /**
     * A sejtt�rbe "�l�l�nyeket" helyez�nk, ez a "sikl�".
     * Adott ir�nyban halad, m�solja mag�t a sejtt�rben.
     * Az �l�l�ny ismertet�s�t l�sd p�ld�ul a
     * [MATEK J�T�K] hivatkoz�sban (Cs�k�ny B�la: Diszkr�t
     * matematikai j�t�kok. Polygon, Szeged 1998. 172. oldal.)
     *
     * @param   r�cs    a sejtt�r ahov� ezt az �llatk�t helyezz�k
     * @param   x       a befoglal� t�gla bal fels� sark�nak oszlopa
     * @param   y       a befoglal� t�gla bal fels� sark�nak sora
     */
    public void sikl�(boolean [][] r�cs, int x, int y) {
        
        r�cs[y+ 0][x+ 2] = �L�;
        r�cs[y+ 1][x+ 1] = �L�;
        r�cs[y+ 2][x+ 1] = �L�;
        r�cs[y+ 2][x+ 2] = �L�;
        r�cs[y+ 2][x+ 3] = �L�;
        
    }
    /**
     * A sejtt�rbe "�l�l�nyeket" helyez�nk, ez a "sikl� �gy�".
     * Adott ir�nyban sikl�kat l� ki.
     * Az �l�l�ny ismertet�s�t l�sd p�ld�ul a
     * [MATEK J�T�K] hivatkoz�sban /Cs�k�ny B�la: Diszkr�t
     * matematikai j�t�kok. Polygon, Szeged 1998. 173. oldal./,
     * de itt az �bra hib�s, egy oszloppal told m�g balra a 
     * bal oldali 4 sejtes n�gyzetet. A helyes �gy� rajz�t 
     * l�sd pl. az [�LET CIKK] hivatkoz�sban /Robert T. 
     * Wainwright: Life is Universal./ (Megeml�thetj�k, hogy
     * mindkett� tartalmaz k�t felesleges sejtet is.)
     *
     * @param   r�cs    a sejtt�r ahov� ezt az �llatk�t helyezz�k
     * @param   x       a befoglal� t�gla bal fels� sark�nak oszlopa
     * @param   y       a befoglal� t�gla bal fels� sark�nak sora
     */    
    public void sikl�Kil�v�(boolean [][] r�cs, int x, int y) {
        
        r�cs[y+ 6][x+ 0] = �L�;
        r�cs[y+ 6][x+ 1] = �L�;
        r�cs[y+ 7][x+ 0] = �L�;
        r�cs[y+ 7][x+ 1] = �L�;
        
        r�cs[y+ 3][x+ 13] = �L�;
        
        r�cs[y+ 4][x+ 12] = �L�;
        r�cs[y+ 4][x+ 14] = �L�;
        
        r�cs[y+ 5][x+ 11] = �L�;
        r�cs[y+ 5][x+ 15] = �L�;
        r�cs[y+ 5][x+ 16] = �L�;
        r�cs[y+ 5][x+ 25] = �L�;
        
        r�cs[y+ 6][x+ 11] = �L�;
        r�cs[y+ 6][x+ 15] = �L�;
        r�cs[y+ 6][x+ 16] = �L�;
        r�cs[y+ 6][x+ 22] = �L�;
        r�cs[y+ 6][x+ 23] = �L�;
        r�cs[y+ 6][x+ 24] = �L�;
        r�cs[y+ 6][x+ 25] = �L�;
        
        r�cs[y+ 7][x+ 11] = �L�;
        r�cs[y+ 7][x+ 15] = �L�;
        r�cs[y+ 7][x+ 16] = �L�;
        r�cs[y+ 7][x+ 21] = �L�;
        r�cs[y+ 7][x+ 22] = �L�;
        r�cs[y+ 7][x+ 23] = �L�;
        r�cs[y+ 7][x+ 24] = �L�;
        
        r�cs[y+ 8][x+ 12] = �L�;
        r�cs[y+ 8][x+ 14] = �L�;
        r�cs[y+ 8][x+ 21] = �L�;
        r�cs[y+ 8][x+ 24] = �L�;
        r�cs[y+ 8][x+ 34] = �L�;
        r�cs[y+ 8][x+ 35] = �L�;
        
        r�cs[y+ 9][x+ 13] = �L�;
        r�cs[y+ 9][x+ 21] = �L�;
        r�cs[y+ 9][x+ 22] = �L�;
        r�cs[y+ 9][x+ 23] = �L�;
        r�cs[y+ 9][x+ 24] = �L�;
        r�cs[y+ 9][x+ 34] = �L�;
        r�cs[y+ 9][x+ 35] = �L�;
        
        r�cs[y+ 10][x+ 22] = �L�;
        r�cs[y+ 10][x+ 23] = �L�;
        r�cs[y+ 10][x+ 24] = �L�;
        r�cs[y+ 10][x+ 25] = �L�;
        
        r�cs[y+ 11][x+ 25] = �L�;
        
    }
    /** Pillanatfelv�telek k�sz�t�se. */
    public void pillanatfelv�tel(java.awt.image.BufferedImage felvetel) {
        // A pillanatfelv�tel k�p f�jlneve
        StringBuffer sb = new StringBuffer();
        sb = sb.delete(0, sb.length());
        sb.append("sejtautomata");
        sb.append(++pillanatfelv�telSz�ml�l�);
        sb.append(".png");
        // png form�tum� k�pet ment�nk
        try {
            javax.imageio.ImageIO.write(felvetel, "png",
                    new java.io.File(sb.toString()));
        } catch(java.io.IOException e) {
            e.printStackTrace();
        }
    }
    // Ne villogjon a fel�let (mert a "gy�ri" update()
    // lemeszeln� a v�szon fel�let�t).    
    public void update(java.awt.Graphics g) {
        paint(g);
    }    
    /**
     * P�ld�nyos�t egy Conway-f�le �letj�t�k szab�lyos
     * sejtt�r obektumot.
     */    
    public static void main(String[] args) {
        // 100 oszlop, 75 sor m�rettel:
        new Sejtautomata(100, 75);
    }
}                
