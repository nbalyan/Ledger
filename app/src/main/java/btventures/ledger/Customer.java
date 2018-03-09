package btventures.ledger;

/**
 * Created by Anuj on 2/22/2018.
 */

public class Customer {

    private String name;
    private String account;
    private String Address;
    private String phone;

    public String getName() {
        return name;
    }

    public Customer(String name, String account, String address, String phone) {
        this.name = name;
        this.account = account;
        Address = address;
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
