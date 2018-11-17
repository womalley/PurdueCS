/**
 * Created by bomal_000 on 2/22/2016.
 */
public class Contact {

    //Variables
    private String name;
    private long number;
    private String address;

    //Constructors
    public Contact (String name) {
        this.name = name;
        this.number = 0;
        this.address = null;
    }

    public Contact (String name, long number) {
        this.name = name;
        this.number = number;
        this.address = null;
    }

    public Contact (String name, long number, String address) {
        this.name = name;
        this.number = number;
        this.address = address;
    }

    //Methods
    public String getName(){
        return (name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNumber() {
        return (number);
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getAddress() {
        return (address);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean equals(Contact info) {
        if ((info.name).equals(this.name)) {
            return (true);
        }
        else {
            return (false);
        }
    }

}
