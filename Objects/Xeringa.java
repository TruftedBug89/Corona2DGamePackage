package Corona2DGamePackage.Objects;

import Corona2DGamePackage.BaseGame.Game;
import Corona2DGamePackage.Map.BackgroundMap;
import Corona2DGamePackage.Player.Infected;
import Corona2DGamePackage.Player.Player;

import java.awt.*;

public class Xeringa extends GameObj {
    public static int pointsOnPickUp = 1;
    public static int throwSpeed = 10;
    public static boolean throwed = false;

    public Xeringa(String imgpath, String name, int size, int x, int y, BackgroundMap bgmap) {
        super(imgpath, name, size, x, y, bgmap);
        this.throwDistance = Game.THROW_DISTANCE;
    }

    public Xeringa(String[] imgpaths, String name, int size, int x, int y, BackgroundMap bgmap) {
        super(imgpaths, name, size, x, y, bgmap);
        this.throwDistance = Game.THROW_DISTANCE;
    }

    public void use(Player p, Graphics g) {
        int radius = this.throwDistance * 2;
        g.drawOval(((p.xPos - (this.width / 2)) - (radius)) + (p.width / 2), ((p.yPos - (p.width / 2)) - (radius)) + (p.width / 2), radius * 2, radius * 2);


    }


    public void throwObj(Point mouseCoordinates) {
        if (!this.using)this.using = true;

        System.out.println("xertinga");
        //animation of throw
        //getpoints


    }

    public void hitInfected(Infected inf) {
//        inf.heal();

    }
}
