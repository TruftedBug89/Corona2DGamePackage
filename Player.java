package Corona2DGamePackage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;

public class Player {
    public int xPos;
    public int yPos;
    public int points;
    public int hp;
    public int maxHp;
    public int width;
    public int height;
    public GameObj holding = null;
    public int distWalk;
    public Image img;
    public String facing;
    private static Hashtable<String, Image> playerImages = new Hashtable<>();


    public Player(int x, int y, int distWalk) {
        this.hp = Game.PLAYER_HP;
        this.maxHp = this.hp;
        this.xPos = x;
        this.facing  = "forward";
        this.points = 0;
        this.yPos = y;
        loadImages();
        this.updateImage();
        this.distWalk = distWalk;
    }


    public static void loadImages(){
        int[] angleRotations = {0, 180, 90, -90, 45, -45, 135, -135};
        String[] facingList = {"forward", "backward", "right", "left", "upright", "upleft", "downright", "downleft"};
        for (int s = 0; s < facingList.length; s++) {
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File("src/images/player.png"));
            } catch (Exception i) {
                System.out.println("Can't find player image" + facingList[s]);
            }
            double rotationRequired = Math.toRadians (angleRotations[s]);

            double locationX = img.getWidth(null) / 2;
            double locationY = img.getHeight(null) / 2;
            AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            playerImages.put(facingList[s],op.filter(img,null));
        }
    }
    public void getPoints(String action){
        switch (action){
            case "xeringa":
                int puntsPerXeringa = 1;
                this.points += puntsPerXeringa;
                break;
            default:
                break;

        }

    }

    public void updateImage() {
        this.img = playerImages.get(this.facing);
        this.width = (int)(this.img.getWidth(null)*Game.resolution);
        this.height = (int)(this.img.getHeight(null)*Game.resolution);
    }
//    public void checkCoords(int x, int y){
//
//    }
    public void movePlayer(String direction,String facingImg, BackgroundMap bg) {
        this.facing = facingImg;
        this.updateImage();
        switch (direction) {
            case "forward":
                if ( this.yPos - this.distWalk > 0 ) {
                    this.yPos -= this.distWalk;
                } else {
                    if ( bg.changeZone(1, +1) ) {
//                        System.out.println(Game.FRAMEHEIGHT);
                        this.yPos = Game.FRAMEHEIGHT - this.width*2;
                    }
                }

                break;
            case "backward":
                if ( (this.yPos + this.distWalk + this.height + Game.BG_SIZE_BUG) < Game.FRAMEHEIGHT ) {
                    this.yPos += this.distWalk;
                } else {
                    if ( bg.changeZone(1, -1) ) {
                        this.yPos = 0;
                    }

                }
                break;
            case "right":

                if ( (this.xPos + this.distWalk + this.width) < Game.FRAMEWIDTH ) {
                    this.xPos += this.distWalk;
                } else {
                    if ( bg.changeZone(0, -1) ) {
                        this.xPos = 0;
                    }
                }
                break;
            case "left":

                if ( this.xPos - this.distWalk > 0 ) {
                    this.xPos -= this.distWalk;
                } else {

                    if ( bg.changeZone(0, +1) ) {
                        this.xPos = Game.FRAMEWIDTH - this.width;
                    }
                }
                break;
            case "upright":
                this.movePlayer("forward",direction,bg);
                this.movePlayer("right",direction,bg);

                break;
            case "upleft":
                this.movePlayer("forward",direction,bg);
                this.movePlayer("left",direction,bg);

                break;
            case "downright":
                this.movePlayer("backward",direction,bg);
                this.movePlayer("right",direction,bg);

                break;
            case "downleft":
                this.movePlayer("backward",direction,bg);
                this.movePlayer("left",direction,bg);

                break;
        }

    }
    public void drawHpBar(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(1));
        g.setColor(Color.black);
        g.drawString("HP", 30, 860);
        g.drawRect(49, Game.FRAMEWIDTH / 2, this.maxHp * 2, 16);
        Color color = this.hp > this.maxHp/2
                ? new Color(50,205,50,127)
                : new Color(255, 0, 2, 127);
//        Color colorHp = new Color(255, 0, 2, 127);
//        if ( this.hp > this.maxHp/2){
//
//        }
        g.setColor(color);//127 = 50% transparent
        if (this.hp > 0) {
            g.fillRect(50, Game.FRAMEWIDTH / 2, this.hp * 2, 15);
        }
    }
    public void draw(Graphics g) {
        g.drawImage(this.img, this.xPos, this.yPos, this.width, this.height, null,null);
        if ( this.holding != null){
//            this.holding.drawOnPlayer(this.xPos,this.yPos);
//            if(this.holding.using)this.holding.use(this,g);
        }

    }
}
