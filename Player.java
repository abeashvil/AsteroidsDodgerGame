import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.xml.namespace.QName;
import java.io.File;
import java.io.IOException;
import java.nio.channels.ScatteringByteChannel;
public class Player extends JComponent
{
    private int x,y,diam, nextx, nexty;
    private int centerX;
    private int centerY;
    private Color color;
    private ImageIcon imageG;
    private Image image;
    private int health;
    private String name;
    public Player(int x1, int y1, int diamD)
    {
        x = x1;
        y = y1;
        diam = diamD;
        health = 100;
        name = "spaceforward.png";
        imageG = new ImageIcon(Player.class.getResource(name));
        image = imageG.getImage();
    }
    public void drawSelf(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        imageG = new ImageIcon(Player.class.getResource(name));
        image = imageG.getImage();
        g2.drawImage(image, x, y, diam, diam,null);
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public void setX(int x2)
    {
        x += x2;
    }
    public void setY(int y2)
    {
        y += y2;
    }
    public int getCenterX()
    {
        centerX = (2*(x+5) + diam)/2;
        return centerX;
    }
    public int getCenterY()
    {
        centerY = (2*(y+5) + diam)/2;
        return centerY;
    }
    public int getDiam()
    {
        return diam;
    }
    public int getHealth()
    {
        return health;
    }
    public void changeHealth(int x)
    {
        health -=x;
    }
    public void setImage(String str)
    {
        name = str;
    }
}
