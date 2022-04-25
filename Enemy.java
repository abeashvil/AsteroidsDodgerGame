import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.channels.ScatteringByteChannel;
public class Enemy extends JComponent
{
    private int x,y,w,h;
    private Color color;
    private ImageIcon imageG;
    private Image image;
    private int diam;
    private int xChange = (int) (Math.random()*2);
    private int yChange = (int) (Math.random()*2);
    private int centerX;
    private int centerY;
    private int WIDTH = (int) getToolkit().getScreenSize().getWidth();
    private int HEIGHT = (int) getToolkit().getScreenSize().getHeight();
    private int xVel, yVel;
    private int helpernum = 0;
    private String name = "spin-00.png";
    private long start = System.currentTimeMillis();


    public Enemy()
    {
        x = (int) (Math.random()*(WIDTH - 160)) + 80;
        y = (int) (Math.random()*(HEIGHT-160)) + 80;
        diam = 120;
        if(xChange== 0)
            xVel = 15;
        else
            xVel =-15;
        if(yChange== 0)
            yVel = 15;
        else
            yVel = -15;
        imageG = new ImageIcon(Player.class.getResource(name));
        image = imageG.getImage();
    }
    public void spawn(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        imageG = new ImageIcon(Player.class.getResource(name));
        image = imageG.getImage();
        g2.drawImage(image, x, y, diam, diam,null);
    }
    public void shoot()
    {
        x+= xVel;
        y+= yVel;
        int nextX, nextY;
        nextX = x + 5;
        nextY = y + 5;
        if(nextX + diam >=WIDTH)
            xVel*= -1;
        if(nextY+diam >= HEIGHT)
            yVel*= -1;
        if(x - 5 <= 0)
            xVel*=-1;
        if(y - 5 <= 0)
            yVel*=-1;
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
    public void setImage(String str)
    {
        name = "spin-" + str + ".png";
    }
}
