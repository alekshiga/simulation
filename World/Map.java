
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Map {
    
    private final int startSize;
    
    // world представляет собой список, содержащий все маршруты (решения) задачи
    private List<Way> ways; 
    
    Map(final int startSize,
             final List<City> cities) {
        this.startSize = startSize;
        this.ways = initialize(cities, startSize);
    }

    // метод должен создать начальное поколение
    private List<Way> initialize(final List<City> cities, final int startSize) {
        List<Way> map = new ArrayList<>();
        for (int i = 0; i < startSize; ++i) {
            Way way = Way.create(cities);
            map.add(way);
        }
        return map;
    }

    //  должен вернуть лучший маршрут из `world`
    public Way bestSolution() {
        return this.ways.get(0);
    }

    
    void update() {
        // метод должен создавать новые маршруты путем кроссовера (скрещивания) существующих маршрутов
        executeCrossover();
        // метод должен изменить маршрут случайным образом(мутация)
        executeMutation();
        // метод должен создавать новые маршруты после кроссовера и мутации
        executeCreation();
        // метод должен отбирать лучшие маршруты из `world` для следующего поколения
        executeSelection();
    }

    private void executeCrossover() {
        final List<Way> newWorld = new ArrayList<>();
        for (Way way : this.ways) {
            Way pair = getPair(way);
            newWorld.addAll(Arrays.asList(way.crossover(pair)));
        }
        newWorld.addAll(this.ways);
        this.ways = newWorld;
    }

    private void executeMutation() {
        final List<Way> newWorld = new ArrayList<>();
        for (int i = 0; i < this.ways.size()/10; i++) {
            Way newWay = this.ways.get(HelperForm.randomIndex(this.ways.size())).mutate();
            newWorld.add(newWay);
        }
        this.ways.addAll(newWorld);
    }

    private void executeCreation() {
        for (int i = 0; i < 1000; ++i) {
            this.ways.add(Way.create(HelperForm.cities));
        }    
    }

    private void executeSelection() {
        // Сортируем маршруты по расстоянию (от меньшего к большему)
        this.ways.sort(Comparator.comparingDouble(Way::calculateTotalDistance));
    
        // Создаем новый список для выбранных хромосом
        List<Way> selected = new ArrayList<>();
    
        // Выбираем первые startSize из отсортированного списка
        for (int i = 0; i < this.startSize; i++) {
            selected.add(this.ways.get(i));
        }
    
        // Заменяем существующую популяцию выбранными хромосомами
        this.ways = selected;
    }

    //метод выбирает случайный маршрут из списка маршрутов, после чего создаётся пара
    private Way getPair(Way way) {
        Way pair = this.ways.get(HelperForm.randomIndex(this.ways.size()));
        while (way == pair) {
            pair = this.ways.get(HelperForm.randomIndex(this.ways.size()));
        }
        return way;
    }
    

}
