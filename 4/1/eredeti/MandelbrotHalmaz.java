/*
 * MandelbrotHalmaz.java
 *
 * DIGIT 2005, Javat tan�tok
 * B�tfai Norbert, nbatfai@inf.unideb.hu
 *
 */
/**
 * A Mandelbrot halmazt kisz�mol� �s kirajzol� oszt�ly.
 *
 * @author B�tfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.2
 */
public class MandelbrotHalmaz extends java.awt.Frame implements Runnable {
    /** A komplex s�k vizsg�lt tartom�nya [a,b]x[c,d]. */
    protected double a, b, c, d;
    /** A komplex s�k vizsg�lt tartom�ny�ra fesz�tett
     * h�l� sz�less�ge �s magass�ga. */
    protected int sz�less�g, magass�g;
    /** A komplex s�k vizsg�lt tartom�ny�ra fesz�tett h�l�nak megfelel� k�p.*/
    protected java.awt.image.BufferedImage k�p;
    /** Max. h�ny l�p�sig vizsg�ljuk a z_{n+1} = z_n * z_n + c iter�ci�t?
     * (tk. most a nagy�t�si pontoss�g) */
    protected int iter�ci�sHat�r = 255;
    /** Jelzi, hogy �ppen megy-e a szam�t�s? */
    protected boolean sz�m�t�sFut = false;
    /** Jelzi az ablakban, hogy �ppen melyik sort sz�moljuk. */
    protected int sor = 0;
    /** A pillanatfelv�telek sz�moz�s�hoz. */
    protected static int pillanatfelv�telSz�ml�l� = 0;
    /**
     * L�trehoz egy a Mandelbrot halmazt a komplex s�k
     * [a,b]x[c,d] tartom�nya felett kisz�mol�
     * <code>MandelbrotHalmaz</code> objektumot.
     *
     * @param      a              a [a,b]x[c,d] tartom�ny a koordin�t�ja.
     * @param      b              a [a,b]x[c,d] tartom�ny b koordin�t�ja.
     * @param      c              a [a,b]x[c,d] tartom�ny c koordin�t�ja.
     * @param      d              a [a,b]x[c,d] tartom�ny d koordin�t�ja.
     * @param      sz�less�g      a halmazt tartalmaz� t�mb sz�less�ge.
     * @param      iter�ci�sHat�r a sz�m�t�s pontoss�ga.
     */
    public MandelbrotHalmaz(double a, double b, double c, double d,
            int sz�less�g, int iter�ci�sHat�r) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.sz�less�g = sz�less�g;
        this.iter�ci�sHat�r = iter�ci�sHat�r;
        // a magass�g az (b-a) / (d-c) = sz�less�g / magass�g
        // ar�nyb�l kisz�molva az al�bbi lesz:
        this.magass�g = (int)(sz�less�g * ((d-c)/(b-a)));
        // a k�p, amire r�rajzoljuk majd a halmazt
        k�p = new java.awt.image.BufferedImage(sz�less�g, magass�g,
                java.awt.image.BufferedImage.TYPE_INT_RGB);
        // Az ablak bez�r�sakor kil�p�nk a programb�l.
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                setVisible(false);
                System.exit(0);
            }
        });
        // A billenty�zetr�l �rkez� esem�nyek feldolgoz�sa
        addKeyListener(new java.awt.event.KeyAdapter() {
            // Az 's', 'n' �s 'm' gombok lenyom�s�t figyelj�k
            public void keyPressed(java.awt.event.KeyEvent e) {
                if(e.getKeyCode() == java.awt.event.KeyEvent.VK_S)
                    pillanatfelv�tel();
                // Az 'n' gomb benyom�s�val pontosabb sz�m�t�st v�gz�nk.
                else if(e.getKeyCode() == java.awt.event.KeyEvent.VK_N) {
                    if(sz�m�t�sFut == false) {
                        MandelbrotHalmaz.this.iter�ci�sHat�r += 256;
                        // A sz�m�t�s �jra indul:
                        sz�m�t�sFut = true;
                        new Thread(MandelbrotHalmaz.this).start();
                    }
                    // Az 'm' gomb benyom�s�val pontosabb sz�m�t�st v�gz�nk,
                    // de k�zben sokkal magasabbra vessz�k az iter�ci�s
                    // hat�rt, mint az 'n' haszn�lata eset�n
                } else if(e.getKeyCode() == java.awt.event.KeyEvent.VK_M) {
                    if(sz�m�t�sFut == false) {
                        MandelbrotHalmaz.this.iter�ci�sHat�r += 10*256;
                        // A sz�m�t�s �jra indul:
                        sz�m�t�sFut = true;
                        new Thread(MandelbrotHalmaz.this).start();
                    }
                }
            }
        });
        // Ablak tulajdons�gai
        setTitle("A Mandelbrot halmaz");
        setResizable(false);
        setSize(sz�less�g, magass�g);
        setVisible(true);
        // A sz�m�t�s indul:
        sz�m�t�sFut = true;
        new Thread(this).start();
    }
    /** A halmaz aktu�lis �llapot�nak kirajzol�sa. */
    public void paint(java.awt.Graphics g) {
        // A Mandelbrot halmaz kirajzol�sa
        g.drawImage(k�p, 0, 0, this);
        // Ha �ppen fut a sz�m�t�s, akkor egy v�r�s
        // vonallal jel�lj�k, hogy melyik sorban tart:
        if(sz�m�t�sFut) {
            g.setColor(java.awt.Color.RED);
            g.drawLine(0, sor, getWidth(), sor);
        }
    }
    // Ne villogjon a fel�let (mert a "gy�ri" update()
    // lemeszeln� a v�szon fel�let�t).
    public void update(java.awt.Graphics g) {
        paint(g);
    }
    /** Pillanatfelv�telek k�sz�t�se. */
    public void pillanatfelv�tel() {
        // Az elmentend� k�p elk�sz�t�se:
        java.awt.image.BufferedImage mentK�p =
                new java.awt.image.BufferedImage(sz�less�g, magass�g,
                java.awt.image.BufferedImage.TYPE_INT_RGB);
        java.awt.Graphics g = mentK�p.getGraphics();
        g.drawImage(k�p, 0, 0, this);
        g.setColor(java.awt.Color.BLUE);
        g.drawString("a=" + a, 10, 15);
        g.drawString("b=" + b, 10, 30);
        g.drawString("c=" + c, 10, 45);
        g.drawString("d=" + d, 10, 60);
        g.drawString("n=" + iter�ci�sHat�r, 10, 75);
        g.dispose();
        // A pillanatfelv�tel k�pf�jl nev�nek k�pz�se:
        StringBuffer sb = new StringBuffer();
        sb = sb.delete(0, sb.length());
        sb.append("MandelbrotHalmaz_");
        sb.append(++pillanatfelv�telSz�ml�l�);
        sb.append("_");
        // A f�jl nev�be belevessz�k, hogy melyik tartom�nyban
        // tal�ltuk a halmazt:
        sb.append(a);
        sb.append("_");
        sb.append(b);
        sb.append("_");
        sb.append(c);
        sb.append("_");
        sb.append(d);
        sb.append(".png");
        // png form�tum� k�pet ment�nk
        try {
            javax.imageio.ImageIO.write(mentK�p, "png",
                    new java.io.File(sb.toString()));
        } catch(java.io.IOException e) {
            e.printStackTrace();
        }
    }
    /** 
     * A Mandelbrot halmaz sz�m�t�si algoritmusa.
     * Az algoritmus r�szletes ismertet�s�t l�sd p�ld�ul a
     * [BARNSLEY K�NYV] (M. Barnsley: Fractals everywhere, 
     * Academic Press, Boston, 1986) hivatkoz�sban vagy 
     * ismeretterjeszt� szinten a [CS�SZ�R K�NYV] hivatkoz�sban.
     */
    public void run() {
        // A [a,b]x[c,d] tartom�nyon milyen s�r� a
        // megadott sz�less�g, magass�g h�l�:
        double dx = (b-a)/sz�less�g;
        double dy = (d-c)/magass�g;
        double reC, imC, reZ, imZ, ujreZ, ujimZ;
        int rgb;
        // H�ny iter�ci�t csin�ltunk?
        int iter�ci� = 0;
        // V�gigzongor�zzuk a sz�less�g x magass�g h�l�t:
        for(int j=0; j<magass�g; ++j) {
            sor = j;
            for(int k=0; k<sz�less�g; ++k) {
                // c = (reC, imC) a h�l� r�cspontjainak
                // megfelel� komplex sz�m
                reC = a+k*dx;
                imC = d-j*dy;
                // z_0 = 0 = (reZ, imZ)
                reZ = 0;
                imZ = 0;
                iter�ci� = 0;
                // z_{n+1} = z_n * z_n + c iter�ci�k
                // sz�m�t�sa, am�g |z_n| < 2 vagy m�g
                // nem �rt�k el a 255 iter�ci�t, ha
                // viszont el�rt�k, akkor �gy vessz�k,
                // hogy a kiindul�ci c komplex sz�mra
                // az iter�ci� konvergens, azaz a c a
                // Mandelbrot halmaz eleme
                while(reZ*reZ + imZ*imZ < 4 && iter�ci� < iter�ci�sHat�r) {
                    // z_{n+1} = z_n * z_n + c
                    ujreZ = reZ*reZ - imZ*imZ + reC;
                    ujimZ = 2*reZ*imZ + imC;
                    reZ = ujreZ;
                    imZ = ujimZ;
                    
                    ++iter�ci�;
                    
                }
                // ha a < 4 felt�tel nem teljes�lt �s a
                // iter�ci� < iter�ci�sHat�r s�r�l�s�vel l�pett ki, azaz
                // feltessz�k a c-r�l, hogy itt a z_{n+1} = z_n * z_n + c
                // sorozat konvergens, azaz iter�ci� = iter�ci�sHat�r
                // ekkor az iter�ci� %= 256 egyenl� 255, mert az esetleges
                // nagy�tasok sor�n az iter�ci� = valah�ny * 256 + 255
                iter�ci� %= 256;
                // �gy a halmaz elemeire 255-255 �rt�ket haszn�ljuk,
                // azaz (Red=0,Green=0,Blue=0) fekete sz�nnel:
                rgb = (255-iter�ci�)|
                        ((255-iter�ci�) << 8) |
                        ((255-iter�ci�) << 16);
                // rajzoljuk a k�pre az �ppen vizsg�lt pontot:
                k�p.setRGB(k, j, rgb);
            }
            repaint();
        }
        sz�m�t�sFut = false;
    }
    /** Az aktu�lis Mandelbrot halmaz [a,b]x[c,d] adatai.
     * @return double a */
    public double getA() {
        return a;
    }
    /** Az aktu�lis Mandelbrot halmaz [a,b]x[c,d] adatai.
     * @return double b */
    public double getB() {
        return b;
    }
    /** Az aktu�lis Mandelbrot halmaz [a,b]x[c,d] adatai.
     * @return double c */
    public double getC() {
        return c;
    }
    /** Az aktu�lis Mandelbrot halmaz [a,b]x[c,d] adatai.
     * @return double d */
    public double getD() {
        return d;
    }
    /** Az aktu�lis Mandelbrot halmaz feletti r�cs adatai.
     * @return int sz�less�g */    
    public int getSz() {
        return sz�less�g;
    }
    /** Az aktu�lis Mandelbrot halmaz feletti r�cs adatai.
     * @return int magass�g */    
    public int getM() {
        return magass�g;
    }
    /** Az aktu�lis Mandelbrot halmazt tartalmaz� k�p.
     * @return BufferedImage k�p */    
    public java.awt.image.BufferedImage k�p() {
        return k�p;
    }
    /** P�ld�nyos�t egy Mandelbrot halmazt kisz�mol� obektumot. */
    public static void main(String[] args) {
        // A halmazt a komplex s�k [-2.0, .7]x[-1.35, 1.35] tartom�ny�ban
        // keress�k egy 400x400-as h�l�val:
        new MandelbrotHalmaz(-2.0, .7, -1.35, 1.35, 600, 255);
    }
}                    
