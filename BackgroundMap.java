package Corona2DGamePackage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class BackgroundMap {
    Image img;
    String strZone;
    int[] codeZone;
    String properties;
    int width, height;

    public BackgroundMap(String path) {

        this.loadBGimg(path);

        this.codeZone = new int[]{0, 0};
        this.properties = null;
        this.strZone = "[" + this.codeZone[0] + "," + this.codeZone[1] + "]";
    }
    public String strZone(){
        return this.strZone = "[" + this.codeZone[0] + "," + this.codeZone[1] + "]";

    }
    public boolean changeZone(int axis0xand1y, int value) {
        switch (axis0xand1y) {
            case 1:
                if ((this.codeZone[axis0xand1y] + value) < 1 && (this.codeZone[axis0xand1y] + value) > -(this.height / Game.FRAMEHEIGHT)) {
                    this.codeZone[axis0xand1y] += value;

                    return true;
                }
                return false;
            case 0:
                if ((this.codeZone[axis0xand1y] + value) < 1 && (this.codeZone[axis0xand1y] + value) > -(this.width / Game.FRAMEWIDTH)) {
                    this.codeZone[axis0xand1y] += value;

                    return true;
                }
                return false;
        }

        return false;

    }

    public void loadBGimg(String path) {
        try {
            this.img = ImageIO.read(new File(path));
        } catch (Exception e) {
            System.out.println("Can't find background image");
        }
        this.width = this.img.getWidth(null);
        this.height = this.img.getHeight(null);
    }

    //this method checks if a zone is correct or not, to respect background boundaries
    //axis are inverted to the usual, they work like they should basically 0 is X and 1 Y
//    return true;
    public void draw(Graphics g) {

        g.drawImage(this.img, this.codeZone[0] * Game.FRAMEWIDTH, this.codeZone[1] * Game.FRAMEHEIGHT, null);
//        g.drawImage(this.bg.img, 0, 0, Game.FRAMEWIDTH, Game.FRAMEHEIGHT-Game.SIZEBUG, null, this);
        g.drawString(this.strZone(), 10, 20);
    }

}
