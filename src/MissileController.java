import java.util.Objects;

public class MissileController implements Runnable {
    private final Missile missille;
    private final GamePanel gamePanel;

    public MissileController(Missile missile, GamePanel gamePanel) {
        this.missille = missile;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {
        int y;
        String direction;
        direction = missille.getDirection();
        while (!Thread.interrupted()) {
            gamePanel.repaint();
            if (missille.getVisible()) {
                y = missille.getY();
                //moving the rocket missles up
                if (Objects.equals(direction, "up")) {
                    while (missille.getVisible()) {
                        if (y > 0) {
                            y -= 7;
                            missille.setY(y);
                        } else {
                            missille.setVisible(false);
                        }
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            if(!Thread.interrupted())
                                System.out.println("Hiba a misslecontrol thread-ben a rocket misslejainal");
                        }
                    }
                }
                //moving the alien missles down
                if (Objects.equals(direction, "down")) {
                    while (missille.getVisible()) {
                        if (y < 450) {
                            y += 3;
                            missille.setY(y);
                        } else {
                            missille.setVisible(false);
                        }
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            if(!Thread.interrupted())
                                System.out.println("Hiba a missileControler thread-ben az alienek misseljeinel");
                        }
                    }
                }
            }
        }
    }
}
