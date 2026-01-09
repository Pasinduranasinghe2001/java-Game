package Mini_Game;

import javax.swing.*;
import java.awt.*;

public class Page {

    public Page(String name) {
        //Button 1
        JButton button1 = new JButton("START");
        button1.setFont(new Font("Arial", Font.BOLD, 24));
        button1.setForeground(Color.WHITE);
        button1.setBackground(new Color(0, 0, 180));
        button1.setFocusPainted(false);
        button1.setBounds(280, 200, 200, 80);
        button1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

        //Button 2
        JButton button2 = new JButton("EXIT");
        button2.setFont(new Font("Arial", Font.BOLD, 24));
        button2.setForeground(Color.WHITE);
        button2.setBackground(new Color(0, 0, 180));
        button2.setFocusPainted(false);
        button2.setBounds(280, 300, 200, 80);
        button2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

        //Panel
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.GRAY);
        panel.add(button1);
        panel.add(button2);

        //Frame
        JFrame frame = new JFrame(name);
        frame.setSize(800, 700);
        frame.setLocation(400, 50);
        frame.setLayout(new BorderLayout());
        frame.add(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        button2.addActionListener(e -> frame.dispose());
        button1.addActionListener(e -> {
            frame.dispose();
            new Game("Gold Bomb");
        });
    }
}
