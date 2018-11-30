/*
 * MandelbrotHalmazNagy�t�.java
 *
 * DIGIT 2005, Javat tan�tok
 * B�tfai Norbert, nbatfai@inf.unideb.hu
 *
 */
/**
 * A Mandelbrot halmazt nagy�t� �s kirajzol� oszt�ly.
 *
 * @author B�tfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.2
 */
public class MandelbrotHalmazNagy�t� extends MandelbrotHalmaz {
    /** A nagy�tand� kijel�lt ter�letet bal fels� sarka. */
    private int x, y;
    /** A nagy�tand� kijel�lt ter�let sz�less�ge �s magass�ga. */
    private int mx, my;
    /**
     * L�trehoz egy a Mandelbrot halmazt a komplex s�k
     * [a,b]x[c,d] tartom�nya felett kisz�mol� �s nyg�tani tud�
     * <code>MandelbrotHalmazNagy�t�</code> objektumot.
     *
     * @param      a              a [a,b]x[c,d] tartom�ny a koordin�t�ja.
     * @param      b              a [a,b]x[c,d] tartom�ny b koordin�t�ja.
     * @param      c              a [a,b]x[c,d] tartom�ny c koordin�t�ja.
     * @param      d              a [a,b]x[c,d] tartom�ny d koordin�t�ja.
     * @param      sz�less�g      a halmazt tartalmaz� t�mb sz�less�ge.
     * @param      iter�ci�sHat�r a sz�m�t�s pontoss�ga.
     */
    public MandelbrotHalmazNagy�t�(double a, double b, double c, double d,
            int sz�less�g, int iter�ci�sHat�r) {
        // Az �s oszt�ly konstruktor�nak h�v�sa
        super(a, b, c, d, sz�less�g, iter�ci�sHat�r);
        setTitle("A Mandelbrot halmaz nagy�t�sai");
        // Eg�r kattint� esem�nyek feldolgoz�sa:
        addMouseListener(new java.awt.event.MouseAdapter() {
            // Eg�r kattint�ssal jel�lj�k ki a nagy�tand� ter�letet
            // bal fels� sark�t vagy ugyancsak eg�r kattint�ssal
            // vizsg�ljuk egy adott pont iter�ci�it:
            public void mousePressed(java.awt.event.MouseEvent m) {
                // Az eg�rmutat� poz�ci�ja
                x = m.getX();
                y = m.getY();
                // Az 1. eg�r gombbal a nagy�tand� ter�let kijel�l�s�t
                // v�gezz�k:
                if(m.getButton() == java.awt.event.MouseEvent.BUTTON1 ) {
                    // A nagy�tand� kijel�lt ter�letet bal fels� sarka: (x,y)
                    // �s sz�less�ge (majd a vonszol�s n�veli)
                    mx = 0;
                    my = 0;
                    repaint();
                } else {
                    // Nem az 1. eg�r gombbal az eg�rmutat� mutatta c
                    // komplex sz�mb�l ind�tott iter�ci�kat vizsg�lhatjuk
                    MandelbrotIter�ci�k iter�ci�k =
                            new MandelbrotIter�ci�k(
                            MandelbrotHalmazNagy�t�.this, 50);
                    new Thread(iter�ci�k).start();
                }
            }
            // Vonszolva kijel�l�nk egy ter�letet...
            // Ha felengedj�k, akkor a kijel�lt ter�let
            // �jrasz�m�t�sa indul:
            public void mouseReleased(java.awt.event.MouseEvent m) {
                if(m.getButton() == java.awt.event.MouseEvent.BUTTON1 ) {
                    double dx = (MandelbrotHalmazNagy�t�.this.b
                            - MandelbrotHalmazNagy�t�.this.a)
                            /MandelbrotHalmazNagy�t�.this.sz�less�g;
                    double dy = (MandelbrotHalmazNagy�t�.this.d
                            - MandelbrotHalmazNagy�t�.this.c)
                            /MandelbrotHalmazNagy�t�.this.magass�g;
                    // Az �j Mandelbrot nagy�t� objektum elk�sz�t�se:
                    new MandelbrotHalmazNagy�t�(
                            MandelbrotHalmazNagy�t�.this.a+x*dx,
                            MandelbrotHalmazNagy�t�.this.a+x*dx+mx*dx,
                            MandelbrotHalmazNagy�t�.this.d-y*dy-my*dy,
                            MandelbrotHalmazNagy�t�.this.d-y*dy,
                            600,
                            MandelbrotHalmazNagy�t�.this.iter�ci�sHat�r);
                }
            }
        });
        // Eg�r mozg�s esem�nyek feldolgoz�sa:
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            // Vonszol�ssal jel�lj�k ki a n�gyzetet:
            public void mouseDragged(java.awt.event.MouseEvent m) {
                // A nagy�tand� kijel�lt ter�let sz�less�ge �s magass�ga:
                mx = m.getX() - x;
                my = m.getY() - y;
                repaint();
            }
        });
    }
    /**
     * Pillanatfelv�telek k�sz�t�se.
     */
    public void pillanatfelv�tel() {
        // Az elmentend� k�p elk�sz�t�se:
        java.awt.image.BufferedImage mentK�p =
                new java.awt.image.BufferedImage(sz�less�g, magass�g,
                java.awt.image.BufferedImage.TYPE_INT_RGB);
        java.awt.Graphics g = mentK�p.getGraphics();
        g.drawImage(k�p, 0, 0, this);
        g.setColor(java.awt.Color.BLACK);
        g.drawString("a=" + a, 10, 15);
        g.drawString("b=" + b, 10, 30);
        g.drawString("c=" + c, 10, 45);
        g.drawString("d=" + d, 10, 60);
        g.drawString("n=" + iter�ci�sHat�r, 10, 75);
        if(sz�m�t�sFut) {
            g.setColor(java.awt.Color.RED);
            g.drawLine(0, sor, getWidth(), sor);
        }
        g.setColor(java.awt.Color.GREEN);
        g.drawRect(x, y, mx, my);
        g.dispose();
        // A pillanatfelv�tel k�pf�jl nev�nek k�pz�se:
        StringBuffer sb = new StringBuffer();
        sb = sb.delete(0, sb.length());
        sb.append("MandelbrotHalmazNagyitas_");
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
     * A nagy�tand� kijel�lt ter�letet jelz� n�gyzet kirajzol�sa.
     */
    public void paint(java.awt.Graphics g) {
        // A Mandelbrot halmaz kirajzol�sa
        g.drawImage(k�p, 0, 0, this);
        // Ha �ppen fut a sz�m�t�s, akkor egy v�r�s
        // vonallal jel�lj�k, hogy melyik sorban tart:
        if(sz�m�t�sFut) {
            g.setColor(java.awt.Color.RED);
            g.drawLine(0, sor, getWidth(), sor);
        }
        // A jelz� n�gyzet kirajzol�sa:
        g.setColor(java.awt.Color.GREEN);
        g.drawRect(x, y, mx, my);
    }
    /**
     * Hol �ll az eg�rmutat�?
     * @return int a kijel�lt pont oszlop poz�ci�ja.
     */    
    public int getX() {
        return x;
    }
    /**
     * Hol �ll az eg�rmutat�?
     * @return int a kijel�lt pont sor poz�ci�ja.
     */    
    public int getY() {
        return y;
    }
    /**
     * P�ld�nyos�t egy Mandelbrot halmazt nagy�t� obektumot.
     */
    public static void main(String[] args) {
        // A kiindul� halmazt a komplex s�k [-2.0, .7]x[-1.35, 1.35]
        // tartom�ny�ban keress�k egy 600x600-as h�l�val �s az
        // aktu�lis nagy�t�si pontoss�ggal:
        new MandelbrotHalmazNagy�t�(-2.0, .7, -1.35, 1.35, 600, 255);
    }
}                    
