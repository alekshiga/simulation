

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;

public class World extends JPanel {
    
    private int generation;

    public static final int WIDTH = 1002;
    public static final int HEIGHT = 532;

    private static BufferedImage image;

    private static Map map = null;

    public World() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.DARK_GRAY);
        this.map = new Map(50, Helper.cities);
        this.generation = 0;
        try {
            image = ImageIO.read(new File("World/img/russia.jpg"));
        } catch (IOException e) {
            System.err.println("Не удалось загрузить изображение: " + e.getMessage());
        }
    }

    @Override
    public void paintComponent(final Graphics graphics) {
        
        super.paintComponent(graphics); 
        final Graphics2D g = (Graphics2D) graphics;

        if (image != null) {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); // 50% прозрачности
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this); 
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)); // Возвращаем полную непрозрачность
        }

        // Рисуем текст
        g.setColor(Color.CYAN);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.generation++;
        g.drawString("generation: " + (this.generation), 450, 15);
        g.drawString("shortest way: " + String.format("%.2f", map.bestSolution().calculateTotalDistance()), 850, 15);

        drawShortestWay(g); 
    }

    private void drawShortestWay(final Graphics2D g) {
    List<City> shortestWay = map.bestSolution().getPath(); // Добавьте метод getShortestPath() в класс Map
    if (shortestWay != null && !shortestWay.isEmpty()) {
        g.setColor(Color.WHITE);

        for (int i = 0; i < shortestWay.size() - 1; ++i) {
            City city1 = shortestWay.get(i);
            City city2 = shortestWay.get(i + 1);
            g.drawLine(city1.getX(), city1.getY(), city2.getX(), city2.getY()); // Используйте drawLine для рисования линий пути
        }
        g.setColor(Color.RED);
        for (City city : shortestWay) {
            g.fillOval(city.getX(), city.getY(), 5, 5);
        }
    }
}

    public static void main(String[] args) {
        
        JFrame frame = new JFrame("World");

        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); // Разместить окно по центру
        frame.add(new World());
        frame.setVisible(true);

        // Используем таймер, чтобы перерисовывать окно
        Timer timer = new Timer(15, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map.update();
                frame.repaint();

            }
        });
        timer.start();
    }
}
