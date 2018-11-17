/**
 * Created by bomal_000 on 2/28/2016.
 */
public class DVD implements Sellable {
    public String getProductName() {
        return (productName);
    }

    public double getPrice() {
        return (price);
    }

    public VideoType getType() {
        return (type);
    }

    private String productName;
    private VideoType type;
    private double price;

    public DVD(String productName, VideoType type, double price) {
        this.productName = productName;
        this.type = type;
        this.price = price;
    }
}
