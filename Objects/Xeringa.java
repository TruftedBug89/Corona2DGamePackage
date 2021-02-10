package Corona2DGamePackage.Objects;

import Corona2DGamePackage.BaseGame.Game;
import Corona2DGamePackage.Map.BackgroundMap;
import Corona2DGamePackage.Player.Infected;
import Corona2DGamePackage.Player.Player;

import javax.swing.*;
import java.awt.*;

public class Xeringa extends GameObj {
    public static int pointsOnPickUp = 1;
    public static int throwSpeed = 1;//has to be one because if x == finishlocation then stop, it could get skiped if finishlocation is not pair in case it was 2 or smth
    public int throwXSpeed;
    public int throwYSpeed;
    //    public  boolean throwed = false;
    public int[] startEndCoords;

    public Xeringa(String imgpath, String name, int size, int x, int y, BackgroundMap bgmap) {
        super(imgpath, name, size, x, y, bgmap);
        this.throwDistance = Game.THROW_DISTANCE;
    }

    public Xeringa(String[] imgpaths, String name, int size, int x, int y, BackgroundMap bgmap) {
        super(imgpaths, name, size, x, y, bgmap);
        this.throwDistance = Game.THROW_DISTANCE;
    }

    public void use(Player p, Graphics g) {
        int radius = this.throwDistance;
        g.drawOval(((p.xPos - (this.width / 2)) - (radius)) + (p.width / 2), ((p.yPos - (p.width / 2)) - (radius)) + (p.width / 2), radius * 2, radius * 2);


    }

    @Override
    public void drawObject(Graphics g, JPanel panel) {

        if (!this.pickedUp) {

            if (this.image == null) {
                g.drawImage(this.animation[animationCount], this.X, this.Y, this.width, this.height, panel);
                this.animationCount = this.animationCount == (this.animation.length - 1) ? this.animationCount = 0 : animationCount++;

            } else {
                int x = this.X + (Game.FRAMEWIDTH * bg.codeZone[0]);
                int y = this.Y + (Game.FRAMEHEIGHT * bg.codeZone[1]);
                g.drawImage(this.image, x, y, this.width, this.height, panel);
            }
        }
        if (this.using) {
            int x = this.X + (Game.FRAMEWIDTH * bg.codeZone[0]);
            int y = this.Y + (Game.FRAMEHEIGHT * bg.codeZone[1]);
            if (x == this.startEndCoords[2] && y == this.startEndCoords[3]) {
                this.using = false;
            } else {
                g.drawImage(this.image, x, y, this.width, this.height, panel);
                this.X += (x == this.startEndCoords[2]) ? 0 : this.throwXSpeed;
                this.Y += (y == this.startEndCoords[3]) ? 0 : this.throwYSpeed;
            }


        }
    }

    public void throwObj(int playerx, int playery, Point mouseCoordinates) {
        if (!this.using) {
            if ( Math.sqrt(Math.pow(playerx - mouseCoordinates.getX(), 2) + Math.pow(playery - mouseCoordinates.getY(), 2)) > (this.throwDistance))return;
            this.using = true;
            this.startEndCoords = new int[]{playerx + (Game.FRAMEWIDTH * bg.codeZone[0]),
                    playery + (Game.FRAMEHEIGHT * bg.codeZone[1]),
                    (int) mouseCoordinates.getX() + (Game.FRAMEWIDTH * bg.codeZone[0]),
                    (int) mouseCoordinates.getY() + (Game.FRAMEHEIGHT * bg.codeZone[1])};//
            this.X = this.startEndCoords[0];
            this.Y = this.startEndCoords[1];
            this.throwXSpeed = (this.startEndCoords[2] > this.startEndCoords[0]) ? throwSpeed : -throwSpeed;
            this.throwYSpeed = (this.startEndCoords[3] > this.startEndCoords[1]) ? throwSpeed : -throwSpeed;
        } else {

        }

//        System.out.println("xertinga");
        //animation of throw
        //getpoints


    }

    public void hitInfected(Infected inf) {
//        inf.heal();

    }
}
