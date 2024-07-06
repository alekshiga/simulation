import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RectangularShape;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import java.awt.event.MouseEvent;

public class HelperForm extends JFrame {

    static final List<City> cities = new ArrayList<>();

    static final Random random = new Random();

    private static BufferedImage image;
    
    private JPanel drawingPanel;

    public HelperForm() {
        super("Choose Points");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(World.WIDTH, World.HEIGHT);
        setLocationRelativeTo(null);

        try {
            image = ImageIO.read(new File("World/img/russia.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }



        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.RED);

                if (image != null) {
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this); 
                    ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)); // Возвращаем полную непрозрачность
                }

                for (City city : cities) {
                    g2d.fillOval(city.getX(), city.getY(), 5, 5);
                }
            }
        };

        drawingPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (cities.size() < 10) {
                    cities.add(new City(e.getX(), e.getY()));
                    drawingPanel.repaint(); // Перерисовываем форму после добавления точки
                }
                else {
                    // отладка
                    setVisible(false);
                    for (City city : cities) {
                        System.out.println(city.getX() + "," + city.getY());
                    }
                }
            }
        });

        add(drawingPanel);
        setVisible(true);
    }

    static int randomIndex(final int limit) {
        return random.nextInt(limit);
    }

    @SuppressWarnings({ "unchecked" })
    public static List<City>[] split(List<City> list) {
        final List<City> listOne = new ArrayList<>();
        final List<City> listTwo = new ArrayList<>();

        final int size = list.size();

        for (int i = 0; i < size; ++i) {
            if (i < (size + 1) / 2) {
                listOne.add(list.get(i));
            }
            else {
                listTwo.add(list.get(i));
            }
        }

        return new List[] {listOne, listTwo};

    }
}
