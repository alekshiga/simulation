package World;

public class City {
    
    private final int x;
    private final int y;

    City(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public final int getX() {
        return this.x;
    }

    public final int getY() {
        return this.y;
    }

    public final double getDistance(City other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    public final String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        City other = (City) obj;
        if (this.x != other.x || this.y != other.y) return false;
        
        else return true;
    }
}
