

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Way {
    
    private final List<City> path;

    Way(final List<City> path) {
        this.path = path;
    }

    // Метод возвращает перемешанный список городов
    // Для нахождения нового случайного пути
    public static Way create(final City[] data) {
        final List<City> cities = Arrays.asList(Arrays.copyOf(data, data.length));
        Collections.shuffle(cities);
        return new Way(cities);
    }

    // Отладочный метод, возвращающий список городов в пути в формате строки
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (City city : this.path) {
            builder.append(city.toString()).append(" : ");
        }
        return builder.toString();
    }

    List<City> getPath() {
        return this.path;
    }

    public double calculateTotalDistance() {   // Подсчёт итоговой длины пути между городами
        double sum = 0.0;
        for (int i = 0; i < this.path.size() - 1; ++i) {
            sum += this.path.get(i).getDistance(this.path.get(i + 1));
        }
        return sum;
    }

    Way[] crossover(final Way other) {

        // разделяет два пути this.path и other.getPath() на две части
        final List<City>[] myList = Helper.split(this.path); 
        final List<City>[] otherList = Helper.split(other.getPath()); 
        
        // создание новых списков, которые изначально заполняются 
        // первой частью первого пути и второй частью второго пути
        final List<City> crossoverOne = new ArrayList<>(myList[0]);
        final List<City> crossoverTwo = new ArrayList<>(otherList[1]);

        // перебором в crossoverOne и crossoverTwo добавляются оставшиеся города
        // из otherList[0] и otherList[1] и из myList[0] и myList[1],
        // но только те города, которые еще не входят в эти списки
        for (City city : otherList[0]) {
            if (!crossoverOne.contains(city)) {
                crossoverOne.add(city);
            }
        }
        
        for (City city : otherList[1]) {
            if (!crossoverOne.contains(city)) {
                crossoverOne.add(city);
            }
        }

        for (City city : myList[0]) {
            if (!crossoverTwo.contains(city)) {
                crossoverTwo.add(city);
            }
        }

        for (City city : myList[1]) {
            if (!crossoverTwo.contains(city)) {
                crossoverTwo.add(city);
            }
        }

        if (!Helper.isValid(crossoverOne) || !Helper.isValid(crossoverTwo)) {  // проверка валидности сгенерированного пересечения
            throw new RuntimeException("Невалидное пересечение маршрутов");  // городов в пересечении должно быть столько же, сколько всего существует городов
        }

        return new Way[] { new Way(crossoverOne), 
                            new Way(crossoverTwo) };
    }

    Way mutate() {
        // создаем копию существующего пути
        final List<City> copy = new ArrayList<>(this.path);

        // генерируем 2 случайных индекса
        int a = Helper.random.nextInt(copy.size());
        int b = Helper.random.nextInt(copy.size());

        // генерируем новые, пока они совпадают
        while (a == b) {
            a = Helper.random.nextInt(copy.size());
            b = Helper.random.nextInt(copy.size());
        }
        
        // случайным образом меняем элементы местами, чтобы получить новый случайный путь
        Collections.swap(copy, a, b);
        return new Way(copy);
    }

}
