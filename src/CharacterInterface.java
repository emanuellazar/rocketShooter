import java.awt.*;

public interface CharacterInterface {

    int getXPosition();
    int getYPosition();
    void setPosition(int x, int y);
    boolean getShootingAbility();
    void setShootingAbility(boolean cnShoot);
    void setVisibility(boolean visible);
    boolean getVisibility();
    int getColorID();
    void setLives(int lives);
    int getLives();
    void draw(Graphics g);

}
