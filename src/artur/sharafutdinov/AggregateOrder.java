package artur.sharafutdinov;

/**
 * Created by first on 10.09.17.
 */
public class AggregateOrder implements Comparable<AggregateOrder> {
    private int volume;
    private double price;

    public AggregateOrder(double price) {
        super();
        this.price = price;
    }

    public AggregateOrder(int volume, double price) {
        super();
        this.volume = volume;
        this.price = price;
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
    public int compareTo(AggregateOrder o) {
        return price > o.price ? 1 : price == o.price ? 0 : -1;
    }

    @Override
    public String toString() {
        return volume + " " + price;
    }
}
