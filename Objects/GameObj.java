package Corona2DGamePackage.Objects;

import Corona2DGamePackage.BaseGame.Game;
import Corona2DGamePackage.Map.BackgroundMap;
import Corona2DGamePackage.Player.Infected;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public abstract class GameObj {
    public Image image;
    public Image[] animation;
    public int animationCount;
    public BackgroundMap bg;
    public int height;
    public int width;
    public int X;
    public String name;
    public int Y;
    public boolean pickedUp;
    public boolean using;
    public int throwDistance;


    public GameObj(String name, int size, int x, int y, BackgroundMap bgmap) {
        this.height = (int) (size * Game.resolution);
        this.width = (int) (size * Game.resolution);
        this.bg = bgmap;

//        if (x == -1 && y == -1) this.setRandomPostionInBg();
        if (x == -1 && y == -1) this.Y = this.X = 600;
        else {

            this.Y = y;
            this.X = x;
        }
        this.using = false;
        this.name = name;
        this.pickedUp = false;

    }

    public GameObj(String imgpath, String name, int size, int x, int y, BackgroundMap bgmap) {//single image
        this(name, size, x, y, bgmap);
        loadImages(imgpath, null);

    }

    public GameObj(String[] imgpaths, String name, int size, int x, int y, BackgroundMap bgmap) {//multiple images
        this(name, size, x, y, bgmap);
        loadImages(null, imgpaths);
    }


    public void loadImages(String path, String[] paths) {
        if (path.equals(null)) {
            this.animation = new Image[paths.length];
            this.animationCount = 0;
            for (int frame = 0; frame < paths.length; frame++) {
                Image img;
                try {
                    img = ImageIO.read(new File(paths[frame]));
                    this.animation[frame] = img;
                } catch (Exception i) {
                    System.out.println("Can't find object image" + paths[frame]);
                }
            }
        } else {
            Image img;
            try {
                img = ImageIO.read(new File(path));
                this.image = img;
            } catch (Exception i) {
                System.out.println("Can't find object image" + path);
            }
        }
    }


    public void drawObject(Graphics g, JPanel panel) {

        if (this.pickedUp) return;
        if (this.image == null) {
            g.drawImage(this.animation[animationCount], this.X, this.Y, this.width, this.height, panel);
            this.animationCount = this.animationCount == (this.animation.length - 1) ? this.animationCount = 0 : animationCount++;

        } else {
            int x = this.X + (Game.FRAMEWIDTH * bg.codeZone[0]);
            int y = this.Y + (Game.FRAMEHEIGHT * bg.codeZone[1]);
//            System.out.println("Drawing "+this.name+" at x:"+x+" and y:"+y);

            g.drawImage(this.image, x, y, this.width, this.height, panel);
        }
    }


    public void setPostion(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    public void setRandomPostionInBg() {
        double random = Math.random();
        this.setPostion((int) (random * (this.bg.width)), (int) (random * (this.bg.height)));
        System.out.println("obj new abstract pos: " + this.X + "y : " + this.Y);
    }


    public boolean objectCollision(int playerX, int playerY, BackgroundMap bg) {
        int x = this.X;
        int y = this.Y;
        int relx = playerX + (Game.FRAMEWIDTH * bg.codeZone[0]);
        int rely = playerY + (Game.FRAMEWIDTH * bg.codeZone[1]);
        return Math.sqrt(Math.pow(relx - x, 2) + Math.pow(rely - y, 2)) < (this.height);
    }

    @Override
    public String toString() {
        return this.name;
    }

//    public void hitInfected(Infected each) {
//        int x = this.X;
//        int y = this.Y;
//        int relx = each.xPos + (Game.FRAMEWIDTH * bg.codeZone[0]);
//        int rely = each.yPos + (Game.FRAMEWIDTH * bg.codeZone[1]);
//        return Math.sqrt(Math.pow(relx - x, 2) + Math.pow(rely - y, 2)) < (this.height);
//    }
}
