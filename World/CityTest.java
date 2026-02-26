import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CityTest {

    @Test
    public void testCityCreationAndGetters() {
        City city = new City(10, 20);
        assertEquals(10, city.getX());
        assertEquals(20, city.getY());
    }

    @Test
    public void testDistanceCalculation() {
        City city1 = new City(0, 0);
        City city2 = new City(3, 4);
        // Расстояние между (0,0) и (3,4) по теореме Пифагора = 5.0
        assertEquals(5.0, city1.getDistance(city2), 0.0001);
    }

    @Test
    public void testDistanceIsCommutative() {
        City city1 = new City(1, 1);
        City city2 = new City(4, 5);
        assertEquals(city1.getDistance(city2), city2.getDistance(city1), 0.0001);
    }

    @Test
    public void testEqualsAndHashCode() {
        City city1 = new City(5, 7);
        City city2 = new City(5, 7);
        City city3 = new City(8, 2);

        assertEquals(city1, city2); // Одинаковые координаты, значит города равны
        assertNotEquals(city1, city3); // Разные координаты, значит не равны
        assertEquals(city1.hashCode(), city2.hashCode()); // Хэш-коды тоже должны совпадать
    }

    @Test
    public void testToString() {
        City city = new City(15, 30);
        assertEquals("(15, 30)", city.toString());
    }
}