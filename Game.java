import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;

public class Game extends JComponent implements KeyListener, MouseListener, MouseMotionListener {
    //instance variables

    ArrayList<Bullet> bullets =new ArrayList<Bullet>();
    ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    ArrayList<Enemy> enemies1 = new ArrayList<Enemy>();
    ArrayList<Enemy> enemies2 = new ArrayList<Enemy>();
    ArrayList<Enemy> enemies3 = new ArrayList<Enemy>();
    ArrayList<Enemy> enemies4 = new ArrayList<Enemy>();
    ArrayList<Enemy> enemies5 = new ArrayList<Enemy>();
    ArrayList<Enemy> enemies6 = new ArrayList<Enemy>();
    public int WIDTH;
    private Color color;
    public int HEIGHT;
    private Player p1;
    private int rX, rY,targetX, targetY;
    private boolean mU, mD, mL, mR, mouseClick;
    public int mousePosX, mousePosY, mouseMoveX, mouseMoveY, rClickX, rClickY, rW, rH;
    private int cX, cY, diam, cVx, cVy;
    private int wave;
    private int titleX;
    private int background2x;
    private int background1x;
    private ImageIcon screen;
    private Image screenimg;
    private AudioClip maintheme1, maintheme2;
    private long start = System.currentTimeMillis();
    private boolean helper;

    //Default Constructor
    public Game()
    {
        //wave1 enemies
        for(int i = 0; i <= 7; i++)
        {
            enemies1.add(new Enemy());
        }

        //wave2 enemies
        for(int i = 0; i <= 9; i++)
        {
            enemies2.add(new Enemy());
        }

        //wave3 enemies
        for(int i = 0; i <= 11; i++)
        {
            enemies3.add(new Enemy());
        }

        //wave4 enemies
        for(int i = 0; i <= 13; i++)
        {
            enemies4.add(new Enemy());
        }

        //wave5 enemies
        for(int i = 0; i <= 14; i++)
        {
            enemies5.add(new Enemy());
        }

        //wave6
        for(int i = 0; i <= 15; i++)
        {
            enemies6.add(new Enemy());
        }

        //initializing instance variables
        color = Color.red;
        WIDTH = (int) getToolkit().getScreenSize().getWidth();
        HEIGHT = (int) getToolkit().getScreenSize().getHeight();
        background1x= 0;
        background2x = -WIDTH;
        helper = false;
        wave = 0;
        titleX = -50;
        cX = 300;
        cY = 300;
        diam = 50;
        cVx = 10;
        cVy = 10;
        rX = WIDTH/2;
        rY = HEIGHT/2;
        rW = 100;
        rH = 100;
        targetX=0;
        targetY = 0;
        maintheme1 = Applet.newAudioClip(this.getClass().getResource("gamemusic1.wav"));
        maintheme2 = Applet.newAudioClip(this.getClass().getResource("gamemusic2.wav"));
        p1 = new Player(rX, rY, rW);

        //Setting up the GUI
        JFrame gui = new JFrame(); //This makes the gui box
        gui.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gui.setUndecorated(true);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Makes sure program can close
        gui.setTitle("Learning Graphics"); //This is the title of the game, you can change it
        gui.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
        gui.setResizable(true); //Makes it so the gui cant be resized
        gui.getContentPane().add(this); //Adding this class to the gui

        gui.pack(); //Packs everything together
        gui.setLocationRelativeTo(null); //Makes so the gui opens in the center of screen
        gui.setVisible(true); //Makes the gui visible
        gui.addKeyListener(this);
        gui.addMouseListener(this);
        gui.addMouseMotionListener(this);

        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");

        gui.getContentPane().setCursor(blankCursor);

    }
    //All your UI drawing goes in here
    public void paintComponent(Graphics g)
    {

        if(wave != 0)
        {
            //background
            backgroundmovement1((Graphics2D)g);
            backgroundmovement2((Graphics2D)g);
            //drawing the health bars
            g.setColor(Color.red);
            g.fillRect(WIDTH / 14, HEIGHT - (HEIGHT / 9), 100, 20);
            g.setColor(Color.green);
            g.fillRect(WIDTH / 14, HEIGHT - (HEIGHT / 9), p1.getHealth(), 20);


            //drawing the aim line
            g.setColor(Color.getHSBColor(31, 100, 155));
            g.drawLine(mouseMoveX, mouseMoveY, rX + rW / 2, rY + rH / 2);

            if(p1.getHealth() > 0)
            {
                p1.drawSelf(g);
                playImage();

            }

            for(int i = 0; i < bullets.size(); i++)
            {
                bullets.get(i).drawSelf(g);
                bullets.get(i).shoot();
            }

            for(int i = 0; i < bullets.size(); i++)
            {
                if(bullets.get(i).getX() > WIDTH || bullets.get(i).getX() < -100 || bullets.get(i).getY() > HEIGHT || bullets.get(i).getY() < -100)
                    bullets.remove(i);
            }
        }


        if(wave == 0)
        {
            screen = new ImageIcon(Player.class.getResource("game pics.png"));
            screenimg = screen.getImage();
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(screenimg, 0, 0, WIDTH, HEIGHT,null);

            if(helper == true) {
                wave = 1;
            }
        }
        else if(p1.getHealth() <= 0)
        {
            g.setColor(Color.BLACK);
            g.fillRect(0,0, WIDTH, HEIGHT);


            Font font = new Font("Arial", Font.BOLD, 50);
            g.setFont(font);
            g.setColor(Color.WHITE);
            g.drawString("YOU DIED!", WIDTH/3, HEIGHT/3);
            g.setFont(font);
            g.setColor(Color.WHITE);
            g.drawString("YOU MADE IT TO WAVE " + wave + "!!!", WIDTH/3, HEIGHT/2);

        }
        else
        {
            if(p1.getHealth() > 0)
            {
                int helper = 0;
                g.setColor(Color.WHITE);
                Font font = new Font("Arial", Font.BOLD, 50);
                g.setFont(font);
                g.drawString("WAVE " + wave + " PREPARE YOURSELF", titleX, HEIGHT/3);
                titleX+=10;
                if(titleX> WIDTH+300 && helper == 0)
                {
                    for(int i = 0; i< enemies.size(); i++)
                    {
                        enemies.get(i).spawn(g);
                        enemies.get(i).shoot();
                    }
                    bulletOnEnemyAction(enemies, bullets);
                    enemyOnPlayerAction(enemies, p1);
                    if(enemies.size() <= 0)
                    {
                        titleX = -100;
                        wave++;
                    }
                }
            }
        }

        //System.out.println(bullets.size());
        if(mU && rY - 2 >= 0)
        {
            rY+= -5;
            p1.setY(-5);
        }
        if(mD && (rY + p1.getDiam())+ 2 <= HEIGHT)
        {
            rY+=5;
            p1.setY(5);
        }
        if(mL && rX- 2 >= 0)
        {
            rX+=-5;
            p1.setX(-5);
        }
        if(mR && (rX + p1.getDiam())+ 2 <= WIDTH)
        {
            rX+=5;
            p1.setX(5);
        }
    }
    public void loop()
    {
        //making the autonomous circle move
        cX += cVx;
        cY += cVy;
        //handling when the circle collides with the edges
        int nextX = cX + cVx;
        int nextY = cY + cVy;

        if(nextY + diam > HEIGHT)
            cVy *= -1;
        if(nextX + diam > WIDTH)
            cVx *= -1;
        if(nextY < 0)
            cVy *= -1;
        if(nextX < 0)
            cVx *= -1;
        //handling the collision of the circle with the rectangle
        System.out.println(background2x);
        System.out.println(background1x);
        if(wave != 0)
        {
            background2x+=5;
            background1x+=5;
            if(enemies.size() == 0)
            {
                initEnemies();
            }
        }
        //Do not write below this
        repaint();
    }
    //These methods are required by the compiler.
    //You might write code in these methods depending on your goal.
    public void keyTyped(KeyEvent e)
    {
    }
    public void keyPressed(KeyEvent e)
    {
       // System.out.println(e.getKeyCode());
        if(e.getKeyCode() == 87)
            mU = true;
        if(e.getKeyCode() == 83)
            mD = true;
        if(e.getKeyCode() == 65)
            mL = true;
        if(e.getKeyCode() == 68)
            mR = true;
        if(e.getKeyCode() == 32)
            helper = true;
    }
    public void keyReleased(KeyEvent e)
    {
        if(e.getKeyCode() == 87)
            mU = false;
        if(e.getKeyCode() == 83)
            mD = false;
        if(e.getKeyCode() == 65)
            mL = false;
        if(e.getKeyCode() == 68)
            mR = false;
    }
    public void mousePressed(MouseEvent e)
    {
        mousePosX = e.getXOnScreen();
        mousePosY = e.getYOnScreen();
        targetX = mousePosX;
        targetY = mousePosY;
        rClickX = rX;
        rClickY = rY;
        Bullet b1 = new Bullet(rX , rY , 30, 30, "bullet.png", setXPow(), setYPow());
        bullets.add(b1);
        mouseClick = true;
    }
    public void mouseReleased(MouseEvent e)
    {
        mousePosX = e.getXOnScreen();
        mousePosY = e.getYOnScreen();
        mouseClick = false;
    }
    public void mouseClicked(MouseEvent e)
    {
        mouseMoveX = e.getXOnScreen();
        mouseMoveY = e.getYOnScreen();

    }
    public void mouseEntered(MouseEvent e)
    {
        mouseMoveX = e.getXOnScreen();
        mouseMoveY = e.getYOnScreen();
    }
    public void mouseExited(MouseEvent e)
    {
        mouseMoveX = e.getXOnScreen();
        mouseMoveY = e.getYOnScreen();
    }
    public void mouseMoved(MouseEvent e)
    {
        mouseMoveX = e.getXOnScreen();
        mouseMoveY = e.getYOnScreen();
    }
    public void mouseDragged(MouseEvent e)
    {
        mouseMoveX = e.getXOnScreen();
        mouseMoveY = e.getYOnScreen();
    }
    public void start(final int ticks)
    {
        Thread gameThread = new Thread(){
            public void run(){
                while(true){
                    loop();
                    try{
                        Thread.sleep(1000 / ticks);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        };
        gameThread.start();
    }
    public double angle()
    {
        double ref = Math.atan((double)((rClickY +rH/2) - mousePosY)/((rClickX+rW/2) - mousePosX));
        ref = Math.abs((ref*180)/Math.PI);
        if(getQuad() == 1)
            return (ref*Math.PI)/180;
        if(getQuad() == 2)
            return (((180 - ref)*Math.PI) /180);
        if(getQuad() == 3)
            return (((360 -ref)*Math.PI)/180);
        if(getQuad() == 4)
            return (((180 + ref)*Math.PI)/180);
        return ref;
    }
    public double setXPow()
    {
        double ref = Math.atan((double)((rClickY+rH/2) - mousePosY)/((rClickX+rW/2) - mousePosX));
        ref = Math.abs((ref*180)/Math.PI);
        double power;
        if(getQuad() == 1)
            power = Math.cos((ref*Math.PI)/180)*20;
        else if (getQuad() == 2)
            power = Math.cos(((180 - ref)*Math.PI) /180)*20;
        else if(getQuad() == 4)
            power = Math.cos(((360 -ref)*Math.PI)/180)*20;
        else
            power = Math.cos(((180 + ref)*Math.PI)/180 )*20;
        return power;
    }
    public double setYPow()
    {
        double angle;
        double ref = Math.atan((double)(rClickY+rH/2 - mousePosY)/(rClickX+rW/2 - mousePosX));
        ref = Math.abs((ref*180)/Math.PI);
        double power;
        if(getQuad() == 1)
            power = Math.sin((ref*Math.PI)/180)*20;
        else if (getQuad() == 2)
            power = Math.sin(((180 - ref)*Math.PI)/180)*20;
        else if(getQuad() == 4)
            power = Math.sin(((360 -ref)*Math.PI)/180)*20;
        else
            power = Math.sin(((180 + ref)*Math.PI)/180)*20;
        return power;
    }
    public int getQuad()
    {
        int quad = 0;
        if(mousePosX >= rClickX+(rW/2) && mousePosY >= rClickY+(rH/2))
            quad = 1;
        else if(mousePosX >= rClickX+(rW/2) && mousePosY <= rClickY+(rH/2))
            quad = 4;
        else if(mousePosX <= rClickX+(rW/2) && mousePosY <= rClickY+(rH/2))
            quad = 3;
        else if(mousePosX <= rClickX+(rW/2) && mousePosY>= rClickY+(rH/2))
            quad = 2;
        return quad;
    }
    public void bulletOnEnemyAction(ArrayList<Enemy> enemies, ArrayList<Bullet> bullets)
    {
        for(int i = 0; i < enemies.size(); i++)
        {
            for(int j = 0; j < bullets.size(); j++)
            {
                if((bullets.size() > 0) && (enemies.size() > 0))
                {
                    if(bulletOnEnemy(enemies.get(i), bullets.get(j)))
                    {
                        enemies.remove(i);
                        bullets.remove(j);
                        if(i > 0)
                            i--;
                        if(j > 0)
                            j--;
                        System.out.println("poop");
                    }
                }
            }
        }
    }
    public boolean bulletOnEnemy(Enemy enemy, Bullet bullet)
    {
        double d = Math.sqrt(Math.pow(bullet.getCenterX() - enemy.getCenterX(),2) + Math.pow(bullet.getCenterY() - enemy.getCenterY(),2 ));
        if(d <= ((enemy.getDiam()/2)+ (bullet.getDiam()/2)))
            return true;
        return false;
    }
    public void enemyOnPlayerAction(ArrayList<Enemy> enemies, Player player)
    {
        for(int i = 0; i < enemies.size(); i++)
        {
            if((enemies.size() > 0) && enemyOnPlayer(enemies.get(i), player))
            {
                enemies.remove(i);
                i--;
                player.changeHealth(10);
                System.out.println("poop2");
            }
        }
    }
    public boolean enemyOnPlayer(Enemy enemy, Player player)
    {
        double d = Math.sqrt(Math.pow(enemy.getCenterX() - player.getCenterX(),2) + Math.pow(enemy.getCenterY() - player.getCenterY(),2 ));
        if(d <= ((enemy.getDiam()/2)+ (player.getDiam()/2)))
            return true;
        return false;
    }
    public void playImage()
    {
        if(mU)
        {
            if(mL)
                p1.setImage("spacenorthwest.png");
            else if(mR)
                p1.setImage("spacenortheast.png");
            else
                p1.setImage("spaceforward.png");
        }
        if(mR)
        {
            if(mU)
                p1.setImage("spacenortheast.png");
            else if(mD)
                p1.setImage("spacesoutheast.png");
            else
                p1.setImage("spaceright.png");
        }
        if(mD)
        {
            if(mR)
                p1.setImage("spacesoutheast.png");
            else if(mL)
                p1.setImage("spacesouthwest.png");
            else
                p1.setImage("spacedown.png");
        }
        if(mL)
        {
            if(mU)
                p1.setImage("spacenorthwest.png");
            else if(mD)
                p1.setImage("spacesouthwest.png");
            else
                p1.setImage("spaceleft.png");
        }
    }
    public void initEnemies()
    {
        for(int i = 0; i < 4 + wave; i++)
        {
            enemies.add(new Enemy());
        }
    }
    public void backgroundmovement1(Graphics2D g2)
    {
        ImageIcon imageG1 = new ImageIcon(Player.class.getResource("background1.png"));
        Image background1 = imageG1.getImage();
        g2.drawImage(background1, background1x, 0, WIDTH, HEIGHT,null);
        if(background1x == WIDTH)
            background1x = -WIDTH;
    }
    public void backgroundmovement2(Graphics2D g2)
    {
        ImageIcon imageG2 = new ImageIcon(Player.class.getResource("background2.png"));
        Image background2 = imageG2.getImage();
        g2.drawImage(background2, background2x, 0, WIDTH, HEIGHT, null);
        if (background2x == WIDTH)
            background2x = -WIDTH;
    }
    public static void main(String[] args)
    {
        Game g = new Game();
        g.start(60);
    }
}
