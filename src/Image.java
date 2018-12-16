
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Makes images easier to make and takes up less space in the main file.
 * 
 * @since 22.11.2018
 * @author Taylor
 */
public class Image {
    int x, y;
    public BufferedImage img;
    
    public Image(int xx, int yy, String str) {
        x = xx;
        y = yy;
        
        try {
            File imgLoc = new File("./img/" + str);
            img = ImageIO.read(imgLoc);
        } catch(Exception e) {
            System.out.println(e);
        }
    }
    
    public void setPos(int xx, int yy) {
        x = xx;
        y = yy;
    }
    
    public void draw(Graphics g, ImageObserver i) {
        g.drawImage(img, x, y, i);
    }
    
    public void draw(int w, int h, Graphics g, ImageObserver i) {
        g.drawImage(img, x, y, w, h, i);
    }
}
