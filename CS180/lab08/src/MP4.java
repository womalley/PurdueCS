import java.util.UUID;

/**
 * Created by bomal_000 on 2/28/2016.
 */
public class MP4 implements Sellable, Downloadable {
    public String getProductName() {
        return (productName);
    }

    public double getPrice() {
        double price;

        if (type.toString().equals("MOVIE")) {
            price = (4.99);
            return (price);
        }
        else {
            price = (19.99);
            return (price);
        }
    }

    public String generateDownloadCode() {
        String code;

        code = UUID.randomUUID().toString();
        return (code);
    }

    public VideoType getType() {
        return (type);
    }

    private String productName;
    private VideoType type;

    public MP4(String productName, VideoType type) {
        this.productName = productName;
        this.type = type;
    }

}
