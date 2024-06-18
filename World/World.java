package World;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;

public class World extends JPanel {
    
    private int generation;

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    public World() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.DARK_GRAY);
        this.generation = 0;
    }

    @Override
    public void paintComponent(final Graphics graphics) {
        super.paintComponent(graphics);
        final Graphics2D g = (Graphics2D) graphics;
        g.setColor(Color.CYAN);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.generation++;
        g.drawString("generation: " + (this.generation), 575, 15);

        // дополнить логику рисования путей
    } 

    public static void main(String[] args) throws InterruptedException {
        
        JFrame frame = new JFrame("World map");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); // разместить по центру
        frame.add(new World());
        frame.setVisible(true);

        // Используем таймер, чтобы перерисовывать окно
        Timer timer = new Timer(5, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.repaint();
            }
        });
        timer.start();
    }
}
