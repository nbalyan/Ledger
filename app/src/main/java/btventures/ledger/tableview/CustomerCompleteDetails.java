package btventures.ledger.tableview;

/**
 * Created by Anuj on 2/22/2018.
 */

public class CustomerCompleteDetails {

    private String name;
    private String account;
    private String Address;
    private String phone;
    private String amount;
    private String openingDate;
    private String code;
    private String cif;
    private String aadhar;
    private String pan_no;
    private String jointAccountName;
    private String jointAccountAadharNo;
    private String jointAccountCIF;
    private String nomination;
    private String accountType;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    private String objectID;

    public CustomerCompleteDetails(){}


    public CustomerCompleteDetails(String name, String account, String address, String phone) {
        this.name = name;
        this.account = account;
        Address = address;
        this.phone = phone;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(String openingDate) {
        this.openingDate = openingDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getPan_no() {
        return pan_no;
    }

    public void setPan_no(String pan_no) {
        this.pan_no = pan_no;
    }

    public String getJointAccountName() {
        return jointAccountName;
    }

    public void setJointAccountName(String jointAccountName) {
        this.jointAccountName = jointAccountName;
    }

    public String getJointAccountAadharNo() {
        return jointAccountAadharNo;
    }

    public void setJointAccountAadharNo(String jointAccountAadharNo) {
        this.jointAccountAadharNo = jointAccountAadharNo;
    }

    public String getJointAccountCIF() {
        return jointAccountCIF;
    }

    public void setJointAccountCIF(String jointAccountCIF) {
        this.jointAccountCIF = jointAccountCIF;
    }

    public String getNomination() {
        return nomination;
    }

    public void setNomination(String nomination) {
        this.nomination = nomination;
    }

    public String getName() {
        return name;
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
