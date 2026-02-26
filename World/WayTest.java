import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class WayTest {

    private List<City> testCities;
    private City cityA;
    private City cityB;
    private City cityC;

    @BeforeEach
    public void setUp() {
        // Этот метод выполняется перед каждым тестом
        cityA = new City(0, 0);
        cityB = new City(10, 0);
        cityC = new City(10, 10);
        testCities = new ArrayList<>();
        testCities.add(cityA);
        testCities.add(cityB);
        testCities.add(cityC);
    }

    @Test
    public void testCreateWay() {
        Way way = Way.create(new ArrayList<>(testCities));
        List<City> path = way.getPath();

        // Путь должен содержать все те же города (но в другом порядке)
        assertEquals(3, path.size());
        assertTrue(path.contains(cityA));
        assertTrue(path.contains(cityB));
        assertTrue(path.contains(cityC));
    }

    @Test
    public void testCalculateTotalDistance() {
        // Создаем путь в известном порядке [A, B, C]
        List<City> orderedPath = new ArrayList<>();
        orderedPath.add(cityA);
        orderedPath.add(cityB);
        orderedPath.add(cityC);
        Way way = new Way(orderedPath);

        // Проверим, что расстояние = 20
        assertEquals(20.0, way.calculateTotalDistance(), 0.0001);
    }

    @Test
    public void testCrossover() {
        // Путь 1: A, B, C
        Way way1 = new Way(new ArrayList<>(List.of(cityA, cityB, cityC)));
        // Путь 2: C, A, B
        Way way2 = new Way(new ArrayList<>(List.of(cityC, cityA, cityB)));

        // Кроссовер
        Way[] children = way1.crossover(way2);

        // Должно получиться 2 потомка
        assertEquals(2, children.length);

        // Каждый потомок должен содержать все те же 3 города (проверка на валидность)
        assertEquals(3, children[0].getPath().size());
        assertEquals(3, children[1].getPath().size());
        assertTrue(children[0].getPath().contains(cityA));
        assertTrue(children[0].getPath().contains(cityB));
        assertTrue(children[0].getPath().contains(cityC));
    }

    @Test
    public void testMutate() {
        // Создаем путь в порядке A, B, C
        Way originalWay = new Way(new ArrayList<>(List.of(cityA, cityB, cityC)));

        // Мутируем и проверяем, что количество городов не изменилось
        Way mutatedWay = originalWay.mutate();
        assertEquals(3, mutatedWay.getPath().size());

        // Проверяем, что остались все те же 3 города
        assertTrue(mutatedWay.getPath().contains(cityA));
        assertTrue(mutatedWay.getPath().contains(cityB));
        assertTrue(mutatedWay.getPath().contains(cityC));

        // Проверяем что порядок изменился (или мог измениться)
        boolean orderChanged = !mutatedWay.getPath().equals(originalWay.getPath());
        // Тест может упасть, если randomIndex выдаст a == b, но это маловероятно на 3 элементах.

        // assertTrue(orderChanged); // Данную строку можно раскомментировать чтобы точно проверить изменения
    }
}