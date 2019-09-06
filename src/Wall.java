import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Wall extends ArenaObject {
    private int x;
    private int y;
    private BufferedImage img;
    public abstract void drawImage(Graphics g);
}
