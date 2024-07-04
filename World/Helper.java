package World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Helper {

    static final Random random = new Random(1000);

    static final City[] cities = generateData(100);

    private static final City[] generateData(final int num) {
        
        final City[] data =  new City[num];

        for (int i = 0; i < num; i++) {
            data[i] = new City(Helper.randomIndex(World.WIDTH), 
                                    Helper.randomIndex(World.HEIGHT));
        }

        return data;
    }

    private Helper() {
        throw new RuntimeException("Неинициализируемый класс");
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

    public static boolean isValid(List<City> crossover) {
        return crossover.size() == cities.length;
    }
    
}