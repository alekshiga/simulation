package World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.management.RuntimeErrorException;

public class TSPWay {
    
    private final List<TSPCity> path;

    TSPWay(final List<TSPCity> path) {
        this.path = path;
    }

    // Метод возвращает перемешанный список городов
    // Для нахождения нового случайного пути
    public TSPWay create(final TSPCity[] points) {
        final List<TSPCity> cities = Arrays.asList(Arrays.copyOf(points, points.length));
        Collections.shuffle(cities);
        return new TSPWay(cities);
    }

    // Отладочный метод, возвращающий список городов в пути в формате строки
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (TSPCity city : this.path) {
            builder.append(city.toString()).append(" : ");
        }
        return builder.toString();
    }

    List<TSPCity> getPath() {
        return this.path;
    }

    public double calculateTotalDistance() {   // Подсчёт итоговой длины пути между городами
        double sum = 0.0;
        for (int i = 0; i < this.path.size() - 1; ++i) {
            sum += this.path.get(i).getDistance(this.path.get(i + 1));
        }
        return sum;
    }

    TSPWay[] crossover(final TSPWay other) {

        // разделяет два пути this.path и other.getPath() на две части
        final List<TSPCity>[] myList = TSPHelper.split(this.path); 
        final List<TSPCity>[] otherList = TSPHelper.split(other.getPath()); 
        
        // создание новых списков, которые изначально заполняются 
        // первой частью первого пути и второй частью второго пути
        final List<TSPCity> crossoverOne = new ArrayList<>(myList[0]);
        final List<TSPCity> crossoverTwo = new ArrayList<>(otherList[1]);

        // перебором в crossoverOne и crossoverTwo добавляются оставшиеся города
        // из otherList[0] и otherList[1] и из myList[0] и myList[1],
        // но только те города, которые еще не входят в эти списки
        for (TSPCity city : otherList[0]) {
            if (!crossoverOne.contains(city)) {
                crossoverOne.add(city);
            }
        }
        
        for (TSPCity city : otherList[1]) {
            if (!crossoverOne.contains(city)) {
                crossoverOne.add(city);
            }
        }

        for (TSPCity city : myList[0]) {
            if (!crossoverTwo.contains(city)) {
                crossoverTwo.add(city);
            }
        }

        for (TSPCity city : myList[1]) {
            if (!crossoverTwo.contains(city)) {
                crossoverTwo.add(city);
            }
        }

        if (!TSPHelper.isValid(crossoverOne) || !TSPHelper.isValid(crossoverTwo)) {  // проверка валидности сгенерированного пересечения
            throw new RuntimeException("Невалидное пересечение маршрутов");  // городов в пересечении должно быть столько же, сколько всего существует городов
        }

        return new TSPWay[] { new TSPWay(crossoverOne), 
                            new TSPWay(crossoverTwo) };
    }

    TSPWay mutate() {
        // создаем копию существующего пути
        final List<TSPCity> copy = new ArrayList<>(this.path);

        // генерируем 2 случайных индекса
        int a = TSPHelper.random.nextInt(copy.size());
        int b = TSPHelper.random.nextInt(copy.size());

        // генерируем новые, пока они совпадают
        while (a == b) {
            a = TSPHelper.random.nextInt(copy.size());
            b = TSPHelper.random.nextInt(copy.size());
        }
        
        // случайным образом меняем элементы местами, чтобы получить новый случайный путь
        Collections.swap(copy, a, b);
        return new TSPWay(copy);
    }

}
