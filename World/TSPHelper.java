package World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class TSPHelper {

    public static Random random = new Random(10000);

    final static TSPCity[] cities = data(60);

    private TSPHelper() {
        throw new RuntimeException("Неинициализируемый класс");
    }

    private static TSPCity[] data(final int num) {
        final TSPCity[] data = new TSPCity[num];
        for(int i = 0; i < num; i++) {
            data[i] = new TSPCity(TSPHelper.random(World.WIDTH),
                                  TSPHelper.random(World.HEIGHT));
        }
        return data;
    }

    static int random(final int limit) {
        return random.nextInt(limit);
    }
 
    //разделяет список на два списка, используя случайный индекс
    List<TSPCity>[] split(final List<T> list) {

        final List<T> first = new ArrayList<>();
        final List<T> second = new ArrayList<>();
        final int size = list.size();
        final int index = 1 + TSPHelper.randomIndex(list.size());

        for (int i = 0; i < size; i++) {
            if (i < (size + 1) / index) {
                first.add(list.get(i));
            } else {
                second.add(list.get(i));
            }
        }

        return (List<T>[]) new List[] {first, second};
    }


}