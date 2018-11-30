/*
 * GaltonDeszka.java
 *
 * DIGIT 2005, Javat tan�tok
 * B�tfai Norbert, nbatfai@inf.unideb.hu
 *
 */
/**
 * A Galton deszka k�s�rletet szimul�l� oszt�ly.
 * A k�s�rlet le�r�s�t l�sd a [R�NYI VALS�G K�NYV] (R�nyi
 * Alfr�d: Val�sz�n�s�gsz�m�t�s, Tank�nyvkiad�, 1973, 144 o.) 
 * k�nyvben.
 *
 * @author B�tfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 */
public class GaltonDeszka extends java.awt.Frame implements Runnable {
    /** Melyik oszlopban van �ppen az es� goly�? */
    private int oszlop = 0;
    /** Melyik sorban van �ppen az es� goly�? */
    private int sor = 0;
    /** Hov� h�ny goly� esett, az i. helyre hisztogram[i] */
    private int [] hisztogram;
    /** H�ny pixel magas legyen egy deszkasor. */
    private int sorMagass�g;
    /** H�ny pixel sz�les legyen a k�s�rleti elrendez�s ablaka? */
    private int ablakSz�less�g;
    /** H�ny pixel magas legyen a k�s�rleti elrendez�s ablaka? */
    private int ablakMagass�g;
    // V�letlensz�m gener�tor
    private java.util.Random random = new java.util.Random();
    // Pillanatfelv�tel k�sz�t�s�hez
    private java.awt.Robot robot;
    /** K�sz�ts�nk pillanatfelv�telt? */
    private boolean pillanatfelv�tel = false;
    /** A pillanatfelv�telek sz�moz�s�hoz. */
    private static int pillanatfelv�telSz�ml�l� = 0;
    /**
     * L�trehoz egy Galton deszka k�s�rleti elrendez�st
     * absztrah�l� <code>GaltonDeszka</code> objektumot.
     *
     * @param      magass�g       a deszkasorok sz�ma.
     * @param      sorMagass�g    a deszkasorok magass�ga pixelben.
     */
    public GaltonDeszka(int magass�g, int sorMagass�g) {
        // Hov� h�ny goly� esett, az i. helyre hisztogram[i]
        hisztogram = new int [magass�g];
        // Null�zzuk a hisztogram elemeit (nem lenne sz�ks�ges, de ez a
        // biztons�gos taktika)
        for(int i=0; i<hisztogram.length; ++i)
            hisztogram[i] = 0;
        this.sorMagass�g = sorMagass�g;
        // Az ablak bez�r�sakor kil�p�nk a programb�l.
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                setVisible(false);
                System.exit(0);
            }
        });
        // Az s gomb benyom�s�val ki/bekapcsoljuk a 
        // pillanatfelv�tel k�sz�t�st a k�s�rletr�l:
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent e) {
                if(e.getKeyCode() == java.awt.event.KeyEvent.VK_S)
                    pillanatfelv�tel = !pillanatfelv�tel;
            }
        });
        // Pillanatfelv�tel k�sz�t�s�hez:
        try {
            robot = new java.awt.Robot(
                    java.awt.GraphicsEnvironment.
                    getLocalGraphicsEnvironment().
                    getDefaultScreenDevice());
        } catch(java.awt.AWTException e) {
            e.printStackTrace();
        }
        // Ablak tulajdons�gai
        setTitle("Galton deszka k�s�rlet");
        setResizable(false);
        ablakSz�less�g = magass�g*sorMagass�g*2;
        ablakMagass�g = magass�g*sorMagass�g+400;                
        setSize(ablakSz�less�g, ablakMagass�g);
        setVisible(true);
        // A k�s�rlet indul:
        new Thread(this).start();
    }
    /**
     * A k�s�rlet aktu�lis �llapot�nak kirajzol�sa.
     */
    public void paint(java.awt.Graphics g) {
        // A deszkasorok �s a goly� kirajzol�sa
        for(int i=0; i<hisztogram.length; ++i) {
            // Deszk�k kirajzol�sa
            g.setColor(java.awt.Color.BLACK);
            for(int j=0; j<i; ++j)
                g.fillRect(getWidth()/2
                        -((i-1)*sorMagass�g+sorMagass�g/2)
                        +j*2*sorMagass�g+sorMagass�g/3,
                        50+i*sorMagass�g, sorMagass�g/3, sorMagass�g);
            // Minden lehets�ges poz�ci�ra egy feh�r
            // goly� kirajzol�sa (t�rli a kor�bbi piros goly�kat)
            g.setColor(java.awt.Color.WHITE);
            for(int j=0; j<=i; ++j)
                g.fillArc(getWidth()/2
                        -(i*sorMagass�g+sorMagass�g/2)+j*2*sorMagass�g,
                        50+i*sorMagass�g,
                        sorMagass�g,
                        sorMagass�g, 0, 360);
            // A most �ppen al�hull� goly� kirajzol�sa
            if(i == sor) {
                g.setColor(java.awt.Color.RED);
                g.fillArc(getWidth()/2
                        -(i*sorMagass�g+sorMagass�g/2)+oszlop*2*sorMagass�g,
                        50+i*sorMagass�g, sorMagass�g, sorMagass�g, 0, 360);
            }
        }
        // A hisztogram kirajzol�sa
        g.setColor(java.awt.Color.GREEN);
        for(int j=0; j<hisztogram.length; ++j)
            g.fillRect(getWidth()/2
                    -((hisztogram.length-1)*sorMagass�g
                    +sorMagass�g/2)+j*2*sorMagass�g,
                    50+hisztogram.length*sorMagass�g,
                    sorMagass�g, sorMagass�g*hisztogram[j]/10);
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
                    ablakSz�less�g, ablakMagass�g)));                
        }
    }
    // Ne villogjon a fel�let (mert a "gy�ri" update()
    // lemeszeln� a v�szon fel�let�t).
    public void update(java.awt.Graphics g) {
        paint(g);
    }
    /**
     * Pillanatfelv�telek k�sz�t�se.
     */
    public void pillanatfelv�tel(java.awt.image.BufferedImage felvetel) {
        
        // A pillanatfelv�tel k�p f�jlneve
        StringBuffer sb = new StringBuffer();
        sb = sb.delete(0, sb.length());
        sb.append("GaltonDeszkaKiserlet");
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
    /**
     * A k�s�rlet id�beli fejl�d�s�nek vez�rl�se.
     */
    public void run() {
        // V�gtelen ciklus, azaz v�gtelen sok goly�t
        // dobunk le a deszk�k k�z�tt.
        while(true) {
            // Kezdetben a goly� a legfels� deszka felett.
            oszlop = 0;
            // A ciklus minden iter�ci�ja egy deszkasornyi
            // es�st jelent a goly� �let�ben
            for(int i=0; i<hisztogram.length; ++i) {
                // Melyik sorban van �ppen az es� goly�?
                sor = i;
                // Ha n�velni akarjuk a sebess�get (a
                // l�tv�ny rov�s�ra) akkor kommentezz�k be
                // ezt a v�rakoz� try blokkot (de ekkor
                // ne felejts�k el a hisztogram oszlopainak
                // magass�g�t sorMagass�g*hisztogram[j]/10-r�l
                // p�ld�ul sorMagass�g*hisztogram[j]/10000-re �ll�tani).
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {}
                
                // Az tetej�n a goly� az els� deszka felett
                if(i>0)
                    // ha nem a tetej�n, akkor 50%-50%, hogy
                    // jobbra vagy balra esik tov�bb.
                    // Melyik oszlopban van �ppen az es� goly�?
                    oszlop = oszlop + random.nextInt(2);
                // Rajzoljuk ki a k�s�rlet aktu�lis �llapot�t!
                repaint();
            }
            // Ha kil�p a goly� a ciklusb�l, akkor
            // v�gig esett a deszkasorokon �s valamelyik
            // t�rol�ba esett
            ++hisztogram[oszlop];
        }
    }
    /**
     * P�ld�nyos�t egy Galton deszk�s k�s�rleti
     * elrendez�s obektumot.
     */
    public static void main(String[] args) {
        // Legyen 30 sor, soronk�nt 10 pixellel
        new GaltonDeszka(30, 10);
    }
}                
