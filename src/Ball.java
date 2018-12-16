
import java.awt.Color;
import java.awt.Graphics;

/**
 * The main ball that must be hit around.
 * 
 * @since 10.26.2018
 * @author seymour
 */

public class Ball {
    
    public int x, y, w = 20, h = 20, xSpeed1 = 2, xSpeed = xSpeed1, ySpeed1 = 2, ySpeed = ySpeed1, state;
    /** BALL STATES
     * 0 : following paddle (start)
     * 1 : in-play
     */
    public int speed = 0, hitNum = 0;
    public Color color = Color.white;
    public boolean left = true, up = true;
    
    public Ball() {
        
    }
    
    public Ball(int xx, int yy) {
        x = xx;
        y = yy;
    }
    
    public void setSize(int ww, int hh) {
        w = ww;
        h = hh;
    }
    
    public void setSpeed(int xSp, int ySp) {
        xSpeed = xSp;
        ySpeed = ySp;
    }
    
    public void setDirection(boolean d, boolean u) {
        left = d;
        up = u;
    }
    
    public void setPos(int xx, int yy) {
        y = yy;
        x = xx;
    }
    
    public void setColor(Color c) {
        color = c;
    }
    
    public void move() {
        if (up) y -= ySpeed;
        else y += ySpeed;
        
        if (left) x -= xSpeed;
        else x += xSpeed;
    }
    
    public void bounceX() {
        left = !left;
    }
    
    public void bounceY() {
        up = !up;
    }
    
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, w, h);
    }    
}
