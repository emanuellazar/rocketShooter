import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private final Alien[] aliens;
    private final Rocket rocket;
    private final Missile[] alienMissiles;
    private final Missile[] rocketMissiles;
    private BufferedImage gameOverImage;
    private BufferedImage youWinImage;

    public GamePanel(ArrayList<Integer> alienColours) {
        setSize(500, 500);
        this.setBackground(Color.black);

        //creating the label showing the score
        JLabel scoreLabel = new JLabel("Score:" + 0);
        scoreLabel.setFont(new Font("Courier New", Font.ITALIC, 15));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setVisible(true);
        add(scoreLabel);

        //creating 3 labels with the little hearts
        JLabel[] rocketLives = new JLabel[3];
        for (int i = 0; i < 3; i++) {
            rocketLives[i] = new JLabel();
            rocketLives[i].setIcon(new ImageIcon("photos//heart.png"));
            rocketLives[i].setVisible(true);
            add(rocketLives[i]);
        }

        //creating the game over label
        try {
            gameOverImage =  ImageIO.read(new File("photos//gameover.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image scaledGameOver = gameOverImage.getScaledInstance(460, 460, Image.SCALE_AREA_AVERAGING);
        JLabel gameOverLabel = new JLabel(new ImageIcon(scaledGameOver));
        gameOverLabel.setVisible(false);
        this.add(gameOverLabel, BorderLayout.CENTER);

        //creating the you win label
        try {
            youWinImage =  ImageIO.read(new File("photos//youwin.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image scaledYouWin = youWinImage.getScaledInstance(460, 460, Image.SCALE_AREA_AVERAGING);
        JLabel youWinLabel = new JLabel(new ImageIcon(scaledYouWin));
        youWinLabel.setVisible(false);
        this.add(youWinLabel, BorderLayout.CENTER);

        //creating the rocket missles
        rocketMissiles = new Missile[40];
        MissileController[] rocketMissileControllers = new MissileController[40];
        Thread[] rocketMissleThreads = new Thread[40];
        for (int i = 0; i < 40; i++) {
            rocketMissiles[i] = new Missile(0, 0, "up");
            rocketMissileControllers[i] = new MissileController(rocketMissiles[i], this);
            rocketMissleThreads[i] = new Thread(rocketMissileControllers[i]);
            rocketMissleThreads[i].start();
        }

        //creating the rocket
        rocket = new Rocket(250, 430, true, true, 3);
        RocketController rocketController = new RocketController(rocket, rocketMissiles, this);
        Thread rocketThread = new Thread(rocketController);
        rocketThread.start();

        //creating the allien missles
        alienMissiles = new Missile[10];
        MissileController[] alienMissileControllers = new MissileController[10];
        Thread[] alienMissleThreads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            alienMissiles[i] = new Missile(0, 0, "down");
            alienMissileControllers[i] = new MissileController(alienMissiles[i], this);
            alienMissleThreads[i] = new Thread(alienMissileControllers[i]);
            alienMissleThreads[i].start();
        }

        //creating the alliens
        aliens = new Alien[44];
        int xAlien = 10, yAlien = 50;
        for (int i = 0; i < 44; i++) {
            aliens[i] = new Alien(xAlien, yAlien, true, false, 1, alienColours.get(i));
            xAlien += 30;
            if ((i + 1) % 11 == 0) {
                yAlien += 40;
                xAlien = 10;
            }
            aliens[i].setShootingAbility(i > 32);  //az also sornak adunk lovesi engedelyt
        }

        AlienController alienController = new AlienController(aliens, this, alienMissiles, rocket, rocketMissiles, gameOverLabel, youWinLabel, rocketLives,
                                                                scoreLabel, rocketThread, rocketMissleThreads, alienMissleThreads);
        Thread alienThread = new Thread(alienController);
        alienThread.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Alien i : aliens) {
            if (i.getVisibility()) {
                i.draw(g);
            }
        }
        rocket.draw(g);
        for (Missile i : alienMissiles) {
            if (i.getVisible()) {
                i.drawMissile(g);
            }
        }
        for (Missile i : rocketMissiles) {
            if (i.getVisible()) {
                i.drawMissile(g);
            }
        }
    }
}
