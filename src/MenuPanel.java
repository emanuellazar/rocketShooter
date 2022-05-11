import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class MenuPanel extends JPanel {
    ArrayList<Integer> alienColours;

    public MenuPanel(CardLayout card, JPanel cards) {

        setLayout(null);
        setSize(500, 500);
        setBackground(Color.black);

        //a play gomb letrehozasa
        Icon startIcon = new ImageIcon("photos//play.png");
        JButton startButton = new JButton(startIcon);
        startButton.setBounds(160, 240, 153, 43);
        Border emptyBorder = BorderFactory.createEmptyBorder();
        startButton.setBorder(emptyBorder);
        startButton.setVisible(true);

        //a quit gomb letrehozasa
        Icon quitIcon = new ImageIcon("photos//quit.png");
        JButton quitButton = new JButton(quitIcon);
        quitButton.setBounds(160, 290, 152, 41);
        Border emptyBorder2 = BorderFactory.createEmptyBorder();
        quitButton.setBorder(emptyBorder2);
        quitButton.setVisible(true);

        //a gamename label letrehozasa
        Icon gameNameIcon = new ImageIcon("photos//gamename.png");
        JLabel mainMenuLabel = new JLabel(gameNameIcon);
        mainMenuLabel.setBounds(75, 70, 338, 84);
        mainMenuLabel.setVisible(true);

        add(mainMenuLabel);
        add(startButton);
        add(quitButton);
        mainMenuLabel.setVisible(true);

        alienColours = new ArrayList<>(44);

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //ha a play gombra nyomtunk, eltuntetjuk a panel eddigi elemeit es megjelenitunk 2 gombot
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButton.setVisible(false);
                quitButton.setVisible(false);
                mainMenuLabel.setVisible(false);

                //az randButton gombbal random urleny szineket generalunk
                JButton randButton = new JButton("Randomly generate alien colours");
                randButton.setFont(new Font("Courier New", Font.ITALIC, 15));
                randButton.setBounds(30, 90, 400, 60);
                randButton.setVisible(true);
                add(randButton);

                //a fileButon gombra kattintva kivalaszthatjuk tobb opcio kozul, hogy milyen urlenyeket akarunk
                JButton fileButton = new JButton("Choose aliens from file");
                fileButton.setFont(new Font("Courier New", Font.ITALIC, 15));
                fileButton.setBounds(30, 250, 400, 60);
                fileButton.setVisible(true);
                add(fileButton);

                fileButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser fileChooser = new JFileChooser(new File("aliencolours"));
                        fileChooser.showSaveDialog(null);
                        try {
                            copyToArray(fileChooser.getSelectedFile());
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        cards.add(new GamePanel(alienColours));
                        card.next(cards);
                    }
                });


                randButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Random rand = new Random();
                        for (int i = 0; i < 44; i++) {
                            alienColours.add(rand.nextInt(6));
                        }
                        cards.add(new GamePanel(alienColours));
                        card.next(cards);
                    }
                });
            }

            private void copyToArray(File selectedFile) throws FileNotFoundException {
                File alienFile = new File(selectedFile.getAbsolutePath());
                Scanner in = new Scanner(alienFile);

                for (int i = 0; i < 44; i++) {
                    String data = in.nextLine();
                    alienColours.add(Integer.parseInt(data));
                }
            }
        });
    }

}
