import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame {
    private CardLayout card;
    private JPanel cards;
    private MenuPanel menuPanel;

    public MainFrame() throws HeadlessException, LineUnavailableException, UnsupportedAudioFileException, IOException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(500, 100, 500, 500);

        card = new CardLayout();
        cards = new JPanel();
        cards.setLayout(card);
        menuPanel = new MenuPanel(card, cards);

        cards.add(menuPanel);
        add(cards);
        setVisible(true);

        File bmusic = new File("sound//backgroundMusic.wav");
        Clip backgroundMusic = AudioSystem.getClip();
        backgroundMusic.open(AudioSystem.getAudioInputStream(bmusic));
        backgroundMusic.start( );
    }
}

//180 260