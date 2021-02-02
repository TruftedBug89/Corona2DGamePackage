package Corona2DGamePackage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Hashtable;

public class Infected {
    static BufferedImage PATH_TEXTURE;
    int xPos, yPos;
    int width, height;
    Image img;
    String facing;
    int distWalk;

    int pathLineSize;
    int directionTimes;//times that moves in the same direction, has to be static to coordinate the collisions and lines to draw of the path
    String[] pathLocations;//x and y positions to create a lines
    String[] pathCollisions;
    int pathCollisionCount;
    int pathLocationCount;
    String[] movements;
    int nextMove = 0;//move count to change the direction
    private static String[] facingList = {"forward", "backwards", "right", "left", "upright", "upleft", "downright", "downleft", "standing"};

    private static Hashtable<String, Image> infectedImages = new Hashtable<>();


    public Infected(int x, int y, int distWalk) {
        this.distWalk = distWalk;
        this.xPos = x;
        this.yPos = y;
        loadImages();
        this.facing = facingList[0];
        this.updateImage();
        this.pathLineSize = (int) (20 * Game.resolution);
        this.directionTimes = (int) ((Math.random() + 1) * this.distWalk) * 5;//random number this has consequences on other variables
        this.movements = new String[this.directionTimes];
        this.pathLocations = new String[5];//number of leaving lines per infected path
        this.pathCollisions = new String[this.pathLocations.length * this.directionTimes];
        this.pathLocationCount = 0;
        this.pathCollisionCount = 0;

    }

    public void updateImage() {
        Image last = null;
        if ( this.facing.equals("standing") ) {
            this.img = infectedImages.get("forward");
        } else {
            this.img = infectedImages.get(this.facing);
        }
        this.width = (int) (this.img.getWidth(null) * Game.resolution);
        this.height = (int) (this.img.getHeight(null) * Game.resolution);
    }

    public static void loadImages() {
        int[] angleRotations = {0, 180, 90, -90, 45, -45, 135, -135};
        for (int s = 0; s < Infected.facingList.length - 1; s++) {
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File("src/images/infected.png"));
            } catch (Exception i) {
                System.out.println("Can't find infected image" + facingList[s]);
            }
            double rotationRequired = Math.toRadians(angleRotations[s]);

            double locationX = img.getWidth(null) / 2;
            double locationY = img.getHeight(null) / 2;
            AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            infectedImages.put(facingList[s], op.filter(img, null));
        }
    }

    public void moveInfected(BackgroundMap bg) {
        if ( this.movements[0] == null || this.nextMove == this.movements.length ) {
            double randomMovement = Math.random();
            Arrays.fill(this.movements, facingList[(int) (randomMovement * facingList.length)]);
            this.nextMove = 0;
            //get point for drawing path line
            if ( this.pathLocationCount == this.pathLocations.length ) {
                //move all locations to the left to make space for the next one
                for (int e = 0; e < this.pathLocationCount - 1; e++) {
                    this.pathLocations[e] = this.pathLocations[e + 1];
                }
                this.pathLocations[this.pathLocationCount - 1] = ((this.xPos + 27) + "," + (this.yPos + 27));

            } else {
                this.pathLocations[this.pathLocationCount] = ((this.xPos + 27) + "," + (this.yPos + 27));
                this.pathLocationCount++;
            }

        }
        this.facing = this.movements[this.nextMove];
        this.nextMove++;
        //this variable is not very consise by its name, it does multple things, it gets the position in the map not on the screen
        int positionRelBgx = ((this.xPos) + (Game.FRAMEWIDTH * bg.codeZone[0]));
        int positionRelBgy = ((this.yPos) + (Game.FRAMEHEIGHT * bg.codeZone[1]));
        switch (this.facing) {
            case "forward":
                if ( positionRelBgy - this.distWalk > 0 && positionRelBgy - this.distWalk < bg.height ) {
                    this.yPos -= this.distWalk;
                }
                break;
            case "backwards":
                if ( positionRelBgy + this.distWalk > 0 && positionRelBgy + this.distWalk < bg.height ) {
                    this.yPos += this.distWalk;
                }

                break;
            case "right":
                if ( positionRelBgx + this.distWalk > 0 && positionRelBgx + this.distWalk < bg.width ) {
                    this.xPos += this.distWalk;
                }
                break;
            case "left":
                if ( positionRelBgx - this.distWalk > 0 && positionRelBgx - this.distWalk < bg.width ) {
                    this.xPos -= this.distWalk;
                }
                break;
            case "upright":
                if ( positionRelBgy - this.distWalk > 0 && positionRelBgy - this.distWalk < bg.height ) {
                    this.yPos -= this.distWalk;
                }
                if ( positionRelBgx + this.distWalk > 0 && positionRelBgx + this.distWalk < bg.width ) {
                    this.xPos += this.distWalk;
                }
                break;
            case "upleft":
                if ( positionRelBgy - this.distWalk > 0 && positionRelBgy - this.distWalk < bg.height ) {
                    this.yPos -= this.distWalk;
                }
                if ( positionRelBgx - this.distWalk > 0 && positionRelBgx - this.distWalk < bg.width ) {
                    this.xPos -= this.distWalk;
                }
                break;
            case "downright":
                if ( positionRelBgy + this.distWalk > 0 && positionRelBgy + this.distWalk < bg.height ) {
                    this.yPos += this.distWalk;
                }
                if ( positionRelBgx + this.distWalk > 0 && positionRelBgx + this.distWalk < bg.width ) {
                    this.xPos += this.distWalk;
                }
                break;
            case "downleft":
                if ( positionRelBgy + this.distWalk > 0 && positionRelBgy + this.distWalk < bg.height ) {
                    this.yPos += this.distWalk;
                }
                if ( positionRelBgx - this.distWalk > 0 && positionRelBgx - this.distWalk < bg.width ) {
                    this.xPos -= this.distWalk;
                }
                break;
        }
        this.updateImage();
        //get collision points for the path
        if ( this.pathCollisionCount == this.pathCollisions.length ) this.pathCollisionCount = 0;
        this.pathCollisions[this.pathCollisionCount] = (this.xPos + "," + this.yPos);
        this.pathCollisionCount++;

    }
    //drawpath is going to be draw every tick so we only draw the last phat coordinates
    public void drawPath(Graphics g, BackgroundMap bg) {
        Graphics2D g2 = (Graphics2D) g;
        TexturePaint tp = new TexturePaint(Infected.PATH_TEXTURE, new Rectangle(0, 0, 20, 20));
        g2.setPaint(tp);
        g2.setStroke(new BasicStroke(this.pathLineSize));
        for (int pos = 0; pos < this.pathLocationCount - 1; pos++) {
            String[] xy = this.pathLocations[pos].split(",");
            String[] xy2 = this.pathLocations[pos + 1].split(",");
            g2.drawLine(Integer.parseInt(xy[0]) + (Game.FRAMEWIDTH * bg.codeZone[0]), Integer.parseInt(xy[1]) + (Game.FRAMEHEIGHT * bg.codeZone[1]), Integer.parseInt(xy2[0]) + (Game.FRAMEWIDTH * bg.codeZone[0]), Integer.parseInt(xy2[1]) + (Game.FRAMEHEIGHT * bg.codeZone[1]));
        }
        String[] xy = this.pathLocations[this.pathLocationCount - 1].split(",");
        g2.drawLine(Integer.parseInt(xy[0]) + (Game.FRAMEWIDTH * bg.codeZone[0]), Integer.parseInt(xy[1]) + (Game.FRAMEHEIGHT * bg.codeZone[1]), this.xPos + (int) (27 * Game.resolution) + (Game.FRAMEWIDTH * bg.codeZone[0]), this.yPos + (int) (27 * Game.resolution) + (Game.FRAMEHEIGHT * bg.codeZone[1]));

    }

    public void drawInfected(Graphics g, BackgroundMap bg) {
        g.drawImage(this.img, this.xPos + (Game.FRAMEWIDTH * bg.codeZone[0]), this.yPos + (Game.FRAMEHEIGHT * bg.codeZone[1]), this.width, this.height, null);
    }
}


