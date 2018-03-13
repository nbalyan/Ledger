package btventures.ledger;

import java.util.Date;

/**
 * Created by Anuj on 2/22/2018.
 */

public class Customer {

    private String name;
    private String account;
    private String Address;
    private String phone;
    private String cifno;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    private Date createdAt;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    private String accountType;

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    private String agentCode;

    public String getmAmount() {
        return mAmount;
    }

    public void setmAmount(String amount) {
        this.mAmount = amount;
    }

    private String mAmount;

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public String getName() {
        return name;
    }

    public Customer(){}

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
