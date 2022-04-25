import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.channels.ScatteringByteChannel;
public class Bullet extends JComponent
{
    private int x,y,w,h, centerX, centerY;
    private double px, py;
    private Color color;
    private Image image;
    private ImageIcon imageG;

    public Bullet(int x1, int y1, int w1, int h1, String i, double p1, double p2)
    {
        x = x1;
        y = y1;
        w = w1;
        h = h1;
        imageG = new ImageIcon(Player.class.getResource(i));
        image = imageG.getImage();
        px = p1;
        py = p2;
    }
    public void drawSelf(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(image, x + getDiam(), y + getDiam(), w, h,this);
    }
    public void shoot()
    {
        x += px;
        y += py;
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public int getCenterX()
    {
        centerX = (2*(x+5) + w)/2;
        return centerX;
    }
    public int getCenterY()
    {
        centerY = (2*(y+5) + w)/2;
        return centerY;
    }
    public int getDiam()
    {
        return w;
    }
}
