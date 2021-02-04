package Corona2DGamePackage.Objects;

import Corona2DGamePackage.Map.BackgroundMap;
import Corona2DGamePackage.Player.Infected;
import Corona2DGamePackage.Player.Player;

import java.awt.*;

public class Xeringa extends GameObj {
    public int pointsOnPickUp = 1;
    public Xeringa(String imgpath, String name, int size, int x, int y, BackgroundMap bgmap) {
        super(imgpath, name, size, x, y, bgmap);
    }

    public Xeringa(String[] imgpaths, String name, int size, int x, int y, BackgroundMap bgmap) {
        super(imgpaths, name, size, x, y, bgmap);
    }
    public void use(Player p, Graphics g){
        switch (this.name){
            case "xeringa":
                //draw range
                int radius = this.throwDistance*2;
                g.drawOval(((p.xPos-(this.width/2))-(radius))+(p.width/2),((p.yPos-(p.width/2))-(radius))+(p.width/2),radius*2,radius*2);

                //draw xeringa
                break;

        }
    }

    public void throwObj(){
        System.out.println("xertinga");
        //animation of throw
        //getpoints


    }
    public void hitInfected(Infected inf){
//        inf.heal();

    }
}
