import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class PowerUp extends ArenaObject {
    private int x;
    private int y;
    private BufferedImage img;
    private boolean isVisible;

    public PowerUp(int x, int y, BufferedImage img, boolean isVisible) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.isVisible = isVisible;
    }

    public void collision(){
        this.isVisible = false;
    }

    public boolean isvisible(){
        return this.isVisible;
    }

    public Rectangle getBounds(){
        return new Rectangle(this.x, this.y, 10, 10);
    }

    public void drawImage(Graphics g) {
        if(isVisible) {
            AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(this.img, rotation, null);
        }
    }
}
