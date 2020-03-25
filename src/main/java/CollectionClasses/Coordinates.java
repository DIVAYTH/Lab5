package CollectionClasses;

public class Coordinates {
    private Integer x; //Поле не может быть null
    private double y;

    public Coordinates(Integer x, double y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return this.x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}