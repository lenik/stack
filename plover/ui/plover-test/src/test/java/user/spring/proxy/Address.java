package user.spring.proxy;

public class Address
        implements IAddress {

    private String street;

    private String city = "City1";
    private String pincode = "Pincode 1";

    public Address() {
        System.out.println("Address Constructor()");
        this.street = "No street";
    }

    public Address(String street) {
        System.out.println("Address Constructor(street)");
        this.street = street;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String getPincode() {
        return pincode;
    }

    @Override
    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    @Override
    public String getStreet() {
        return street;
    }

    @Override
    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "Address [street=" + street + ", city=" + city + ", pincode=" + pincode + "]";
    }

}
