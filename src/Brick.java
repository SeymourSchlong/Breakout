
import java.awt.Color;
import java.awt.Graphics;

/**
 * The bricks that the ball must hit.
 * 
 * @since 11.15.2018
 * @author seymour
 */
public class Brick {
    
    public int x, y, h = 26, w = 50;
    public boolean visible = true;
    public Color color = Color.red;
    
    public Brick(int xx, int yy) {
        x = xx;
        y = yy;
    }
    
    public void setColor(Color c) {
        color = c;
    }
    
    public void setSize(int ww, int hh) {
        w = ww;
        h = hh;
    }
    
    public void draw(Graphics g) {
        g.setColor(color);
        if (visible) g.fill3DRect(x, y, w, h, true);
    }
}
