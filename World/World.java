

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;

public class World extends JPanel {
    
    private int generation;

    public static final int WIDTH = 1002;
    public static final int HEIGHT = 532;

    private static BufferedImage image;

    public static Map map = null;

    public World(List<City> cities) {
        this.map = new Map(10, cities);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.DARK_GRAY);
        
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                for (City city : HelperForm.cities) {
                    g2d.setColor(Color.BLUE); 
                    g2d.fillOval(city.getX(), city.getY(), 5, 5); 
                }
            }
        };

        add(panel);
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

}
