package World;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;

public class World extends JPanel {
    
    private int generation;

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    public static Map map;

    public World() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.DARK_GRAY);
        this.generation = 0;
        this.map = new Map(100, Helper.cities);
    }

    @Override
    public void paintComponent(final Graphics graphics) {
        super.paintComponent(graphics); // Выполнение стандартной логики рисования
        final Graphics2D g = (Graphics2D) graphics;
        g.setColor(Color.CYAN);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.generation++;
        g.drawString("generation: " + (this.generation), 575, 15);
        g.drawString("shortest way: " + map.bestSolution().calculateTotalDistance(), 900, 15);
        drawShortestWay();
    } 

    private void drawShortestWay() {
    Graphics2D g = (Graphics2D) getGraphics();
    // Например, если кратчайший путь хранится в виде списка координат:
    Way shortestWay = map.bestSolution(); // Добавьте метод getShortestPath() в класс Map
    if (shortestWay != null && !shortestWay.getPath().isEmpty()) {
        g.setColor(Color.white); 
        for (int i = 0; i < shortestWay.getPath().size() - 1; i++) {
            City city1 = shortestWay.getPath().get(i);
            City city2 = shortestWay.getPath().get(i + 1);
            g.drawLine(city1.getX(), city1.getY(), city2.getX(), city2.getY()); // Используйте drawLine для рисования линий пути
        }
    }
}

    public static void main(String[] args) throws InterruptedException {
        
        JFrame frame = new JFrame("World map");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); // Разместить окно по центру
        frame.add(new World());
        frame.setVisible(true);

        // Используем таймер, чтобы перерисовывать окно
        Timer timer = new Timer(5, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map.update();
                frame.revalidate();

            }
        });
        timer.start();
    }
}
