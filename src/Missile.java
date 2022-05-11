import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Missile {
    private int x;
    private int y;
    private String direction;
    private BufferedImage missileImgage;
    private boolean isVisible = false;
    private int imageHeight;
    private int imageWidth;

    public Missile(int x, int y, String direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        try {
            missileImgage = ImageIO.read(new File("photos//missile.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageHeight = missileImgage.getHeight();
        imageWidth = missileImgage.getWidth();
    }

    public void drawMissile(Graphics g) {
        g.drawImage(missileImgage, x, y, null);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public boolean getVisible() {
        return isVisible;
    }

    public void setVisible(boolean visibility) {
        isVisible = visibility;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }
}
