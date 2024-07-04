
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Map {
    
    private final int startSize;
    
    // world представляет собой список, содержащий все маршруты (решения) задачи
    private List<Way> world; 
    
    Map(final int startSize,
             final City[] cities) {
        this.startSize = startSize;
        this.world = initialize(cities, startSize);
    }

    // метод должен создать начальное поколение
    private List<Way> initialize(final City[] cities, final int startSize) {
        List<Way> map = new ArrayList<>();
        for (int i = 0; i < startSize; ++i) {
            Way way = Way.create(cities);
            map.add(way);
        }
        return map;
    }

    //  должен вернуть лучший маршрут из `world`
    public Way bestSolution() {
        return this.world.get(0);
    }

    
    void update() {
        // метод должен создавать новые маршруты путем кроссовера (скрещивания) существующих маршрутов
        executeCrossover();
        // метод должен найти пару для кроссовера с маршрутом `way`
        executeMutation();
        // метод должен создавать новые маршруты.
        executeCreation();
        // метод должен отбирать лучшие маршруты из `world` для следующего поколения
        executeSelection();
    }

    private void executeCrossover() {
        final List<Way> newWorld = new ArrayList<>();
        for (Way way : this.world) {
            Way pair = getPair(way);
            newWorld.addAll(Arrays.asList(way.crossover(pair)));
        }
        newWorld.addAll(this.world);
        this.world = newWorld;
    }

    private void executeMutation() {
        final List<Way> newWorld = new ArrayList<>();
        for (int i = 0; i < this.world.size()/10; i++) {
            Way newWay = this.world.get(Helper.randomIndex(this.world.size())).mutate();
            newWorld.add(newWay);
        }
        this.world.addAll(newWorld);
    }

    private void executeCreation() {
        for (int i = 0; i < 1000; ++i) {
            this.world.add(Way.create(Helper.cities));
        }    
    }

    private void executeSelection() {
        // Сортируем маршруты по расстоянию (от меньшего к большему)
        this.world.sort(Comparator.comparingDouble(Way::calculateTotalDistance));
    
        // Создаем новый список для выбранных хромосом
        List<Way> selected = new ArrayList<>();
    
        // Выбираем первые startSize из отсортированного списка
        for (int i = 0; i < this.startSize; i++) {
            selected.add(this.world.get(i));
        }
    
        // Заменяем существующую популяцию выбранными хромосомами
        this.world = selected;
    }

    private Way getPair(Way way) {
        Way pair = this.world.get(Helper.randomIndex(this.world.size()));
        while (way == pair) {
            pair = this.world.get(Helper.randomIndex(this.world.size()));
        }
        return way;
    }
    

}
