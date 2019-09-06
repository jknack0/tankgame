import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.nio.Buffer;
import java.util.ArrayList;
import java.awt.Font;

import static javax.imageio.ImageIO.read;

public class Arena extends JPanel {
    public static final int SCREEN_WIDTH = 900;
    public static final int SCREEN_HEIGHT = 700;
    public static final int MAP_WIDTH = 2000;
    public static final int MAP_HEIGHT = 1500;
    private BufferedImage world;
    private Graphics2D buffer;
    private JFrame jf;
    private Tank t1;
    private Tank t2;
    private TankController tc1;
    private TankController tc2;
    private BufferedImage t1camera;
    private BufferedImage t2camera;
    private BufferedImage mapBackdrop;
    private BufferedImage regWall;
    private ArrayList<Wall> gameWalls;
    private BufferedImage breakWall;
    private BufferedImage powerUp;
    private ArrayList<PowerUp> powerUps;
    private BufferedImage t1img = null, t2img = null;
    private BufferedImage livesImg;
    static BufferedImage bulletImg;
    static ArrayList<Bullet> gameBullets;
    private ArrayList<Tank> gameTanks;


    public static void main(String[] args) {
        Thread x;
        Arena trex = new Arena();
        trex.init();
        try {

            while (true) {
                trex.t1.update();
                trex.t2.update();
                trex.repaint();
                Thread.sleep(1000 / 144);
            }
        } catch (InterruptedException ignored) {

        }

    }


    private void init() {
        this.jf = new JFrame("Tank Wars");
        this.world = new BufferedImage(MAP_WIDTH, MAP_HEIGHT, BufferedImage.TYPE_INT_RGB);


        try {
            BufferedImage tmp;
            System.out.println(System.getProperty("user.dir"));
            /*
             * note class loaders read files from the out folder (build folder in netbeans) and not the
             * current working directory.
             */
            t1img = read(new File("resources/Tank1.gif"));
            t2img = read(new File("resources/Tank2.gif"));
            mapBackdrop = read(new File("resources/Background.bmp"));
            regWall = read(new File("resources/Wall1.gif"));
            breakWall = read(new File("resources/Wall2.gif"));
            powerUp = read(new File("resources/bouncing.gif"));
            livesImg = read(new File("resources/icon.png"));
            bulletImg = read(new File("resources/shell.gif"));



        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        t1 = new Tank(100 , 100, 0, 0, 3, 100,0, t1img, 1);
        t2 = new Tank(1900,1400,0,0,3, 100,180, t2img, 2);

        tc1 = new TankController(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        tc2 = new TankController(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);


        this.jf.setLayout(new BorderLayout());
        this.jf.add(this);

        this.jf.addKeyListener(tc1);
        this.jf.addKeyListener(tc2);

        this.jf.setSize(Arena.SCREEN_WIDTH, Arena.SCREEN_HEIGHT + 30);
        this.jf.setResizable(false);
        jf.setLocationRelativeTo(null);

        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jf.setVisible(true);

        gameBullets = new ArrayList<>();
        powerUps = new ArrayList<>();
        gameWalls = new ArrayList<>();
        for(int i= 240; i < 1800; i+=32) {
            gameWalls.add(new RegularWall(i,718, regWall));
            gameWalls.add(new RegularWall(i,750, regWall));
            gameWalls.add(new RegularWall(i, 782, regWall));
        }
        for(int i = 200;i < 500; i+=32){
            gameWalls.add(new RegularWall(1000, i, regWall));
            gameWalls.add(new RegularWall(1032, i, regWall));
            gameWalls.add(new RegularWall(1064, i, regWall));
        }
        for(int i =1000; i < 1300; i +=32){
            gameWalls.add(new RegularWall(1000, i, regWall));
            gameWalls.add(new RegularWall(1032, i, regWall));
            gameWalls.add(new RegularWall(1064, i, regWall));
        }
        for(int i=300;i< 460; i+=32){
            gameWalls.add(new BreakableWall(436,i, breakWall, true ));
            gameWalls.add(new BreakableWall(468,i, breakWall, true ));
            gameWalls.add(new BreakableWall(500,i, breakWall, true ));
            gameWalls.add(new BreakableWall(532,i, breakWall, true ));
            gameWalls.add(new BreakableWall(564,i, breakWall, true ));
        }
        powerUps.add(new PowerUp(500, 350, powerUp, true));
        for(int i=1050;i< 1210; i+=32){
            gameWalls.add(new BreakableWall(436,i, breakWall, true ));
            gameWalls.add(new BreakableWall(468,i, breakWall, true ));
            gameWalls.add(new BreakableWall(500,i, breakWall, true ));
            gameWalls.add(new BreakableWall(532,i, breakWall, true ));
            gameWalls.add(new BreakableWall(564,i, breakWall, true ));
        }
        powerUps.add(new PowerUp(500, 1100, powerUp, true));
        for(int i=300;i< 460; i+=32){
            gameWalls.add(new BreakableWall(1436,i, breakWall, true ));
            gameWalls.add(new BreakableWall(1468,i, breakWall, true ));
            gameWalls.add(new BreakableWall(1500,i, breakWall, true ));
            gameWalls.add(new BreakableWall(1532,i, breakWall, true ));
            gameWalls.add(new BreakableWall(1564,i, breakWall, true ));
        }
        powerUps.add(new PowerUp(1500, 350, powerUp, true));
        for(int i=1050;i< 1210; i+=32){
            gameWalls.add(new BreakableWall(1436,i, breakWall, true ));
            gameWalls.add(new BreakableWall(1468,i, breakWall, true ));
            gameWalls.add(new BreakableWall(1500,i, breakWall, true ));
            gameWalls.add(new BreakableWall(1532,i, breakWall, true ));
            gameWalls.add(new BreakableWall(1564,i, breakWall, true ));
        }
        powerUps.add(new PowerUp(1500, 1100, powerUp, true));
        gameTanks = new ArrayList<>();
        gameTanks.add(t1);
        gameTanks.add(t2);





    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;


        buffer = world.createGraphics();

        super.paintComponent(g2);

        //buffer.setColor(Color.BLUE);
       //  buffer.fillRect(0,0, MAP_WIDTH, MAP_HEIGHT);
        buffer.drawImage(mapBackdrop,0,0,null);
        buffer.drawImage(mapBackdrop,1000,0,null);
        buffer.drawImage(mapBackdrop,0,750,null);
        buffer.drawImage(mapBackdrop,1000,750,null);
        this.jf.revalidate();
        this.jf.repaint();

        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);
        for(PowerUp powerUp: powerUps)
            powerUp.drawImage(buffer);
        for(Wall wall: gameWalls)
            wall.drawImage(buffer);
        for(Bullet bullet: gameBullets)
            bullet.drawImage(buffer);
        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);


        t1camera = world.getSubimage(t1.getSubimageX(), t1.getSubimageY() , 445, 500);
        t2camera = world.getSubimage(t2.getSubimageX() , t2.getSubimageY(), 450, 500);
        g2.drawImage(t1camera,0,0,null);
        g2.drawImage(t2camera, 450, 0, null);
        g2.drawImage(world,350, 500,200,205,null );
        g2.setColor(Color.BLACK);
        g2.fillRect(0,500,350,210);
        g2.fillRect(550, 500, 350, 210);
        g2.setFont(new Font("TimesRoman",Font.PLAIN, 30));
        g2.setColor(Color.RED);
        g2.drawString("TANK 1", 110, 550);
        g2.setColor(Color.white);
        g2.drawString("LIVES:", 10, 600);
        for(int i = 110; i < t1.getLives() * 40 + 110; i += 40 )
            g2.drawImage(livesImg, i, 575, null);
        g2.drawString("Health:", 10, 660);
        g2.setColor(Color.RED);
        g2.drawRect(120, 635, 200, 30);
        g2.fillRect(120, 635, t1.getHealth() * 2, 30);
        g2.setColor(Color.BLUE);
        g2.drawString("TANK 2", 660, 550);
        g2.setColor(Color.white);
        g2.drawString("LIVES:", 563, 600);
        for(int i = 665; i < t2.getLives() * 40 + 665; i += 40 )
            g2.drawImage(livesImg, i, 575, null);
        g2.drawString("Health:", 563, 660);
        g2.setColor(Color.BLUE);
        g2.drawRect(673, 635, 200, 30);
        g2.fillRect(673, 635, t2.getHealth() * 2, 30);

        handleCollisions();
        gameOver(g2);
    }

    public void handleCollisions(){
        for(Bullet b: gameBullets){
            for(Wall w: gameWalls){
               if(w instanceof BreakableWall){
                   if(b.getBounds().intersects(((BreakableWall)w).getBounds()) && ((BreakableWall)w).isVisible()){
                       b.collision();
                       ((BreakableWall)w).collision();
                   }
               } else {
                   if(b.getBounds().intersects(((RegularWall)w).getBounds())){
                       b.collision();
                   }
               }
            }
        }

        for(Tank t: gameTanks){
            for(Bullet b: gameBullets){
                if(t.getBounds().intersects(b.getBounds()) && t.getId() != b.getId() && b.isVis()   ){
                    b.collision();
                    t.bulletCollision();
                    if(t.getHealth() <= 0) {
                        t.sethealth();
                        t.loseLive();
                    }
                }
            }
        }

        for(Tank t: gameTanks){
            for(PowerUp p: powerUps) {
                if(t.getBounds().intersects(p.getBounds()) && p.isvisible()){
                    t.getLive();
                    p.collision();
                }
            }
        }

        for(Tank t: gameTanks){
            for(Wall w: gameWalls){
                if(w instanceof BreakableWall){
                    if(t.getBounds().intersects(((BreakableWall)w).getBounds()) && ((BreakableWall) w).isVisible()){
                        t.setX(t.getX() - t.getDx());
                        t.setY(t.getY() - t.getDy());
                    }
                }
                if(w instanceof RegularWall){
                    if(t.getBounds().intersects(((RegularWall)w).getBounds()) && ((RegularWall) w).isVisible()){
                        t.setX(t.getX() - t.getDx());
                        t.setY(t.getY() - t.getDy());
                    }
                }
            }
        }

    }

    public void gameOver(Graphics2D G){

        if(t1.getLives() <= 0){
            G.setFont(new Font("TimesRoman",Font.PLAIN, 100));
            G.setColor(Color.BLUE);
            G.drawString("TANK 2 WINS!", 110, 350);
        } else if(t2.getLives() <= 0){
            G.setFont(new Font("TimesRoman",Font.PLAIN, 100));
            G.setColor(Color.RED);
            G.drawString("TANK 1 WINS!", 110, 350);
        }
    }

}
