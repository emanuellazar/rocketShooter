import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Rocket extends Character {
    private BufferedImage rocketImgage;
    private final int imageHeight;
    private final int imageWidth;

    public Rocket(int xPosition, int yPosition, boolean visibility, boolean canShoot, int lives) {
        super(xPosition, yPosition, visibility, canShoot, lives);
        try {
            rocketImgage = ImageIO.read(new File("photos//rocket.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageHeight = rocketImgage.getHeight();
        imageWidth = rocketImgage.getWidth();
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
        drawRocket(g);
    }

    private void drawRocket(Graphics g) {
        g.drawImage(rocketImgage, xPosition, yPosition, null);
    }
}
