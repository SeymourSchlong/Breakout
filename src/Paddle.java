
import java.awt.Color;
import java.awt.Graphics;

/**
 * Paddle to hit the ball. Controllable by the player
 * 
 * @since 10.29.2018
 * @author seymour
 */

public class Paddle {
    
    public int dir = 0;
    public int x, y, w = 75, h = 20, speed = 10;
    public Color color = Color.white;
    
    public Paddle() {
        
    }
    
    public Paddle(int xx, int yy) {
        x = xx;
        y = yy;
    }
    
    public void setWidth(int ww) {
        w = ww;
    }
    
    public void setPos(int xx, int yy) {
        x = xx;
        y = yy;
    }
    
    public void setColor(Color c) {
        color = c;
    }
    
    public void moveLeft() {
        if (x - speed > 0) x -= speed;
    }
    
    public void moveRight() {
        if (x + w + 4 + speed < 800) x += speed;
    }
    
    public void draw(Graphics g) {
        g.setColor(color);
        g.fill3DRect(x, y, w, h, true);
    }
}
