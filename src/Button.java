
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Buttons that you can click. To be used in menus.
 * 
 * @since 21.11.2018
 * @author seymour
 */
public class Button {
    public BufferedImage img, imgHover;
    public int x, y, w, h;
    public boolean hover;
    
    public Button(int xx, int yy, String image, String imageH) {
        x = xx;
        y = yy;
        
        try {
            File imgLoc = new File("./img/" + image);
            File imgHLoc = new File("./img/" + imageH);
            img = ImageIO.read(imgLoc);
            imgHover = ImageIO.read(imgHLoc);
        } catch(Exception e) {
            System.out.println(e);
        }
        
        w = img.getWidth();
        h = img.getHeight();
    }
    
    public void draw(Graphics g, ImageObserver i) {
        if (!hover) g.drawImage(img, x, y, i);
        else g.drawImage(imgHover, x, y, i);
    }
}
