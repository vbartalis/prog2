/*
 * MandelbrotIter�ci�k.java
 *
 * DIGIT 2005, Javat tan�tok
 * B�tfai Norbert, nbatfai@inf.unideb.hu
 *
 */
/**
 * A nagy�tott Mandelbrot halmazok adott pontj�ban k�pes
 * nyomonk�vetni az z_{n+1} = z_n * z_n + c iter�ci�t.
 *
 * @author B�tfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 */
public class MandelbrotIter�ci�k implements Runnable{
    /** Mennyi id�t v�rakozzunk k�t iter�ci� bemutat�sa k�z�tt? */
    private int v�rakoz�s;
    // Kiss� igaz redund�nsan, s nem sz�pen, de k�nyelmesen:
    private MandelbrotHalmazNagy�t� nagy�t�;
    private int j, k;
    private double a, b, c, d;
    private  int sz�less�g, magass�g;
    private java.awt.image.BufferedImage k�p;
    /**
     * L�trehoz egy iter�ci�kat vizsg�l� <code>MandelbrotIter�ci�k</code>
     * sz�l objektumot egy adott <code>MandelbrotHalmaznagy�t�</code>
     * objektumhoz.
     *
     * @param      nagy�t�      egy <code>MandelbrotHalmazNagy�t�</code> objektum
     * @param      v�rakoz�s    v�rakoz�si id�
     */
    public MandelbrotIter�ci�k(MandelbrotHalmazNagy�t� nagy�t�, int v�rakoz�s) {        
        this.nagy�t� = nagy�t�;
        this.v�rakoz�s = v�rakoz�s;
        j = nagy�t�.getY();
        k = nagy�t�.getX();
        a = nagy�t�.getA();
        b = nagy�t�.getB();
        c = nagy�t�.getC();
        d = nagy�t�.getD();
        k�p = nagy�t�.k�p();
        sz�less�g  = nagy�t�.getSz();
        magass�g = nagy�t�.getM();
    }
    /** Az vizsg�lt pontb�l indul� iter�ci�k bemutat�sa. */
    public void run() {
        /* Az al�bbi k�d javar�szt a MandelbrotHalmaz.java sz�mol�st 
         v�gz� run() m�dszer�b�l sz�rmazik, hiszen ugyanazt csin�ljuk,
         csak most nem a h�l�n megy�nk v�gig, hanem a h�l� adott a
         p�ld�nyos�t�sunkkor az eg�rmutat� mutatta csom�pontj�ban (ennek
         felel meg a c kompelx sz�m) sz�molunk, teh�t a k�t k�ls� for 
         ciklus nem kell. */
        // A [a,b]x[c,d] tartom�nyon milyen s�r� a
        // megadott sz�less�g, magass�g h�l�:
        double dx = (b-a)/sz�less�g;
        double dy = (d-c)/magass�g;
        double reC, imC, reZ, imZ, ujreZ, ujimZ;
        // H�ny iter�ci�t csin�ltunk?
        int iter�ci� = 0;
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
        while(reZ*reZ + imZ*imZ < 4 && iter�ci� < 255) {
            // z_{n+1} = z_n * z_n + c
            ujreZ = reZ*reZ - imZ*imZ + reC;
            ujimZ = 2*reZ*imZ + imC;
         
            // az iter�ci� (reZ, imZ) -> (ujreZ, ujimZ)
            // ezt az egyenest kell kirajzolnunk, de most
            // a komplex sz�mokat vissza kell transzform�lnunk
            // a r�cs oszlop, sor koordin�t�j�v�:
            java.awt.Graphics g = k�p.getGraphics();
            g.setColor(java.awt.Color.WHITE);
            g.drawLine(
                    (int)((reZ - a)/dx),
                    (int)((d - imZ)/dy),
                    (int)((ujreZ - a)/dx),
                    (int)((d - ujimZ)/dy)
                    );
            g.dispose();
            nagy�t�.repaint();
            
            reZ = ujreZ;
            imZ = ujimZ;
            
            ++iter�ci�;
            // V�rakozunk, hogy k�zben csod�lhassuk az iter�ci�
            // l�tv�ny�t:
            try {
                Thread.sleep(v�rakoz�s);
            } catch (InterruptedException e) {}
        }
    }    
}                    
