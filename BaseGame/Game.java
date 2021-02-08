package Corona2DGamePackage.BaseGame;

import Corona2DGamePackage.Map.BackgroundMap;
import Corona2DGamePackage.Objects.GameObj;
import Corona2DGamePackage.Objects.Xeringa;
import Corona2DGamePackage.Player.Infected;
import Corona2DGamePackage.Player.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class Game {
    public static Window frame;
    public static GamePanel defaultGame;
    public static double resolution = 0.66;
    //    public static double resolution = 1;
    public static final int BG_SIZE_BUG = (int) (35 * resolution);
    public static final int FRAMEHEIGHT = (int) (1080 * resolution);
    public static final int FRAMEWIDTH = (int) (1920 * resolution);
    //    public static final int HP_HEAL_PAPERBATER = 20;
    public static final int PLAYER_HP = 100;
    public static final int VEL_MOVIMENT_PLAYER = (int) (5 * resolution);
    public static final int VEL_MOVIMENT_INFECTED = (int) (5 * resolution);
    public static final int NUM_INFECTATS = 1;
    public static final int TAMANY_OBJECTE_XERINGA = (int) (50 * resolution);
    public static final int PLAYERDMGPATH = 1;
    public static final int THROW_DISTANCE = 200;

    public static void main(String[] args) {
        //Init frame
        frame = new Window(FRAMEWIDTH, FRAMEHEIGHT, "Corona2D_pre_alpha_v0.1");
        frame.configFrame();
        //loading the texture image for the path
        try {
            Infected.PATH_TEXTURE = ImageIO.read(new File("src/Corona2DGamePackage/images/texturacami.png"));
        } catch (IOException ignored) {

        }
//        Menu menu = new Menu("Corona2DMenu");
//        frame.add(menu);
//        menu.start();
        do {
            //Create player
            Player player1 = new Player(FRAMEWIDTH / 2, FRAMEHEIGHT / 2, VEL_MOVIMENT_PLAYER);
            BackgroundMap bg = null;
            if (resolution == 1) {
                bg = new BackgroundMap("src/Corona2DGamePackage/images/16-9bg.png");
            } else if (resolution == 0.66) {
                bg = new BackgroundMap("src/Corona2DGamePackage/images/16-9bgmini.jpg");
            } else {
                bg = new BackgroundMap("src/Corona2DGamePackage/images/16-9bgmini.jpg");

            }
            //creating a couple of enemies
            Infected[] infectedPair = new Infected[NUM_INFECTATS];
            for (int inf = 0; inf < infectedPair.length; inf++) {
                infectedPair[inf] = new Infected((int) (Math.random() * bg.width), (int) (Math.random() * bg.height), VEL_MOVIMENT_INFECTED);
            }
//        infectedPair[0] = new Infected(0,0,10);

            Xeringa xeringa = new Xeringa("src/Corona2DGamePackage/images/xeringa.png", "xeringa", TAMANY_OBJECTE_XERINGA, -1, -1, bg) {
            };//-1 means random-
            Xeringa[] xeringes = {xeringa};
//            GameObj[] xeringes = {xeringa};
//            System.out.println(toiletPaper.X+","+toiletPaper.Y);
            //Create panel
            defaultGame = new GamePanel("Corona2D", player1, infectedPair, xeringes, bg);
            frame.add(defaultGame);
            frame.addMouseListener(new MouseListener() {
                public void mousePressed(MouseEvent me) { }
                public void mouseReleased(MouseEvent me) { }
                public void mouseEntered(MouseEvent me) { }
                public void mouseExited(MouseEvent me) { }
                public void mouseClicked(MouseEvent me) {
                    int x = me.getX();
                    int y = me.getY();
                    if (defaultGame.player.holding != null) {
                        if (defaultGame.player.holding.using == false) {
                            defaultGame.player.holding.using = true;
                        } else {
//                            defaultGame.fine
                            System.out.println(MouseInfo.getPointerInfo().getLocation().getX() + "," + MouseInfo.getPointerInfo().getLocation().getY());
                            defaultGame.player.holding.throwObj(MouseInfo.getPointerInfo().getLocation());

                        }
                    }
                }
            });
            defaultGame.start();
        } while (defaultGame.running);
    }
}
