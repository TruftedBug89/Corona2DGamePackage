package Corona2DGamePackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class GamePanel extends JPanel implements KeyListener {

    private boolean[] keys = new boolean[256];
    Infected[] infected;
    String name;
    Boolean running;
    Player player;
    BackgroundMap bg;
    GameObj[] items;

    int fps;
    boolean paused;

    public GamePanel(String name, Player player, Infected[] infected, GameObj[] items, BackgroundMap bg) {
        this.name = name;
        this.fps = 0;
        this.running = true;
        this.player = player;
        this.bg = bg;
        this.addKeyListener(this);
        this.setFocusable(true);
        this.infected = infected;
        this.items = items;
        this.paused = false;
    }

    //No need to call this function, it runs when updating, clicking, ... the frame
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.bg.draw(g);
        this.player.draw(g);
        this.player.drawHpBar(g);
        this.drawStats(g);
        //draw infected+path
        for (Infected each : this.infected) {
            each.drawInfected(g, this.bg);
            each.drawPath(g, this.bg);
        }
        //draw items
        for (GameObj obj : this.items) {
            obj.drawObject(g, this);
        }
        // this.gameobjects..drawObject(g, this);


    }

    public void drawStats(Graphics g) {
        g.setColor(Color.GRAY);
        g.drawString(this.fps + " FPS", 50, 20);
        g.drawString("Points: " + this.player.points, 100, 20);
        g.drawString("Player X :" + this.player.xPos + " Player Y :" + this.player.yPos, 150, 20);
        int relx = this.player.xPos + Game.FRAMEWIDTH * -this.bg.codeZone[0];
        int rely = this.player.yPos + Game.FRAMEHEIGHT * -this.bg.codeZone[1];
        g.drawString("Relative to bg : Player X :" + relx + " Player Y :" + rely, 150, 40);
        GameObj tot = this.items[0];
        g.drawString(tot.name + "x: " + tot.X + " y: " + tot.Y, 150, 60);
        g.drawString("Player holding : " + this.player.holding, 150, 80);
    }


    //Inicialize game components
    public void start() {
        this.requestFocusInWindow();//Request focus for the keyListener
        this.loop();
    }

    public void loop() {
        double MS_PER_FRAME = 16.8;
        double endTimeFps;
        int fpsIter = 0;
        int frameTimes = 0;
        while (this.running) {
            if ( keys[KeyEvent.VK_P] ) {
                this.paused = !this.paused;
                keys[KeyEvent.VK_P] = false;

            }
            double start = System.currentTimeMillis();
            if ( !this.paused ) {
                this.update();
            }
            this.render();
            try {
                if ( (start + MS_PER_FRAME - System.currentTimeMillis()) > 0 ) {
                    Thread.sleep((long) (start + MS_PER_FRAME - System.currentTimeMillis()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            endTimeFps = System.currentTimeMillis();
            frameTimes = frameTimes + (int) (endTimeFps - start);
            fpsIter++;
            if ( frameTimes >= 500 ) {//1000 = 1 sec in mils
//                System.out.println("px:"+this.player.xPos+" py:"+this.player.yPos+"tpx:"+
//
//                        this.toiletPaper.X+" tpy:"+this.toiletPaper.Y);
                this.fps = fpsIter * 2;
                fpsIter = 0;
                frameTimes = 0;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    //Render : updateimages on screen
    public void render() {
        this.revalidate();
        this.repaint();
    }

    //Move screeen objects and update based on time for now
    public void update() {
        if ( this.player.hp <= 0 ) {//end game
            this.running = false;
        }
        if ( keys[KeyEvent.VK_A] && keys[KeyEvent.VK_W] ) {
            this.player.movePlayer("upleft", "upleft", this.bg);
        } else if ( keys[KeyEvent.VK_A] && keys[KeyEvent.VK_S] ) {
            this.player.movePlayer("downleft", "downleft", this.bg);
        } else if ( keys[KeyEvent.VK_S] && keys[KeyEvent.VK_D] ) {
            this.player.movePlayer("downright", "downright", this.bg);
        } else if ( keys[KeyEvent.VK_D] && keys[KeyEvent.VK_W] ) {
            this.player.movePlayer("upright", "upright", this.bg);
        } else if ( keys[KeyEvent.VK_W] ) {
            this.player.movePlayer("forward", "forward", this.bg);
        } else if ( keys[KeyEvent.VK_D] ) {
            this.player.movePlayer("right", "right", this.bg);
        } else if ( keys[KeyEvent.VK_S] ) {
            this.player.movePlayer("backward", "backward", this.bg);
        } else if ( keys[KeyEvent.VK_A] ) {
            this.player.movePlayer("left", "left", this.bg);
        }
        if ( keys[KeyEvent.VK_Q] ) {
            if ( this.player.holding.using == false ) {
                this.player.holding.using = true;
            } else {
                this.player.holding.throwObj();

            }
        }
        for (Infected each : this.infected) {
            each.moveInfected(this.bg);
        }

        //Check collisions with infected
        for (Infected each : this.infected) {
            for (int pos = 0; pos < each.pathCollisions.length; pos++) {
                if ( each.pathCollisions[pos] != null ) {
                    String[] xy = each.pathCollisions[pos].split(",");
                    //Calculating the distance between the player and all the paths, to check collisions, using the angle formula
                    if ( Math.sqrt(Math.pow(Integer.parseInt(xy[0]) + (Game.FRAMEWIDTH * this.bg.codeZone[0]) - this.player.xPos, 2) + Math.pow(((Integer.parseInt(xy[1]) + (Game.FRAMEHEIGHT * this.bg.codeZone[1])) - this.player.yPos), 2)) < each.pathLineSize ) {
                        this.player.hp -= Game.PLAYERDMGPATH;
                    }
                }
            }
        }
        //Check collisions with objects
        for (int e = 0; e < this.items.length; e++) {
//            for (Infected each : this.infected) {
//                if ( this.items[e].objectCollision(this.player.xPos, this.player.yPos, this.bg) ) {
//                    this.items[e].hitInfected(each);
//                }
//            }
//            System.out.println(obj.objectCollision(this.player.xPos, this.player.yPos));
            if ( this.items[e].objectCollision(this.player.xPos, this.player.yPos, this.bg) ) {
                System.out.println("hitting");
//                this.player.getPoints(this.items[e].pointsOnPickUp);
                this.player.holding = this.items[e];
                this.items[e].pickedUp = true;


            }
        }
        //Update all objects in the game

    }
    //Main function, not used actually
}
