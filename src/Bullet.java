import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends ArenaObject {
    private int x;
    private int y;
    private int dx;
    private int dy;
    private int angle;
    private BufferedImage img;
    private boolean isVisible;
    private int id;


    Bullet(int x, int y, int dx, int dy, int angle, BufferedImage img, int id, boolean isVisible) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.img = img;
        this.angle = angle;
        this.id = id;
        this.isVisible = isVisible;
    }

    public void collision(){
        this.isVisible = false;
    }

    public Rectangle getBounds(){
        return new Rectangle(this.x, this.y, 10, 10);
    }

    public int getId() {
        return this.id;
    }

    public boolean isVis(){
        return this.isVisible;
    }

    public void drawImage(Graphics g) {
        if(isVisible) {
            AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
            rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(this.img, rotation, null);
            this.x += dx;
            this.y += dy;
        }
    }
}
