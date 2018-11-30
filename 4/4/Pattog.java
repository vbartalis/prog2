import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;


public class Pattog extends JFrame
{
  GraphicsDevice graphicsDevice;

  public Pattog(){
    GraphicsEnvironment graphicsEnvironment= java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
    graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
    fullScreen(graphicsDevice);

    addKeyListener(new KeyAdapter(){
      @Override
      public void keyPressed(KeyEvent event){
        if(event.getKeyCode() == KeyEvent.VK_ESCAPE){
          setVisible(false);
          graphicsDevice.setFullScreenWindow(null);
          System.exit(0);
        };
      }/*
      @Override
      public void keyReleased( KeyEvent keyEvent ){}*/
    });
//    draw();
  }

    int width=800, height=600;

  public void fullScreen(GraphicsDevice graphicsDevice) {

    if (graphicsDevice.isFullScreenSupported())
    {
      graphicsDevice.setFullScreenWindow(this);
      DisplayMode displayMode = graphicsDevice.getDisplayMode();
      width = displayMode.getWidth();
      height = displayMode.getHeight();
      int színMélység = displayMode.getBitDepth();
      int frissítésiFrekvencia = displayMode.getRefreshRate();
      System.out.println(width + "x"  + height + ", " + színMélység + ", " + frissítésiFrekvencia);
    }
    else {
      System.err.println("Full screen not supported");
      System.exit(1);
    }
  }

  int x;
  int y;
//körmozgásban fog a labda mozogni
  public void move() throws InterruptedException {
    double a = 0; //szög
    while(true){
      x = (int)(width / 2 + Math.cos(Math.toRadians(a)) * 200);
      y = (int)(height / 2 + Math.sin(Math.toRadians(a)) * 200);
      repaint();

      a = a + 5;
      try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
      //Thread.sleep(5);
      if(a == 360) {
        a=0;
      }
  }

  }

  public void paint(Graphics g) {
    super.paint(g);
    g.setColor(Color.red);
    g.fillOval(x-25,y-25,50,50);
    g.fillOval(width/2,height/2,10,10);
    g.drawOval(width/2 -200,height/2 -200,400,400);
    //g.fillOval(-x,y,50,50);
    //g.fillOval(x,-y,50,50);
    //g.fillOval(-1*x,-1*y,50,50);
  }


	public static void main (String[] args) throws InterruptedException
	{
    Pattog app = new Pattog();

      app.move();
      //repaint();
      //Thread.sleep(10);

  }
}
