package Mini_Game;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Random;
import javax.sound.sampled.*;
import java.io.IOException;

public class Game {
    JFrame frame;
    JLabel label;
    JButton button;
    JPanel panel1;
    JPanel panel2;

    ImageIcon Gold;
    ImageIcon Bomb;

    JButton currentGold_button;
    JButton currentBomb_button;

    Random random = new Random();

    Timer setBombTimer;
    Timer setGoldTimer;

    int score = 0;

    Clip bgClip;
    Clip sfxClip;

    JButton restart = new JButton("RESTART");
    JButton exit = new JButton("EXIT");

    public Game(String GameName) {

        // --- Frame ---
        frame = new JFrame(GameName);
        frame.setSize(800, 700);
        frame.setLocation(400, 50);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        // Load Images
        ImageIcon bomb = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Mini_Game/Bomb.png")));
        ImageIcon gold = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Mini_Game/Gold.png")));
        Bomb = new ImageIcon(bomb.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
        Gold = new ImageIcon(gold.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));

        // --- Label Panel  ---
        panel1 = new JPanel(null);
        panel1.setPreferredSize(new Dimension(800, 100));
        label = new JLabel("SCORE: 0", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 40));
        panel1.add(label);
        label.setBounds(200, 10, 400, 100);
        panel1.setBackground(Color.GRAY);

        // --- Button Panel ---
        panel2 = new JPanel(new GridLayout(3, 3));
        JButton[] buttons = new JButton[9];
        for (int i = 0; i < 9; i++) {
            button = new JButton();
            buttons[i] = button;
            panel2.add(button);
            button.setFocusable(false);
            button.addActionListener(e -> {
                JButton clicked = (JButton) e.getSource();
                if (clicked == currentGold_button) {
                    score += 50;
                    label.setText("SCORE:" + score);
                    playSound("/Click.wav", false);
                } else if (clicked == currentBomb_button) {
                    label.setText("Game Over! " + score);
                    setBombTimer.stop();
                    setGoldTimer.stop();

                    //After Game Over Enable 2 Buttons
                    panel1.add(restart);
                    panel1.add(exit);
                    exit.setFont(new Font("Arial", Font.BOLD, 24));
                    exit.setForeground(Color.RED);
                    exit.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
                    restart.setFont(new Font("Arial", Font.BOLD, 24));
                    restart.setForeground(Color.RED);
                    restart.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
                    restart.setBounds(5, 10, 150, 80);
                    exit.setBounds(630, 10, 150, 80);
                    exit.addActionListener(k -> frame.dispose());
                    restart.addActionListener(k -> {
                        frame.dispose();
                        new Game("Gold Bomb");
                    });

                    if (bgClip != null && bgClip.isRunning()) {
                        bgClip.stop();
                        bgClip.close();
                    }

                    for (JButton b : buttons) {
                        b.setEnabled(false);
                    }
                }
            });
        }
        panel2.setBackground(Color.BLUE);

        //Timers
        setBombTimer = new Timer(500, e -> {
            if (currentBomb_button != null) {
                currentBomb_button.setIcon(null);
                currentBomb_button = null;
            }
            int number = random.nextInt(9);
            JButton button = buttons[number];
            if (currentGold_button == button) return;
            button.setIcon(Bomb);
            currentBomb_button = button;
        });

        setGoldTimer = new Timer(600, e -> {
            if (currentGold_button != null) {
                currentGold_button.setIcon(null);
                currentGold_button = null;
            }
            int number = random.nextInt(9);
            JButton button = buttons[number];
            if (currentBomb_button == button) return;
            button.setIcon(Gold);
            currentGold_button = button;
        });

        // --- Add panels to frame ---
        frame.add(panel1, BorderLayout.NORTH);
        frame.add(panel2, BorderLayout.CENTER);
        frame.setVisible(true);

        setBombTimer.start();
        setGoldTimer.start();

        // play background music
        playSound("/BackGround.wav", true);
    }

    public void playSound(String resourcePath, boolean isBackground) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    Objects.requireNonNull(getClass().getResource(resourcePath))
            );
            Clip newClip = AudioSystem.getClip();
            newClip.open(audioInputStream);

            if (isBackground) {
                bgClip = newClip;
                bgClip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                sfxClip = newClip;
                sfxClip.start();
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
