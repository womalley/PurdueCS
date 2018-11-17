import java.util.Arrays;

/**
 * The MusicStore class models a Music Store where you can add, remove, or get products and sort them
 * by price in ascending or descending order.
 */
public class MusicStore {
    private int totalProducts;
    private int maxProducts;
    private Sellable[] storeProducts;

    /**
     * Constructor. Initializes this MusicStore with 0 products, a max capacity of 10 products, and 
     * an empty array of Sellable products of size 10.
     */
    public MusicStore() {
        maxProducts = 10;
        totalProducts = 0;

        storeProducts = new Sellable[maxProducts];
    }
	
    /**
     * Adds the specified Sellable product to this MusicStore. If the product already exists, then 
     * the method returns false and no changes are made. If the product does not already exist, a check
     * is made if the totalProducts is equal to the maxProducts and the array capacity is double if
     * needed before the new product is added.
     * 
     * @param product The Sellable product to add to this MusicStore
     * @return true if the product was added, false otherwise
     */
    public boolean addProduct(Sellable product) {
        int i;

        for (i = 0; i < totalProducts; i++) {
            if (storeProducts[i] == product) {
                return (false);
            }
        }

        if (maxProducts == totalProducts) {

            maxProducts *= 2;
            Sellable[] tmp = new Sellable[maxProducts];

            for (int k = 0; k < totalProducts; k++) {
                tmp[k] = storeProducts[k];

            }

            storeProducts = new Sellable[maxProducts];

            for (int k = 0; k < totalProducts; k++) {
                storeProducts[k] = tmp[k];
            }
        }

        System.out.println(totalProducts);
        storeProducts[totalProducts] = product;

        totalProducts++;

        return (true);
    }
	
    /**
     * Removes the product given by the name argument from this MusicStore and moves all products that 
     * follow it in the storeProducts array to the left so that there are no empty entries between
     * products. For example:
     * 
     * before -> {S1, S2, S3, S4, S5, S6, S7, S8, S9, null}
     * remove S6
     * after -> {S1, S2, S3, S4, S5, S7, S8, S9, null, null}
     * 
     * @param name The name of the product to remove from this MusicStore
     * @return true if the product was removed, false otherwise
     */
    public boolean removeProductByName(String name) {
        int i;
        int k;
        int loc = 0; //location
        boolean found = false; //checks to see if there are two names, and ultimately will delete doubles

        for (i = 0; i < totalProducts; i++) {
            if (storeProducts[i].getProductName().equals(name)) {
                loc = i;
                found = (true);
            }
        }

        if (!found) {  //found name
            return (false);
        }

        for (k = loc; k < totalProducts; k++) {

            storeProducts[k] = storeProducts[k + 1];
        }
        storeProducts[totalProducts - 1] = null;
        totalProducts -= 1;

        return (true);
    }
	
    /**
     * Gets the product from the list of Sellable products of this MusicStore by the given name.
     * 
     * @param product The name of the product to get
     * @return The product with the given name, or null if the product is not found
     */
    public Sellable getProductByName(String product) {
        int i;

        for (i = 0; i < totalProducts; i++) {
            if (storeProducts[i] != null && product.equals(storeProducts[i].getProductName())) {
                return storeProducts[i];
            }
        }return (null);
    }
	
    /**
     * Sorts all the Sellable products in this MusicStore by price. The ascending argument
     * specifies if the the products should be sorted in ascending or descending order. You
     * are free to use whichever sorting algorithm you choose from any outside resources
     * as long as the storeProducts array is sorted correctly as specified by the argument.
     * 
     * @param ascending True to sort ascending by price, false to sort descending by price
     */
    public void sortAllProductsByPrice(boolean ascending) {

        if (ascending) {

            Sellable temp;
            for (int i = 0; i < totalProducts - 1; i++) {

                for (int j = 1; j < totalProducts - i; j++) {
                    if (storeProducts[j - 1].getPrice() > storeProducts[j].getPrice()) {
                        temp = storeProducts[j - 1];
                        storeProducts[j - 1] = storeProducts[j];
                        storeProducts[j] = temp;
                    }
                }
            }
        }
        else {

            Sellable temp;
            for (int i = 0; i < totalProducts - 1; i++) {

                for (int j = 1; j < totalProducts - i; j++) {
                    if (storeProducts[j - 1].getPrice() < storeProducts[j].getPrice()) {
                        temp = storeProducts[j - 1];
                        storeProducts[j - 1] = storeProducts[j];
                        storeProducts[j] = temp;
                    }
                }
            }
        }
    }
	
    /**
     * Checks if a given Sellable product is already in this MusicStore.
     * 
     * @param product The product to find
     * @return true if the product is found, false otherwise
     */
    private boolean contains(Sellable product) {
        int i;

        for (i = 0; i < totalProducts; i++) {
            if (product == (storeProducts[i])) {
                return (true);
            }
        }return (false);
    }
	
    
    /**
     * Checks if a Sellable product with the given name is already in this MusicStore.
     * 
     * @param name The name of the product to find
     * @return true if the product is found, false otherwise
     */
    private boolean contains(String name) {
        int i;

        for (i = 0; i < totalProducts; i++) {
            if (name.equals(storeProducts[i].getProductName())) {
                return (true);
            }
        }return (false);
    }
	
    /**
     * Prints all the products in this MusicStore. This method is complete and should not be modified.
     */
    public void printProducts() {
	for (int i = 0; i < this.totalProducts; i++) {
	    Sellable temp = this.storeProducts[i];
	    if (temp instanceof CD) {
		System.out.printf("CD Name:\t%s%n", temp.getProductName());
		System.out.printf("Total Songs:\t%d%n", ((CD) temp).getTotalSongs());
		System.out.printf("Price:\t\t%.2f%n", temp.getPrice());
	    } else if (temp instanceof DVD) {
		System.out.printf("DVD Name:\t%s%n", temp.getProductName());
		System.out.printf("Type:\t\t%s%n", ((DVD) temp).getType());
		System.out.printf("Price:\t\t%.2f%n", temp.getPrice());
	    } else if (temp instanceof MP3) {
		System.out.printf("MP3 Name:\t%s%n", temp.getProductName());
		System.out.printf("Price:\t\t%.2f%n", temp.getPrice());
	    } else {
		System.out.printf("MP4 Name:\t%s%n", temp.getProductName());
		System.out.printf("Type:\t\t%s%n", ((MP4) temp).getType());
		System.out.printf("Price:\t\t%.2f%n", temp.getPrice());
	    }
		System.out.println();
	}
    }
}