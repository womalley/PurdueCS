import java.util.UUID;

/**
 * Created by bomal_000 on 2/28/2016.
 */
public class MP3 implements Sellable, Downloadable {
    public String getProductName() {
        return (productName);
    }

    public double getPrice() {
        double priceMP3 = (0.99);
        return (priceMP3);
    }

    public String generateDownloadCode() {
        String code;

        code = UUID.randomUUID().toString();
        return (code);
    }

    private String productName;

    public MP3(String productName) {
        this.productName = productName;
    }
}
