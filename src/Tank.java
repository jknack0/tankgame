import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


public class Tank extends ArenaObject {
    private int x;
    private int y;
    private int dx;
    private int dy;
    private int angle;
    private int shootCooldown = 0;
    private int id;


    private int lives;
    private int health;

    private final int R = 2;
    private final int ROTATIONALSPEED = 2;

    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;

    Tank(int x, int y, int vx, int vy,int lives, int health, int angle, BufferedImage img, int id) {
        this.x = x;
        this.y = y;
        this.dx = vx;
        this.dy = vy;
        this.lives = lives;
        this.health = health;
        this.img = img;
        this.angle = angle;
        this.id = id;
    }

    void toggleShootPressed(){
        this.ShootPressed = true;
    }

    void unToggleShootPressed(){
        this.ShootPressed = false;
    }

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }



    public void update() {
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if(this.ShootPressed){
            this.shootBullet();
        }


    }

    private void shootBullet(){
        if(shootCooldown < 0) {
            dx = (int) Math.round(4 * Math.cos(Math.toRadians(angle)));
            dy = (int) Math.round(4 * Math.sin(Math.toRadians(angle)));
            Arena.gameBullets.add(new Bullet(this.x + 32, this.y + 32, dx, dy, this.angle, Arena.bulletImg, this.id, true));
            shootCooldown += 100;
        }
    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONALSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONALSPEED;
    }

    private void moveBackwards() {
        dx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        dy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= dx;
        y -= dy;
        checkBorder();
    }

    private void moveForwards() {
        dx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        dy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += dx;
        y += dy;
        checkBorder();
    }



    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= Arena.MAP_WIDTH - 88) {
            x = Arena.MAP_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= Arena.MAP_HEIGHT - 80) {
            y = Arena.MAP_HEIGHT - 80;
        }
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getDx() {return this.dx; }

    public int getDy() {return this.dy; }

    public void setX (int x){
        this.x = x;
    }

    public void setY (int y) {
        this.y = y;
    }

    public Rectangle getBounds(){
        return new Rectangle(this.x, this.y, 60, 60);
    }


    public int getSubimageX(){

        if(this.x <= 200)
            return 0;
        else if(this.x >= Arena.MAP_WIDTH - 450 + 200)
            return Arena.MAP_WIDTH  - 450 ;
        else
            return this.x - 200;
    }

    public int getId(){
        return this.id;
    }

    public int getSubimageY(){
        if(this.y <= 220 )
            return 0;
        else if(this.y >= Arena.MAP_HEIGHT - 300){
            return Arena.MAP_HEIGHT - 500;
        } else {
            return this.y - 220;
        }
    }

    public void bulletCollision(){
        this.health = this.health - 10;
    }

    public int getLives(){
        return this.lives;
    }

    public int getHealth(){
        return this.health;
    }

    public void sethealth(){
        this.health = 100;
    }

    public void loseLive() {
        this.lives = this.lives - 1;
    }

    public void getLive() {
        this.lives = this.lives + 1;
    }

    @Override
    public String toString() {
       return "x=" + x + ", y=" + y + ", angle=" + angle;
    }




    void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
        if(shootCooldown >= 0)
            shootCooldown -= 1;
    }


}