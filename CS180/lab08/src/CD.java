/**
 * Created by bomal_000 on 2/27/2016.
 */
public class CD implements Sellable {
    public String getProductName() {
        return (productName);
    }

    public double getPrice() {
        return (price);
    }

    public int getTotalSongs() {
        return (totalSongs);
    }

    private String productName;
    private int totalSongs;
    private double price;

    public CD(String productName, int totalSongs, double price) {
        this.productName = productName;
        this.totalSongs = totalSongs;
        this.price = price;
    }
}
