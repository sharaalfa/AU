package artur.sharafutdinov;

/**
 * Created by first on 09.09.17.
 */
public class Order implements Comparable<Order> {
    private String type;
    private int volume;
    private double price;

    public Order(double price) {
        super();
        this.price = price;
    }

    public Order(String type,  int volume, double price) {
        super();
        this.type = type;
        this.volume = volume;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Override
    public int compareTo(Order o) {
        return Double.compare(price, o.price);
    }

    @Override
    public String toString() {
        return type + " " + volume + " " + price;
    }
}
