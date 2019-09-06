import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


public class RegularWall extends Wall {
    private int x;
    private int y;
    private BufferedImage img;
    private boolean isVisible = true;

    RegularWall(int x, int y, BufferedImage img){
        this.x = x;
        this.y = y;
        this.img = img;
    }

    public Rectangle getBounds(){
        return new Rectangle(this.x, this.y, 25, 25);
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public boolean isVisible(){
        return this.isVisible;
    }

    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
    }

}
