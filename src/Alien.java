import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Alien extends Character {
    private BufferedImage alienImgage;
    private final int imageHeight;
    private final int imageWidth;

    public Alien(int xPosition, int yPosition, boolean visibility, boolean canShoot, int lives, int colorID) {
        super(xPosition, yPosition, visibility, canShoot, lives, colorID);
        String[] characterChoice = new String[]{"black", "blue", "green", "orange", "pink", "yellow"};
        String fileName = "photos//" + characterChoice[colorID] + ".png";
        try {
            alienImgage = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageHeight = alienImgage.getHeight();
        imageWidth = alienImgage.getWidth();
        setVisibility(true);
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    @Override
    public void draw(Graphics g) {
        drawAlien(g);
    }

    private void drawAlien(Graphics g) {
        g.drawImage(alienImgage, xPosition, yPosition, null);
    }
}
