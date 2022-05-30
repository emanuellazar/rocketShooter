import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class AlienController implements Runnable {
    private final Alien[] aliens;
    private final GamePanel gamePanel;
    private final Missile[] alienMissiles;
    private final Rocket rocket;
    private final Missile[] rocketMissiles;
    private final Thread rocketThread;
    private final Thread[] rocketMissileThreads;
    private final Thread[] alienMissileThreads;
    private final JLabel gameOverLabel;
    private final JLabel youWinLabel;
    private final JLabel[] rocketLives;
    private final JLabel scoreLabel;
    private int aliensAlive = 44;
    private int score = 0;

    public AlienController(Alien[] aliens, GamePanel gamePanel, Missile[] alienMissiles, Rocket rocket, Missile[] rocketMissiles, JLabel gameOverLabel,
                           JLabel youWinLabel, JLabel[] rocketLives, JLabel scoreLabel, Thread rocketThread, Thread[] rocketMissileThreads, Thread[] alienMissileThreads) {
        this.aliens = aliens;
        this.gamePanel = gamePanel;
        this.alienMissiles = alienMissiles;
        this.rocket = rocket;
        this.rocketMissiles = rocketMissiles;
        this.gameOverLabel = gameOverLabel;
        this.youWinLabel = youWinLabel;
        this.rocketLives = rocketLives;
        this.scoreLabel = scoreLabel;
        this.rocketThread = rocketThread;
        this.rocketMissileThreads = rocketMissileThreads;
        this.alienMissileThreads = alienMissileThreads;
    }

    @Override
    public void run() {
        Random rand = new Random();
        int shoot = 0, r;
        int movingDistance = 2;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 65; j++) {
                // megnezzuk, ha utkoztek-e a lovedekek mas objektumokkal
                checkForCollisionWithRocket();
                checkForCollisionWithAliens();
                //ha mar nincs urleny, nyertunk, ezert a youWin labelt jelenitunk meg
                if(aliensAlive == 0){
                    for (int h = 0; h < 3; h++) {
                        rocketLives[h].setVisible(false);
                    }
                    stopAllThreads();
                    youWinLabel.setVisible(true);
                    return;
                }
                //ha 3x meghaltunk vesztettunk, ezert a gameOver labelt megjelenitjuk
                if (rocket.getLives() == 0)
                {
                    stopAllThreads();
                    gameOverLabel.setVisible(true);
                    return;
                }
                //az urlenyek adott idokozonkent lonek
                shoot++;
                if (shoot % 15 == 0 && aliensAlive != 0) {
                    do {
                        r = rand.nextInt(44);
                    }while (!aliens[r].getShootingAbility());
                    int s = accesibleMissle();
                    alienMissiles[s].setX(aliens[r].getXPosition() + 20);
                    alienMissiles[s].setY(aliens[r].getYPosition() + 20);
                    alienMissiles[s].setVisible(true);
                }
                //mozgatjuk az urlenyeket vizszintesen
                for (Character character : aliens) {
                    character.setPosition(character.getXPosition() + movingDistance, character.getYPosition());
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    System.out.println("Hiba az alienoCntroller thread-ben urlenyek mozgatasakor vizszintes iranyba");
                }
                gamePanel.repaint();
            }
            //mozgatjuk az urlenyeket fuggolegesen
            for (int j = 0; j < 10; j++) {
                checkForCollisionWithRocket();
                checkForCollisionWithAliens();
                for (Character character : aliens) {
                    character.setPosition(character.getXPosition(), character.getYPosition() + 2);
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    System.out.println("Hiba az alienoCntroller thread-ben urlenyek mozgatasakor fuggoleges iranyba");
                }
                gamePanel.repaint();
            }
            gamePanel.repaint();
            movingDistance *= -1;
        }
        // the aliens got to the bottom of the panel
        stopAllThreads();
        gameOverLabel.setVisible(true);
    }

    //egy nem hasznalt lovedek sorszamat visszateritjuk
    private int accesibleMissle() {
        Random rand = new Random();
        int r;
        do{
            r = rand.nextInt(10);
        }while (alienMissiles[r].getVisible());
        return r;
    }

    //checks if a given missle is colliding with the rocket
    private boolean isCollidingWithRocket(Missile missile){
        Rectangle r = new Rectangle(rocket.getXPosition() + 10, rocket.getYPosition(), rocket.getImageWidth() - 20, rocket.getImageHeight());
        Rectangle m = new Rectangle(missile.getX(), missile.getY(), missile.getImageWidth(), missile.getImageHeight());
        return r.intersects(m) || m.intersects(r);
    }

    //checks is any of the missles are colliding with the rocket
    private void checkForCollisionWithRocket()
    {
        for (Missile i : alienMissiles){
            if(i.getVisible()){
                if(isCollidingWithRocket(i))
                {
                    rocket.setLives(rocket.getLives()-1);
                    rocketLives[rocket.getLives()].setVisible(false);
                    i.setVisible(false);
                    makeSound("sound//rocketshot.wav");
                }
            }
        }
    }

    //checks if a given missle is colliding a given alien
    private boolean isCollidingWithAlien(Alien alien, Missile missile){
        Rectangle a = new Rectangle(alien.getXPosition(), alien.getYPosition(), alien.getImageWidth(), alien.getImageHeight());
        Rectangle m = new Rectangle(missile.getX(), missile.getY(), missile.getImageWidth(), missile.getImageHeight());
        return a.intersects(m) || m.intersects(a);
    }

    //checks is any of the missles are colliding with the rocket
    private void checkForCollisionWithAliens()
    {
        for (int i = 0; i<aliens.length; i++){
            for(Missile j : rocketMissiles)
                if(j.getVisible() && aliens[i].getVisibility()){
                    if(isCollidingWithAlien(aliens[i],j))
                    {
                        j.setVisible(false);
                        aliens[i].setVisibility(false);
                        aliens[i].setShootingAbility(false);
                        aliensAlive--;
                        int l = i - 11;
                        while (l >= 0 && !aliens[l].getVisibility()) {
                            l -= 11;
                        }
                        if(l > 0) {
                            aliens[l].setShootingAbility(true);
                        }
                        makeSound("sound//aliendyeing.wav");
                        score += aliens[i].getColorID();
                        scoreLabel.setText("Score: " + score);
                    }
                }
        }
    }

    //given the path to the sound, this function starts playing it
    private void makeSound(String path)
    {
        File file = new File(path);
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        }
        try {
            clip.open(AudioSystem.getAudioInputStream(file));
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
            ex.printStackTrace();
        }
        clip.start( );
    }

    private void stopAllThreads(){
        for(Thread rocketMissileThread : rocketMissileThreads){
            rocketMissileThread.interrupt();
        }
        for(Thread alienMissileThread : alienMissileThreads){
            alienMissileThread.interrupt();
        }
        rocketThread.interrupt();
    }
}
