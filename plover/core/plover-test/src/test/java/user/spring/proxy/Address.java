package user.spring.proxy;

public class Address implements IAddress {

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

    /* (non-Javadoc)
     * @see user.spring.proxy.IAddress#getCity()
     */
    @Override
    public String getCity() {
        return city;
    }

    /* (non-Javadoc)
     * @see user.spring.proxy.IAddress#setCity(java.lang.String)
     */
    @Override
    public void setCity(String city) {
        this.city = city;
    }

    /* (non-Javadoc)
     * @see user.spring.proxy.IAddress#getPincode()
     */
    @Override
    public String getPincode() {
        return pincode;
    }

    /* (non-Javadoc)
     * @see user.spring.proxy.IAddress#setPincode(java.lang.String)
     */
    @Override
    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    /* (non-Javadoc)
     * @see user.spring.proxy.IAddress#getStreet()
     */
    @Override
    public String getStreet() {
        return street;
    }

    /* (non-Javadoc)
     * @see user.spring.proxy.IAddress#setStreet(java.lang.String)
     */
    @Override
    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "Address [street=" + street + ", city=" + city + ", pincode=" + pincode + "]";
    }

}
