package lab1;

public class Dot {
    private double x;
    private double y;

    public double getX() {
        return x;
    }

    public Dot setX(double x) {
        this.x = x;
        return this;
    }

    public double getY() {
        return y;
    }

    public Dot setY(double y) {
        this.y = y;
        return this;
    }

    public Dot(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
