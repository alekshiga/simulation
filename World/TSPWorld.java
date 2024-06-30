package World;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TSPWorld {
    
    private final int startSize;
    
    // world представляет собой список, содержащий все маршруты (решения) задачи
    private List<TSPList> world; 
    
    TSPWorld(final int startSize,
             final TSPCity[] cities) {
        this.startSize = startSize;
        this.world = initialize(cities, startSize);
    }

    // метод должен создать начальное поколение
    private List<TSPList> initialize(final TSPCity[] cities, final int startSize2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initialize'");
    }

    //  должен вернуть лучший маршрут из `world`
    private TSPList bestSolution() {
        return this.world.get(0);
    }

    
    private void update() {
        
        // метод должен создавать новые маршруты путем кроссовера (скрещивания) существующих маршрутов
        executeCrossover();
        
        // этот метод должен найти пару для кроссовера с маршрутом `way`
        executeMutation();
        
        // метод должен создавать новые маршруты.
        executeCreation();
        
        // метод должен отбирать лучшие маршруты из `world` для следующего поколения
        executeSelection();
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    private void executeSelection() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'executeSelection'");
    }

    private void executeCreation() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'executeCreation'");
    }

    private void executeMutation() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'executeMutation'");
    }

    private void executeCrossover() {
        final List<TSPList> newWorld = new ArrayList<>();
        for (TSPList way : this.world) {
            TSPList pair = getPair(way);
            newWorld.addAll(Arrays.asList(way.crossover(pair)));
        }
        newWorld.addAll(this.world);
        this.world = newWorld;
    }

    private TSPList getPair(TSPList way) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPair'");
    }
    

}
