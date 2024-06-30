package World;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TSPWorld {
    
    private final int startSize;
    
    // world представляет собой список, содержащий все маршруты (решения) задачи
    private List<TSPWay> world; 
    
    TSPWorld(final int startSize,
             final TSPWay[] cities) {
        this.startSize = startSize;
        this.world = initialize(cities, startSize);
    }

    // метод должен создать начальное поколение
    private List<TSPWay> initialize(final TSPCity[] cities, final int startSize2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initialize'");
    }

    //  должен вернуть лучший маршрут из `world`
    private TSPWay bestSolution() {
        return this.world.get(0);
    }

    
    private void update() {
        
        // метод должен создавать новые маршруты путем кроссовера (скрещивания) существующих маршрутов
        executeCrossover();
        
        // метод должен найти пару для кроссовера с маршрутом `way`
        executeMutation();
        
        // метод должен создавать новые маршруты.
        executeCreation();
        
        // метод должен отбирать лучшие маршруты из `world` для следующего поколения
        executeSelection();
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    private void executeCrossover() {
        final List<TSPWay> newWorld = new ArrayList<>();
        for (TSPWay way : this.world) {
            TSPWay pair = getPair(way);
            newWorld.addAll(Arrays.asList(way.crossover(pair)));
        }
        newWorld.addAll(this.world);
        this.world = newWorld;
    }

    private void executeMutation() {
        final List<TSPWay> newWorld = new ArrayList<>();
        for (int i = 0; i < this.world.size()/10; i++) {
            TSPWay newWay = this.world.get(TSPHelper.randomIndex(this.world.size())).mutate();
            newWorld.add(newWay);
        }
        this.world.addAll(newWorld);
    }

    private void executeCreation() {
        for (int i = 0; i < 1000; ++i) {
            this.world.add(TSPWay.create(TSPHelper.cities));
        }    
    }

    private void executeSelection() {
        // Сортируем популяцию по расстоянию (от меньшего к большему)
        this.world.sort(Comparator.comparingDouble(TSPWay::calculateTotalDistance));
    
        // Создаем новый список для выбранных хромосом
        List<TSPChromosome> selected = new ArrayList<>();
    
        // Выбираем первые startSize из отсортированного списка
        for (int i = 0; i < this.startSize; i++) {
            selected.add(this.world.get(i));
        }
    
        // Заменяем существующую популяцию выбранными хромосомами
        this.population = selectedPopulation;
    }

    private TSPWay getPair(TSPWay way) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPair'");
    }
    

}
