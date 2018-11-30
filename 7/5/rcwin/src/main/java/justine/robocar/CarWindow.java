package justine.robocar;

class Traffic {

    public java.util.Set<org.jxmapviewer.viewer.Waypoint> waypoints;
    public String title;

    public Traffic(java.util.Set<org.jxmapviewer.viewer.Waypoint> waypoints, String title) {

        this.waypoints = waypoints;
        this.title = title;

    }

}

class WaypointPolice implements org.jxmapviewer.viewer.Waypoint {

    org.jxmapviewer.viewer.GeoPosition geoPosition;

    public WaypointPolice(double lat, double lon) {
        geoPosition = new org.jxmapviewer.viewer.GeoPosition(lat, lon);
    }

    @Override
    public org.jxmapviewer.viewer.GeoPosition getPosition() {
        return geoPosition;
    }
}

class WaypointGangster implements org.jxmapviewer.viewer.Waypoint {

    org.jxmapviewer.viewer.GeoPosition geoPosition;

    public WaypointGangster(double lat, double lon) {
        geoPosition = new org.jxmapviewer.viewer.GeoPosition(lat, lon);
    }

    @Override
    public org.jxmapviewer.viewer.GeoPosition getPosition() {
        return geoPosition;
    }
}

class WaypointCaught implements org.jxmapviewer.viewer.Waypoint {

    org.jxmapviewer.viewer.GeoPosition geoPosition;

    public WaypointCaught(double lat, double lon) {
        geoPosition = new org.jxmapviewer.viewer.GeoPosition(lat, lon);
    }

    @Override
    public org.jxmapviewer.viewer.GeoPosition getPosition() {
        return geoPosition;
    }
}

class WaypointStar implements org.jxmapviewer.viewer.Waypoint {

    org.jxmapviewer.viewer.GeoPosition geoPosition;

    public WaypointStar(double lat, double lon) {
        geoPosition = new org.jxmapviewer.viewer.GeoPosition(lat, lon);
    }

    @Override
    public org.jxmapviewer.viewer.GeoPosition getPosition() {
        return geoPosition;
    }
}

class Loc {

    double lat;
    double lon;

    public Loc(double lat, double lon) {

        this.lat = lat;
        this.lon = lon;

    }

}

public class CarWindow extends javax.swing.JFrame {

    org.jxmapviewer.viewer.WaypointPainter<org.jxmapviewer.viewer.Waypoint> waypointPainter = new org.jxmapviewer.viewer.WaypointPainter<org.jxmapviewer.viewer.Waypoint>();
    org.jxmapviewer.viewer.GeoPosition[] geopos = new org.jxmapviewer.viewer.GeoPosition[4];
    org.jxmapviewer.JXMapViewer jXMapViewer = new org.jxmapviewer.JXMapViewer();
    java.util.Map<Long, Loc> lmap = null;
    java.io.File tfile = null;
    java.util.Random rnd = new java.util.Random();
    java.util.Scanner scan = null;


    java.awt.Robot robot;

    public CarWindow(double lat, double lon, java.util.Map<Long, Loc> lmap) {

        this.lmap = lmap;

        final org.jxmapviewer.viewer.TileFactory tileFactoryArray[] = {
            new org.jxmapviewer.viewer.DefaultTileFactory( new org.jxmapviewer.OSMTileFactoryInfo()),
            new org.jxmapviewer.viewer.DefaultTileFactory( new org.jxmapviewer.VirtualEarthTileFactoryInfo(org.jxmapviewer.VirtualEarthTileFactoryInfo.MAP)),
            new org.jxmapviewer.viewer.DefaultTileFactory( new org.jxmapviewer.VirtualEarthTileFactoryInfo(org.jxmapviewer.VirtualEarthTileFactoryInfo.SATELLITE)),
            new org.jxmapviewer.viewer.DefaultTileFactory( new org.jxmapviewer.VirtualEarthTileFactoryInfo(org.jxmapviewer.VirtualEarthTileFactoryInfo.HYBRID))

        };

        org.jxmapviewer.viewer.GeoPosition debrecen = new org.jxmapviewer.viewer.GeoPosition(lat, lon);

        javax.swing.event.MouseInputListener mouseListener = new org.jxmapviewer.input.PanMouseInputListener(jXMapViewer);
        jXMapViewer.addMouseListener(mouseListener);
        jXMapViewer.addMouseMotionListener(mouseListener);
        jXMapViewer.addMouseListener( new org.jxmapviewer.input.CenterMapListener(jXMapViewer));
        jXMapViewer.addMouseWheelListener( new org.jxmapviewer.input.ZoomMouseWheelListenerCursor(jXMapViewer));
        jXMapViewer.addKeyListener( new org.jxmapviewer.input.PanKeyListener(jXMapViewer));

        jXMapViewer.setTileFactory(tileFactoryArray[0]);

        ClassLoader classLoader = this.getClass().getClassLoader();

        final java.awt.Image markerImg = new javax.swing.ImageIcon(classLoader.getResource("logo1.png")).getImage();
        final java.awt.Image markerImgPolice = new javax.swing.ImageIcon(classLoader.getResource("logo2.png")).getImage();
        final java.awt.Image markerImgGangster = new javax.swing.ImageIcon(classLoader.getResource("logo3.png")).getImage();
        final java.awt.Image markerImgCaught = new javax.swing.ImageIcon(classLoader.getResource("logo4.png")).getImage();
        final java.awt.Image markerImgStar = new javax.swing.ImageIcon(classLoader.getResource("star2.png")).getImage();

        waypointPainter.setRenderer(
                new org.jxmapviewer.viewer.WaypointRenderer<org.jxmapviewer.viewer.Waypoint>() {
                    @Override
                    public void paintWaypoint(java.awt.Graphics2D g2d, org.jxmapviewer.JXMapViewer jXMapV, org.jxmapviewer.viewer.Waypoint w) {

                        java.awt.geom.Point2D point = jXMapV.getTileFactory().geoToPixel( w.getPosition(), jXMapV.getZoom());

                        if (w instanceof WaypointPolice) {
                            g2d.drawImage(markerImgPolice, (int) point.getX() - markerImgPolice.getWidth(jXMapV), (int) point.getY() - markerImgPolice.getHeight(jXMapV), null);

                            g2d.setColor(java.awt.Color.GRAY);
                            java.awt.Rectangle rect = new java.awt.Rectangle((int) point.getX(), (int) point.getY(), 20, 20);
                            g2d.fill(rect);
                            g2d.setColor(java.awt.Color.CYAN);
                            g2d.draw(rect);

                        } else if (w instanceof WaypointGangster) {
                            g2d.drawImage(markerImgGangster, (int) point.getX() - markerImgGangster.getWidth(jXMapV), (int) point.getY() - markerImgGangster.getHeight(jXMapV), null);
                        } else if (w instanceof WaypointCaught) {
                            g2d.drawImage(markerImgCaught, (int) point.getX() - markerImgCaught.getWidth(jXMapV), (int) point.getY() - markerImgCaught.getHeight(jXMapV), null);
                        } else if (w instanceof WaypointStar) {
                            g2d.drawImage(markerImgStar, (int) point.getX() - markerImgStar.getWidth(jXMapV), (int) point.getY() - markerImgStar.getHeight(jXMapV), null);
                        } else {
                            g2d.drawImage(markerImg, (int) point.getX() - markerImg.getWidth(jXMapV), (int) point.getY() - markerImg.getHeight(jXMapV), null);
                        }
                    }
                });

        jXMapViewer.setOverlayPainter(waypointPainter);
        jXMapViewer.setZoom(7);
        jXMapViewer.setAddressLocation(debrecen);
        jXMapViewer.setCenterPosition(debrecen);
        
        jXMapViewer.addKeyListener(new java.awt.event.KeyAdapter() {
            int index = 0;

            public void keyPressed(java.awt.event.KeyEvent evt) {

                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_SPACE) {
                    jXMapViewer.setTileFactory(tileFactoryArray[++index % 4]);
                    jXMapViewer.repaint();
                    repaint();
                } else if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_S) {
                    jXMapViewer.repaint();
                    repaint();

                    shootScreenshot(robot.createScreenCapture(new java.awt.Rectangle(
                            getLocation().x, getLocation().y,
                            getSize().width, getSize().height)));

                }

            }
        });

        setTitle("7.hét 5.feladat - OSM térképre rajzolása");
        getContentPane().add(jXMapViewer);

        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

        try {
            robot = new java.awt.Robot( java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
        } catch (java.awt.AWTException e) {
            java.util.logging.Logger.getLogger( CarWindow.class.getName()).log(java.util.logging.Level.SEVERE, "Nem lesz pillanatfelvétel...", e);
        }
        
		java.util.Set<org.jxmapviewer.viewer.Waypoint> waypoints = new java.util.HashSet<org.jxmapviewer.viewer.Waypoint>();
		int i=0;
		for (java.util.Map.Entry<Long, Loc> Lmap : lmap.entrySet()) {
		//	waypoints.add(new WaypointPolice(lat, lon));
		//	waypoints.add(new WaypointGangster(lat, lon));
		//	waypoints.add(new WaypointCaught(lat, lon));
			waypoints.add(new WaypointStar(Lmap.getValue().lat, Lmap.getValue().lon));
		//	waypoints.add(new org.jxmapviewer.viewer.DefaultWaypoint(lat, lon));
		}
		waypointPainter.setWaypoints(waypoints);
    }

    public void shootScreenshot(java.awt.image.BufferedImage mapview) {

        StringBuffer sb = new StringBuffer();
        sb = sb.delete(0, sb.length());
        sb.append("OOCWC-");
        sb.append(new java.util.Date());
        sb.append(".png");

        try {
            javax.imageio.ImageIO.write(mapview, "png", new java.io.File(sb.toString()));
        } catch (java.io.IOException e) {
            java.util.logging.Logger.getLogger( CarWindow.class.getName()).log(java.util.logging.Level.SEVERE, "Pillanatfelvétel hiba...", e);
        }
    }

    public static void readMap(java.util.Map<Long, Loc> lmap, String name) {

        java.util.Scanner scan;
        java.io.File file = new java.io.File(name);

        long ref = 0;
        double lat = 0.0, lon = 0.0;
        try {
            scan = new java.util.Scanner(file).useLocale(java.util.Locale.US);

            while (scan.hasNext()) {

                ref = scan.nextLong();
                lat = scan.nextDouble();
                lon = scan.nextDouble();

                lmap.put(ref, new Loc(lat, lon));
            }
        } catch (Exception e) {
            java.util.logging.Logger.getLogger( CarWindow.class.getName()).log(java.util.logging.Level.SEVERE, "hibás noderef2GPS leképezés", e);
        }
    }

    public static void main(String[] args) {

        final java.util.Map<Long, Loc> lmap = new java.util.HashMap<Long, Loc>();
        if (args.length == 1) {
            readMap(lmap, args[0]);
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    java.util.Map.Entry<Long, Loc> e = lmap.entrySet().iterator().next();
                    new CarWindow(e.getValue().lat, e.getValue().lon, lmap).setVisible(true);
                }
            });
        } else {

            System.out.println("java -jar target/site/justine-rcwin-0.0.16-jar-with-dependencies.jar ../../../lmap.txt localhost");
        }
    }
}
