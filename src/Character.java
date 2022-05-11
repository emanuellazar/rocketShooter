import java.awt.*;

public class Character implements CharacterInterface {

    protected int xPosition;
    protected int yPosition;
    protected boolean visibility;
    protected boolean canShoot;
    protected int lives;

    protected int colorID;

    public Character(int xPosition, int yPosition, boolean visibility, boolean canShoot, int lives, int colorId) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.visibility = visibility;
        this.canShoot = canShoot;
        this.lives = lives;
        this.colorID = colorId;
    }

    public Character(int xPosition, int yPosition, boolean visibility, boolean canShoot, int lives) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.visibility = visibility;
        this.canShoot = canShoot;
        this.lives = lives;
    }


    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public void setPosition(int x, int y) {
        xPosition = x;
        yPosition = y;
    }

    public boolean getShootingAbility() {
        return canShoot;
    }

    public void setShootingAbility(boolean canShoot) {
        this.canShoot = canShoot;
    }

    public boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visible) {
        visibility = visible;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getLives() {
        return lives;
    }

    public void draw(Graphics g) {
        this.draw(g);
    }

    public int getColorID() {
        return colorID;
    }
}
