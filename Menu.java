package Corona2DGamePackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Menu extends JPanel {
    String name;
    Boolean running;
    BackgroundMap bg;
    int fps;
    boolean paused;
    public static Rectangle startButton;

    public Menu(String name) {
        this.name = name;
        this.running = true;
        this.fps = 0;
        this.paused = !this.running;
        this.setFocusable(true);
    }

    //No need to call this function, it runs when updating, clicking, ... the frame
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.green);
        g.fillRect(0, 0, Game.FRAMEWIDTH, Game.FRAMEHEIGHT);
        this.drawMenuButtons(g);
        //this.drawStuff(g);
        g.setColor(Color.black);
        g.drawString(this.fps + " FPS", 50, 20);

    }
    public void drawMenuButtons(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g.setColor(Color.red);
        g.drawRect(Game.FRAMEWIDTH/2,Game.FRAMEHEIGHT/2,100,50);
        startButton = new Rectangle(Game.FRAMEWIDTH/2,Game.FRAMEHEIGHT/2,100,50);
        //g2.draw(startButton);
        g2.fill(startButton);
    //g.setColor(new Color(104, 196, 43));

    //Graphics2D g2 = (Graphics2D) g;

    }


    public void start() {
        MouseListener mouse = new MouseListener() {
            //NOSE PERK PERO NO SE POT POSAR THIS A DINS D?AQUESTES FUNCIONS
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (startButton.contains(mouseEvent.getPoint())) {
                    running = false;
                    // Was clicked...
                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        };
        addMouseListener(mouse);
        this.loop();
    }

    public void loop() {
        double MS_PER_FRAME = 33.6;
        double endTimeFps;
        int fpsIter = 0;
        int frameTimes = 0;
        while (this.running) {
            double start = System.currentTimeMillis();
            if (!this.paused) {
                this.update();
            }
            this.render();
            try {
                if ((start + MS_PER_FRAME - System.currentTimeMillis()) > 0) {
                    Thread.sleep((long) (start + MS_PER_FRAME - System.currentTimeMillis()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            endTimeFps = System.currentTimeMillis();
            frameTimes = frameTimes + (int) (endTimeFps - start);
            fpsIter++;
            if (frameTimes >= 500) {
                this.fps = fpsIter * 2;
                fpsIter = 0;
                frameTimes = 0;
            }
        }
    }

    //Render : updateimages on screen
    public void render() {
        this.revalidate();
        this.repaint();
    }

    //Move screeen objects
    public void update() {

    }
}

