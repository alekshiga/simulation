import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.WindowConstants;

public class Main {

    public static int counter = 0;
    public static void main(String[] args) {

        SwingUtilities.invokeLater(HelperForm::new);

        // Создаем SwingWorker для создания второго окна
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                // ждём, пока в HelperForm.cities будет 10 точек
                while (HelperForm.cities.size() < 10) {
                    Thread.sleep(100); // задержка, чтобы не блокировать главный поток
                }
                return null;
            }
            
            @Override
            protected void done() {
                // Создаем второе окно после того, как HelperForm.cities достигнет размера 10
                JFrame frame = new JFrame("World");
                frame.setSize(World.WIDTH, World.HEIGHT);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);

                frame.add(new World(HelperForm.cities));
                frame.setVisible(true);

                // используем таймер, чтобы перерисовывать окно
                Timer timer = new Timer(15, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        World.map.update();
                        frame.repaint();
                        System.out.println(World.map.bestSolution().calculateTotalDistance());
                    }
                });
                timer.start();
            }
        };
        worker.execute();
    }
}
