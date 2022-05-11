import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class RocketController implements Runnable {
    private final Rocket rocket;
    private final GamePanel gamePanel;
    private final Missile[] rocketMissles;

    public RocketController(Rocket rocket, Missile[] rocketMissles, GamePanel gamePanel) {
        this.rocket = rocket;
        this.gamePanel = gamePanel;
        this.rocketMissles = rocketMissles;
    }

    @Override
    public void run() {

        Action shootOnSpace = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                int s = accesibleMissle();
                rocketMissles[s].setX(rocket.getXPosition() + 20);
                rocketMissles[s].setY(rocket.getYPosition() - 20);
                rocketMissles[s].setVisible(true);
                makeSound("sound//rocketshooting.wav");
            }
        };

        Action moveLeft = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                int x = rocket.getXPosition();
                if (x > 5) {
                    rocket.setPosition(x - 8, rocket.getYPosition());
                    gamePanel.revalidate();
                    gamePanel.repaint();
                }
            }
        };
        Action moveRight = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                int x = rocket.getXPosition();
                if (x < 440) {
                    rocket.setPosition(x + 8, rocket.getYPosition());
                    gamePanel.revalidate();
                    gamePanel.repaint();
                }
            }
        };
        gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"),
                "shootOnSpace");
        gamePanel.getActionMap().put("shootOnSpace", shootOnSpace);
        gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"),
                "moveLeft");
        gamePanel.getActionMap().put("moveLeft", moveLeft);
        gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"),
                "moveRight");
        gamePanel.getActionMap().put("moveRight", moveRight);
    }

    private int accesibleMissle() {
        Random rand = new Random();
        int r;
        do{
            r = rand.nextInt(10);
        }while (rocketMissles[r].getVisible());
        return r;
    }

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
}
